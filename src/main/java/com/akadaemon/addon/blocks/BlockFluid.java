package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class BlockFluid extends BlockFluidClassic {

    public BlockFluid(Fluid fluid, Material material, int quanta, int ticks) {
        super(fluid, material);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.quantaPerBlock = quanta;
        this.tickRate = ticks;
        this.setTickRandomly(true);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate);
        checkForReaction(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        super.onNeighborBlockChange(world, x, y, z, neighbor);
        checkForReaction(world, x, y, z);
    }

    private void checkForReaction(World world, int x, int y, int z) {
        int myMeta = world.getBlockMetadata(x, y, z);

        if (myMeta == 0) return;

        for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
            int tx = x + dir.offsetX;
            int ty = y + dir.offsetY;
            int tz = z + dir.offsetZ;

            Block target = world.getBlock(tx, ty, tz);
            int targetMeta = world.getBlockMetadata(tx, ty, tz);

            if (isReactions(this, target)) {
                if (targetMeta != 0) {
                    world.setBlock(x, y, z, Blocks.glowstone);
                    world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F);

                    // Обновляем всех соседей (6 сторон), чтобы жидкости начали течь в пустоту
                    for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS) {
                        world.notifyBlockOfNeighborChange(x + d.offsetX, y + d.offsetY, z + d.offsetZ, Blocks.glowstone);
                    }
                    return;
                }
            }
        }
    }

    private boolean isReactions(Block b1, Block b2) {
        if (b1 == AkadaemonAddon.blockGlacialQuicksilver && b2 == AkadaemonAddon.blockEtherealPhoton) return true;
        if (b1 == AkadaemonAddon.blockEtherealPhoton && b2 == AkadaemonAddon.blockGlacialQuicksilver) return true;
        return false;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, x, y, z);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) { return false; }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) { return false; }
}