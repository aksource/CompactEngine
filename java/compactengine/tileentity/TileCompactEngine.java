package compactengine.tileentity;

import buildcraft.api.transport.IPipeTile.PipeType;
import buildcraft.core.lib.engines.TileEngineBase;
import compactengine.CompactEngine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class TileCompactEngine extends TileEngineBase {

    public static final float OUTPUT = 8f / 20f * 1.25f;
    public static final float HEAT_BASE = 0.025F;
//    public static final ResourceLocation Compact1_BASE_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood1.png");
//    public static final ResourceLocation Compact2_BASE_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood2.png");
//    public static final ResourceLocation Compact3_BASE_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood3.png");
//    public static final ResourceLocation Compact4_BASE_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood4.png");
//    public static final ResourceLocation Compact5_BASE_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood5.png");
//    public static final ResourceLocation[] Res = new ResourceLocation[]{Compact1_BASE_TEXTURE, Compact2_BASE_TEXTURE, Compact3_BASE_TEXTURE, Compact4_BASE_TEXTURE, Compact5_BASE_TEXTURE};
    public int power;        //1tickごとのエネルギー生産量、圧縮レベル÷20ｘ1.25（赤ピストンで釣り合うように）
    public int no;                //テクスチャ番号
    public int level;            //圧縮レベル8〜2048
    public int limitTime;        //爆発までの猶予tick
    public int time = 0;        //爆発破カウンター
    public int alertTime = 0;    //爆発警告タイマー設定
    public double stageRed;    //赤ピストンの閾値
    public static final int explosionPower = CompactEngine.CompactEngineExplosionPowerLevel;
    public static final int explosionTime = CompactEngine.CompactEngineExplosionTimeLevel;
    public static final int alert = CompactEngine.CompactEngineExplosionAlertMinute;
    public static final int[] powerLevel = {8, 32, 128, 512, 2048};
    public static final int[][] explosionRanges = {        //爆発力定数、x=Level、y＝爆発力設定レベル
            {2, 4, 6, 10, 16},        //低い
            {3, 6, 10, 16, 32},        //標準
            {4, 8, 16, 32, 128},        //災害
            {8, 32, 128, 512, 2048}};    //崩壊（ワールドに入れなくなる危険あり）
    public static final int[][] explosionTimes = {        //爆発時間定数、x=Level、y＝爆発時間設定レベル
            {120, 90, 60, 30, 15},        //長い
            {30, 30, 30, 10, 5},        //標準
            {15, 15, 15, 5, 3},        //短い
            {5, 5, 5, 3, 1}};    //無理

    public TileCompactEngine(int meta) {
        super();
        this.initCompactEngine(meta);
    }

    public void initCompactEngine(int meta) {
        level = powerLevel[meta];
        no = meta;
        power = MathHelper.ceiling_float_int(level * 0.5F);
        this.limitTime = (explosionTimes[explosionTime][meta] * 20 * 60);
        this.time = this.limitTime;
        alertTime = alert * 60 * 20;
        this.stageRed = (250.0D * this.level);
    }

    @Override
    public ResourceLocation getBaseTexture() {
        return CompactEngine.RESOURCE_LOCATION_LIST.get(no)/*Res[no]*/;
    }

    @Override
    public ResourceLocation getChamberTexture() {
        return super.getChamberTexture();
    }

//    @Override
//    public String getTexturePrefix() {
//        return "buildcraftcore:textures/blocks/engineWood";
//    }

    //	@Override
    public float getExplosionRange() {
        return explosionRanges[explosionPower][no];
    }

//    @Override
//    public int minEnergyReceived() {
//        return 0;
//    }
//
//    @Override
//    public int maxEnergyReceived() {
//        return 50 * level;
//    }

//    @Override
//	public float getHeatLevel() {
//		return this.energy >= this.getMaxEnergy() - this.stageRed ?
//				(int)(this.getMaxEnergy() - this.stageRed * ((double)this.time / this.limitTime)) : (int)this.energy;
//	}

    @Override
    public void updateHeat() {
//        super.updateHeat();
        if (!isRedstonePowered) {
            this.heat -= HEAT_BASE * 20;
        }
    }

    @Override
    protected EnergyStage computeEnergyStage() {
        double energyLevel = getHeatLevel();
        if (energyLevel < 0.25d)
            return EnergyStage.BLUE;
        else if (energyLevel < 0.5d)
            return EnergyStage.GREEN;
        else if (energyLevel < 0.75d)
            return EnergyStage.YELLOW;
        else if (energyLevel < 1.0d || time > 0) {
            return EnergyStage.RED;
        } else {
            //爆発メッセージ表示
            CompactEngine.addChat(StatCollector.translateToLocal("engine.explode")
                    , getExplosionRange(), xCoord, yCoord, zCoord);
            return EnergyStage.OVERHEAT;
        }
    }

    @Override
    public float getPistonSpeed() {
        if (!worldObj.isRemote)
            return Math.max(0.8f * getHeatLevel(), 0.01f);
        switch (getEnergyStage()) {
            case BLUE:
                return 0.02F;
            case GREEN:
                return 0.04F;
            case YELLOW:
                return 0.08F;
            case RED:
                return 0.16F;
            default:
                return 0;
        }
    }

    @Override
    public void engineUpdate() {
        super.engineUpdate();
        //スイッチがオフの時、20倍の速さでエンジンが冷える
        if (!isRedstonePowered/* && energy > power * 20*/) {
//            energy -= power * 20;
            this.time += this.time * 20;
            if (this.time > this.limitTime) this.time = this.limitTime;
        }

        //スイッチがオンの時
        if (isRedstonePowered) {
//            energy += power;
            //赤ピストン時の処理
            if (getHeatLevel() >= 1.0F /*this.energy >= this.getMaxEnergy() * 0.75D*/ && !CompactEngine.neverExplosion) {
                //爆発カウントダウン
                time--;
                if (alert != 0 && time == alertTime) {
                    CompactEngine.addChat(StatCollector.translateToLocal("engine.alert")
                            , level, alert, xCoord, yCoord, zCoord);
                }
/*                if (time <= 0 || energy > getMaxEnergy() + power) {

                    //エネルギーステージ判定
                    computeEnergyStage();

                    //爆発メッセージ表示
                    CompactEngine.addChat(StatCollector.translateToLocal("engine.explode")
                            , getExplosionRange(), xCoord, yCoord, zCoord);
                    //エネルギーステージ取得メソッド経由で、BCの爆発処理を呼び出す
                    getEnergyStage();
                }*/
            } else {
                this.time = this.limitTime;
            }
        }

        this.heat = MathHelper.clamp_float(this.heat, MIN_HEAT, MAX_HEAT);
    }

    @Override
    public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
        return (type == PipeType.POWER) ? ConnectOverride.CONNECT : ConnectOverride.DISCONNECT;
    }

    @Override
    public boolean isBurning() {
        return isRedstonePowered;
    }

    @Override
    protected void burn() {
        if (this.isRedstonePowered) {
            int output = power;
            currentOutput = output; // Comment out for constant power
/*            if (time <= 0 || energy > getMaxEnergy() + power) {
                //エネルギーステージ判定
                computeEnergyStage();
                //爆発メッセージ表示
                CompactEngine.addChat(StatCollector.translateToLocal("engine.explode")
                        , getExplosionRange(), xCoord, yCoord, zCoord);
                getEnergyStage();
            }*/
            this.heat += HEAT_BASE;
            addEnergy(output);
        }
    }

    @Override
    public void overheat() {
        this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.createExplosion(null, this.xCoord, this.yCoord, this.zCoord, getExplosionRange(), true);
    }

    @Override
    public int getMaxEnergy() {
        return 100 * level * 20;
    }

//    @Override
//    public int calculateCurrentOutput() {
//        return power;
//    }

//    @Override
//    public int maxEnergyExtracted() {
//        return level * 20;
//    }

    //爆発タイマーをNBTに保存／呼び出し
    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.time = nbttagcompound.getInteger("time");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("time", this.time);
    }

    //将来的に機能拡張する際の予備、現在は未使用
    public int getTime() {
        return this.time;
    }

    @Override
    public int getIdealOutput() {
        return 0;
    }
}
