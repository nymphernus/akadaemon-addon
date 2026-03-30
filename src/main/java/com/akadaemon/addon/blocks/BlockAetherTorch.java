package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

import java.util.Random;

public class BlockAetherTorch extends BlockTorch {

    public BlockAetherTorch() {
        super();
        this.setLightLevel(1.0F);
        this.setBlockName("aether_torch");
        this.setBlockTextureName(AkadaemonAddon.MODID + ":aether_torch");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextInt(5) != 0) return;

        BiomeGenBase biome = world.getBiomeGenForCoords(x, z);
        int dim = world.provider.dimensionId;
        String p = "happyVillager";

        if (dim == -1) p = "flame";
        else if (dim == 1) p = "portal";
        else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.MAGICAL)) p = "witchMagic";
        else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.JUNGLE)) p = "heart";
        else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SNOWY)) p = "snowballpoof";
        else if (BiomeDictionary.isBiomeOfType(biome, BiomeDictionary.Type.SANDY)) p = "reddust";

        int meta = world.getBlockMetadata(x, y, z);
        double px = x + 0.5D, py = y + 0.7D, pz = z + 0.5D;
        double o1 = 0.22D, o2 = 0.27D;

        if (meta == 1) world.spawnParticle(p, px - o2, py + o1, pz, 0, 0.02, 0);
        else if (meta == 2) world.spawnParticle(p, px + o2, py + o1, pz, 0, 0.02, 0);
        else if (meta == 3) world.spawnParticle(p, px, py + o1, pz - o2, 0, 0.02, 0);
        else if (meta == 4) world.spawnParticle(p, px, py + o1, pz + o2, 0, 0.02, 0);
        else world.spawnParticle(p, px, py, pz, 0, 0.02, 0);
    }
}