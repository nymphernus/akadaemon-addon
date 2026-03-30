package com.akadaemon.addon.recipes;

import com.akadaemon.addon.items.ModItems;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.lang.reflect.Method;

public class ForestryCompatibility {
    public static void init() {
        if (Loader.isModLoaded("Forestry")) {
            addSqueezerRecipe(new ItemStack(ModItems.barleySeeds), 10);
        }
    }

    private static void addSqueezerRecipe(ItemStack input, int amount) {
        try {
            Class<?> recipeManagerClass = Class.forName("forestry.api.recipes.RecipeManagers");
            Object squeezerManager = recipeManagerClass.getField("squeezerManager").get(null);

            if (squeezerManager != null) {
                Method addRecipeMethod = squeezerManager.getClass().getMethod("addRecipe",
                        int.class, ItemStack[].class, FluidStack.class);

                FluidStack seedOil = new FluidStack(FluidRegistry.getFluid("seedoil"), amount);
                addRecipeMethod.invoke(squeezerManager, 20, new ItemStack[]{input}, seedOil);
            }
        } catch (Exception ignored) {}
    }
}