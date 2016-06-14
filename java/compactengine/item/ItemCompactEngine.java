package compactengine.item;

import buildcraft.api.core.Position;

import buildcraft.core.lib.engines.ItemEngine;
import buildcraft.core.lib.engines.TileEngineBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemCompactEngine extends ItemEngine {
    public ItemCompactEngine(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage() + 1;
        return (meta > 5) ? null : "tile.CompactEngineWood.level_" + meta;
    }

    @Override
    public String getItemStackDisplayName(ItemStack par1ItemStack) {
        return StatCollector.translateToLocal(this.getUnlocalizedName(par1ItemStack));
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        Block blockID = this.field_150939_a;
        if (!world.setBlock(x, y, z, blockID, metadata, 3)) {
            return false;
        }
        if (!(world.getTileEntity(x, y, z) instanceof TileEngineBase)) {
            return false;
        }
        TileEngineBase tileengine = (TileEngineBase) world.getTileEntity(x, y, z);
        tileengine.orientation = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[side]);
        ForgeDirection o = ForgeDirection.values()[ForgeDirection.OPPOSITES[side]];
        Position pos = new Position(tileengine.xCoord, tileengine.yCoord, tileengine.zCoord, o);
        pos.moveForwards(1);
        TileEntity tile = world.getTileEntity((int) pos.x, (int) pos.y, (int) pos.z);
        if (!tileengine.isPoweredTile(tile, o)) {
            tileengine.switchOrientation(false);
        }

        if (world.getBlock(x, y, z) == blockID) {
            blockID.onBlockPlacedBy(world, x, y, z, player, stack);
            blockID.onPostBlockPlaced(world, x, y, z, metadata);
        }

        return true;
    }
}
