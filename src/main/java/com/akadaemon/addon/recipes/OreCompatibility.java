package com.akadaemon.addon.recipes;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import static com.akadaemon.addon.AkadaemonAddon.oreExchanger;

public class OreCompatibility {
    public static void init() {
        ItemStack mainAmber = new ItemStack(ExternalItems.amber.getItem());
        List<ItemStack> allAmbers = OreDictionary.getOres("gemAmber");
        for (ItemStack amber : allAmbers) {
            if (!OreDictionary.itemMatches(mainAmber, amber, false)) {
                GameRegistry.addShapelessRecipe(mainAmber, amber);
            }
        }

        List<ItemStack> salt = OreDictionary.getOres("dustSalt");
        if (!salt.isEmpty()) {
            OreDictionary.registerOre("dustGunpowder", salt.get(0));
            OreDictionary.registerOre("dustNetherQuartz", salt.get(0));
            OreDictionary.registerOre("dustCertusQuartz", salt.get(0));
        }

        if (ConfigHandler.ingotTransfer) {
            AkadaemonAddon.oreExchanger.setContainerItem(AkadaemonAddon.oreExchanger);
            GameRegistry.addRecipe(new ItemStack(oreExchanger),
                    "III", "IGI", " I ",
                    'I', Items.iron_ingot,
                    'G', Items.gold_nugget
            );

            GameRegistry.addRecipe(new RecipeOreExchanger());
        }


    }
    public static class RecipeOreExchanger implements IRecipe {

        @Override
        public boolean matches(InventoryCrafting inv, World world) {
            ItemStack exchanger = null;
            ItemStack metal = null;

            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (stack == null) continue;

                if (stack.getItem() == AkadaemonAddon.oreExchanger) {
                    if (exchanger != null) return false;
                    exchanger = stack;
                } else if (isAnyIngot(stack)) {
                    if (metal != null) return false;
                    metal = stack;
                } else {
                    return false;
                }
            }
            return exchanger != null && metal != null;
        }

        private boolean isAnyIngot(ItemStack stack) {
            int[] ids = OreDictionary.getOreIDs(stack);
            for (int id : ids) {
                if (OreDictionary.getOreName(id).startsWith("ingot")) return true;
            }
            return false;
        }

        @Override
        public ItemStack getCraftingResult(InventoryCrafting inv) {
            int metalSlot = -1;
            for (int i = 0; i < inv.getSizeInventory(); i++) {
                ItemStack is = inv.getStackInSlot(i);
                if (is != null && is.getItem() != AkadaemonAddon.oreExchanger) {
                    metalSlot = i;
                    break;
                }
            }

            switch (metalSlot) {
                case 0: return getOre("ingotIron");
                case 1: return getOre("ingotGold");
                case 2: return getOre("ingotCopper");
                case 3: return getOre("ingotTin");
                case 4: return getOre("ingotLead");
                case 5: return getOre("ingotSilver");
                case 6: return getOre("ingotNickel");
                case 7: return getOre("ingotAluminium");
                case 8: return getOre("ingotPlatinum");
                default: return null;
            }
        }

        private ItemStack getOre(String name) {
            List<ItemStack> ores = OreDictionary.getOres(name);
            if (ores != null && !ores.isEmpty()) {
                return ores.get(0).copy();
            }
            return null;
        }

        @Override
        public int getRecipeSize() { return 10; }

        @Override
        public ItemStack getRecipeOutput() { return null; }
    }
}
