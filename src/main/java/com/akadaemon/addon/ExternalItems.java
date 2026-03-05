package com.akadaemon.addon;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ExternalItems {
    public static ItemStack ingotManullyn, ingotCobalt, ingotArdite;
    public static ItemStack iridiumOre;
    public static ItemStack energyCrystal;
    public static ItemStack solarPanel;
    public static ItemStack mfe;
    public static ItemStack jetpack;
    public static ItemStack advCircuit;
    public static ItemStack carbonPlate;
    public static ItemStack dustDiamond;
    public static ItemStack dustLapis;

    public static ItemStack qHelmet, qChest, qLegs, qBoots;

    public static ItemStack knowledgeFragment, amber, blockTube, blockAmber, thaumIngot;

    public static void init() {
        Item tinkerMaterials = GameRegistry.findItem("TConstruct", "materials");
        ingotManullyn = new ItemStack(tinkerMaterials, 1, 5);
        ingotCobalt = new ItemStack(tinkerMaterials, 1, 3);
        ingotArdite = new ItemStack(tinkerMaterials, 1, 4);

        iridiumOre = ic2.api.item.IC2Items.getItem("iridiumOre");
        solarPanel = ic2.api.item.IC2Items.getItem("solarPanel");
        mfe = ic2.api.item.IC2Items.getItem("mfeUnit");
        energyCrystal = getIc2Wildcard("energyCrystal");
        jetpack = getIc2Wildcard("electricJetpack");
        advCircuit = ic2.api.item.IC2Items.getItem("advancedCircuit");
        carbonPlate = ic2.api.item.IC2Items.getItem("carbonPlate");
        dustDiamond = ic2.api.item.IC2Items.getItem("diamondDust");
        dustLapis = ic2.api.item.IC2Items.getItem("lapiDust");

        qHelmet = ic2.api.item.IC2Items.getItem("quantumHelmet");
        qChest = ic2.api.item.IC2Items.getItem("quantumBodyarmor");
        qLegs = ic2.api.item.IC2Items.getItem("quantumLeggings");
        qBoots = ic2.api.item.IC2Items.getItem("quantumBoots");

        Item resourceItem = (Item) Item.itemRegistry.getObject("Thaumcraft:ItemResource");
        knowledgeFragment = new ItemStack(resourceItem, 1, 9);
        amber = new ItemStack(resourceItem, 1, 6);
        thaumIngot = new ItemStack(resourceItem, 1, 2);

        blockTube = createStackFromName("Thaumcraft:blockTube", 0);
        blockAmber = createStackFromName("Thaumcraft:blockCosmeticOpaque", 0);
    }

    private static ItemStack getIc2Wildcard(String name) {
        ItemStack stack = ic2.api.item.IC2Items.getItem(name);
        if (stack != null) stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
        return stack;
    }

    private static ItemStack createStackFromName(String name, int meta) {
        Item item = (Item) Item.itemRegistry.getObject(name);
        return (item != null) ? new ItemStack(item, 1, meta) : null;
    }
}
