package com.akadaemon.addon.recipes;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.IArcaneRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchItem;
import thaumcraft.api.research.ResearchPage;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.config.ConfigItems;
import thaumcraft.common.items.wands.ItemWandCasting;

import static com.akadaemon.addon.blocks.ModBlocks.*;
import static com.akadaemon.addon.items.ModItems.*;
import static thaumcraft.api.ThaumcraftApi.getCraftingRecipes;

public class ThaumcraftIntegration {

    public static WandRod WAND_ROD_IRIDIUM, WAND_ROD_IRIDIUM_TITAN;
    public static WandCap WAND_CAP_MANYULLYN, WAND_CAP_MYTHRIL;
    public static final String CAT_ID = "AKADAEMON";

    public static void init() {
        initWandComponents();
        registerCategory();
        registerRecipes();
        registerResearch();
    }

    private static void initWandComponents() {
        WAND_ROD_IRIDIUM = new WandRod("iridium", 250, new ItemStack(wandRodIridium), 10);
        WAND_ROD_IRIDIUM.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_rod_iridium_model.png"));

        WAND_CAP_MANYULLYN = new WandCap("manyullyn", 0.88F, new ItemStack(wandCapManyullyn), 10);
        WAND_CAP_MANYULLYN.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_cap_manyullyn_model.png"));

        WAND_ROD_IRIDIUM_TITAN = new WandRod("iridium_titan", 500, new ItemStack(wandRodIridiumTitan), 20);
        WAND_ROD_IRIDIUM_TITAN.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_rod_iridium_titan_model.png"));

        WAND_CAP_MYTHRIL = new WandCap("mythril", 0.8F, new ItemStack(wandCapMythril), 20);
        WAND_CAP_MYTHRIL.setTexture(new ResourceLocation(AkadaemonAddon.MODID, "textures/models/wand_cap_mythril_model.png"));
    }

