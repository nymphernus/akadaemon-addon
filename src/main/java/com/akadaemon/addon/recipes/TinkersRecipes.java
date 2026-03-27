package com.akadaemon.addon.recipes;

import com.akadaemon.addon.ExternalItems;
import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.Smeltery;

import static com.akadaemon.addon.fluids.ModFluids.*;

public class TinkersRecipes {
    public static void init() {
        int blockAmount = 500;
        int resAmount = 125;

        Smeltery.addMelting(Blocks.ice, 0, 100, new FluidStack(fluidIce, blockAmount));
        Smeltery.addMelting(new ItemStack(Items.snowball, 1, 0), Blocks.snow, 0, 50, new FluidStack(fluidSnow, resAmount));
        Smeltery.addMelting(ExternalItems.quicksilver, ModBlocks.blockTitan, 0, 800, new FluidStack(fluidQuicksilver, resAmount));
        Smeltery.addMelting(new ItemStack(Items.dye, 1, 4), Blocks.lapis_block, 0, 400, new FluidStack(fluidLapis, resAmount));


        Smeltery.addMelting(Blocks.glowstone, 0, 600, new FluidStack(fluidGlowstone, blockAmount));
        Smeltery.addMelting(ExternalItems.amber, ModBlocks.blockEtherealPhoton, 0, 800, new FluidStack(fluidAmber, resAmount));

        Smeltery.addMelting(new ItemStack(Items.redstone), Blocks.redstone_block, 0, 400, new FluidStack(fluidRedstone, resAmount));



        Smeltery.addAlloyMixing(
                new FluidStack(fluidGlacialQuicksilver, 1000),
                new FluidStack(fluidIce, 500),
                new FluidStack(fluidSnow, 125),
                new FluidStack(fluidQuicksilver, 250),
                new FluidStack(fluidLapis, 125)
        );

        Smeltery.addAlloyMixing(
                new FluidStack(fluidEtherealPhoton, 1000),
                new FluidStack(fluidGlowstone, 500),
                new FluidStack(fluidAmber, 500)
        );

        Smeltery.addAlloyMixing(
                new FluidStack(fluidRubyFlux, 1000),
                new FluidStack(fluidRedstone, 750),
                new FluidStack(fluidAmber, 250)
        );

        TConstructRegistry.getTableCasting().addCastingRecipe(
                new ItemStack(ModItems.bucketGlacialQuicksilver),
                new FluidStack(fluidGlacialQuicksilver, 1000),
                ExternalItems.emptyBucket,
                true,
                20
        );

        TConstructRegistry.getTableCasting().addCastingRecipe(
                new ItemStack(ModItems.bucketEtherealPhoton),
                new FluidStack(fluidEtherealPhoton, 1000),
                ExternalItems.emptyBucket,
                true,
                20
        );

        TConstructRegistry.getTableCasting().addCastingRecipe(
                new ItemStack(ModItems.bucketRubyFlux),
                new FluidStack(fluidRubyFlux, 1000),
                ExternalItems.emptyBucket,
                true,
                20
        );

    }
}