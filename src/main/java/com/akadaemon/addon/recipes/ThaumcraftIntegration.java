package com.akadaemon.addon.recipes;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
                new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/logo.png"),
                new ResourceLocation(AkadaemonAddon.MODID, "textures/gui/gui_researchback.png"));

        registerRecipesAndResearch();

    }

    private static void registerRecipesAndResearch() {
        Item wandCapItem = (Item) Item.itemRegistry.getObject("Thaumcraft:WandCap");
        Item wandRodItem = (Item) Item.itemRegistry.getObject("Thaumcraft:WandRod");

        if (wandCapItem == null || wandRodItem == null) {return;}

        ItemStack thaumiumCap = new ItemStack(wandCapItem, 1, 2);
        ItemStack silverRod = new ItemStack(wandRodItem, 1, 2);

        InfusionRecipe recipeQHelmet = new InfusionElectricRecipe(
                "TECH_QUANTUM",
                new ItemStack(mythrilQHelmet),
                6,
                new AspectList().add(Aspect.ARMOR, 64).add(Aspect.ENERGY, 64).add(Aspect.SENSES, 32).add(Aspect.MIND, 32),
                ExternalItems.qHelmet,
                new ItemStack[] {
                        new ItemStack(ConfigItems.itemGoggles), ExternalItems.energyCrystal,
                        new ItemStack(blockMythril), new ItemStack(iridiumComposite),
                        new ItemStack(iridiumComposite), new ItemStack(blockMythril),
                        ExternalItems.energyCrystal
                });
        InfusionRecipe recipeQChest = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQChest), 8,
                new AspectList().add(Aspect.ARMOR, 128).add(Aspect.ENERGY, 128).add(Aspect.FLIGHT, 64).add(Aspect.METAL, 64), ExternalItems.qChest,
                new ItemStack[] {
                        ExternalItems.jetpack, new ItemStack(blockMythril), new ItemStack(blockMythril),
                        ExternalItems.energyCrystal,
                        new ItemStack(iridiumComposite), new ItemStack(iridiumComposite),
                        ExternalItems.energyCrystal,
                        new ItemStack(blockMythril), new ItemStack(blockMythril)
                });
        InfusionRecipe recipeQLegs = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQLegs), 6,
                new AspectList().add(Aspect.ARMOR, 64).add(Aspect.ENERGY, 64).add(Aspect.TRAVEL, 64).add(Aspect.ELDRITCH, 32), ExternalItems.qLegs,
                new ItemStack[] {
                        new ItemStack(blockMythril), new ItemStack(Items.ender_eye),
                        ExternalItems.energyCrystal, new ItemStack(iridiumComposite),
                        new ItemStack(blockMythril), new ItemStack(iridiumComposite),
                        ExternalItems.energyCrystal, new ItemStack(Items.ender_eye)
                });
        InfusionRecipe recipeQBoots = new InfusionElectricRecipe("TECH_QUANTUM", new ItemStack(mythrilQBoots), 6,
                new AspectList().add(Aspect.ARMOR, 48).add(Aspect.ENERGY, 64).add(Aspect.TRAVEL, 32).add(Aspect.AIR, 32), ExternalItems.qBoots,
                new ItemStack[] {
                        new ItemStack(iridiumComposite), new ItemStack(blockMythril),
                        ExternalItems.energyCrystal, new ItemStack(iridiumComposite),
                        ExternalItems.energyCrystal, new ItemStack(blockMythril)
                });

        ThaumcraftApi.getCraftingRecipes().add(recipeQHelmet);
        ThaumcraftApi.getCraftingRecipes().add(recipeQChest);
        ThaumcraftApi.getCraftingRecipes().add(recipeQLegs);
        ThaumcraftApi.getCraftingRecipes().add(recipeQBoots);
        // Общее
        CrucibleRecipe recipeMythril = ThaumcraftApi.addCrucibleRecipe("TRY_MYTHRIL",
                new ItemStack(ingotMythril), "ingotManyullyn",
                new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ENTROPY, 16));
        CrucibleRecipe recipeTitan = ThaumcraftApi.addCrucibleRecipe("TRY_TITAN",
                new ItemStack(ingotTitan), "ingotCobalt",
                new AspectList().add(Aspect.MAGIC, 16).add(Aspect.ORDER, 16));
        CrucibleRecipe recipeAdamantit = ThaumcraftApi.addCrucibleRecipe("TRY_ADAMANTIT",
                new ItemStack(ingotAdamantit), "ingotArdite",
                new AspectList().add(Aspect.MAGIC, 16).add(Aspect.FIRE, 16));

        IArcaneRecipe recipeTransformer = ThaumcraftApi.addArcaneCraftingRecipe("THAUM_TRANSFORMER", new ItemStack(thaumTransformer),
                    new AspectList().add(Aspect.EARTH, 30).add(Aspect.AIR, 30).add(Aspect.ORDER, 20),
                    "SPS", "SMS", "TTT",
                    'T', blockTitan, 'S', ingotMythril, 'M', ExternalItems.mfe, 'P', ExternalItems.solarPanel);

        IArcaneRecipe recipeFiber = ThaumcraftApi.addArcaneCraftingRecipe("AMBER_FIBER", new ItemStack(amberFiber, 2),
                new AspectList().add(Aspect.ORDER, 10).add(Aspect.AIR, 10),
                " D ", "ATA", " D ",
                'A', ExternalItems.blockAmber, 'T', ExternalItems.blockTube, 'D', enderDust);

        IArcaneRecipe recipeKnowledge = ThaumcraftApi.addArcaneCraftingRecipe("KNOWLEDGE_CRAFT", ExternalItems.knowledgeFragment,
                new AspectList().add(Aspect.ORDER, 5),
                "GPG", "PBP", "GPG", 'G', Items.gold_nugget, 'P', enderDust, 'B', Items.paper);

        // Аксессуары
        IArcaneRecipe recipeRing = ThaumcraftApi.addArcaneCraftingRecipe("WORLD_RING", new ItemStack(worldRing),
                new AspectList().add(Aspect.AIR, 20).add(Aspect.WATER, 20).add(Aspect.EARTH, 20).add(Aspect.FIRE, 20),
                "MAM", "AEA", "MMM", 'M', "ingotMithril", 'E', Items.ender_eye, 'A', ExternalItems.amber);

        IArcaneRecipe recipeAmulet = ThaumcraftApi.addArcaneCraftingRecipe("SOLAR_AMULET", new ItemStack(solarAmulet),
                new AspectList().add(Aspect.AIR, 40).add(Aspect.ORDER, 20).add(Aspect.FIRE, 50),
                "LPL", "SAS", "SSS", 'P', ExternalItems.solarPanel, 'L', "ingotAdamantit", 'S', "ingotMithril", 'A', ExternalItems.amber);

        IArcaneRecipe recipeBelt = ThaumcraftApi.addArcaneCraftingRecipe("MINER_BELT", new ItemStack(minerBelt),
                new AspectList().add(Aspect.ENTROPY, 40).add(Aspect.EARTH, 40).add(Aspect.FIRE, 10),
                "MEM", "ARA", "MEM", 'R', "blockAdamantit", 'M', "ingotMithril", 'E', Items.ender_pearl, 'A', ExternalItems.amber);

        IArcaneRecipe recipeDrill = ThaumcraftApi.addArcaneCraftingRecipe("TITAN_DRILL", new ItemStack(titanDrill),
                new AspectList().add(Aspect.EARTH, 30).add(Aspect.ENTROPY, 40).add(Aspect.ORDER, 10).add(Aspect.FIRE, 10),
                "MAM", "TIT", "MTM",
                'T', blockTitan, 'M', ingotMythril, 'I', ExternalItems.iridiumDrill, 'A', blockAdamantit);

        IArcaneRecipe recipeChip = ThaumcraftApi.addArcaneCraftingRecipe("TINKER_MODS", new ItemStack(expansionChip),
                new AspectList().add(Aspect.ORDER, 30).add(Aspect.FIRE, 20),
                "MAM",
                " E ",
                "CAC",
                'C', ExternalItems.carbonPlate,
                'E', ExternalItems.energyCrystal,
                'A', ExternalItems.advCircuit,
                'M', "ingotMithril");

        IArcaneRecipe recipeMod = ThaumcraftApi.addArcaneCraftingRecipe("TINKER_MODS", new ItemStack(compositeMod),
                new AspectList().add(Aspect.ENTROPY, 50).add(Aspect.WATER, 30).add(Aspect.EARTH, 40),
                "MMM",
                "TTT",
                "AAA",
                'M', "blockMithril",
                'T', "blockTitan",
                'A', "blockAdamantit");

                // Палочка
        IArcaneRecipe recipeCap = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandCapManullyn),
                new AspectList().add(Aspect.FIRE, 20).add(Aspect.ENTROPY, 20),
                "MWM", "M M", 'M', "ingotManyullyn", 'W', "ingotMithril");

        IArcaneRecipe recipeRod = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND", new ItemStack(wandRodIridium),
                new AspectList().add(Aspect.ORDER, 50).add(Aspect.EARTH, 50).add(Aspect.FIRE, 50),
                "  I", " T ", "I  ", 'I', ExternalItems.iridiumOre, 'T', "ingotTitan");

        IArcaneRecipe recipeWandMain = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND",
                createWand(WAND_ROD_IRIDIUM, WAND_CAP_MANULLYN),
                new AspectList().add(Aspect.ORDER, 100).add(Aspect.ENTROPY, 100).add(Aspect.FIRE, 100)
                        .add(Aspect.AIR, 100).add(Aspect.EARTH, 100).add(Aspect.WATER, 100),
                " EC", "MRE", "CM ", 'C', wandCapManullyn, 'R', wandRodIridium, 'E', ExternalItems.energyCrystal, 'M', "ingotMithril");

        IArcaneRecipe recipeWandSilver = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND_SILVER",
                createWand((WandRod)WandRod.rods.get("silverwood"), WAND_CAP_MANULLYN),
                new AspectList().add(Aspect.ORDER, 30).add(Aspect.ENTROPY, 60).add(Aspect.FIRE, 60)
                        .add(Aspect.AIR, 30).add(Aspect.EARTH, 60).add(Aspect.WATER, 30),
                "  C", " R ", "C  ", 'C', wandCapManullyn, 'R', silverRod);

        IArcaneRecipe recipeWandThaumium = ThaumcraftApi.addArcaneCraftingRecipe("AKADAEMON_WAND_THAUMIUM",
                createWand(WAND_ROD_IRIDIUM, (WandCap)WandCap.caps.get("thaumium")),
                new AspectList().add(Aspect.ORDER, 70).add(Aspect.ENTROPY, 100).add(Aspect.FIRE, 100)
                        .add(Aspect.AIR, 70).add(Aspect.EARTH, 100).add(Aspect.WATER, 70),
                "  C", " R ", "C  ", 'C', thaumiumCap, 'R', wandRodIridium);

        new ResearchItem("TRINITY_ALLOYS", CAT_ID, new AspectList().add(Aspect.MAGIC, 10).add(Aspect.METAL, 10), 0, 0, 2, new ItemStack(ingotMythril))
                .setPages(new ResearchPage("tc.research_page.TRINITY_ALLOYS.1"),
                        new ResearchPage(recipeMythril),
                        new ResearchPage(recipeTitan),
                        new ResearchPage(recipeAdamantit))
                .setRound()
                .setParents("THAUMIUM")
                .registerResearchItem();

        new ResearchItem("AKADAEMON_WAND", CAT_ID,
                new AspectList().add(Aspect.METAL, 10).add(Aspect.ENERGY, 10).add(Aspect.MECHANISM, 10), 0, 2, 3, new ItemStack(wandRodIridium))
                .setPages(new ResearchPage("tc.research_page.AKADAEMON_WAND.1"),
                        new ResearchPage(recipeCap),
                        new ResearchPage(recipeRod),
                        new ResearchPage(recipeWandSilver),
                        new ResearchPage(recipeWandThaumium),
                        new ResearchPage(recipeWandMain))
                .setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();
        new ResearchItem("TECH_QUANTUM", CAT_ID, new AspectList().add(Aspect.MAGIC, 30).add(Aspect.ARMOR, 30).add(Aspect.TRAVEL, 30).add(Aspect.FLIGHT, 30), 0, 4, 4, new ItemStack(mythrilQChest))
                .setPages(
                        new ResearchPage("tc.research_page.TECH_QUANTUM.1"),
                        new ResearchPage((InfusionRecipe)recipeQHelmet),
                        new ResearchPage((InfusionRecipe)recipeQChest),
                        new ResearchPage((InfusionRecipe)recipeQLegs),
                        new ResearchPage((InfusionRecipe)recipeQBoots)
                )
                .setParents("AKADAEMON_WAND")
                .setConcealed()
                .setSpecial()
                .setRound()
                .registerResearchItem();

        new ResearchItem("MINER_BELT", CAT_ID,
                new AspectList().add(Aspect.FLIGHT, 10).add(Aspect.MINE, 10).add(Aspect.TOOL, 10).add(Aspect.TRAVEL, 10), 2, -2, 4, new ItemStack(minerBelt))
                .setPages(new ResearchPage("tc.research_page.MINER_BELT.1"), new ResearchPage(recipeBelt))
                .setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();
        new ResearchItem("WORLD_RING", CAT_ID,
                new AspectList().add(Aspect.AIR, 10).add(Aspect.FIRE, 10).add(Aspect.WATER, 10).add(Aspect.METAL, 10), 0, -2, 4, new ItemStack(worldRing))
                .setPages(new ResearchPage("tc.research_page.WORLD_RING.1"), new ResearchPage(recipeRing))
                .setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();
        new ResearchItem("SOLAR_AMULET", CAT_ID,
                new AspectList().add(Aspect.CRYSTAL, 10).add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.LIGHT, 10), -2, -2, 4, new ItemStack(solarAmulet))
                .setPages(new ResearchPage("tc.research_page.SOLAR_AMULET.1"), new ResearchPage(recipeAmulet))
                .setParents("TRINITY_ALLOYS").setRound().setConcealed().registerResearchItem();

        new ResearchItem("KNOWLEDGE_CRAFT", CAT_ID,
                new AspectList().add(Aspect.MIND, 10).add(Aspect.ORDER, 5), 3, 2, 1, ExternalItems.knowledgeFragment)
                .setPages(new ResearchPage("tc.research_page.KNOWLEDGE_CRAFT.1"), new ResearchPage(recipeKnowledge)).setParentsHidden("TRINITY_ALLOYS").setSecondary().registerResearchItem();

        new ResearchItem("AMBER_FIBER", CAT_ID,
                new AspectList().add(Aspect.VOID, 10).add(Aspect.ENERGY, 10).add(Aspect.MAGIC, 10), -3, 2, 3, new ItemStack(amberFiber))
                .setPages(new ResearchPage("tc.research_page.AMBER_FIBER.1"), new ResearchPage(recipeFiber)).setParents("AKADAEMON_WAND").setConcealed().registerResearchItem();

        new ResearchItem("TITAN_DRILL", CAT_ID,
                new AspectList().add(Aspect.MECHANISM, 15).add(Aspect.ENERGY, 20).add(Aspect.CRAFT, 20), -4, 0, 3, new ItemStack(titanDrill))
                .setPages(new ResearchPage("tc.research_page.TITAN_DRILL.1"), new ResearchPage(recipeDrill)).setParents("AMBER_FIBER").setConcealed().registerResearchItem();

        new ResearchItem("THAUM_TRANSFORMER", CAT_ID,
                new AspectList().add(Aspect.MECHANISM, 20).add(Aspect.ENERGY, 25).add(Aspect.EXCHANGE, 20), -4, -2, 3, new ItemStack(thaumTransformer))
                .setPages(new ResearchPage("tc.research_page.THAUM_TRANSFORMER.1"), new ResearchPage(recipeTransformer)).setParents("TITAN_DRILL").setConcealed().registerResearchItem();

        new ResearchItem("TINKER_MODS", CAT_ID,
                new AspectList().add(Aspect.CRAFT, 15).add(Aspect.TOOL, 15).add(Aspect.MINE, 20), 4, 0, 1, new ItemStack(expansionChip))
                .setPages(new ResearchPage("tc.research_page.TINKER_MODS.1"), new ResearchPage(recipeChip), new ResearchPage(recipeMod)).setParentsHidden("AKADAEMON_WAND").setConcealed().setSecondary().registerResearchItem();


        new ResearchItem("AKADAEMON_LORE", CAT_ID,
                new AspectList(),
                2, 0,0,
                new ResourceLocation("akadaemon", "textures/items/lore_tome.png"))
                .setRound()
                .setStub()
                .setAutoUnlock()
                .setPages(
                        new ResearchPage("tc.research_page.AKADAEMON_LORE.1"),
                        new ResearchPage("tc.research_page.AKADAEMON_LORE.2"),
                        new ResearchPage("TRINITY_ALLOYS", "tc.research_page.AKADAEMON_LORE.3"),
                        new ResearchPage("AKADAEMON_WAND", "tc.research_page.AKADAEMON_LORE.4"))
                .registerResearchItem();

        new ResearchItem("AKADAEMON_FINAL", CAT_ID,
                new AspectList(),
                0, -4,0,
                new ResourceLocation("akadaemon", "textures/items/item_book.png"))
                .setSpecial()
                .setHidden()
                .setPages(
                        new ResearchPage("tc.research_page.AKADAEMON_FINAL.1"),
                        new ResearchPage("tc.research_page.AKADAEMON_FINAL.2"),
                        new ResearchPage("tc.research_page.AKADAEMON_FINAL.3"),
                        new ResearchPage("tc.research_page.AKADAEMON_FINAL.4"))
                .registerResearchItem();

        new ResearchItem("AKADAEMON_FLUIDS", CAT_ID,
                new AspectList(),
                4, -2,0, new ItemStack(AkadaemonAddon.bucketGlacialQuicksilver))
                .setSecondary()
                .setAutoUnlock()
                .setPages(
                        new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.1"),
                        new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.2"),
                        new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.3"),
                        new ResearchPage("tc.research_page.AKADAEMON_FLUIDS.4"))
                .registerResearchItem();

    }

    private static ItemStack createWand(WandRod rod, WandCap cap) {
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

                    String localizedMessage = net.minecraft.util.StatCollector.translateToLocal("chat.akadaemon.dragon_egg");
                    player.addChatMessage(new ChatComponentText(localizedMessage));

                    player.worldObj.playSoundAtEntity(player, "thaumcraft:whispers", 0.5F, 0.7F);
                }
            }
        }
    }
}