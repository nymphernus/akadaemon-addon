package com.akadaemon.addon.recipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.crafting.InfusionRecipe;

import java.util.ArrayList;

public class InfusionElectricRecipe extends InfusionRecipe {
    public InfusionElectricRecipe(String research, Object result, int inst, AspectList aspects, ItemStack centralItem, ItemStack[] recipe) {
        super(research, result, inst, aspects, centralItem, recipe);
    }

    @Override
    public boolean matches(ArrayList<ItemStack> input, ItemStack central, World world, EntityPlayer player) {
        if (getResearch().length() > 0 && !ThaumcraftApiHelper.isResearchComplete(player.getCommandSenderName(), getResearch())) {
            return false;
        }
        ItemStack recipeInput = getRecipeInput();
        if (central == null || recipeInput == null || central.getItem() != recipeInput.getItem()) {
            return false;
        }
        ArrayList<ItemStack> components = new ArrayList<ItemStack>();
        for (ItemStack stack : input) {
            if (stack != null) components.add(stack.copy());
        }
        for (ItemStack expected : getComponents()) {
            boolean found = false;
            for (int a = 0; a < components.size(); a++) {
                ItemStack provided = components.get(a);
                if (ThaumcraftApiHelper.areItemsEqual(provided, expected)) {
                    components.remove(a);
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return components.size() == 0;
    }
}