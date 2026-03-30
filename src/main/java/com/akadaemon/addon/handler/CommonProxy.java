package com.akadaemon.addon.handler;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.blocks.*;
import com.akadaemon.addon.entity.EntityDraugr;
import com.akadaemon.addon.entity.EntityFocusPearl;
import com.akadaemon.addon.recipes.ThaumcraftIntegration;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy {
    public void registerHandlers() {
        MinecraftForge.EVENT_BUS.register(new BucketHandler());
        MinecraftForge.EVENT_BUS.register(new DurabilityEventHandler());
        MinecraftForge.EVENT_BUS.register(new ThaumcraftIntegration());
        ForgeChunkManager.setForcedChunkLoadingCallback(AkadaemonAddon.instance, new ChunkLoaderHandler());
    }

    public void registerEntities() {
        EntityRegistry.registerModEntity(EntityFocusPearl.class, "FocusPearl", 0, AkadaemonAddon.instance, 64, 10, true);

        int draugrId = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityDraugr.class, "Draugr", draugrId, 0x4682B4, 0x00FFFF);
        EntityRegistry.registerModEntity(EntityDraugr.class, "Draugr", draugrId, AkadaemonAddon.instance, 80, 3, true);
    }

    public static void setupSpawning() {
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null && !isForbiddenBiome(biome)) {
                EntityRegistry.addSpawn(EntityDraugr.class, 35, 1, 2, EnumCreatureType.monster, biome);
            }
        }
    }

    public static boolean isForbiddenBiome(BiomeGenBase biome) {
        if (biome.biomeID == 8 || biome.biomeID == 24) {
            return true;
        }
        if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.NETHER) ||
                BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.END)) {
            return true;
        }
        return false;
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileThaumTransformer.class, "TileThaumTransformer");
        GameRegistry.registerTileEntity(TileAmberFiber.class, "TileAmberFiber");
        GameRegistry.registerTileEntity(TileEntityTitanDrill.class, "TileEntityDrill");
        GameRegistry.registerTileEntity(TileEntityChunkLoader.class, "TEChunkLoader");
        GameRegistry.registerTileEntity(TileSolarPanel.class, "TileSolarPanel");
    }

    public void registerGuiHandler() {
        NetworkRegistry.INSTANCE.registerGuiHandler(AkadaemonAddon.instance, new GuiHandler());
    }

    public void registerRenderers() {}
    public void registerClientHandlers() {}
}