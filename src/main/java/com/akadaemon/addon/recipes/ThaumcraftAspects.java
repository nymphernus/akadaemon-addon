package com.akadaemon.addon.recipes;

import com.akadaemon.addon.ExternalItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import static com.akadaemon.addon.AkadaemonAddon.*;

public class ThaumcraftAspects {

    public static void register() {
        ThaumcraftApi.registerObjectTag(ExternalItems.ingotManullyn, new AspectList().add(Aspect.METAL, 3).add(Aspect.CRYSTAL, 2).add(Aspect.ENTROPY, 1));
        ThaumcraftApi.registerObjectTag(ExternalItems.ingotCobalt, new AspectList().add(Aspect.METAL, 3).add(Aspect.CRYSTAL, 2).add(Aspect.ORDER, 1));
        ThaumcraftApi.registerObjectTag(ExternalItems.ingotArdite, new AspectList().add(Aspect.METAL, 3).add(Aspect.CRYSTAL, 2).add(Aspect.FIRE, 1));
        reg(ingotMythril, new AspectList().add(Aspect.METAL, 3).add(Aspect.MAGIC, 2).add(Aspect.ENTROPY, 1).add(Aspect.CRYSTAL, 1));
        reg(ingotTitan, new AspectList().add(Aspect.METAL, 3).add(Aspect.MAGIC, 2).add(Aspect.ORDER, 1));
        reg(ingotAdamantit, new AspectList().add(Aspect.METAL, 3).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 1));
        reg(iridiumComposite, new AspectList().add(Aspect.METAL, 3).add(Aspect.ENERGY, 2).add(Aspect.VOID, 1));
        reg(compositeMod, new AspectList().add(Aspect.METAL, 2).add(Aspect.EXCHANGE, 1));

        reg(cobaltDust, new AspectList().add(Aspect.METAL, 1).add(Aspect.ENTROPY, 1));
        reg(arditeDust, new AspectList().add(Aspect.METAL, 1).add(Aspect.EARTH, 1));
        reg(enderDust, new AspectList().add(Aspect.ELDRITCH, 1).add(Aspect.TRAVEL, 1).add(Aspect.ENTROPY, 1));

        reg(blockMythril, new AspectList().add(Aspect.METAL, 27).add(Aspect.MAGIC, 18));
        reg(blockTitan, new AspectList().add(Aspect.METAL, 27).add(Aspect.MAGIC, 18));
        reg(blockAdamantit, new AspectList().add(Aspect.METAL, 27).add(Aspect.MAGIC, 18));
        reg(amberFiber, new AspectList().add(Aspect.TRAP, 2).add(Aspect.CLOTH, 2).add(Aspect.CRYSTAL, 1));
        reg(thaumTransformer, new AspectList().add(Aspect.MECHANISM, 8).add(Aspect.ENERGY, 8).add(Aspect.EXCHANGE, 4));
        reg(titanDrill, new AspectList().add(Aspect.MECHANISM, 12).add(Aspect.METAL, 10).add(Aspect.MINE, 15).add(Aspect.ENERGY, 6));

        reg(wandRodIridium, new AspectList().add(Aspect.METAL, 4).add(Aspect.ENERGY, 4).add(Aspect.TOOL, 2));
        reg(wandCapManullyn, new AspectList().add(Aspect.METAL, 2).add(Aspect.WEAPON, 2).add(Aspect.DARKNESS, 2));

        reg(expansionChip, new AspectList().add(Aspect.MIND, 4).add(Aspect.MECHANISM, 2));
        reg(worldRing, new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.TRAVEL, 4).add(Aspect.MAGIC, 2));
        reg(solarAmulet, new AspectList().add(Aspect.LIGHT, 6).add(Aspect.ENERGY, 6).add(Aspect.MAGIC, 2));
        reg(minerBelt, new AspectList().add(Aspect.MINE, 4).add(Aspect.TOOL, 4).add(Aspect.CLOTH, 2));

        reg(goldenSchnitzel, new AspectList().add(Aspect.HUNGER, 4).add(Aspect.FLESH, 2).add(Aspect.GREED, 2).add(Aspect.HEAL, 1));

        reg(bucketGlacialQuicksilver, new AspectList().add(Aspect.METAL, 4).add(Aspect.CRYSTAL, 8).add(Aspect.VOID, 2));
        reg(bucketEtherealPhoton, new AspectList().add(Aspect.LIGHT, 10).add(Aspect.ENERGY, 8).add(Aspect.VOID, 2));
        reg(bucketActiveRedstone, new AspectList().add(Aspect.ENERGY, 10).add(Aspect.MECHANISM, 4).add(Aspect.VOID, 2));
        reg(blockGlacialQuicksilver, new AspectList().add(Aspect.METAL, 2).add(Aspect.CRYSTAL, 4).add(Aspect.ORDER, 2));
        reg(blockEtherealPhoton, new AspectList().add(Aspect.LIGHT, 8).add(Aspect.ENERGY, 4).add(Aspect.AURA, 2));
        reg(blockActiveRedstone, new AspectList().add(Aspect.ENERGY, 8).add(Aspect.MECHANISM, 4));

        regWildcard(mythrilQHelmet, new AspectList().add(Aspect.ARMOR, 4).add(Aspect.ENERGY, 6).add(Aspect.SENSES, 4));
        regWildcard(mythrilQChest, new AspectList().add(Aspect.ARMOR, 8).add(Aspect.ENERGY, 10).add(Aspect.FLIGHT, 4));
        regWildcard(mythrilQLegs, new AspectList().add(Aspect.ARMOR, 6).add(Aspect.ENERGY, 8).add(Aspect.TRAVEL, 4));
        regWildcard(mythrilQBoots, new AspectList().add(Aspect.ARMOR, 4).add(Aspect.ENERGY, 6).add(Aspect.TRAVEL, 4));
    }

    private static void reg(Object item, AspectList aspects) {
        if (item == null) return;

        if (item instanceof net.minecraft.item.Item) {
            ThaumcraftApi.registerObjectTag(new ItemStack((net.minecraft.item.Item) item), aspects);
        } else if (item instanceof net.minecraft.block.Block) {
            ThaumcraftApi.registerObjectTag(new ItemStack((net.minecraft.block.Block) item), aspects);
        } else if (item instanceof ItemStack) {
            ThaumcraftApi.registerObjectTag((ItemStack) item, aspects);
        }
    }

    private static void regWildcard(net.minecraft.item.Item item, AspectList aspects) {
        ThaumcraftApi.registerObjectTag(new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE), aspects);
    }
}