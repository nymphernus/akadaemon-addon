package com.akadaemon.addon;

import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.RecipeInputItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.tools.TinkerTools;
import tconstruct.world.TinkerWorld;

import static com.akadaemon.addon.AkadaemonAddon.*;

public class Recipes {
    public static void init() {
        ItemStack thaumIngot = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource"), 1, 2);

        ItemStack advCircuit = ic2.api.item.IC2Items.getItem("advancedCircuit");
        ItemStack carbonPlate = ic2.api.item.IC2Items.getItem("carbonPlate");
        ItemStack energyCrystal = ic2.api.item.IC2Items.getItem("energyCrystal");
        ItemStack dustDiamond = ic2.api.item.IC2Items.getItem("diamondDust");
        ItemStack dustLapis = ic2.api.item.IC2Items.getItem("lapiDust");
        ItemStack iridiumOre = ic2.api.item.IC2Items.getItem("iridiumOre");


        // Композитный модификатор
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(compositeMod),
                " M ",
                " O ",
                " A ",
                'M', "blockManyullyn",
                'O', new ItemStack(Blocks.obsidian),
                'A', "blockAlumite"));

        // Расширительная микросхема
        if (advCircuit != null && carbonPlate != null && energyCrystal != null) {
            ItemStack anyCrystal = energyCrystal.copy();
            anyCrystal.setItemDamage(OreDictionary.WILDCARD_VALUE);

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(expansionChip),
                    " C ",
                    " E ",
                    "CAC",
                    'C', carbonPlate,
                    'E', anyCrystal,
                    'A', advCircuit));
        }

        // Шницель
        GameRegistry.addShapelessRecipe(new ItemStack(goldenSchnitzel),
                Items.gold_nugget,
                Items.cooked_beef);

        // Слиток алюмита
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapelessOreRecipe(new ItemStack(TinkerTools.materials, 3, 15),
                "ingotAluminum", "ingotIron", Blocks.obsidian));

        // Эндер пыль
        if (dustDiamond != null && dustLapis != null) {
            GameRegistry.addRecipe(new ItemStack(enderDust, 9),
                    "RLR", "LAL", "RLR",
                    'R', Items.redstone,
                    'L', dustLapis,
                    'A', dustDiamond
            );
        }

        // Ардитовая и кобальтовая пыль, эндер жемчуг, композит, кожа
        ic2.api.recipe.Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 2)), null, new ItemStack(arditeDust, 2));
        ic2.api.recipe.Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 1)), null, new ItemStack(cobaltDust, 2));

        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(enderDust, 3)), null, new ItemStack(Items.ender_pearl));
        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(iridiumComposite)), null, iridiumOre.copy());
        ic2.api.recipe.Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(Items.rotten_flesh)), null, new ItemStack(Items.leather));

        // Иридиевый композит
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapedOreRecipe(new ItemStack(iridiumComposite),
                "MMM",
                "TTT",
                "SSS",
                'M', "ingotManyullyn",
                'T', thaumIngot,
                'S', "ingotSilver"
        ));
        GameRegistry.addShapelessRecipe(new ItemStack(goldenSchnitzel),
                Items.gold_nugget,
                Items.cooked_beef);
    }
}
