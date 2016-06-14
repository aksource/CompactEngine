package compactengine.Client;

import buildcraft.core.lib.engines.RenderEngine;
import buildcraft.core.lib.engines.TileEngineBase;
import buildcraft.core.render.RenderingEntityBlocks;
import buildcraft.core.render.RenderingEntityBlocks.EntityRenderIndex;

import compactengine.CommonProxy;
import compactengine.CompactEngine;
import compactengine.tileentity.TileCompactEngine;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {
    private static ResourceLocation chamberResourceLocation = new ResourceLocation("buildcraftenergy:textures/blocks/engineStone/chamber.png");
    @Override
    public void registerRenderInformation() {
    }

    @Override
    public void registerTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileCompactEngine.class, new RenderEngine());
        for (int i = 0;i < 5; i++) {
            RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, i), new RenderEngine(CompactEngine.RESOURCE_LOCATION_LIST.get(i), chamberResourceLocation, TileEngineBase.TRUNK_BLUE_TEXTURE));
        }
//        RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 1), new RenderEngine(TileCompactEngine.Compact2_BASE_TEXTURE, chamberResourceLocation, TileEngineBase.TRUNK_BLUE_TEXTURE));
//        RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 2), new RenderEngine(TileCompactEngine.Compact3_BASE_TEXTURE, chamberResourceLocation, TileEngineBase.TRUNK_BLUE_TEXTURE));
//        RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 3), new RenderEngine(TileCompactEngine.Compact4_BASE_TEXTURE, chamberResourceLocation, TileEngineBase.TRUNK_BLUE_TEXTURE));
//		RenderingEntityBlocks.blockByEntityRenders.put(new EntityRenderIndex(CompactEngine.engineBlock, 4), new RenderEngine(TileCompactEngine.CompactEngine.TEX + "base_wood5.png"));
    }

    @Override
    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }
}