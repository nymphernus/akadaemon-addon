package com.akadaemon.addon.entity;

import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.common.Thaumcraft;

public class EntityFocusPearl extends EntityEnderPearl {

    public EntityFocusPearl(World world) {
        super(world);
    }

    public EntityFocusPearl(World world, EntityLivingBase thrower) {
        super(world, thrower);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.worldObj.isRemote) {
            spawnFlyingEffects();
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnFlyingEffects() {
        Thaumcraft.proxy.sparkle((float)this.posX, (float)this.posY, (float)this.posZ, 2, 0x22AAFF, 0.0F);

        if (this.ticksExisted % 2 == 0) {
            Thaumcraft.proxy.wispFX(this.worldObj, this.posX, this.posY, this.posZ, 0.2F, 0.137F, 0.666F, 1.0F);
        }
    }

    @Override
    protected float getGravityVelocity() {
        return ConfigHandler.teleportGravity;
    }

    @Override
    protected void onImpact(MovingObjectPosition mop) {
        if (mop.entityHit != null) {
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
        }
        if (!this.worldObj.isRemote) {
            if (this.getThrower() != null && this.getThrower() instanceof EntityLivingBase) {
                if (!this.getThrower().isDead) {
                    this.getThrower().setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    this.getThrower().fallDistance = 0.0F;
                    this.worldObj.playSoundEffect(this.posX, this.posY, this.posZ, "thaumcraft:zap", 1.0F, 1.0F);
                }
            }
            this.setDead();
        } else {
            for (int i = 0; i < 20; i++) {
                Thaumcraft.proxy.burst(this.worldObj, this.posX, this.posY, this.posZ, 1.0F);
            }
        }
    }
}