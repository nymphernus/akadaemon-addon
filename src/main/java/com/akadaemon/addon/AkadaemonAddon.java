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

import java.util.List;

@Mod(modid = AkadaemonAddon.MODID, name = AkadaemonAddon.NAME, version = AkadaemonAddon.VERSION,
        dependencies = "required-after:TConstruct;required-after:IC2;required-after:Thaumcraft;required-after:Baubles")
public class AkadaemonAddon {

    public static final String MODID = "akadaemon";
    public static final String NAME = "Akadaemon Addon";
    public static final String VERSION = "1.2";

    public static Item compositeMod;
    public static Item expansionChip;
    public static Item worldRing;
    public static Item solarAmulet;
    public static Item minerBelt;
    public static Item goldenSchnitzel;
    public static Block thaumTransformer;
    public static Item cobaltDust;
    public static Item arditeDust;
    public static Item enderDust;
    public static Item iridiumComposite;
    public static Block amberFiber;
    public static Item wandRodIridium;
    public static Item wandCapManullyn;
    public static Block blockMythril;


    public static CreativeTabs tabAkadaemon = new CreativeTabs("akadaemon") {
        @Override
        public Item getTabIconItem() {
            return worldRing;
        }
    };

    @Mod.Instance(MODID)
    public static AkadaemonAddon instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        compositeMod = new Item() {
            @Override
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
                list.add(StatCollector.translateToLocal("tooltip.composite_mod.desc"));
                list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.composite_mod.extra"));
            }
        }.setUnlocalizedName("composite_mod").setTextureName(MODID + ":composite_mod").setCreativeTab(tabAkadaemon);
        expansionChip = new Item() {
            @Override
            @SideOnly(Side.CLIENT)
            public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
                list.add(StatCollector.translateToLocal("tooltip.expansion_chip.desc"));
                list.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("tooltip.expansion_chip.extra"));
            }
        }.setUnlocalizedName("expansion_chip")
                .setTextureName(MODID + ":expansion_chip")
                .setCreativeTab(tabAkadaemon);
        iridiumComposite = new Item().setUnlocalizedName("iridium_composite")
                .setTextureName(MODID + ":iridium_composite")
                .setCreativeTab(tabAkadaemon);
        blockMythril = new BlockMythrilMaterial(Material.iron)
                .setBlockName("block_mythril")
                .setBlockTextureName(MODID + ":block_mythril")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(tabAkadaemon);

        worldRing = new ItemWorldRing();
        solarAmulet = new ItemSolarAmulet();
        minerBelt = new ItemMinerBelt();
        goldenSchnitzel = new ItemGoldenSchnitzel();
        cobaltDust = new ItemDust("cobalt_dust");
        arditeDust = new ItemDust("ardite_dust");
        enderDust = new ItemDust("ender_dust");
        thaumTransformer = new BlockThaumTransformer();
        amberFiber = new BlockAmberFiber().setBlockName("amber_fiber").setCreativeTab(tabAkadaemon);
        wandRodIridium = new Item().setUnlocalizedName("wand_rod_iridium").setTextureName(MODID + ":wand_rod_iridium").setCreativeTab(tabAkadaemon);
        wandCapManullyn = new Item().setUnlocalizedName("wand_cap_manullyn").setTextureName(MODID + ":wand_cap_manullyn").setCreativeTab(tabAkadaemon);

        GameRegistry.registerItem(compositeMod, "composite_mod");
        GameRegistry.registerItem(expansionChip, "expansion_chip");
        GameRegistry.registerItem(worldRing, "world_ring");
        GameRegistry.registerItem(solarAmulet, "solar_amulet");
        GameRegistry.registerItem(minerBelt, "miner_belt");
        GameRegistry.registerItem(goldenSchnitzel, "golden_schnitzel");
        GameRegistry.registerItem(cobaltDust, "cobalt_dust");
        GameRegistry.registerItem(arditeDust, "ardite_dust");
        GameRegistry.registerItem(enderDust, "ender_dust");
        GameRegistry.registerBlock(thaumTransformer, "thaum_transformer");
        GameRegistry.registerTileEntity(TileThaumTransformer.class, "TileThaumTransformer");
        GameRegistry.registerItem(iridiumComposite, "iridium_composite");
        GameRegistry.registerBlock(amberFiber, "amber_fiber");
        GameRegistry.registerTileEntity(TileAmberFiber.class, "TileAmberFiber");
        GameRegistry.registerItem(wandRodIridium, "wand_rod_iridium");
        GameRegistry.registerItem(wandCapManullyn, "wand_cap_manullyn");
        GameRegistry.registerBlock(blockMythril, "block_mythril");

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        OreDictionary.registerOre("dustCobalt", cobaltDust);
        OreDictionary.registerOre("dustArdite", arditeDust);
        OreDictionary.registerOre("dustEnder", enderDust);
        OreDictionary.registerOre("blockMythril", blockMythril);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ModInfiniteDurability durabilityMod = new ModInfiniteDurability(new ItemStack[] { new ItemStack(compositeMod) });
        tconstruct.library.crafting.ModifyBuilder.registerModifier(durabilityMod);

        ModExtraSlot extraSlotMod = new ModExtraSlot(new ItemStack[] { new ItemStack(expansionChip) });
        tconstruct.library.crafting.ModifyBuilder.registerModifier(extraSlotMod);

        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(new DurabilityEventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ThaumcraftIntegration.init();
        com.akadaemon.addon.Recipes.init();
    }
}