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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
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
    public static Item enderDust;
    public static Item bucketGlacialQuicksilver;
    public static Item bucketEtherealPhoton;
    public static Item bucketRubyFlux;
    public static Item wandRodIridium;
    public static Item wandRodIridiumTitan;
    public static Item wandCapManullyn;
    public static Item wandCapMythril;
    public static Item focusTeleport;
    public static Item focusSunstrike;
    public static Item mythrilQHelmet;
    public static Item mythrilQChest;
    public static Item mythrilQLegs;
    public static Item mythrilQBoots;
    public static Item wheatFlour;
    public static Item barleyFlour;
    public static Item barleySeeds;
    public static Item oreExchanger;
    public static Item neuralInterface;

    public static void init() {
        initializeItems();
        registerItems();
    }

    private static void initializeItems() {
        compositeMod = createColoredInfoItem("composite_mod", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "composite_mod");
        expansionChip = createColoredInfoItem("expansion_chip", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "expansion_chip");

        iridiumComposite = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET).setUnlocalizedName("iridium_composite").setTextureName(AkadaemonAddon.MODID + ":iridium_composite").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        wandRodIridium = new ItemBase(EnumChatFormatting.WHITE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_rod_iridium").setTextureName(AkadaemonAddon.MODID + ":wand_rod_iridium").setCreativeTab(AkadaemonAddon.tabAkadaemon);
        wandCapManullyn = new ItemBase(EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_cap_manullyn").setTextureName(AkadaemonAddon.MODID + ":wand_cap_manullyn").setCreativeTab(AkadaemonAddon.tabAkadaemon);
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

        worldRing = new ItemWorldRing();
        solarAmulet = new ItemSolarAmulet();
        minerBelt = new ItemMinerBelt();
        goldenSchnitzel = new ItemGoldenSchnitzel();
        cobaltDust = new ItemDust("cobalt_dust");
        arditeDust = new ItemDust("ardite_dust");
        enderDust = new ItemDust("ender_dust");
        barleySeeds = new ItemBarleySeeds(ModBlocks.barleyCrop);

        neuralInterface = new ItemNeuralInterface();
        mythrilQHelmet = new ItemQuantumMythril(0).setUnlocalizedName("mythril_quantum_helmet");
        mythrilQChest = new ItemQuantumMythril(1).setUnlocalizedName("mythril_quantum_chest");
        mythrilQLegs = new ItemQuantumMythril(2).setUnlocalizedName("mythril_quantum_legs");
        mythrilQBoots = new ItemQuantumMythril(3).setUnlocalizedName("mythril_quantum_boots");
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
        reg(enderDust, "ender_dust");
        reg(iridiumComposite, "iridium_composite");
        reg(wandRodIridium, "wand_rod_iridium");
        reg(wandCapManullyn, "wand_cap_manullyn");
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
