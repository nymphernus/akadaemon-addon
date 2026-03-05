package com.akadaemon.addon.recipes;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.Smeltery;

public class TinkersRecipes {
    public static void init() {
        int blockAmount = 500;
        int resAmount = 125;

        Smeltery.addMelting(Blocks.ice, 0, 100, new FluidStack(AkadaemonAddon.fluidIce, blockAmount));
        Smeltery.addMelting(new ItemStack(Items.snowball, 1, 0), Blocks.snow, 0, 50, new FluidStack(AkadaemonAddon.fluidSnow, resAmount));
        Smeltery.addMelting(ExternalItems.quicksilver, AkadaemonAddon.blockTitan, 0, 800, new FluidStack(AkadaemonAddon.fluidQuicksilver, resAmount));
        Smeltery.addMelting(new ItemStack(Items.dye, 1, 4), Blocks.lapis_block, 0, 400, new FluidStack(AkadaemonAddon.fluidLapis, resAmount));


        Smeltery.addMelting(Blocks.glowstone, 0, 600, new FluidStack(AkadaemonAddon.fluidGlowstone, blockAmount));
        Smeltery.addMelting(ExternalItems.amber, AkadaemonAddon.blockEtherealPhoton, 0, 800, new FluidStack(AkadaemonAddon.fluidAmber, resAmount));



        Smeltery.addAlloyMixing(
                new FluidStack(AkadaemonAddon.fluidGlacialQuicksilver, 1000),
                new FluidStack(AkadaemonAddon.fluidIce, 500),
                new FluidStack(AkadaemonAddon.fluidSnow, 125),
                new FluidStack(AkadaemonAddon.fluidQuicksilver, 250),
                new FluidStack(AkadaemonAddon.fluidLapis, 125)
        );

        Smeltery.addAlloyMixing(
                new FluidStack(AkadaemonAddon.fluidEtherealPhoton, 1000),
                new FluidStack(AkadaemonAddon.fluidGlowstone, 500),
                new FluidStack(AkadaemonAddon.fluidAmber, 500)
        );

        TConstructRegistry.getTableCasting().addCastingRecipe(
                new ItemStack(AkadaemonAddon.bucketGlacialQuicksilver),
                new FluidStack(AkadaemonAddon.fluidGlacialQuicksilver, 1000),
                ExternalItems.emptyBucket,
                true,
                20
        );

        TConstructRegistry.getTableCasting().addCastingRecipe(
                new ItemStack(AkadaemonAddon.bucketEtherealPhoton),
                new FluidStack(AkadaemonAddon.fluidEtherealPhoton, 1000),
                ExternalItems.emptyBucket,
                true,
                20
        );

    }
}