package com.akadaemon.addon.recipes;

import com.akadaemon.addon.ExternalItems;
import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.Smeltery;

import static com.akadaemon.addon.fluids.ModFluids.*;

public class TinkersRecipes {

    public static void init() {
        final int BLOCK = 500;
        final int ITEM = 125;
        final int BUCKET = 1000;

        melt(Blocks.ice, 100, fluidIce, BLOCK);
        melt(new ItemStack(Items.snowball), Blocks.snow, 50, fluidSnow, ITEM);
        melt(ExternalItems.quicksilver, ModBlocks.blockTitan, 800, fluidQuicksilver, ITEM);
        melt(new ItemStack(Items.dye, 1, 4), Blocks.lapis_block, 400, fluidLapis, ITEM);
        melt(Blocks.glowstone, 600, fluidGlowstone, BLOCK);
        melt(ExternalItems.amber, ModBlocks.blockEtherealPhoton, 800, fluidAmber, ITEM);
        melt(new ItemStack(Items.redstone), Blocks.redstone_block, 400, fluidRedstone, ITEM);

        Smeltery.addAlloyMixing(new FluidStack(fluidGlacialQuicksilver, BUCKET),
                f(fluidIce, 500), f(fluidSnow, 125), f(fluidQuicksilver, 250), f(fluidLapis, 125));

        Smeltery.addAlloyMixing(new FluidStack(fluidEtherealPhoton, BUCKET),
                f(fluidGlowstone, 500), f(fluidAmber, 500));

        Smeltery.addAlloyMixing(new FluidStack(fluidRubyFlux, BUCKET),
                f(fluidRedstone, 750), f(fluidAmber, 250));

        castBucket(ModItems.bucketGlacialQuicksilver, fluidGlacialQuicksilver);
        castBucket(ModItems.bucketEtherealPhoton, fluidEtherealPhoton);
        castBucket(ModItems.bucketRubyFlux, fluidRubyFlux);
    }

    private static FluidStack f(net.minecraftforge.fluids.Fluid fluid, int amount) {
        return new FluidStack(fluid, amount);
    }

    private static void melt(Object input, int temp, net.minecraftforge.fluids.Fluid fluid, int amount) {
        if (input instanceof net.minecraft.block.Block)
            Smeltery.addMelting((net.minecraft.block.Block)input, 0, temp, f(fluid, amount));
        else if (input instanceof ItemStack)
            Smeltery.addMelting((ItemStack)input, Blocks.iron_block, 0, temp, f(fluid, amount));
    }

    private static void melt(ItemStack stack, net.minecraft.block.Block render, int temp, net.minecraftforge.fluids.Fluid fluid, int amount) {
        Smeltery.addMelting(stack, render, 0, temp, f(fluid, amount));
    }

    private static void castBucket(Item bucket, net.minecraftforge.fluids.Fluid fluid) {
        TConstructRegistry.getTableCasting().addCastingRecipe(new ItemStack(bucket), f(fluid, 1000), ExternalItems.emptyBucket, true, 20);
    }
}