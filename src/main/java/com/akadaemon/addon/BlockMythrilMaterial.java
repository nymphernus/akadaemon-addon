package com.akadaemon.addon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import thaumcraft.api.crafting.IInfusionStabiliser;

public class BlockMythrilMaterial extends Block implements IInfusionStabiliser {
    public BlockMythrilMaterial(Material material) {
        super(material);
    }

    @Override
    public boolean canStabaliseInfusion(World world, int x, int y, int z) {
        return true;
    }
}