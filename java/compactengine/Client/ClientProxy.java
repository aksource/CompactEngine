package compactengine.Client;

import compactengine.tileentity.TileCompactEngine;
import net.minecraft.world.World;
import buildcraft.core.render.RenderingEntityBlocks;
import buildcraft.core.render.RenderingEntityBlocks.EntityRenderIndex;
import buildcraft.energy.TileEngine;
import buildcraft.energy.render.RenderEngine;

import compactengine.CommonProxy;
import compactengine.CompactEngine;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderInformation(){}

	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileCompactEngine.class, new RenderEngine());
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 0), new RenderEngine(TileCompactEngine.Compact1_BASE_TEXTURE, TileEngine.CHAMBER_TEXTURES[0], TileEngine.TRUNK_BLUE_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 1), new RenderEngine(TileCompactEngine.Compact2_BASE_TEXTURE, TileEngine.CHAMBER_TEXTURES[0], TileEngine.TRUNK_BLUE_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 2), new RenderEngine(TileCompactEngine.Compact3_BASE_TEXTURE, TileEngine.CHAMBER_TEXTURES[0], TileEngine.TRUNK_BLUE_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 3), new RenderEngine(TileCompactEngine.Compact4_BASE_TEXTURE, TileEngine.CHAMBER_TEXTURES[0], TileEngine.TRUNK_BLUE_TEXTURE));
//		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 4), new RenderEngine(TileCompactEngine.CompactEngine.TEX + "base_wood5.png"));
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}