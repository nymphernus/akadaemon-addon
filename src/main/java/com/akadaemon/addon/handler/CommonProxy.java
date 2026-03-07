package com.akadaemon.addon.handler;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.blocks.TileAmberFiber;
import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import com.akadaemon.addon.blocks.TileThaumTransformer;
import com.akadaemon.addon.entity.EntityDraugr;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class CommonProxy {
    public void registerEntities() {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerGlobalEntityID(EntityDraugr.class, "Draugr", entityID, 0x4682B4, 0x00FFFF);
        EntityRegistry.registerModEntity(EntityDraugr.class, "Draugr", entityID, AkadaemonAddon.instance, 64, 3, true);
        for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
            if (biome != null) {
                EntityRegistry.addSpawn(EntityDraugr.class, 25, 1, 2, EnumCreatureType.monster, biome);
            }
        }
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileThaumTransformer.class, "TileThaumTransformer");
        GameRegistry.registerTileEntity(TileAmberFiber.class, "TileAmberFiber");
        GameRegistry.registerTileEntity(TileEntityTitanDrill.class, "TileEntityDrill");
    }

    public void registerRenderers() {}
}