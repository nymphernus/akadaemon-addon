package com.akadaemon.addon.recipes;

import com.akadaemon.addon.ExternalItems;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.tools.TinkerTools;
import tconstruct.world.TinkerWorld;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.akadaemon.addon.blocks.ModBlocks.*;
import static com.akadaemon.addon.items.ModItems.*;


public class MainRecipes {
    public static void init() {
        registerCraftingRecipes();
        registerSmeltingRecipes();
        registerIC2Recipes();
        if (Loader.isModLoaded("AdvancedSolarPanel")) fixMTCore();
    }

    private static void registerIC2Recipes() {
        Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 2)), null, new ItemStack(arditeDust, 2));
        Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(TinkerWorld.oreSlag, 1, 1)), null, new ItemStack(cobaltDust, 2));
        Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(Items.ender_pearl)), null, new ItemStack(enderDust, 3));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("itemSkull", 1), null, new ItemStack(Items.bone, 3));
        Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(Blocks.hay_block)), null, OreDictionary.getOres("dustWheat").get(0).copy().splitStack(9));
        Recipes.macerator.addRecipe(new RecipeInputOreDict("cropBarley", 1), null, new ItemStack(barleyFlour, 1));
        replaceMachineRecipe(Recipes.macerator, new ItemStack(Blocks.obsidian), new ItemStack(obsidianDust));
        replaceMachineRecipe(Recipes.macerator, new ItemStack(Items.wheat), OreDictionary.getOres("dustWheat").get(0).copy().splitStack(1));

        Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(enderDust, 3)), null, new ItemStack(Items.ender_pearl));
        Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(iridiumComposite)), null, ExternalItems.iridiumOre.copy());
        Recipes.compressor.addRecipe(new RecipeInputItemStack(new ItemStack(Items.rotten_flesh)), null, new ItemStack(Items.leather));
    }

    private static void registerSmeltingRecipes() {
        GameRegistry.addSmelting(Items.egg, new ItemStack(friedEggs), 0.35F);
        addSmelting(arditeDust, "ingotArdite", 0.5F);
        addSmelting(cobaltDust, "ingotCobalt", 0.5F);
        addSmelting("dustObsidian", "ingotObsidian", 0.5F);
        addSmelting("dustBarley", barleyBread, 0.35F);
        addSmelting("dustWheat", Items.bread, 0.35F);
    }

    private static void registerCraftingRecipes() {
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TinkerTools.materials, 3, 15),
                "ingotAluminum", "ingotIron", Blocks.obsidian));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TinkerTools.materials, 1, 3),
                "ingotSilver", "ingotObsidian"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(TinkerTools.materials, 1, 4),
                "ingotAlumite", "ingotBronze"));
        GameRegistry.addShapelessRecipe(ExternalItems.yellowHeart, ExternalItems.greenHeart);
        GameRegistry.addRecipe(new ShapelessOreRecipe(ExternalItems.ingotManyullyn, "ingotArdite", "ingotCobalt"));
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapelessOreRecipe(new ItemStack(barleyBread), "cropBarley", "cropBarley", "cropBarley"));

        registerArmorRecipes("ingotManyullyn", manyullynHelmet, manyullynChest, manyullynLegs, manyullynBoots);
        registerArmorRecipes("ingotTitan", titanHelmet, titanChest, titanLegs, titanBoots);
        registerArmorRecipes("ingotAdamantit", adamantitHelmet, adamantitChest, adamantitLegs, adamantitBoots);
        registerArmorRecipes("ingotMythril", mythrilHelmet, mythrilChest, mythrilLegs, mythrilBoots);

        registerSwordRecipes("ingotTitan", titanSword);
        registerSwordRecipes("ingotAdamantit", adamantitSword);
        registerSwordRecipes("ingotMythril", mythrilSword);

        GameRegistry.addRecipe(new ItemStack(enderDust, 9),
                "RLR", "LAL", "RLR",
                'R', Items.redstone,
                'L', ExternalItems.dustLapis,
                'A', ExternalItems.dustDiamond
        );
        GameRegistry.addRecipe(new ItemStack(goldenSchnitzel, 1),
                " G ", "GBG", " G ",
                'G', Blocks.gold_block,
                'B', Items.cooked_beef
        );
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
        GameRegistry.addRecipe(new ItemStack(blockTitan),
                "TTT",
                "TTT",
                "TTT",
                'T', ingotTitan);
        GameRegistry.addRecipe(new ItemStack(blockAdamantit),
                "AAA",
                "AAA",
                "AAA",
                'A', ingotAdamantit);
        GameRegistry.addRecipe(new ItemStack(Items.skull, 1, 1),
                " B ",
                "CSC",
                " C ",
                'B', ExternalItems.netherBone,
                'C', Items.coal,
                'S', new ItemStack(Items.skull, 1, 0)
        );
        GameRegistry.addRecipe(new net.minecraftforge.oredict.ShapedOreRecipe(new ItemStack(iridiumComposite),
                "MMM",
                "TTT",
                "SSS",
                'M', "ingotManyullyn",
                'T', ExternalItems.thaumIngot,
                'S', "ingotSilver"
        ));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ExternalItems.greenHeartCan,
                ExternalItems.yellowHeartCan,
                ExternalItems.greenHeart,
                Items.emerald,
                ExternalItems.zombieBrain));
    }

    public static void addOreSmelting(Item inputItem, String dustName, String ingotName, float xp, boolean isUniversal) {
        List<ItemStack> ingots = OreDictionary.getOres(ingotName);

        ItemStack output = ingots.get(0).copy();
        output.stackSize = 1;

        if (isUniversal && dustName != null && !dustName.isEmpty()) {
            List<ItemStack> dusts = OreDictionary.getOres(dustName);
            for (ItemStack dustStack : dusts) {
                GameRegistry.addSmelting(dustStack, output, xp);
            }
        } else if (inputItem != null) {
            GameRegistry.addSmelting(inputItem, output, xp);
        }
    }

    public static void addSmelting(Item input, String ingotName, float xp) {
        addOreSmelting(input, null, ingotName, xp, false);
    }

    public static void addSmelting(String dustName, String ingotName, float xp) {
        addOreSmelting(null, dustName, ingotName, xp, true);
    }

    public static void addSmelting(String dustName, Item outputItem, float xp) {
        List<ItemStack> inputs = OreDictionary.getOres(dustName);
        for (ItemStack inStack : inputs) {
            GameRegistry.addSmelting(inStack, new ItemStack(outputItem), xp);
        }
    }

    public static void fixMTCore() {
        Item thickReflector = GameRegistry.findItem("IC2", "reactorReflectorThick");
        Item aspCrafting = GameRegistry.findItem("AdvancedSolarPanel", "asp_crafting_items");

        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        Iterator<IRecipe> iterator = recipes.iterator();
        while (iterator.hasNext()) {
            ItemStack output = iterator.next().getRecipeOutput();
            if (output != null && output.getItem() == aspCrafting && output.getItemDamage() == 12) {
                iterator.remove();
            }
        }
        ItemStack glassPane = new ItemStack(aspCrafting, 1, 5);
        ItemStack coreOutput = new ItemStack(aspCrafting, 1, 12);
        ItemStack safeReflector = new ItemStack(thickReflector, 1, 32767);
        GameRegistry.addRecipe(coreOutput,
                "GRG",
                "G G",
                "GRG",
                'G', glassPane,
                'R', safeReflector
        );
    }

    public static void replaceMachineRecipe(IMachineRecipeManager machine, ItemStack input, ItemStack output) {
        Map<IRecipeInput, RecipeOutput> recipes = machine.getRecipes();
        Iterator<Map.Entry<IRecipeInput, RecipeOutput>> it = recipes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<IRecipeInput, RecipeOutput> entry = it.next();
            boolean found = false;
            for (ItemStack stack : entry.getKey().getInputs()) {
                if (stack != null && stack.getItem() == input.getItem() &&
                        (stack.getItemDamage() == input.getItemDamage() || stack.getItemDamage() == 32767)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                it.remove();
            }
        }
        machine.addRecipe(new RecipeInputItemStack(input), null, output);
    }

    public static void registerArmorRecipes(Object ingot, Item helmet, Item chest, Item legs, Item boots) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(helmet), "IHI", "I I", 'I', ingot, 'H', heartContainer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chest), "I I", "IHI", "III", 'I', ingot, 'H', heartContainer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(legs), "IHI", "I I", "I I", 'I', ingot, 'H', heartContainer));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boots), "I I", "I I", 'I', ingot, 'H', heartContainer));
    }

    public static void registerSwordRecipes(Object ingot, Item sword) {
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sword), "  I", " I ", "S  ", 'I', ingot, 'S', ExternalItems.alumiteRod));
    }
}