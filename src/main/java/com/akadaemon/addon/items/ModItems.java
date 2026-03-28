package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.blocks.ModBlocks;
import com.akadaemon.addon.fluids.ModFluids;
import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ModItems {
    public static Item compositeMod;
    public static Item expansionChip;
    public static Item iridiumComposite;
    public static Item worldRing;
    public static Item solarAmulet;
    public static Item minerBelt;
    public static Item goldenSchnitzel;
    public static Item friedEggs;
    public static Item barleyBread;
    public static Item barley;
    public static Item ingotMythril;
    public static Item ingotTitan;
    public static Item ingotAdamantit;
    public static Item cobaltDust;
    public static Item arditeDust;
    public static Item obsidianDust;
    public static Item enderDust;
    public static Item bucketGlacialQuicksilver;
    public static Item bucketEtherealPhoton;
    public static Item bucketRubyFlux;
    public static Item wandRodIridium;
    public static Item wandRodIridiumTitan;
    public static Item wandCapManyullyn;
    public static Item wandCapMythril;
    public static Item focusTeleport;
    public static Item focusSunstrike;
    public static Item wheatFlour;
    public static Item barleyFlour;
    public static Item barleySeeds;
    public static Item oreExchanger;
    public static Item neuralInterface;
    public static Item heartContainer;

    public static ItemArmor.ArmorMaterial matManyullyn = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("MANYULLYN", 35, new int[]{3, 8, 6, 3}, 15);
    public static ItemArmor.ArmorMaterial matTitan = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("TITAN", 50, new int[]{4, 9, 7, 4}, 10);
    public static ItemArmor.ArmorMaterial matAdamantit = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("ADAMANTIT", 45, new int[]{4, 9, 6, 4}, 15);
    public static ItemArmor.ArmorMaterial matMythril = net.minecraftforge.common.util.EnumHelper.addArmorMaterial("MYTHRIL", 55, new int[]{5, 10, 8, 5}, 25);

    public static Item mythrilQHelmet, mythrilQChest, mythrilQLegs, mythrilQBoots;
    public static Item manyullynHelmet, manyullynChest, manyullynLegs, manyullynBoots;
    public static Item titanHelmet, titanChest, titanLegs, titanBoots;
    public static Item adamantitHelmet, adamantitChest, adamantitLegs, adamantitBoots;
    public static Item mythrilHelmet, mythrilChest, mythrilLegs, mythrilBoots;

    public static ItemTool.ToolMaterial toolMatTitan = EnumHelper.addToolMaterial("TITAN", 4, 4000, 10.0F, 5.0F, 15);
    public static ItemTool.ToolMaterial toolMatAdamantit = EnumHelper.addToolMaterial("ADAMANTIT", 4, 2500, 12.0F, 6.0F, 18);
    public static ItemTool.ToolMaterial toolMatMythril = EnumHelper.addToolMaterial("MYTHRIL", 5, 2000, 16.0F, 7.0F, 20);
    public static ItemTool.ToolMaterial toolMatTrinity = EnumHelper.addToolMaterial("TRINITY", 6, 6000, 20.0F, 0.0F, 25);

    public static Item titanSword;
    public static Item adamantitSword;
    public static Item mythrilSword;
    public static Item trinitySword;

    public static void init() {
        initializeItems();
        registerItems();
    }

    private static void initializeItems() {
        compositeMod = createColoredInfoItem("composite_mod", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "composite_mod");
        expansionChip = createColoredInfoItem("expansion_chip", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "expansion_chip");

        iridiumComposite = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET).setUnlocalizedName("iridium_composite").setTextureName(AkadaemonAddon.MODID + ":iridium_composite").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        wandRodIridium = new ItemBase(EnumChatFormatting.WHITE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_rod_iridium").setTextureName(AkadaemonAddon.MODID + ":wand_rod_iridium").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        wandCapManyullyn = new ItemBase(EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_cap_manyullyn").setTextureName(AkadaemonAddon.MODID + ":wand_cap_manyullyn").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        ingotMythril = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET).setUnlocalizedName("mythril_ingot").setTextureName(AkadaemonAddon.MODID + ":mythril_ingot").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        ingotTitan = new ItemBase(EnumChatFormatting.GRAY, EnumChatFormatting.RESET).setUnlocalizedName("titan_ingot").setTextureName(AkadaemonAddon.MODID + ":titan_ingot").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        ingotAdamantit = new ItemBase(EnumChatFormatting.RED, EnumChatFormatting.RESET).setUnlocalizedName("adamantit_ingot").setTextureName(AkadaemonAddon.MODID + ":adamantit_ingot").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        bucketGlacialQuicksilver = new ItemBucketBase(ModBlocks.blockGlacialQuicksilver, "bucket_glacial_quicksilver", EnumChatFormatting.AQUA);
        bucketEtherealPhoton = new ItemBucketBase(ModBlocks.blockEtherealPhoton, "bucket_ethereal_photon", EnumChatFormatting.YELLOW);
        bucketRubyFlux = new ItemBucketBase(ModBlocks.blockRubyFlux, "bucket_ruby_flux", EnumChatFormatting.RED);
        wandRodIridiumTitan = new ItemBase(EnumChatFormatting.GRAY, EnumChatFormatting.BOLD).setUnlocalizedName("wand_rod_iridium_titan").setTextureName(AkadaemonAddon.MODID + ":wand_rod_iridium_titan").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        wandCapMythril = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_cap_mythril").setTextureName(AkadaemonAddon.MODID + ":wand_cap_mythril").setCreativeTab(AkadaemonAddon.tabAkadaemon);

        friedEggs = new ItemFoodBase(4, 0.6F, false, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET).setUnlocalizedName("fried_eggs").setTextureName(AkadaemonAddon.MODID + ":fried_eggs");

        wheatFlour = new ItemBase(EnumChatFormatting.GRAY, EnumChatFormatting.RESET).setUnlocalizedName("flour").setTextureName(AkadaemonAddon.MODID + ":flour").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        barleyFlour = new ItemBase(EnumChatFormatting.GRAY, EnumChatFormatting.RESET).setUnlocalizedName("barley_flour").setTextureName(AkadaemonAddon.MODID + ":barley_flour").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        barley = new ItemBase(EnumChatFormatting.WHITE, EnumChatFormatting.RESET).setUnlocalizedName("barley").setTextureName(AkadaemonAddon.MODID + ":barley").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        barleyBread = new ItemFoodBase(6, 0.7F, false, EnumChatFormatting.YELLOW, EnumChatFormatting.RESET).setUnlocalizedName("barley_bread").setTextureName(AkadaemonAddon.MODID + ":barley_bread");

        oreExchanger = new ItemBase(EnumChatFormatting.DARK_BLUE, EnumChatFormatting.RESET).setUnlocalizedName("ore_exchanger").setTextureName(AkadaemonAddon.MODID + ":ore_exchanger").setCreativeTab(AkadaemonAddon.tabAkadaemon);

        focusTeleport = new ItemFocusTeleport();
        focusSunstrike = new ItemFocusSunstrike();

        heartContainer = new ItemBase(EnumChatFormatting.DARK_RED, EnumChatFormatting.RESET).setUnlocalizedName("heart_container").setTextureName(AkadaemonAddon.MODID + ":heart_container").setCreativeTab(AkadaemonAddon.tabAkadaemon);

        worldRing = new ItemWorldRing();
        solarAmulet = new ItemSolarAmulet();
        minerBelt = new ItemMinerBelt();
        goldenSchnitzel = new ItemGoldenSchnitzel();
        cobaltDust = new ItemDust("cobalt_dust");
        arditeDust = new ItemDust("ardite_dust");
        obsidianDust = new ItemDust("obsidian_dust");
        enderDust = new ItemDust("ender_dust");
        barleySeeds = new ItemBarleySeeds(ModBlocks.barleyCrop);

        neuralInterface = new ItemNeuralInterface();
        mythrilQHelmet = new ItemQuantumMythril(0).setUnlocalizedName("mythril_quantum_helmet");
        mythrilQChest = new ItemQuantumMythril(1).setUnlocalizedName("mythril_quantum_chest");
        mythrilQLegs = new ItemQuantumMythril(2).setUnlocalizedName("mythril_quantum_legs");
        mythrilQBoots = new ItemQuantumMythril(3).setUnlocalizedName("mythril_quantum_boots");

        manyullynHelmet = new ItemArmorBase(matManyullyn, 0, "manyullyn", EnumChatFormatting.WHITE).setUnlocalizedName("manyullyn_helmet").setTextureName(AkadaemonAddon.MODID + ":manyullyn_helmet");
        manyullynChest = new ItemArmorBase(matManyullyn, 1, "manyullyn", EnumChatFormatting.WHITE).setUnlocalizedName("manyullyn_chestplate").setTextureName(AkadaemonAddon.MODID + ":manyullyn_chestplate");
        manyullynLegs = new ItemArmorBase(matManyullyn, 2, "manyullyn", EnumChatFormatting.WHITE).setUnlocalizedName("manyullyn_leggings").setTextureName(AkadaemonAddon.MODID + ":manyullyn_leggings");
        manyullynBoots = new ItemArmorBase(matManyullyn, 3, "manyullyn", EnumChatFormatting.WHITE).setUnlocalizedName("manyullyn_boots").setTextureName(AkadaemonAddon.MODID + ":manyullyn_boots");

        titanHelmet = new ItemArmorBase(matTitan, 0, "titan", EnumChatFormatting.GRAY).setUnlocalizedName("titan_helmet").setTextureName(AkadaemonAddon.MODID + ":titan_helmet");
        titanChest = new ItemArmorBase(matTitan, 1, "titan", EnumChatFormatting.GRAY).setUnlocalizedName("titan_chestplate").setTextureName(AkadaemonAddon.MODID + ":titan_chestplate");
        titanLegs = new ItemArmorBase(matTitan, 2, "titan", EnumChatFormatting.GRAY).setUnlocalizedName("titan_leggings").setTextureName(AkadaemonAddon.MODID + ":titan_leggings");
        titanBoots = new ItemArmorBase(matTitan, 3, "titan", EnumChatFormatting.GRAY).setUnlocalizedName("titan_boots").setTextureName(AkadaemonAddon.MODID + ":titan_boots");

        adamantitHelmet = new ItemArmorBase(matAdamantit, 0, "adamantit", EnumChatFormatting.RED).setUnlocalizedName("adamantit_helmet").setTextureName(AkadaemonAddon.MODID + ":adamantit_helmet");
        adamantitChest = new ItemArmorBase(matAdamantit, 1, "adamantit", EnumChatFormatting.RED).setUnlocalizedName("adamantit_chestplate").setTextureName(AkadaemonAddon.MODID + ":adamantit_chestplate");
        adamantitLegs = new ItemArmorBase(matAdamantit, 2, "adamantit", EnumChatFormatting.RED).setUnlocalizedName("adamantit_leggings").setTextureName(AkadaemonAddon.MODID + ":adamantit_leggings");
        adamantitBoots = new ItemArmorBase(matAdamantit, 3, "adamantit", EnumChatFormatting.RED).setUnlocalizedName("adamantit_boots").setTextureName(AkadaemonAddon.MODID + ":adamantit_boots");

        mythrilHelmet = new ItemArmorBase(matMythril, 0, "mythril", EnumChatFormatting.AQUA).setUnlocalizedName("mythril_helmet").setTextureName(AkadaemonAddon.MODID + ":mythril_helmet");
        mythrilChest = new ItemArmorBase(matMythril, 1, "mythril", EnumChatFormatting.AQUA).setUnlocalizedName("mythril_chestplate").setTextureName(AkadaemonAddon.MODID + ":mythril_chestplate");
        mythrilLegs = new ItemArmorBase(matMythril, 2, "mythril", EnumChatFormatting.AQUA).setUnlocalizedName("mythril_leggings").setTextureName(AkadaemonAddon.MODID + ":mythril_leggings");
        mythrilBoots = new ItemArmorBase(matMythril, 3, "mythril", EnumChatFormatting.AQUA).setUnlocalizedName("mythril_boots").setTextureName(AkadaemonAddon.MODID + ":mythril_boots");

        titanSword = new ItemSwordBase(toolMatTitan, "titan_sword", EnumChatFormatting.GRAY);
        adamantitSword = new ItemSwordBase(toolMatAdamantit, "adamantit_sword", EnumChatFormatting.RED);
        mythrilSword = new ItemSwordBase(toolMatMythril, "mythril_sword", EnumChatFormatting.AQUA);
        trinitySword = new ItemSwordTrinity(toolMatTrinity, "trinity_sword", EnumChatFormatting.GOLD);
    }

    private static void registerItems() {
        reg(compositeMod, "composite_mod");
        reg(expansionChip, "expansion_chip");
        reg(worldRing, "world_ring");
        reg(solarAmulet, "solar_amulet");
        reg(minerBelt, "miner_belt");
        reg(goldenSchnitzel, "golden_schnitzel");
        reg(cobaltDust, "cobalt_dust");
        reg(arditeDust, "ardite_dust");
        reg(obsidianDust, "obsidian_dust");
        reg(enderDust, "ender_dust");
        reg(iridiumComposite, "iridium_composite");
        reg(wandRodIridium, "wand_rod_iridium");
        reg(wandCapManyullyn, "wand_cap_manyullyn");
        reg(ingotMythril, "mythril_ingot");
        reg(ingotTitan, "titan_ingot");
        reg(ingotAdamantit, "adamantit_ingot");
        reg(bucketGlacialQuicksilver, "bucket_glacial_quicksilver");
        reg(bucketEtherealPhoton, "bucket_ethereal_photon");
        reg(bucketRubyFlux, "bucket_ruby_flux");
        reg(wandRodIridiumTitan, "wand_rod_iridium_titan");
        reg(wandCapMythril, "wand_cap_mythril");
        reg(focusTeleport, "focus_teleport");
        reg(focusSunstrike, "focus_sunstrike");
        reg(friedEggs, "fried_eggs");
        reg(wheatFlour, "wheat_flour");
        reg(barley, "barley");
        reg(barleyFlour, "barley_flour");
        reg(barleyBread, "barley_bread");
        reg(barleySeeds, "barley_seeds");
        reg(oreExchanger, "ore_exchanger");
        reg(heartContainer, "heart_container");

        reg(manyullynHelmet, "manyullyn_helmet");
        reg(manyullynChest, "manyullyn_chestplate");
        reg(manyullynLegs, "manyullyn_leggings");
        reg(manyullynBoots, "manyullyn_boots");
        reg(titanHelmet, "titan_helmet");
        reg(titanChest, "titan_chestplate");
        reg(titanLegs, "titan_leggings");
        reg(titanBoots, "titan_boots");
        reg(adamantitHelmet, "adamantit_helmet");
        reg(adamantitChest, "adamantit_chestplate");
        reg(adamantitLegs, "adamantit_leggings");
        reg(adamantitBoots, "adamantit_boots");
        reg(mythrilHelmet, "mythril_helmet");
        reg(mythrilChest, "mythril_chestplate");
        reg(mythrilLegs, "mythril_leggings");
        reg(mythrilBoots, "mythril_boots");

        reg(titanSword, "titan_sword");
        reg(adamantitSword, "adamantit_sword");
        reg(mythrilSword, "mythril_sword");
        reg(trinitySword, "trinity_sword");

        FluidContainerRegistry.registerFluidContainer(
                new FluidStack(ModFluids.fluidGlacialQuicksilver, FluidContainerRegistry.BUCKET_VOLUME),
                new ItemStack(bucketGlacialQuicksilver),
                new ItemStack(net.minecraft.init.Items.bucket)
        );
        FluidContainerRegistry.registerFluidContainer(
                new FluidStack(ModFluids.fluidEtherealPhoton, FluidContainerRegistry.BUCKET_VOLUME),
                new ItemStack(bucketEtherealPhoton),
                new ItemStack(net.minecraft.init.Items.bucket)
        );
        FluidContainerRegistry.registerFluidContainer(
                new FluidStack(ModFluids.fluidRubyFlux, FluidContainerRegistry.BUCKET_VOLUME),
                new ItemStack(bucketRubyFlux),
                new ItemStack(net.minecraft.init.Items.bucket)
        );

        OreDictionary.registerOre("cropBarley", barley);
        OreDictionary.registerOre("seedBarley", barleySeeds);
        OreDictionary.registerOre("listAllseed", barleySeeds);
        OreDictionary.registerOre("dustBarley", barleyFlour);
        OreDictionary.registerOre("dustWheat", wheatFlour);
        OreDictionary.registerOre("foodFlour", wheatFlour);
        OreDictionary.registerOre("foodFlour", barleyFlour);
        OreDictionary.registerOre("foodBread", barleyBread);
        OreDictionary.registerOre("dustCobalt", cobaltDust);
        OreDictionary.registerOre("dustArdite", arditeDust);
        OreDictionary.registerOre("dustObsidian", obsidianDust);
        OreDictionary.registerOre("dustEnder", enderDust);

        for (String name : ConfigHandler.titanNames) { OreDictionary.registerOre(name, ingotTitan); }
        for (String name : ConfigHandler.mythrilNames) { OreDictionary.registerOre(name, ingotMythril); }
        for (String name : ConfigHandler.adamantitNames) { OreDictionary.registerOre(name, ingotAdamantit); }
    }

    private static Item createColoredInfoItem(String name, final EnumChatFormatting color, final EnumChatFormatting style, final String tooltipKey) {
        return new Item() {
            @Override
            public String getItemStackDisplayName(ItemStack stack) {
                return color.toString() + (style == EnumChatFormatting.RESET ? "" : style.toString()) + super.getItemStackDisplayName(stack);
            }
            @Override
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
                list.add(StatCollector.translateToLocal("tooltip." + tooltipKey + ".desc"));
                list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip." + tooltipKey + ".extra"));
            }
        }.setUnlocalizedName(name).setTextureName(AkadaemonAddon.MODID + ":" + name).setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    private static void reg(Item item, String name) { GameRegistry.registerItem(item, name); }
}
