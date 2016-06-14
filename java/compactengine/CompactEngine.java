package compactengine;

import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftSilicon;
import buildcraft.transport.gates.GateDefinition;
import buildcraft.transport.gates.ItemGate;
import compactengine.block.BlockCompactEngine;
import compactengine.item.ItemCompactEngine;
import compactengine.tileentity.*;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod(modid = CompactEngine.MOD_ID,
        name = CompactEngine.MOD_NAME,
        version = CompactEngine.MOD_VERSION,
        dependencies = CompactEngine.MOD_DEPENDENCIES,
        useMetadata = true)
public class CompactEngine {
    public static final String MOD_ID = "CompactEngine";
    public static final String MOD_NAME = "CompactEngine";
    public static final String MOD_VERSION = "@VERSION@";
    public static final String MOD_DEPENDENCIES = "required-after:BuildCraft|Energy";
    public static final List<String> TEXTURE_STRING_LIST = Arrays.asList(
            "base_wood1",
            "base_wood2",
            "base_wood3",
            "base_wood4",
            "base_wood5");
    public static final List<ResourceLocation> RESOURCE_LOCATION_LIST = new ArrayList<>();

    @SidedProxy(clientSide = "compactengine.Client.ClientProxy", serverSide = "compactengine.CommonProxy")
    public static CommonProxy proxy;

    public static Block engineBlock;
    public static Item engineItem;
    public static Item energyChecker;
//	private static Fluid buildcraftFluidOil;

    //	public static int blockID_CompactEngine;
//	public static int itemID_energyChecker;
    public static boolean isAddCompactEngine512and2048;
    public static int CompactEngineExplosionPowerLevel;
    public static int CompactEngineExplosionTimeLevel;
    public static int CompactEngineExplosionAlertMinute;
    public static boolean neverExplosion;
//	public static int OilFlowingSpeed;

