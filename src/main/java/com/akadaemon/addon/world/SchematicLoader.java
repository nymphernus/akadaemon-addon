package com.akadaemon.addon.world;

import com.akadaemon.addon.entity.EntityDraugr;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.util.Constants;

import java.io.InputStream;
import java.util.Random;

public class SchematicLoader {

    public static void generateSchematic(World world, int x, int y, int z, String resourcePath) {
        try {
            InputStream is = SchematicLoader.class.getResourceAsStream(resourcePath);
            if (is == null) return;
            NBTTagCompound nbt = CompressedStreamTools.readCompressed(is);
            is.close();

            short width = nbt.getShort("Width");
            short height = nbt.getShort("Height");
            short length = nbt.getShort("Length");
            byte[] blocks = nbt.getByteArray("Blocks");
            byte[] data = nbt.getByteArray("Data");

            for (int sy = 0; sy < height; sy++) {
                for (int sz = 0; sz < length; sz++) {
                    for (int sx = 0; sx < width; sx++) {
                        int index = sy * width * length + sz * width + sx;
                        Block block = Block.getBlockById(blocks[index] & 0xFF);
                        int metadata = data[index] & 0xFF;
                        if (block != null && block != Blocks.air) {
                            world.setBlock(x + sx, y + sy, z + sz, block, metadata, 2);
                        }
                    }
                }
            }

            if (nbt.hasKey("TileEntities")) {
                NBTTagList tileEntities = nbt.getTagList("TileEntities", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < tileEntities.tagCount(); i++) {
                    NBTTagCompound tileNbt = (NBTTagCompound) tileEntities.getCompoundTagAt(i).copy();

                    int tx = tileNbt.getInteger("x") + x;
                    int ty = tileNbt.getInteger("y") + y;
                    int tz = tileNbt.getInteger("z") + z;

                    tileNbt.setInteger("x", tx);
                    tileNbt.setInteger("y", ty);
                    tileNbt.setInteger("z", tz);

                    TileEntity te = TileEntity.createAndLoadEntity(tileNbt);

                    if (te != null) {
                        world.setTileEntity(tx, ty, tz, te);

                        if (te instanceof TileEntityChest) {
                            fillChest(world.rand, (TileEntityChest) te);
                        }
                        else if (te instanceof TileEntityMobSpawner) {
                            TileEntityMobSpawner spawner = (TileEntityMobSpawner) te;
                            NBTTagCompound spawnerNbt = new NBTTagCompound();
                            spawner.writeToNBT(spawnerNbt);

                            String mobName = "Draugr";
                            spawnerNbt.setString("EntityId", mobName);

                            NBTTagCompound spawnData = new NBTTagCompound();
                            spawnData.setString("id", mobName);
                            spawnerNbt.setTag("SpawnData", spawnData);

                            spawner.readFromNBT(spawnerNbt);
                            spawner.markDirty();
                        }
                    }
                }
            }
            applyEerieBiome(world, x + (width / 2), z + (length / 2), width, length);
            spawnGuards(world, x + (width/2), y + 15, z + (length/2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fillChest(Random rand, TileEntityChest chest) {
        for (int i = 0; i < chest.getSizeInventory(); i++) {
            chest.setInventorySlotContents(i, null);
        }
        WeightedRandomChestContent.generateChestContents(
                rand,
                ChestGenHooks.getItems(WorldGenerator.DRAUGR_Loot, rand),
                chest,
                8
        );
        chest.markDirty();
    }

    private static void spawnGuards(World world, int x, int y, int z) {
        if (!world.isRemote) {
            for (int i = 0; i < 3; i++) {
                EntityDraugr draugr = new EntityDraugr(world);
                draugr.setLocationAndAngles(x + 0.5D, y + 15.0D, z + 0.5D, world.rand.nextFloat() * 360.0F, 0.0F);
                draugr.onSpawnWithEgg(null);
                world.spawnEntityInWorld(draugr);
            }
        }
    }

    private static void applyEerieBiome(World world, int centerX, int centerZ, int width, int length) {
        int radius = Math.max(width, length) / 2 + 5;
        int eerieBiomeId = 194;
        for (int ix = -radius; ix <= radius; ix++) {
            for (int iz = -radius; iz <= radius; iz++) {
                if (ix * ix + iz * iz <= radius * radius) {
                    int targetX = centerX + ix;
                    int targetZ = centerZ + iz;
                    world.getChunkFromBlockCoords(targetX, targetZ).getBiomeArray()[(targetZ & 15) << 4 | (targetX & 15)] = (byte)eerieBiomeId;
                }
            }
        }
    }

}