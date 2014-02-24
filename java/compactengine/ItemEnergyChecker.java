package compactengine;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.core.CreativeTabBuildCraft;
import buildcraft.energy.TileEngine;
import buildcraft.transport.PipeTransport;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.transport.pipes.PipePowerWood;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class ItemEnergyChecker extends Item
{
	public ItemEnergyChecker()
	{
		super();
		setCreativeTab(CreativeTabBuildCraft.MACHINES.get());
	}
    @Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			Block id = world.getBlock(x, y, z);
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile == null)
				return false;
			if(tile instanceof TileEngine)
			{
				TileEngine engine = (TileEngine)tile;
				double mjt = engine.maxEnergyExtracted() / (1.28f / engine.getPistonSpeed());
//				float heat = engine.getHeat();
//				if(heat == 0){
//					heat = (int)(engine.energy / engine.getMaxEnergy() * 100);
//				}else{
//					heat = (int)(heat / 10);
//				}

				CompactEngine.addChat(
					I18n.format("energyChecker.maxPower") + ": %1.2f MJ/t  " +
							I18n.format("energyChecker.energy") + ": %1.0f / %d MJ  " +
							I18n.format("energyChecker.heat") + ": %d\u00B0C / %d\u00B0C"
					, engine.getCurrentOutput(), engine.getEnergyStored(), MathHelper.ceiling_double_int(engine.getMaxEnergy()), MathHelper.ceiling_float_int(engine.getHeat()), MathHelper.ceiling_double_int(TileEngine.MAX_HEAT));
				return true;
			}
			else if(tile instanceof IPowerReceptor && (
					((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.DOWN) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.UP) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.NORTH) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.SOUTH) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.WEST) != null
					|| ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.EAST) != null))
			{
				PowerReceiver receiver;
                for (int i = 0; i < 6; i++) {
                    if(((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.getOrientation(i)) != null) {
                        receiver = ((IPowerReceptor)tile).getPowerReceiver(ForgeDirection.getOrientation(i));
                        CompactEngine.addChat(
                                I18n.format("energyChecker.energy") + ": %1.2f / %d MJ  " +
                                        I18n.format("energyChecker.workEnergy") + ": %d MJ",
                                receiver.getEnergyStored(), MathHelper.ceiling_double_int(receiver.getMaxEnergyStored()), MathHelper.ceiling_double_int(receiver.getMaxEnergyReceived()));
                    }
                }
				return true;
			}
//			else if(tile instanceof TileGenericPipe)
//			{
//				if(((TileGenericPipe)tile).pipe instanceof PipePowerWood)
//				{
//					CompactEngine.addChat(
//							I18n.format("energyChecker.energy") + ": 10000 / 10000 MJ");
//					return true;
//				}
//
//				PipeTransport transport = ((TileGenericPipe)tile).pipe.transport;
//				if(transport instanceof PipeTransportPower)
//				{
//					double PowerMax = 0;
//					float[] internalPower = ((PipeTransportPower)transport).internalNextPower;
//					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
//					internalPower = ObfuscationReflectionHelper.getPrivateValue(PipeTransportPower.class, (PipeTransportPower)transport, 13);
//					for(int i = 0; i < 6; i++){if(PowerMax < internalPower[i]) PowerMax = internalPower[i];}
//					CompactEngine.addChat(
//							I18n.format("energyChecker.pipeEnergy") + ": %1.4f / 10000 MJ", PowerMax);
//					return true;
//				}
//			}
			return false;
		}
		return false;
	}

}