    private static void registerCategory() {
        ResearchCategories.registerCategory(CAT_ID,
                new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/logo.png"),
                new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/gui_researchback.png"));
    }

    private static CrucibleRecipe recMythril, recTitan, recAdamantit;
    private static IArcaneRecipe recTransformer, recFiber, recKnowledge, recHeart, recPanel, recRing, recAmulet, recBelt, recDrill, recChip, recMod, recFocusTele, recFocusSun, recCap, recRod, recWandMain, recWandMan, recWandSilver, recWandThaum;
    private static InfusionRecipe recQHelmet, recQChest, recQLegs, recQBoots, recSuperPanel, recInfCap, recInfRod, recNeural, recTrinity;

    private static void registerRecipes() {
        registerCrucibleRecipes();
        registerArcaneRecipes();
        registerInfusionRecipes();
    }

    private static void registerCrucibleRecipes() {
        recMythril = ThaumcraftApi.addCrucibleRecipe("TRY_MYTHRIL", new ItemStack(ingotMythril), "ingotManyullyn", new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENTROPY, 16));
        recTitan = ThaumcraftApi.addCrucibleRecipe("TRY_TITAN", new ItemStack(ingotTitan), "ingotCobalt", new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ORDER, 16));
        recAdamantit = ThaumcraftApi.addCrucibleRecipe("TRY_ADAMANTIT", new ItemStack(ingotAdamantit), "ingotArdite", new AspectList().add(Aspect.MAGIC, 16).add(Aspect.FIRE, 16));
    }

    private static void registerArcaneRecipes() {
        Object solar = ExternalItems.solarPanel;
        if (Loader.isModLoaded("AdvancedSolarPanel")) {
            Block asp = GameRegistry.findBlock("AdvancedSolarPanel", "BlockAdvSolarPanel");
            if (asp != null) solar = new ItemStack(asp, 1, 2);
        }

        recTransformer = ThaumcraftApi.addArcaneCraftingRecipe("THAUM_TRANSFORMER", new ItemStack(thaumTransformer), new AspectList().add(Aspect.EARTH, 30).add(Aspect.AIR, 30).add(Aspect.ORDER, 20), "SPS", "SMS", "TTT", 'T', blockTitan, 'S', "ingotMithril", 'M', ExternalItems.mfe, 'P', ExternalItems.solarPanel);
        recFiber = ThaumcraftApi.addArcaneCraftingRecipe("AMBER_FIBER", new ItemStack(amberFiber, 2), new AspectList().add(Aspect.ORDER, 10).add(Aspect.AIR, 10), " D ", "ATA", " D ", 'A', ExternalItems.blockAmber, 'T', ExternalItems.blockTube, 'D', enderDust);
        recKnowledge = ThaumcraftApi.addArcaneCraftingRecipe("KNOWLEDGE_CRAFT", ExternalItems.knowledgeFragment, new AspectList().add(Aspect.ORDER, 5), "GPG", "PBP", "GPG", 'G', Items.gold_nugget, 'P', enderDust, 'B', Items.paper);
        recHeart = ThaumcraftApi.addArcaneCraftingRecipe("HEART_CONTAINER", new ItemStack(heartContainer), new AspectList().add(Aspect.WATER, 5).add(Aspect.EARTH, 5), "RZR", "ZRZ", "RZR", 'R', Items.redstone, 'Z', ExternalItems.zombieBrain);
        recPanel = ThaumcraftApi.addArcaneCraftingRecipe("COMPRESSED_PANEL", new ItemStack(compressedPanel), new AspectList().add(Aspect.FIRE, 20).add(Aspect.ORDER, 20), "AAA", "PAP", "AAA", 'A', blockAdamantit, 'P', solar);
        recRing = ThaumcraftApi.addArcaneCraftingRecipe("WORLD_RING", new ItemStack(worldRing), new AspectList().add(Aspect.AIR, 20).add(Aspect.WATER, 20).add(Aspect.EARTH, 20).add(Aspect.FIRE, 20), "MAM", "AEA", "MMM", 'M', "ingotMithril", 'E', Items.ender_eye, 'A', ExternalItems.amber);
        recAmulet = ThaumcraftApi.addArcaneCraftingRecipe("SOLAR_AMULET", new ItemStack(solarAmulet), new AspectList().add(Aspect.AIR, 40).add(Aspect.ORDER, 20).add(Aspect.FIRE, 50), "LPL", "SAS", "SSS", 'P', ExternalItems.solarPanel, 'L', "ingotAdamantit", 'S', "ingotMithril", 'A', ExternalItems.amber);
        recBelt = ThaumcraftApi.addArcaneCraftingRecipe("MINER_BELT", new ItemStack(minerBelt), new AspectList().add(Aspect.ENTROPY, 40).add(Aspect.EARTH, 40).add(Aspect.FIRE, 10), "MEM", "ARA", "MEM", 'R', "blockAdamantit", 'M', "ingotMithril", 'E', Items.ender_pearl, 'A', ExternalItems.amber);
        recDrill = ThaumcraftApi.addArcaneCraftingRecipe("TITAN_DRILL", new ItemStack(titanDrill), new AspectList().add(Aspect.EARTH, 30).add(Aspect.ENTROPY, 40).add(Aspect.ORDER, 10).add(Aspect.FIRE, 10), "MAM", "TIT", "MTM", 'T', blockTitan, 'M', "ingotMithril", 'I', ExternalItems.iridiumDrill, 'A', blockAdamantit);
        recChip = ThaumcraftApi.addArcaneCraftingRecipe("TINKER_MODS", new ItemStack(expansionChip), new AspectList().add(Aspect.ORDER, 30).add(Aspect.FIRE, 20), "MAM", " E ", "CAC", 'C', ExternalItems.carbonPlate, 'E', ExternalItems.energyCrystal, 'A', ExternalItems.advCircuit, 'M', "ingotMithril");
        recMod = ThaumcraftApi.addArcaneCraftingRecipe("TINKER_MODS", new ItemStack(compositeMod), new AspectList().add(Aspect.ENTROPY, 50).add(Aspect.WATER, 30).add(Aspect.EARTH, 40), "MMM", "TTT", "AAA", 'M', "blockMithril", 'T', "blockTitan", 'A', "blockAdamantit");
        recFocusTele = ThaumcraftApi.addArcaneCraftingRecipe("FOCUS_TELEPORT", new ItemStack(focusTeleport), new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 20), "EME", "TFT", "EME", 'E', Items.ender_pearl, 'F', ConfigItems.itemFocusPortableHole, 'T', "ingotTitan", 'M', "ingotMithril");
        recFocusSun = ThaumcraftApi.addArcaneCraftingRecipe("FOCUS_SUNSTRIKE", new ItemStack(focusSunstrike), new AspectList().add(Aspect.FIRE, 50).add(Aspect.ORDER, 30), "AGA", "GIG", "AGA", 'I', ConfigItems.itemFocusFire, 'G', "ingotAdamantit", 'A', ExternalItems.amber);
        recCap = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandCapManyullyn), new AspectList().add(Aspect.FIRE, 20).add(Aspect.ENTROPY, 20), "MMM", "M M", 'M', "ingotManyullyn");
        recRod = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandRodIridium), new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.FIRE, 50), "  I", " I ", "I  ", 'I', ExternalItems.iridiumOre);

        recWandMain = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND_INFUSION", createWand(WAND_ROD_IRIDIUM_TITAN, WAND_CAP_MYTHRIL), new AspectList().add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100).add(Aspect.FIRE, 100).add(Aspect.AIR, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100), " EC", "MRE", "CM ", 'C', wandCapMythril, 'R', wandRodIridiumTitan, 'E', ExternalItems.lapotronCrystal, 'M', "ingotMithril");
        recWandMan = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", createWand(WAND_ROD_IRIDIUM, WAND_CAP_MANYULLYN), new AspectList().add(Aspect.ORDER, 65).add(Aspect.ENTROPY, 65).add(Aspect.FIRE, 65).add(Aspect.AIR, 65).add(Aspect.EARTH, 65).add(Aspect.WATER, 65), "  C", " R ", "C  ", 'C', wandCapManyullyn, 'R', wandRodIridium);

        ItemStack silverRod = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:WandRod"), 1, 2);
        recWandSilver = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND_SILVER", createWand((WandRod) WandRod.rods.get("silverwood"), WAND_CAP_MANYULLYN), new AspectList().add(Aspect.ORDER, 45).add(Aspect.ENTROPY, 45).add(Aspect.FIRE, 45).add(Aspect.AIR, 45).add(Aspect.EARTH, 45).add(Aspect.WATER, 45), "  C", " R ", "C  ", 'C', wandCapManyullyn, 'R', silverRod);

        ItemStack thaumiumCap = new ItemStack((Item) Item.itemRegistry.getObject("Thaumcraft:WandCap"), 1, 2);
        recWandThaum = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND_THAUMIUM", createWand(WAND_ROD_IRIDIUM, (WandCap) WandCap.caps.get("thaumium")), new AspectList().add(Aspect.ORDER, 50).add(Aspect.ENTROPY, 50).add(Aspect.FIRE, 50).add(Aspect.AIR, 50).add(Aspect.EARTH, 50).add(Aspect.WATER, 50), "  C", " R ", "C  ", 'C', thaumiumCap, 'R', wandRodIridium);
    }

    private static void registerInfusionRecipes() {
        recQHelmet = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQHelmet), 6, new AspectList().add(Aspect.ARMOR, 64).add(Aspect.ENERGY, 64).add(Aspect.SENSES, 32).add(Aspect.MIND, 32), ExternalItems.qHelmet, new ItemStack[]{new ItemStack(ConfigItems.itemGoggles), ExternalItems.lapotronCrystal, new ItemStack(blockMythril), new ItemStack(iridiumComposite), new ItemStack(titanHelmet), new ItemStack(mythrilHelmet), new ItemStack(adamantitHelmet), new ItemStack(iridiumComposite), new ItemStack(blockMythril), ExternalItems.lapotronCrystal});
        recQChest = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQChest), 8, new AspectList().add(Aspect.ARMOR, 128).add(Aspect.ENERGY, 128).add(Aspect.FLIGHT, 64).add(Aspect.METAL, 64), ExternalItems.qChest, new ItemStack[]{ExternalItems.jetpack, new ItemStack(blockMythril), new ItemStack(blockMythril), ExternalItems.lapotronCrystal, new ItemStack(iridiumComposite), new ItemStack(titanChest), new ItemStack(mythrilChest), new ItemStack(adamantitChest), new ItemStack(iridiumComposite), ExternalItems.lapotronCrystal, new ItemStack(blockMythril), new ItemStack(blockMythril)});
        recQLegs = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQLegs), 6, new AspectList().add(Aspect.ARMOR, 64).add(Aspect.ENERGY, 64).add(Aspect.TRAVEL, 64).add(Aspect.ELDRITCH, 32), ExternalItems.qLegs, new ItemStack[]{new ItemStack(blockMythril), new ItemStack(Items.ender_eye), ExternalItems.lapotronCrystal, new ItemStack(iridiumComposite), new ItemStack(titanLegs), new ItemStack(mythrilLegs), new ItemStack(adamantitLegs), new ItemStack(iridiumComposite), ExternalItems.lapotronCrystal, new ItemStack(Items.ender_eye)});
        recQBoots = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQBoots), 6, new AspectList().add(Aspect.ARMOR, 48).add(Aspect.ENERGY, 64).add(Aspect.TRAVEL, 32).add(Aspect.AIR, 32), ExternalItems.qBoots, new ItemStack[]{new ItemStack(iridiumComposite), new ItemStack(blockMythril), ExternalItems.lapotronCrystal, new ItemStack(titanBoots), new ItemStack(mythrilBoots), new ItemStack(adamantitBoots), ExternalItems.lapotronCrystal, new ItemStack(blockMythril)});
        recSuperPanel = new InfusionElectricRecipe("COMPRESSED_SUPER_PANEL", new ItemStack(compressedSuperPanel), 3, new AspectList().add(Aspect.ENERGY, 64).add(Aspect.CRYSTAL, 32).add(Aspect.METAL, 48).add(Aspect.VOID, 16), new ItemStack(compressedPanel), new ItemStack[]{new ItemStack(compressedPanel), new ItemStack(blockMythril), new ItemStack(compressedPanel), new ItemStack(blockMythril), new ItemStack(compressedPanel), new ItemStack(blockMythril), new ItemStack(compressedPanel), new ItemStack(blockMythril)});
        recInfCap = new InfusionRecipe("AKADAEMON_WAND_INFUSION", new ItemStack(wandCapMythril), 6, new AspectList().add(Aspect.ARMOR, 8).add(Aspect.MAGIC, 32).add(Aspect.METAL, 16).add(Aspect.CRYSTAL, 16), new ItemStack(wandCapManyullyn), new ItemStack[]{new ItemStack(ingotMythril), new ItemStack(ingotMythril), ExternalItems.crystalBal, ExternalItems.crystalBal, new ItemStack(ingotMythril)});
        recInfRod = new InfusionElectricRecipe("AKADAEMON_WAND_INFUSION", new ItemStack(wandRodIridiumTitan), 6, new AspectList().add(Aspect.TOOL, 32).add(Aspect.MAGIC, 64).add(Aspect.METAL, 32), new ItemStack(wandRodIridium), new ItemStack[]{new ItemStack(ingotTitan), ExternalItems.crystalBal, new ItemStack(ingotTitan), ExternalItems.crystalBal, new ItemStack(ingotTitan), ExternalItems.crystalBal, new ItemStack(ingotTitan), ExternalItems.crystalBal});
        recNeural = new InfusionElectricRecipe("NEURAL_INTERFACE", new ItemStack(neuralInterface), 4, new AspectList().add(Aspect.SENSES, 16).add(Aspect.MIND, 24).add(Aspect.TOOL, 8).add(Aspect.ENERGY, 8), ExternalItems.gogglesRevealing, new ItemStack[]{new ItemStack(heartContainer), new ItemStack(ingotMythril), new ItemStack(ingotMythril), ExternalItems.advCircuit, new ItemStack(heartContainer), ExternalItems.advCircuit, new ItemStack(ingotMythril), new ItemStack(ingotMythril)});
        recTrinity = new InfusionElectricRecipe("TRINITY_SWORD", new ItemStack(trinitySword), 5, new AspectList().add(Aspect.WEAPON, 16).add(Aspect.METAL, 32).add(Aspect.MAGIC, 16).add(Aspect.FIRE, 16).add(Aspect.ENERGY, 32), new ItemStack(wandRodIridiumTitan), new ItemStack[]{new ItemStack(mythrilSword), new ItemStack(heartContainer), new ItemStack(iridiumComposite), new ItemStack(titanSword), ExternalItems.lapotronCrystal, new ItemStack(adamantitSword), new ItemStack(iridiumComposite), new ItemStack(heartContainer)});

        getCraftingRecipes().add(recQHelmet);
        getCraftingRecipes().add(recQChest);
        getCraftingRecipes().add(recQLegs);
        getCraftingRecipes().add(recQBoots);
        getCraftingRecipes().add(recSuperPanel);
        getCraftingRecipes().add(recInfCap);
        getCraftingRecipes().add(recInfRod);
        getCraftingRecipes().add(recNeural);
        getCraftingRecipes().add(recTrinity);
    }

    private static void registerResearch() {
        new ResearchItem("TRINITY_ALLOYS", CAT_ID, new AspectList().add(Aspect.MAGIC, 10).add(Aspect.METAL, 10), 0, 0, 2, new ItemStack(ingotMythril))
                .setPages(new ResearchPage("tc.research_page.TRINITY_ALLOYS.1"), new ResearchPage(recMythril), new ResearchPage(recTitan), new ResearchPage(recAdamantit))
                .setRound().setParents("THAUMIUM").registerResearchItem();

        new ResearchItem("AKADAEMON_WAND", CAT_ID, new AspectList().add(Aspect.METAL, 10).add(Aspect.ENERGY, 10).add(Aspect.MECHANISM, 10), 0, 2, 3, createWand(WAND_ROD_IRIDIUM, WAND_CAP_MANYULLYN))
                .setPages(new ResearchPage("tc.research_page.AKADAEMON_WAND.1"), new ResearchPage(recCap), new ResearchPage(recRod), new ResearchPage(recWandSilver), new ResearchPage(recWandThaum), new ResearchPage(recWandMan))
                .setParents("TRINITY_ALLOYS").setParentsHidden("ROD_silverwood").setRound().setConcealed().registerResearchItem();

        new ResearchItem("AKADAEMON_WAND_INFUSION", CAT_ID, new AspectList().add(Aspect.MIND, 10).add(Aspect.ENERGY, 15).add(Aspect.VOID, 15).add(Aspect.MAGIC, 30), 0, 4, 3, createWand(WAND_ROD_IRIDIUM_TITAN, WAND_CAP_MYTHRIL))
                .setPages(new ResearchPage("tc.research_page.AKADAEMON_WAND_INFUSION.1"), new ResearchPage(recInfCap), new ResearchPage(recInfRod), new ResearchPage(recWandMain))
                .setParents("AKADAEMON_WAND").setParentsHidden("INFUSION").setRound().setConcealed().registerResearchItem();

        new ResearchItem("TECH_QUANTUM", CAT_ID, new AspectList().add(Aspect.MAGIC, 30).add(Aspect.ARMOR, 30).add(Aspect.TRAVEL, 30).add(Aspect.FLIGHT, 30), -2, 6, 4, new ItemStack(mythrilQChest))
                .setPages(new ResearchPage("tc.research_page.TECH_QUANTUM.1"), new ResearchPage(recQHelmet), new ResearchPage(recQChest), new ResearchPage(recQLegs), new ResearchPage(recQBoots))
                .setParents("AKADAEMON_WAND_INFUSION").setParentsHidden("ARMORFORTRESS").setConcealed().setSpecial().setRound().registerResearchItem();

        new ResearchItem("MINER_BELT", CAT_ID, new AspectList().add(Aspect.FLIGHT, 10).add(Aspect.MINE, 10).add(Aspect.TOOL, 10).add(Aspect.TRAVEL, 10), 2, -2, 4, new ItemStack(minerBelt))
                .setPages(new ResearchPage("tc.research_page.MINER_BELT.1"), new ResearchPage(recBelt)).setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();

        new ResearchItem("WORLD_RING", CAT_ID, new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.METAL, 10), 0, -2, 4, new ItemStack(worldRing))
                .setPages(new ResearchPage("tc.research_page.WORLD_RING.1"), new ResearchPage(recRing)).setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();

        new ResearchItem("SOLAR_AMULET", CAT_ID, new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.LIGHT, 10), -2, -2, 4, new ItemStack(solarAmulet))
                .setPages(new ResearchPage("tc.research_page.SOLAR_AMULET.1"), new ResearchPage(recAmulet)).setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();

        new ResearchItem("KNOWLEDGE_CRAFT", CAT_ID, new AspectList().add(Aspect.MIND, 10).add(Aspect.ORDER, 5), 3, 2, 1, ExternalItems.knowledgeFragment)
                .setPages(new ResearchPage("tc.research_page.KNOWLEDGE_CRAFT.1"), new ResearchPage(recKnowledge)).setParentsHidden("TRINITY_ALLOYS").setSecondary().registerResearchItem();

        new ResearchItem("HEART_CONTAINER", CAT_ID, new AspectList().add(Aspect.EARTH, 3).add(Aspect.WATER, 3), 5, 2, 1, new ItemStack(heartContainer))
                .setPages(new ResearchPage("tc.research_page.HEART_CONTAINER.1"), new ResearchPage(recHeart)).setParentsHidden("TRINITY_ALLOYS").setSecondary().registerResearchItem();

        new ResearchItem("AMBER_FIBER", CAT_ID, new AspectList().add(Aspect.VOID, 10).add(Aspect.ENERGY, 10).add(Aspect.MAGIC, 10), -3, 2, 3, new ItemStack(amberFiber))
                .setPages(new ResearchPage("tc.research_page.AMBER_FIBER.1"), new ResearchPage(recFiber)).setParents("AKADAEMON_WAND").setConcealed().registerResearchItem();

        new ResearchItem("TITAN_DRILL", CAT_ID, new AspectList().add(Aspect.MECHANISM, 15).add(Aspect.ENERGY, 20).add(Aspect.CRAFT, 20), -4, 0, 3, new ItemStack(titanDrill))
                .setPages(new ResearchPage("tc.research_page.TITAN_DRILL.1"), new ResearchPage(recDrill)).setParents("AMBER_FIBER").setConcealed().registerResearchItem();

        new ResearchItem("THAUM_TRANSFORMER", CAT_ID, new AspectList().add(Aspect.MECHANISM, 20).add(Aspect.ENERGY, 25).add(Aspect.EXCHANGE, 20), -4, -2, 3, new ItemStack(thaumTransformer))
                .setPages(new ResearchPage("tc.research_page.THAUM_TRANSFORMER.1"), new ResearchPage(recTransformer)).setParents("TITAN_DRILL").setConcealed().registerResearchItem();

        new ResearchItem("TINKER_MODS", CAT_ID, new AspectList().add(Aspect.CRAFT, 15).add(Aspect.TOOL, 15).add(Aspect.MINE, 20), 4, 0, 1, new ItemStack(expansionChip))
                .setPages(new ResearchPage("tc.research_page.TINKER_MODS.1"), new ResearchPage(recChip), new ResearchPage(recMod)).setParentsHidden("AKADAEMON_WAND").setConcealed().setSecondary().registerResearchItem();

        new ResearchItem("FOCUS_TELEPORT", CAT_ID, new AspectList().add(Aspect.TRAVEL, 20).add(Aspect.AIR, 10), 2, 6, 2, new ItemStack(focusTeleport))
                .setPages(new ResearchPage("tc.research_page.FOCUS_TELEPORT.1"), new ResearchPage(recFocusTele)).setParents("AKADAEMON_WAND_INFUSION").setConcealed().setRound().registerResearchItem();

        new ResearchItem("FOCUS_SUNSTRIKE", CAT_ID, new AspectList().add(Aspect.FIRE, 10).add(Aspect.ORDER, 15), 3, 4, 2, new ItemStack(focusSunstrike))
                .setPages(new ResearchPage("tc.research_page.FOCUS_SUNSTRIKE.1"), new ResearchPage(recFocusSun)).setParents("AKADAEMON_WAND_INFUSION").setConcealed().setRound().registerResearchItem();

        new ResearchItem("TRINITY_SWORD", CAT_ID, new AspectList().add(Aspect.WEAPON, 15).add(Aspect.METAL, 10).add(Aspect.ENERGY, 10).add(Aspect.FIRE, 10).add(Aspect.MAGIC, 8), 0, 7, 2, new ItemStack(trinitySword))
                .setPages(new ResearchPage("tc.research_page.TRINITY_SWORD.1"), new ResearchPage(recTrinity)).setParents("AKADAEMON_WAND_INFUSION").setConcealed().setSpecial().setRound().registerResearchItem();

        new ResearchItem("NEURAL_INTERFACE", CAT_ID, new AspectList().add(Aspect.MIND, 10).add(Aspect.SENSES, 10).add(Aspect.ENERGY, 5).add(Aspect.TOOL, 5), -3, 4, 2, new ItemStack(neuralInterface))
                .setPages(new ResearchPage("tc.research_page.NEURAL_INTERFACE.1"), new ResearchPage(recNeural)).setParents("AKADAEMON_WAND").setConcealed().setRound().registerResearchItem();

        new ResearchItem("COMPRESSED_PANEL", CAT_ID, new AspectList().add(Aspect.CRYSTAL, 8).add(Aspect.ORDER, 8).add(Aspect.ENERGY, 12).add(Aspect.LIGHT, 10), -3, -4, 4, new ItemStack(compressedPanel))
                .setPages(new ResearchPage("tc.research_page.COMPRESSED_PANEL.1"), new ResearchPage(recPanel)).setParents("SOLAR_AMULET").setRound().setConcealed().registerResearchItem();

        new ResearchItem("COMPRESSED_SUPER_PANEL", CAT_ID, new AspectList().add(Aspect.VOID, 15).add(Aspect.METAL, 12).add(Aspect.ENERGY, 20).add(Aspect.TOOL, 10), -3, -6, 4, new ItemStack(compressedSuperPanel))
                .setPages(new ResearchPage("tc.research_page.COMPRESSED_SUPER_PANEL.1"), new ResearchPage(recSuperPanel)).setParents("COMPRESSED_PANEL").setRound().setSpecial().setConcealed().registerResearchItem();

        registerLoreResearch();
    }

    private static void registerLoreResearch() {
        new ResearchItem("AKADAEMON_LORE", CAT_ID, new AspectList(), 2, 0, 0, new ResourceLocation("akadaemon", "textures/items/lore_tome.png"))
                .setRound().setStub().setAutoUnlock().setPages(new ResearchPage("tc.research_page.AKADAEMON_LORE.1"), new ResearchPage("tc.research_page.AKADAEMON_LORE.2"), new ResearchPage("TRINITY_ALLOYS", "tc.research_page.AKADAEMON_LORE.3"), new ResearchPage("AKADAEMON_WAND", "tc.research_page.AKADAEMON_LORE.4")).registerResearchItem();

        new ResearchItem("AKADAEMON_FINAL", CAT_ID, new AspectList(), 0, -4, 0, new ResourceLocation("akadaemon", "textures/items/item_book.png"))
                .setSpecial().setHidden().setPages(new ResearchPage("tc.research_page.AKADAEMON_FINAL.1"), new ResearchPage("tc.research_page.AKADAEMON_FINAL.2"), new ResearchPage("tc.research_page.AKADAEMON_FINAL.3"), new ResearchPage("tc.research_page.AKADAEMON_FINAL.4")).registerResearchItem();

        new ResearchItem("AKADAEMON_FLUIDS", CAT_ID, new AspectList(), 4, -2, 0, new ItemStack(bucketGlacialQuicksilver))
                .setSecondary().setAutoUnlock().setPages(new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.1"), new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.2"), new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.3"), new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.4")).registerResearchItem();
    }

    public static ItemStack createWand(WandRod rod, WandCap cap) {
        ItemStack wand = new ItemStack(ConfigItems.itemWandCasting);
        ((ItemWandCasting) wand.getItem()).setRod(wand, rod);
        ((ItemWandCasting) wand.getItem()).setCap(wand, cap);
        return wand;
    }

    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        ItemStack stack = event.item.getEntityItem();
        if (stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.dragon_egg)) {
            EntityPlayer player = event.entityPlayer;
            String researchKey = "AKADAEMON_FINAL";
            if (!Thaumcraft.proxy.getResearchManager().isResearchComplete(player.getCommandSenderName(), researchKey)) {
                if (!player.worldObj.isRemote) {
                    Thaumcraft.proxy.getResearchManager().completeResearch(player, researchKey);
                    player.addChatMessage(new ChatComponentText(net.minecraft.util.StatCollector.translateToLocal("chat.akadaemon.dragon_egg")));
                    player.worldObj.playSoundAtEntity(player, "thaumcraft:whispers", 0.5F, 0.7F);
                }
            }
        }
    }
}