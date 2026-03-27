package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.items.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBarley extends BlockCrops {
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockBarley() {
        super();
        this.setBlockName("barley_crop");
    }

    @Override
    protected Item func_149866_i() {
        return ModItems.barley;
    }

    @Override
    protected Item func_149865_P() {
        return ModItems.barleySeeds;
    }

    @Override
    public java.util.ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        java.util.ArrayList<ItemStack> ret = new java.util.ArrayList<ItemStack>();

        if (metadata >= 5) {
            ret.add(new ItemStack(ModItems.barley, 1 + world.rand.nextInt(2)));
            ret.add(new ItemStack(ModItems.barleySeeds, 1 + world.rand.nextInt(3)));
        } else {
            ret.add(new ItemStack(ModItems.barleySeeds, 1));
        }
        return ret;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        int meta = world.getBlockMetadata(x, y, z);

        if (meta >= 5) {
            if (!world.isRemote) {
                java.util.ArrayList<ItemStack> items = this.getDrops(world, x, y, z, meta, 0);
                boolean seedUsed = false;

                for (ItemStack stack : items) {
                    if (stack == null || stack.stackSize <= 0) continue;

                    if (!seedUsed && stack.getItem() == ModItems.barleySeeds) {
                        stack.stackSize--;
                        seedUsed = true;
                    }

                    if (stack.stackSize > 0) {
                        net.minecraft.entity.item.EntityItem entityItem = new net.minecraft.entity.item.EntityItem(
                                world, x + 0.5D, y + 0.1D, z + 0.5D, stack
                        );
                        world.spawnEntityInWorld(entityItem);
                    }
                }
                world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "dig.grass", 0.7F, 0.9F);
            }
            player.swingItem();
            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        this.icons = new IIcon[6];
        for (int i = 0; i < icons.length; i++) {
            this.icons[i] = register.registerIcon(AkadaemonAddon.MODID + ":barley_stage_" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta > 5) meta = 5;
        return icons[meta];
    }
}