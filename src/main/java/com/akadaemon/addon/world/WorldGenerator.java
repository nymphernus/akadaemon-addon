package com.akadaemon.addon.world;

import com.akadaemon.addon.ExternalItems;
import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.ChestGenHooks;
import thaumcraft.common.config.ConfigItems;

import java.util.Random;

public class WorldGenerator implements IWorldGenerator {
    public static void init() {
        initLoot();
        GameRegistry.registerWorldGenerator(new WorldGenerator(), 10);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 0) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int y = world.getHeightValue(x, z);

            while (y > 0) {
                Block block = world.getBlock(x, y - 1, z);
                boolean isAir = world.isAirBlock(x, y - 1, z);
                boolean isLeaves = block.isLeaves(world, x, y - 1, z);
                boolean isLeavesMaterial = block.getMaterial() == net.minecraft.block.material.Material.leaves;

                if (isAir || isLeaves || isLeavesMaterial) {
                    y--;
                } else {
                    break;
                }
            }

            BiomeGenBase biome = world.getBiomeGenForCoords(x, z);

            Block baseBlock = world.getBlock(x, y - 1, z);
            boolean isWater = baseBlock.getMaterial().isLiquid();

            boolean canSpawn = true;
            if (isWater || world.isAirBlock(x - 5, y - 1, z - 5) ||
                    world.isAirBlock(x + 5, y - 1, z - 5) ||
                    world.isAirBlock(x - 5, y - 1, z + 5) ||
                    world.isAirBlock(x + 5, y - 1, z + 5)) {
                canSpawn = false;
            }

            if (canSpawn && random.nextInt(ConfigHandler.towerGeneration) == 0) {
                SchematicLoader.generateSchematic(world, x - 5, y - 1, z - 5, "/assets/akadaemon/structures/draugr_tower.schematic");
            }
        }
    }

    public static final String DRAUGR_Loot = "draugrLoot";

    public static void initLoot() {
        ChestGenHooks info = ChestGenHooks.getInfo(DRAUGR_Loot);

        info.addItem(new WeightedRandomChestContent(new ItemStack(Items.bone), 1, 3, 15));
        info.addItem(new WeightedRandomChestContent(new ItemStack(Items.rotten_flesh), 1, 3, 15));
        info.addItem(new WeightedRandomChestContent(new ItemStack(Items.iron_ingot), 1, 3, 11));
        info.addItem(new WeightedRandomChestContent(ExternalItems.ingotArdite, 1, 2, 2));
        info.addItem(new WeightedRandomChestContent(ExternalItems.ingotCobalt, 1, 2, 2));
        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemResource, 1, 2), 1, 2, 8));
        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemResource, 1, 6), 1, 3, 10));
        info.addItem(new WeightedRandomChestContent(ExternalItems.netherBone, 1, 1, 6));

        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemSwordThaumium), 1, 1, 3));
        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemAxeThaumium), 1, 1, 3));
        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemPickThaumium), 1, 1, 3));
        info.addItem(new WeightedRandomChestContent(new ItemStack(ConfigItems.itemBowBone), 1, 1, 3));
        info.addItem(new WeightedRandomChestContent(Items.enchanted_book,1, 1, 1, 5));


    }
}