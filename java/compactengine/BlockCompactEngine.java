package compactengine;

import java.util.List;

import buildcraft.energy.BlockEngine;
import buildcraft.energy.TileEngine;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCompactEngine extends BlockEngine
{
	private static IIcon woodTexture;

    public BlockCompactEngine()
    {
        super();
        this.setResistance(10.0f);
        setBlockName("CompactEngine:CompactEngineWood");
        setBlockTextureName("buildcraft:engineWoodBottom");
    }
//	@Override
//	public TileEntity createNewTileEntity(World world) {
//		return null;
//	}
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
    	else
    		return new TileCompactEngine512();
    }
    @Override
	public void getSubBlocks(Item blockid, CreativeTabs par2CreativeTabs, List arraylist)
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

		if(entityplayer.getCurrentEquippedItem() != null )
		{
			Item itemID = entityplayer.getCurrentEquippedItem().getItem();

			if (entityplayer.capabilities.isCreativeMode && itemID == Items.blaze_rod)
			{
//				tileengine.energy += tileengine.getMaxEnergy() / 8;
                tileengine.heat += (TileEngine.MAX_HEAT - TileEngine.MIN_HEAT) / 8;
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

//    public String getBlockName()
//    {
//        return "tile.CompactEngineWood";
//    }

//	@Override
//	@SideOnly(Side.CLIENT)
//	public void registerBlockIcons(IIconRegister par1IconRegister)
//	{
//		woodTexture = par1IconRegister.registerIcon("buildcraft:engineWoodBottom");
//	}
//
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return woodTexture;
	}

}
