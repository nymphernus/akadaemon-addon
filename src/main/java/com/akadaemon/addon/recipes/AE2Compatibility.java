package com.akadaemon.addon.recipes;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AE2Compatibility {

    public static void init() {
        if (Loader.isModLoaded("appliedenergistics2")) {
            Item aeMaterial = GameRegistry.findItem("appliedenergistics2", "item.ItemMultiMaterial");
            Item aeSeeds = GameRegistry.findItem("appliedenergistics2", "item.ItemCrystalSeed");

            if (aeMaterial != null) {
                ItemStack pureCertus = new ItemStack(aeMaterial, 1, 10);
                ItemStack pureNether = new ItemStack(aeMaterial, 1, 11);
                ItemStack pureFluix = new ItemStack(aeMaterial, 1, 12);
                ItemStack chargedCertus = new ItemStack(aeMaterial, 1, 1);
                ItemStack fluixCrystal = new ItemStack(aeMaterial, 2, 7);

                OreDictionary.registerOre("crystalPureCertusQuartz", pureCertus);
                OreDictionary.registerOre("crystalPureNetherQuartz", pureNether);
                OreDictionary.registerOre("crystalPureFluix", pureFluix);
                OreDictionary.registerOre("crystalCertusQuartz", chargedCertus);

                addSafeExtractorRecipe(new ItemStack(aeSeeds, 1, 0), pureCertus);
                addSafeExtractorRecipe(new ItemStack(aeSeeds, 1, 600), pureNether);
                addSafeExtractorRecipe(new ItemStack(aeSeeds, 1, 1200), pureFluix);

                GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapelessOreRecipe(fluixCrystal,
                        "crystalCertusQuartz", "crystalNetherQuartz", "dustRedstone"));
            }
        }
    }
    private static void addSafeExtractorRecipe(ItemStack input, ItemStack output) {
        if (input != null && input.getItem() != null && output != null) {
            try {
                Recipes.extractor.addRecipe(new RecipeInputItemStack(input), null, output);
            } catch (Exception e) {}
        }
    }
}