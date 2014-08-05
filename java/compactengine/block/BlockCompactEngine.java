package compactengine.block;

import buildcraft.energy.BlockEngine;
import buildcraft.energy.TileEngine;
import compactengine.CompactEngine;
import compactengine.tileentity.TileCompactEngine128;
import compactengine.tileentity.TileCompactEngine32;
import compactengine.tileentity.TileCompactEngine512;
import compactengine.tileentity.TileCompactEngine8;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockCompactEngine extends BlockEngine
{
	private static IIcon woodTexture;

//    public BlockCompactEngine()
//    {
//        super();
//        this.setResistance(10.0f);
//        setBlockName("CompactEngine:CompactEngineWood");
//        setBlockTextureName("buildcraft:engineWoodBottom");
//    }

    @Override
    public TileEntity createTileEntity(World world, int metadata)
    {
//    	return new TileCompactEngine(metadata);
    	if(metadata == 0)
    		return new TileCompactEngine8();
    	else if(metadata == 1)
    		return new TileCompactEngine32();
    	else if(metadata == 2)
    		return new TileCompactEngine128();
    	else/* if (metadata == 3)*/
    		return new TileCompactEngine512();
//        else
//            return new TileCompactEngine2048();
    }
    @Override
    @SuppressWarnings("unchecked")
	public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List arraylist)
	{
        arraylist.add(new ItemStack(this, 1, 0));
        arraylist.add(new ItemStack(this, 1, 1));
        arraylist.add(new ItemStack(this, 1, 2));
		if(CompactEngine.isAddCompactEngine512and2048)
		{
	        arraylist.add(new ItemStack(this, 1, 3));
//	        arraylist.add(new ItemStack(this, 1, 4));
		}
//        arraylist.add(new ItemStack(this, 1, 5));
	}

    @Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
    {
        TileEngine tileengine = (TileEngine)world.getTileEntity(x, y, z);

		if(!world.isRemote && entityplayer.getCurrentEquippedItem() != null )
		{
			Item itemID = entityplayer.getCurrentEquippedItem().getItem();

			if (entityplayer.capabilities.isCreativeMode && itemID == Items.blaze_rod)
			{
				tileengine.energy += tileengine.getMaxEnergy() / 8;
//                tileengine.heat += (TileEngine.MAX_HEAT - TileEngine.MIN_HEAT);
                entityplayer.addChatMessage(new ChatComponentText("Heat Up!"));
				return true;
			}
		}

		return super.onBlockActivated(world, x, y, z, entityplayer, par6, par7, par8, par9);

    }

	@Override
	public void onPostBlockPlaced(World world, int x, int y, int z, int par5) {
//		TileEngine tile = (TileEngine) world.getBlockTileEntity(x, y, z);
//		tile.orientation = ForgeDirection.UP;
//		tile.switchOrientation();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		woodTexture = par1IconRegister.registerIcon("buildcraft:engineWoodBottom");
	}
//
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return woodTexture;
	}

}
