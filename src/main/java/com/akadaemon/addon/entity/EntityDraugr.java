package com.akadaemon.addon.entity;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.ExternalItems;
import com.akadaemon.addon.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;

public class EntityDraugr extends EntityZombie implements IRangedAttackMob {
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2D, false);
    private boolean firstUpdate = true;

    public EntityDraugr(World world) {
        super(world);

        this.tasks.taskEntries.clear();
        this.targetTasks.taskEntries.clear();

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
        this.setHealth(50.0F);

        if (world != null && !world.isRemote) {
            this.setCombatTask();
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20D);
        this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
    }

    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);

        ItemStack itemstack = this.getHeldItem();

        if (itemstack != null && itemstack.getItem() == ConfigItems.itemBowBone) {
            this.tasks.addTask(2, this.aiArrowAttack);
        } else {
            this.tasks.addTask(2, this.aiAttackOnCollide);
        }
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        EntityArrow entityarrow = new EntityArrow(this.worldObj, this, target, 1.6F, (float)(14 - this.worldObj.difficultySetting.getDifficultyId() * 4));

        int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());

        entityarrow.setDamage((double)(distanceFactor * 2.0F) + this.rand.nextGaussian() * 0.25D + (double)((float)this.worldObj.difficultySetting.getDifficultyId() * 0.11F));

        if (i > 0) entityarrow.setDamage(entityarrow.getDamage() + (double)i * 0.5D + 0.5D);
        if (j > 0) entityarrow.setKnockbackStrength(j);

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0) {
            entityarrow.setFire(100);
        }

        this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.worldObj.spawnEntityInWorld(entityarrow);
    }

    @Override
    protected void addRandomArmor() {
        if (this.rand.nextBoolean()) {
            if (this.rand.nextBoolean()) {
                this.setCurrentItemOrArmor(0, new ItemStack(ConfigItems.itemSwordThaumium));
            } else {
                this.setCurrentItemOrArmor(0, new ItemStack(ConfigItems.itemAxeThaumium));
            }
        } else {
            this.setCurrentItemOrArmor(0, new ItemStack(ConfigItems.itemBowBone));
        }
        if (this.rand.nextFloat() < 0.15F) {
            int tier = this.rand.nextInt(3);
            int piecesToWear = 1 + this.rand.nextInt(4);

            for (int i = 0; i < piecesToWear; i++) {
                int slot = 1 + this.rand.nextInt(4);

                ItemStack armorPiece = getArmorByTierAndSlot(tier, slot);
                if (armorPiece != null) {
                    this.setCurrentItemOrArmor(slot, armorPiece);
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            this.equipmentDropChances[i] = 0.05F;
        }
        this.setCombatTask();
    }

    private ItemStack getArmorByTierAndSlot(int tier, int slot) {
        switch (tier) {
            case 1:
                if (slot == 4) return new ItemStack(ModItems.manyullynHelmet);
                if (slot == 3) return new ItemStack(ModItems.manyullynChest);
                if (slot == 2) return new ItemStack(ModItems.manyullynLegs);
                if (slot == 1) return new ItemStack(ModItems.manyullynBoots);
            case 2:
                if (slot == 4) return new ItemStack(ModItems.titanHelmet);
                if (slot == 3) return new ItemStack(ModItems.titanChest);
                if (slot == 2) return new ItemStack(ModItems.titanLegs);
                if (slot == 1) return new ItemStack(ModItems.titanBoots);
            default:
                if (slot == 4) return new ItemStack(ConfigItems.itemHelmetThaumium);
                if (slot == 3) return new ItemStack(ConfigItems.itemChestThaumium);
                if (slot == 2) return new ItemStack(ConfigItems.itemLegsThaumium);
                if (slot == 1) return new ItemStack(ConfigItems.itemBootsThaumium);
        }
        return null;
    }

    @Override
    public void setCurrentItemOrArmor(int slot, ItemStack stack) {
        super.setCurrentItemOrArmor(slot, stack);
        if (!this.worldObj.isRemote && slot == 0) {
            this.setCombatTask();
        }
    }

    @Override
    protected void dropFewItems(boolean hitByPlayer, int lootingLevel) {
        int count = this.rand.nextInt(2) + lootingLevel;
        for (int i = 0; i < count; ++i) {
            this.dropItem(Items.bone, 1);
        }
        if (hitByPlayer) {
            if (this.rand.nextInt(100) < (15 + lootingLevel * 2)) {
                this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 9), 0.0F);
            }
            if (this.rand.nextInt(100) < (10 + lootingLevel * 2)) {
                this.entityDropItem(new ItemStack(ConfigItems.itemResource, 1, 2), 0.0F);
            }
            if (this.rand.nextInt(100) < (5 + lootingLevel)) {
                this.entityDropItem(ExternalItems.greenHeart, 0.0F);
            }
        }

    }

    private void super_onLivingUpdate_EntityMob() {
        float f = this.getBrightness(1.0F);
        if (f > 0.5F) {
            this.entityAge += 2;
        }
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.isEntityAlive()) {
            if (this.ticksExisted % 80 == 0) {
                if (this.getHealth() < this.getMaxHealth()) {
                    this.heal(1.0F);
                }
            }
        }
        if (this.isBurning() && this.worldObj.isDaytime()) {
            this.extinguish();
        }
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        this.addRandomArmor();
        this.setHealth(this.getMaxHealth());
        this.setCombatTask();
        return data;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.firstUpdate) {
            this.firstUpdate = false;
            if (this.getHeldItem() == null) {
                this.onSpawnWithEgg(null);
                this.setCombatTask();
            }
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        boolean hasSpace = worldObj.checkNoEntityCollision(boundingBox) &&
                worldObj.getCollidingBoundingBoxes(this, boundingBox).isEmpty() &&
                !worldObj.isAnyLiquid(boundingBox);

        if (!hasSpace) return false;

        if (this.isValidLightLevel()) {
            return true;
        }

        int ix = (int)posX;
        int iz = (int)posZ;
        int eerieBiomeId = 194;

        if (worldObj.getBiomeGenForCoords(ix, iz).biomeID == eerieBiomeId) {
            return true;
        }
        return false;
    }

    @Override
    protected String getLivingSound() {
        return AkadaemonAddon.MODID + ":mob.draugr.say";
    }
    @Override
    protected String getHurtSound() {
        return AkadaemonAddon.MODID + ":mob.draugr.hurt";
    }
    @Override
    protected String getDeathSound() {
        return AkadaemonAddon.MODID + ":mob.draugr.death";
    }
    @Override
    protected void func_145780_a(int x, int y, int z, net.minecraft.block.Block block) {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    @Override protected Item getDropItem() { return null;}
    @Override protected void dropRareDrop(int lootingLevel) {}
    @Override public void onLivingUpdate() { super_onLivingUpdate_EntityMob(); }
    @Override public boolean isChild() { return false; }
}