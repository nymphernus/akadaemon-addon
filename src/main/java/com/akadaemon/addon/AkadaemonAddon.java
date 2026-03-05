package com.akadaemon.addon;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(modid = AkadaemonAddon.MODID, name = AkadaemonAddon.NAME, version = AkadaemonAddon.VERSION,
        dependencies = "required-after:TConstruct;required-after:IC2;required-after:Thaumcraft;required-after:Baubles")
public class AkadaemonAddon {
    public static final String MODID = "akadaemon";
    public static final String NAME = "Akadaemon Addon";
    public static final String VERSION = "1.4.1";
    public static final Logger logger = LogManager.getLogger(NAME);

    public static Item compositeMod, expansionChip, worldRing, solarAmulet, minerBelt, goldenSchnitzel;
    public static Item cobaltDust, arditeDust, enderDust, iridiumComposite, wandRodIridium, wandCapManullyn, ingotMythril, ingotTitan, ingotAdamantit;
    public static Item mythrilQHelmet, mythrilQChest, mythrilQLegs, mythrilQBoots;
    public static Block thaumTransformer, amberFiber, blockMythril, blockTitan, blockAdamantit;


    public static CreativeTabs tabAkadaemon = new CreativeTabs("akadaemon") {
        @Override
        public Item getTabIconItem() { return worldRing; }
    };

    @Mod.Instance(MODID)
    public static AkadaemonAddon instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event);

        initializeBlocks();
        initializeItems();
        registerObjects();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        Log(event);
    }

    private void initializeBlocks() {
        blockMythril = new BlockMaterial(Material.iron)
                .setBlockName("block_mythril")
                .setBlockTextureName(MODID + ":block_mythril")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(tabAkadaemon);
        blockTitan = new BlockMaterial(Material.iron)
                .setBlockName("block_titan")
                .setBlockTextureName(MODID + ":block_titan")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(tabAkadaemon);
        blockAdamantit = new BlockMaterial(Material.iron)
                .setBlockName("block_adamantit")
                .setBlockTextureName(MODID + ":block_adamantit")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(tabAkadaemon);

        thaumTransformer = new BlockThaumTransformer();
        amberFiber = new BlockAmberFiber();
    }

    private void initializeItems() {
        compositeMod = createColoredInfoItem("composite_mod", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "composite_mod");
        expansionChip = createColoredInfoItem("expansion_chip", EnumChatFormatting.YELLOW, EnumChatFormatting.ITALIC, "expansion_chip");

        iridiumComposite = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET).setUnlocalizedName("iridium_composite").setTextureName(MODID + ":iridium_composite").setCreativeTab(tabAkadaemon);
        wandRodIridium = new ItemBase(EnumChatFormatting.WHITE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_rod_iridium").setTextureName(MODID + ":wand_rod_iridium").setCreativeTab(tabAkadaemon);
        wandCapManullyn = new ItemBase(EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.BOLD).setUnlocalizedName("wand_cap_manullyn").setTextureName(MODID + ":wand_cap_manullyn").setCreativeTab(tabAkadaemon);
        ingotMythril = new ItemBase(EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.BOLD).setUnlocalizedName("mythril_ingot").setTextureName(MODID + ":mythril_ingot").setCreativeTab(tabAkadaemon);
        ingotTitan = new ItemBase(EnumChatFormatting.GRAY, EnumChatFormatting.BOLD).setUnlocalizedName("titan_ingot").setTextureName(MODID + ":titan_ingot").setCreativeTab(tabAkadaemon);
        ingotAdamantit = new ItemBase(EnumChatFormatting.RED, EnumChatFormatting.BOLD).setUnlocalizedName("adamantit_ingot").setTextureName(MODID + ":adamantit_ingot").setCreativeTab(tabAkadaemon);

        worldRing = new ItemWorldRing();
        solarAmulet = new ItemSolarAmulet();
        minerBelt = new ItemMinerBelt();
        goldenSchnitzel = new ItemGoldenSchnitzel();
        cobaltDust = new ItemDust("cobalt_dust");
        arditeDust = new ItemDust("ardite_dust");
        enderDust = new ItemDust("ender_dust");

        mythrilQHelmet = new ItemQuantumMythril(0).setUnlocalizedName("mythril_quantum_helmet");
        mythrilQChest = new ItemQuantumMythril(1).setUnlocalizedName("mythril_quantum_chest");
        mythrilQLegs = new ItemQuantumMythril(2).setUnlocalizedName("mythril_quantum_legs");
        mythrilQBoots = new ItemQuantumMythril(3).setUnlocalizedName("mythril_quantum_boots");
    }

    private void registerObjects() {
        regBlock(blockMythril, "block_mythril", EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET);
        regBlock(blockTitan, "block_titan", EnumChatFormatting.GRAY, EnumChatFormatting.RESET);
        regBlock(blockAdamantit, "block_adamantit", EnumChatFormatting.RED, EnumChatFormatting.RESET);
        regBlock(thaumTransformer, "thaum_transformer", EnumChatFormatting.AQUA, EnumChatFormatting.RESET);
        regBlock(amberFiber, "amber_fiber", EnumChatFormatting.YELLOW, EnumChatFormatting.RESET);

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

        GameRegistry.registerTileEntity(TileThaumTransformer.class, "TileThaumTransformer");
        GameRegistry.registerTileEntity(TileAmberFiber.class, "TileAmberFiber");

        OreDictionary.registerOre("dustCobalt", cobaltDust);
        OreDictionary.registerOre("dustArdite", arditeDust);
        OreDictionary.registerOre("dustEnder", enderDust);

        for (String name : ConfigHandler.titanNames) { OreDictionary.registerOre(name, ingotTitan); }
        for (String name : ConfigHandler.mythrilNames) { OreDictionary.registerOre(name, ingotMythril); }
        for (String name : ConfigHandler.mythrilBlockNames) { OreDictionary.registerOre(name, blockMythril); }
        for (String name : ConfigHandler.adamantitNames) { OreDictionary.registerOre(name, ingotAdamantit); }

        OreDictionary.registerOre("blockTitan", blockTitan);
        OreDictionary.registerOre("blockAdamantit", blockAdamantit);
    }

    private void reg(Item item, String name) { GameRegistry.registerItem(item, name); }
    private void regBlock(Block block, String name, EnumChatFormatting color, EnumChatFormatting style) { GameRegistry.registerBlock(block, ItemBlockBase.class, name, color, style); }

    private Item createColoredInfoItem(String name, final EnumChatFormatting color, final EnumChatFormatting style, final String tooltipKey) {
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
        }.setUnlocalizedName(name).setTextureName(MODID + ":" + name).setCreativeTab(tabAkadaemon);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        tconstruct.library.crafting.ModifyBuilder.registerModifier(new ModInfiniteDurability(new ItemStack[] { new ItemStack(compositeMod) }));
        tconstruct.library.crafting.ModifyBuilder.registerModifier(new ModExtraSlot(new ItemStack[] { new ItemStack(expansionChip) }));

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new DurabilityEventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ExternalItems.init();
        ThaumcraftIntegration.init();
        MainRecipes.init();
        ThaumcraftAspects.register();
        if (ConfigHandler.enableOreDump) { OreDump.init(); }
    }

    private void Log(FMLPreInitializationEvent event) {
        String side = event.getSide().isClient() ? "CLIENT" : "SERVER";
        logger.info(MODID + " Version: " + VERSION + " | " + side);
        logger.info("Status : Successfully Initialized");
    }
}