package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.Thaumcraft;
import thaumcraft.common.items.wands.ItemWandCasting;

import java.util.List;

public class ItemFocusSunstrike extends ItemFocusBasic {
    private final double RADIUS = 5.0D;
    private final int FIRE_TICKS = 5;

    public ItemFocusSunstrike() {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName("focus_sunstrike");
        this.setTextureName(AkadaemonAddon.MODID + ":focus_sunstrike");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.itemIcon;
    }

    @Override
    public ItemStack onFocusRightClick(ItemStack wandstack, World world, EntityPlayer player, MovingObjectPosition mop) {
        ItemWandCasting wand = (ItemWandCasting) wandstack.getItem();

        Vec3 startVec = Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        Vec3 lookVec = player.getLook(1.0F);
        Vec3 endVec = startVec.addVector(
                lookVec.xCoord * ConfigHandler.sunstrikeDistance,
                lookVec.yCoord * ConfigHandler.sunstrikeDistance,
                lookVec.zCoord * ConfigHandler.sunstrikeDistance
        );

        MovingObjectPosition blockMop = world.rayTraceBlocks(startVec, endVec, false);

        if (blockMop != null && blockMop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            if (wand.consumeAllVis(wandstack, player, getVisCost(wandstack), true, false)) {
                double targetX = blockMop.blockX + 0.5D;
                double targetY = blockMop.blockY + 1.0D;
                double targetZ = blockMop.blockZ + 0.5D;

                if (!world.isRemote) {
                    AxisAlignedBB box = AxisAlignedBB.getBoundingBox(
                            targetX - RADIUS, targetY - 2.0D, targetZ - RADIUS,
                            targetX + RADIUS, targetY + 6.0D, targetZ + RADIUS
                    );

                    List targets = world.getEntitiesWithinAABB(EntityLivingBase.class, box);

                    for (Object o : targets) {
                        EntityLivingBase entity = (EntityLivingBase) o;

                        double dx = entity.posX - targetX;
                        double dz = entity.posZ - targetZ;
                        double distSq = dx * dx + dz * dz;

                        if (distSq <= (RADIUS * RADIUS)) {
                            if (entity != player) {
                                entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setMagicDamage(), ConfigHandler.sunstrikeDamage);
                                entity.setFire(FIRE_TICKS);
                            }
                        }
                    }
                    world.playSoundEffect(targetX, targetY, targetZ, "thaumcraft:fireloop", 0.8F, 0.9F);
                } else {
                    renderEffects(world, targetX, targetY, targetZ);
                }
                player.swingItem();
            }
        }
        return wandstack;
    }

    @SideOnly(Side.CLIENT)
    private void renderEffects(World world, double x, double y, double z) {
        Thaumcraft.proxy.beam(world, x, y + 20.0D, z, x, y, z, 1, 0xFFFF00, false, 5.0F, 15);
        Thaumcraft.proxy.burst(world, x, y, z, 1.0F);
    }

    @Override
    public int getFocusColor(ItemStack focusstack) { return 0xFFD700; }

    @Override
    public AspectList getVisCost(ItemStack focusstack) {
        return new AspectList().add(Aspect.FIRE, 500).add(Aspect.ORDER, 200);
    }
}