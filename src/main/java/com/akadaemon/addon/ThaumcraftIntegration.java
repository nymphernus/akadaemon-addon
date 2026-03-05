package com.akadaemon.addon;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

import static com.akadaemon.addon.AkadaemonAddon.*;

public class ThaumcraftIntegration {

    public static WandRod WAND_ROD_IRIDIUM;
    public static WandCap WAND_CAP_MANULLYN;
    public static final String CAT_ID = "AKADAEMON";

    public static void init() {
        WAND_ROD_IRIDIUM = new WandRod("iridium", 400, new ItemStack(wandRodIridium), 20);
        WAND_ROD_IRIDIUM.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_rod_iridium_model.png"));

        WAND_CAP_MANULLYN = new WandCap("manullyn", 0.8F, new ItemStack(wandCapManullyn), 10);
        WAND_CAP_MANULLYN.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_cap_manullyn_model.png"));

        ResearchCategories.registerCategory(CAT_ID,
                new ResourceLocation(AkadaemonAddon.MODID, "textures/items/world_ring.png"),
                new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/gui_researchback.png"));

        registerRecipesAndResearch();
    }

    private static void registerRecipesAndResearch() {
        ItemStack energyCrystal = ic2.api.item.IC2Items.getItem("energyCrystal");
        if (energyCrystal != null) energyCrystal.setItemDamage(32767);
        ItemStack iridiumOre = ic2.api.item.IC2Items.getItem("iridiumOre");
        ItemStack solarPanel = ic2.api.item.IC2Items.getItem("solarPanel");
        ItemStack mfe = ic2.api.item.IC2Items.getItem("mfeUnit");

        Item wandCapItem = (Item) Item.itemRegistry.getObject("Thaumcraft:WandCap");
        Item wandRodItem = (Item) Item.itemRegistry.getObject("Thaumcraft:WandRod");
        Item resourceItem = (Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource");

        ItemStack knowledgeFragment = new ItemStack(resourceItem, 1, 9);
        ItemStack silverLog = new ItemStack(net.minecraft.block.Block.getBlockFromName("Thaumcraft:blockMagicalLog"), 1, 1);
        ItemStack amber = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource"), 1, 6);
        ItemStack thaumIngot = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource"), 1, 2);
        ItemStack blockTube = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:blockTube"), 1, 0);
        ItemStack blockAmber = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:blockCosmeticOpaque"), 1, 0);

        if (wandCapItem == null || wandRodItem == null) return;

        ItemStack goldCap = new ItemStack(wandCapItem, 1, 1);
        ItemStack thaumiumCap = new ItemStack(wandCapItem, 1, 2);
        ItemStack silverRod = new ItemStack(wandRodItem, 1, 2);

        IArcaneRecipe recipeTransformer = ThaumcraftApi.addArcaneCraftingRecipe("THAUM_TRANSFORMER", new ItemStack(thaumTransformer),
                    new AspectList().add(Aspect.EARTH, 30).add(Aspect.AIR, 30).add(Aspect.ORDER, 20),
                    "SPS", "SMS", "JJJ",
                    'J', Blocks.iron_block, 'S', thaumIngot, 'M', mfe, 'P', solarPanel);

        IArcaneRecipe recipeFiber = ThaumcraftApi.addArcaneCraftingRecipe("AMBER_FIBER", new ItemStack(amberFiber, 2),
                new AspectList().add(Aspect.ORDER, 10).add(Aspect.AIR, 10),
                " D ", "ATA", " D ",
                'A', blockAmber, 'T', blockTube, 'D', enderDust);

        IArcaneRecipe recipeRing = ThaumcraftApi.addArcaneCraftingRecipe("WORLD_RING", new ItemStack(worldRing),
                new AspectList().add(Aspect.AIR, 20).add(Aspect.WATER, 20).add(Aspect.EARTH, 20).add(Aspect.FIRE, 20),
                "MAM", "AEA", "MMM", 'M', "ingotManyullyn", 'E', Items.ender_eye, 'A', amber);

        IArcaneRecipe recipeAmulet = ThaumcraftApi.addArcaneCraftingRecipe("SOLAR_AMULET", new ItemStack(solarAmulet),
                new AspectList().add(Aspect.AIR, 40).add(Aspect.ORDER, 20).add(Aspect.FIRE, 50),
                "LPL", "SAS", "SSS", 'P', solarPanel, 'L', silverLog, 'S', thaumIngot, 'A', amber);

        IArcaneRecipe recipeBelt = ThaumcraftApi.addArcaneCraftingRecipe("MINER_BELT", new ItemStack(minerBelt),
                new AspectList().add(Aspect.ENTROPY, 40).add(Aspect.EARTH, 40).add(Aspect.FIRE, 10),
                "JRJ", "DDD", "AEA", 'D', Items.diamond, 'R', Blocks.redstone_block, 'J', Blocks.iron_block, 'E', Items.ender_pearl, 'A', amber);

        IArcaneRecipe recipeKnowledge = ThaumcraftApi.addArcaneCraftingRecipe("KNOWLEDGE_CRAFT", knowledgeFragment,
                new AspectList().add(Aspect.ORDER, 5),
                "GPG", "PBP", "GPG", 'G', Items.gold_nugget, 'P', enderDust, 'B', Items.paper);

        IArcaneRecipe recipeCap = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandCapManullyn),
                new AspectList().add(Aspect.FIRE, 20).add(Aspect.ENTROPY, 20),
                "MMM", "M M", 'M', "ingotManyullyn");

        IArcaneRecipe recipeRod = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandRodIridium),
                new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.FIRE, 50),
                "  I", " I ", "I  ", 'I', iridiumOre);

        ItemStack fullWand = createWand(WAND_ROD_IRIDIUM, WAND_CAP_MANULLYN);
        IArcaneRecipe recipeWandMain = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", fullWand,
                new AspectList().add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100).add(Aspect.FIRE, 100)
                        .add(Aspect.AIR, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100),
                " EC", " R ", "CE ", 'C', wandCapManullyn, 'R', wandRodIridium, 'E', energyCrystal);

        ThaumcraftApi.addArcaneCraftingRecipe("",
                createWand(WAND_ROD_IRIDIUM, (WandCap)WandCap.caps.get("gold")),
                new AspectList().add(Aspect.ORDER, 25).add(Aspect.ENTROPY, 25).add(Aspect.FIRE, 25)
                        .add(Aspect.AIR, 25).add(Aspect.EARTH, 25).add(Aspect.WATER, 25),
                "  C",
                " R ",
                "C  ",
                'C', goldCap,
                'R', wandRodIridium);

        ThaumcraftApi.addArcaneCraftingRecipe("",
                createWand((WandRod)WandRod.rods.get("silverwood"), WAND_CAP_MANULLYN),
                new AspectList().add(Aspect.ORDER, 30).add(Aspect.ENTROPY, 60).add(Aspect.FIRE, 60)
                        .add(Aspect.AIR, 30).add(Aspect.EARTH, 60).add(Aspect.WATER, 30),
                "  C",
                " R ",
                "C  ",
                'C', wandCapManullyn,
                'R', silverRod);

        ThaumcraftApi.addArcaneCraftingRecipe("",
                createWand(WAND_ROD_IRIDIUM, (WandCap)WandCap.caps.get("thaumium")),
                new AspectList().add(Aspect.ORDER, 70).add(Aspect.ENTROPY, 100).add(Aspect.FIRE, 100)
                        .add(Aspect.AIR, 70).add(Aspect.EARTH, 100).add(Aspect.WATER, 70),
                "  C",
                " R ",
                "C  ",
                'C', thaumiumCap,
                'R', wandRodIridium);

        new ResearchItem("AKADAEMON_WAND", CAT_ID,
                new AspectList().add(Aspect.METAL, 10).add(Aspect.ENERGY, 10).add(Aspect.MECHANISM, 10),
                0, 0, 3, fullWand)
                .setPages(
                        new ResearchPage("tc.research_page.AKADAEMON_WAND.1"),
                        new ResearchPage(recipeCap),
                        new ResearchPage(recipeRod),
                        new ResearchPage(recipeWandMain)
                ).registerResearchItem();

        new ResearchItem("MINER_BELT", CAT_ID,
                new AspectList().add(Aspect.FLIGHT, 10).add(Aspect.MINE, 10).add(Aspect.TOOL, 10).add(Aspect.TRAVEL, 10),
                4, 0, 2, new ItemStack(minerBelt))
                .setPages(
                        new ResearchPage("tc.research_page.MINER_BELT.1"),
                        new ResearchPage(recipeBelt)
                ).registerResearchItem();

        new ResearchItem("WORLD_RING", CAT_ID,
                new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.METAL, 10),
                4, 2, 2, new ItemStack(worldRing))
                .setPages(
                        new ResearchPage("tc.research_page.WORLD_RING.1"),
                        new ResearchPage(recipeRing)
                ).registerResearchItem();

        new ResearchItem("SOLAR_AMULET", CAT_ID,
                new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.LIGHT, 10),
                4, -2, 2, new ItemStack(solarAmulet))
                .setPages(
                        new ResearchPage("tc.research_page.SOLAR_AMULET.1"),
                        new ResearchPage(recipeAmulet)
                ).registerResearchItem();

        new ResearchItem("KNOWLEDGE_CRAFT", CAT_ID,
                new AspectList().add(Aspect.MIND, 10).add(Aspect.ORDER, 5),
                -2, 2, 1, knowledgeFragment)
                .setPages(
                        new ResearchPage("tc.research_page.KNOWLEDGE_CRAFT.1"),
                        new ResearchPage(recipeKnowledge)
                ).registerResearchItem();

        new ResearchItem("THAUM_TRANSFORMER", CAT_ID,
                new AspectList().add(Aspect.MECHANISM, 10).add(Aspect.ENERGY, 20).add(Aspect.EXCHANGE, 10),
                2, 2, 2, new ItemStack(thaumTransformer))
                .setPages(
                        new ResearchPage("tc.research_page.THAUM_TRANSFORMER.1"),
                        new ResearchPage(recipeTransformer)
                ).registerResearchItem();

        new ResearchItem("AMBER_FIBER", CAT_ID,
                new AspectList().add(Aspect.VOID, 10).add(Aspect.ENERGY, 10).add(Aspect.MAGIC, 10),
                2, 4, 2, new ItemStack(amberFiber))
                .setPages(
                        new ResearchPage("tc.research_page.AMBER_FIBER.1"),
                        new ResearchPage(recipeFiber)
                ).registerResearchItem();
    }

    private static ItemStack createWand(WandRod rod, WandCap cap) {
        ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
        ((ItemWandCasting) wand.getItem()).setRod(wand, rod);
        ((ItemWandCasting) wand.getItem()).setCap(wand, cap);
        return wand;
    }
}