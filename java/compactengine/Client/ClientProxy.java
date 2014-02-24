package compactengine.Client;

import net.minecraft.util.ResourceLocation;
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

	public static final ResourceLocation Compact1_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood1.png");
	public static final ResourceLocation Compact2_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood2.png");
	public static final ResourceLocation Compact3_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood3.png");
	public static final ResourceLocation Compact4_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood4.png");
	public static final ResourceLocation Compact5_TEXTURE = new ResourceLocation("compactengine", "textures/blocks/base_wood5.png");
	@Override
	public void registerTileEntitySpecialRenderer()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEngine.class, new RenderEngine());
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 0), new RenderEngine(Compact1_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 1), new RenderEngine(Compact2_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 2), new RenderEngine(Compact3_TEXTURE));
		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 3), new RenderEngine(Compact4_TEXTURE));
//		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 4), new RenderEngine(CompactEngine.TEX + "base_wood5.png"));
	}

	@Override
	public World getClientWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}