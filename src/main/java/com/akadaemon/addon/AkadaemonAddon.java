package com.akadaemon.addon;

import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.fluids.ModFluids;
import com.akadaemon.addon.handler.CommonProxy;
import com.akadaemon.addon.handler.ConfigHandler;
import com.akadaemon.addon.handler.PacketDrillUpdate;
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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(PacketDrillUpdate.Handler.class, PacketDrillUpdate.class, 0, Side.SERVER);

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();

        proxy.registerGuiHandler();
        proxy.registerEntities();
        proxy.registerTileEntities();

        Log(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerHandlers();
        proxy.registerRenderers();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ExternalItems.init();
        ThaumcraftIntegration.init();
        MainRecipes.init();
        ThaumcraftAspects.init();
        TinkersRecipes.init();
        OreCompatibility.init();
        AE2Compatibility.init();
        BRCompatibility.init();
        ForestryCompatibility.init();
        if (ConfigHandler.enableOreDump) { OreDump.init(); }

        WorldGenerator.init();
        CommonProxy.setupSpawning();

        proxy.registerClientHandlers();
    }

    private void Log(FMLPreInitializationEvent event) {
        String side = event.getSide().isClient() ? "CLIENT" : "SERVER";
        logger.info(MODID + " Version: " + VERSION + " | " + side);
        logger.info("Status : Successfully Initialized");
    }
}