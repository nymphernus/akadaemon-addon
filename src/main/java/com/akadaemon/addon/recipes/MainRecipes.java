package com.akadaemon.addon.recipes;

import com.akadaemon.addon.ExternalItems;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.RecipeInputItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.tools.TinkerTools;
import tconstruct.world.TinkerWorld;

import java.util.ArrayList;

import static com.akadaemon.addon.AkadaemonAddon.*;


public class MainRecipes {
    public static void init() {
        // Шницель
        GameRegistry.addShapelessRecipe(new ItemStack(goldenSchnitzel), Blocks.gold_block, Items.cooked_beef);
        // Слиток алюмита
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapelessOreRecipe(new ItemStack(TinkerTools.materials, 3, 15),
                "ingotAluminum", "ingotIron", Blocks.obsidian));
        // Эндер пыль
        GameRegistry.addRecipe(new ItemStack(enderDust, 9),
                "RLR", "LAL", "RLR",
                'R', Items.redstone,
                'L', ExternalItems.dustLapis,
                'A', ExternalItems.dustDiamond
        );
        // Блок Мифрила
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(blockMythril),
                "MMM",
                "MMM",
                "MMM",
                'M', "ingotMithril"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chunkLoader),
                "IDI",
                "GEG",
                "IGI",
                'I', "ingotIron",
                'G', "ingotGold",
                'D', "gemDiamond",
                'E', Items.ender_pearl
                ));

        // Блок Титана
        GameRegistry.addRecipe(new ItemStack(blockTitan),
                "TTT",
                "TTT",
                "TTT",
                'T', ingotTitan);

        // Блок Адамантита
        GameRegistry.addRecipe(new ItemStack(blockAdamantit),
                "AAA",
                "AAA",
                "AAA",
                'A', ingotAdamantit);

        // Иридиевый композит
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapedOreRecipe(new ItemStack(iridiumComposite),
                "MMM",
                "TTT",
                "SSS",
                'M', "ingotManyullyn",
                'T', ExternalItems.thaumIngot,
                'S', "ingotSilver"
        ));

        GameRegistry.addSmelting(Items.egg, new ItemStack(friedEggs), 0.35F);

        GameRegistry.addRecipe(new ShapelessOreRecipe(ExternalItems.ingotManullyn, "ingotArdite", "ingotCobalt"));

        addOreDictSmelting(arditeDust, "ingotArdite", 0.5F);
        addOreDictSmelting(cobaltDust, "ingotCobalt", 0.5F);

        if (!OreDictionary.getOres("dustWheat").isEmpty()) {
            ItemStack result = OreDictionary.getOres("dustWheat").get(0).copy();
            result.stackSize = 9;
            ic2.api.recipe.Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(Blocks.hay_block)), null, result);
        }

        for (ItemStack flour : OreDictionary.getOres("dustWheat")) {
            GameRegistry.addSmelting(flour.getItem(), new ItemStack(Items.bread), 0.35F);
        }
        ic2.api.recipe.Recipes.macerator.addRecipe(new ic2.api.recipe.RecipeInputOreDict("cropBarley", 1), null, new ItemStack(barleyFlour, 1));
        GameRegistry.addSmelting(barleyFlour, new ItemStack(barleyBread), 0.35F);
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapelessOreRecipe(new ItemStack(barleyBread),
                "cropBarley", "cropBarley", "cropBarley"
        ));



        ic2.api.recipe.Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 2)), null, new ItemStack(arditeDust, 2));
        ic2.api.recipe.Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 1)), null, new ItemStack(cobaltDust, 2));

        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(enderDust, 3)), null, new ItemStack(Items.ender_pearl));
        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(iridiumComposite)), null, ExternalItems.iridiumOre.copy());
        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(Items.rotten_flesh)), null, new ItemStack(Items.leather));

        ic2.api.recipe.Recipes.macerator.addRecipe(new ic2.api.recipe.RecipeInputOreDict("itemSkull", 1), null, new ItemStack(Items.bone, 3));
    }

    public static void addOreDictSmelting(Item inputDust, String oreDictName, float xp) {
        ArrayList<ItemStack> ores = OreDictionary.getOres(oreDictName);
        if (!ores.isEmpty()) {
            ItemStack output = ores.get(0).copy();
            output.stackSize = 1;
            GameRegistry.addSmelting(inputDust, output, xp);
        }
    }
}