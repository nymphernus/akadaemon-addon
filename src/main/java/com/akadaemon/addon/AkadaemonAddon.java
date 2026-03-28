package com.akadaemon.addon;

import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.fluids.ModFluids;
import com.akadaemon.addon.handler.*;
import com.akadaemon.addon.items.ModItems;
import com.akadaemon.addon.recipes.*;
import com.akadaemon.addon.world.WorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(modid = AkadaemonAddon.MODID, name = AkadaemonAddon.NAME, version = AkadaemonAddon.VERSION,
        dependencies = "required-after:TConstruct;required-after:IC2;required-after:Thaumcraft;required-after:Baubles")
public class AkadaemonAddon {
    public static final String MODID = "akadaemon";
    public static final String NAME = "Akadaemon Addon";
    public static final String VERSION = "1.8.2";
    public static final Logger logger = LogManager.getLogger(NAME);
    public static SimpleNetworkWrapper network;

    @SidedProxy(
            clientSide = "com.akadaemon.addon.handler.ClientProxy",
            serverSide = "com.akadaemon.addon.handler.CommonProxy"
    )
    public static CommonProxy proxy;


    public static CreativeTabs tabAkadaemon = new CreativeTabs("akadaemon") {
        @Override
        public Item getTabIconItem() { return ModItems.worldRing; }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack() {
            return new ItemStack(ModItems.worldRing);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllReleventItems(List list) {
            super.displayAllReleventItems(list);

            list.add(ThaumcraftIntegration.createWand(ThaumcraftIntegration.WAND_ROD_IRIDIUM, ThaumcraftIntegration.WAND_CAP_MANYULLYN));
            list.add(ThaumcraftIntegration.createWand(ThaumcraftIntegration.WAND_ROD_IRIDIUM_TITAN, ThaumcraftIntegration.WAND_CAP_MYTHRIL));
        }
    };

    @Mod.Instance(MODID)
    public static AkadaemonAddon instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event);
        ForgeChunkManager.setForcedChunkLoadingCallback(instance, new ChunkLoaderHandler());

        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(PacketDrillUpdate.Handler.class, PacketDrillUpdate.class, 0, Side.SERVER);

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        MinecraftForge.addGrassSeed(new ItemStack(ModItems.barleySeeds), 10);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        proxy.registerEntities();
        proxy.registerTileEntities();

        Log(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        tconstruct.library.crafting.ModifyBuilder.registerModifier(new ModInfiniteDurability(new ItemStack[] { new ItemStack(ModItems.compositeMod) }));
        tconstruct.library.crafting.ModifyBuilder.registerModifier(new ModExtraSlot(new ItemStack[] { new ItemStack(ModItems.expansionChip) }));

        MinecraftForge.EVENT_BUS.register(new BucketHandler());
        MinecraftForge.EVENT_BUS.register(new DurabilityEventHandler());

        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ExternalItems.init();
        ThaumcraftIntegration.init();
        MinecraftForge.EVENT_BUS.register(new ThaumcraftIntegration());
        if (event.getSide().isClient()) {MinecraftForge.EVENT_BUS.register(new TabHandler());}
        MainRecipes.init();
        ThaumcraftAspects.register();
        TinkersRecipes.init();
        OreCompatibility.init();
        AE2Compatibility.init();
        BRCompatibility.init();
        WorldGenerator.initLoot();
        GameRegistry.registerWorldGenerator(new WorldGenerator(), 10);
        if (ConfigHandler.enableOreDump) { OreDump.init(); }
        CommonProxy.setupSpawning();
    }

    private void Log(FMLPreInitializationEvent event) {
        String side = event.getSide().isClient() ? "CLIENT" : "SERVER";
        logger.info(MODID + " Version: " + VERSION + " | " + side);
        logger.info("Status : Successfully Initialized");
    }
}