    public static ItemStack engine1;
    public static ItemStack engine2;
    public static ItemStack engine3;
    public static ItemStack engine4;
    public static ItemStack engine5;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
//		blockID_CompactEngine = config.get(Configuration.CATEGORY_BLOCK, "CompactEngineId", 1529).getInt();
//		itemID_energyChecker = config.get(Configuration.CATEGORY_ITEM, "EnergyCheckerId", 19500).getInt();
        isAddCompactEngine512and2048 = config.get(Configuration.CATEGORY_GENERAL, "Add high compact engine", false, "add Engine is x512 (Note explosion)").getBoolean(false);
        CompactEngineExplosionPowerLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionPowerLevel", 1, "min=0, max=3").getInt();
        CompactEngineExplosionTimeLevel = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionTimeLevel", 1, "min=0, max=3").getInt();
        CompactEngineExplosionAlertMinute = config.get(Configuration.CATEGORY_GENERAL, "CompactEngineExplosionAlertMinute", 3, "0 is not alert display, min=0.0D, max=30.0D").getInt();
        neverExplosion = config.get(Configuration.CATEGORY_GENERAL, "neverExplosion", false, "Engine No Explosion").getBoolean(false);
//		OilFlowingSpeed = config.get(Configuration.CATEGORY_GENERAL, "OilFlowingSpeed", 20, "Change OilFlowingSpeed. Default:20tick").getInt();
        config.save();
        engineBlock = new BlockCompactEngine().setResistance(10.0f).setBlockName("CompactEngine:CompactEngineWood").setBlockTextureName("buildcraft:engineWoodBottom");
        GameRegistry.registerBlock(engineBlock, ItemCompactEngine.class, "compactengineblock");
//		engineItem  = new ItemCompactEngine(engineBlock);
//		GameRegistry.registerItem(engineItem, "compactengineitem");
//		energyChecker = new ItemEnergyChecker().setUnlocalizedName("compactengine:energyChecker").setTextureName("compactengine:energyChecker");
//		GameRegistry.registerItem(energyChecker, "energychecker");
    }

    @Mod.EventHandler
    public void load(FMLInitializationEvent event) {
//		Block.blocksList[BuildCraftEnergy.fluidOil.getBlockID()] = null;
//		oilMoving = (new BlockOilFlowing2(BuildCraftEnergy.fluidOil.getBlockID(), Material.water));
//		buildcraftFluidOil = new Fluid("oil");
//		ObfuscationReflectionHelper.setPrivateValue(BuildCraftEnergy.class, BuildCraftEnergy.instance, buildcraftFluidOil, 6);
//		BuildCraftEnergy.fluidOil = FluidRegistry.getFluid("oil");
//		BuildCraftEnergy.fluidOil.setBlockID(BuildCraftEnergy.blockOil);
        engine1 = new ItemStack(engineBlock, 1, 0);
        engine2 = new ItemStack(engineBlock, 1, 1);
        engine3 = new ItemStack(engineBlock, 1, 2);
        engine4 = new ItemStack(engineBlock, 1, 3);
        engine5 = new ItemStack(engineBlock, 1, 4);
        proxy.registerTileEntitySpecialRenderer();
//        GameRegistry.registerTileEntity(TileCompactEngine.class, "tile.compactengine");
        GameRegistry.registerTileEntity(TileCompactEngine8.class, "tile.compactengine8");
        GameRegistry.registerTileEntity(TileCompactEngine32.class, "tile.compactengine32");
        GameRegistry.registerTileEntity(TileCompactEngine128.class, "tile.compactengine128");
        GameRegistry.registerTileEntity(TileCompactEngine512.class, "tile.compactengine512");
        GameRegistry.registerTileEntity(TileCompactEngine2048.class, "tile.compactengine2048");

        ItemStack woodEngine = new ItemStack(BuildCraftCore.engineBlock, 1, 0);
        ItemStack ironEngine = new ItemStack(BuildCraftCore.engineBlock, 1, 2);
        ItemStack ironGear = new ItemStack(BuildCraftCore.ironGearItem);
        ItemStack diaGear = new ItemStack(BuildCraftCore.diamondGearItem);
        ItemStack diaChip = new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 3);
        ItemStack goldORGate = ItemGate.makeGateItem(GateDefinition.GateMaterial.GOLD, GateDefinition.GateLogic.OR);
		ItemStack diaORGate = ItemGate.makeGateItem(GateDefinition.GateMaterial.DIAMOND, GateDefinition.GateLogic.OR);

        GameRegistry.addRecipe(engine1, "www", "wgw", "www", 'w', woodEngine, 'g', ironGear);
        GameRegistry.addRecipe(engine2, "geg", "eie", "geg", 'e', engine1, 'g', diaGear, 'i', ironEngine);
        GameRegistry.addRecipe(engine3, "geg", "eie", "geg", 'e', engine2, 'g', diaChip, 'i', ironEngine);

        if (isAddCompactEngine512and2048) {
//			GameRegistry.addRecipe(engine4, "geg", "eie", "geg", 'e', engine3, 'g', goldORGate, 'i', ironEngine);
            GameRegistry.addRecipe(new ExtendedShapedRecipe(engine4, "geg", "eie", "geg", 'e', engine3, 'g', goldORGate, 'i', ironEngine));
			GameRegistry.addRecipe(new ExtendedShapedRecipe(engine5, "geg", "eie", "geg", 'e', engine4, 'g', diaORGate, 'i', ironEngine));
        }
//		GameRegistry.addRecipe(new ItemStack(energyChecker), new Object[]{"w", "i",
//			'w', BuildCraftTransport.pipePowerWood, 'i', Items.iron_ingot});
    }

    public static void addChat(String message) {

        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
        } else if (FMLCommonHandler.instance().getEffectiveSide().isServer()) {
            MinecraftServer.getServer().getConfigurationManager().sendChatMsgImpl(new ChatComponentText(message), true);
        }
    }

    public static void addChat(String format, Object... args) {
        addChat(String.format(format, args));
    }

    static {
        for (String str : TEXTURE_STRING_LIST) {
            RESOURCE_LOCATION_LIST.add(new ResourceLocation(MOD_ID.toLowerCase(), String.format("textures/blocks/%s.png", str)));
        }
    }
}