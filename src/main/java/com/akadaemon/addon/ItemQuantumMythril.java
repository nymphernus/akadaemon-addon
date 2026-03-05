package com.akadaemon.addon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.core.item.armor.ItemArmorQuantumSuit;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.nodes.IRevealer;

import java.lang.reflect.Method;

public class ItemQuantumMythril extends ItemArmorQuantumSuit implements IVisDiscountGear, IGoggles, IRevealer {

    private static Method moveRelativeMethod;

    public ItemQuantumMythril(int armorType) {
        super(getNameByType(armorType), armorType);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    private static ic2.core.init.InternalName getNameByType(int type) {
        switch (type) {
            case 0: return ic2.core.init.InternalName.itemArmorQuantumHelmet;
            case 1: return ic2.core.init.InternalName.itemArmorQuantumChestplate;
            case 2: return ic2.core.init.InternalName.itemArmorQuantumLegs;
            case 3: return ic2.core.init.InternalName.itemArmorQuantumBoots;
            default: return ic2.core.init.InternalName.itemArmorQuantumChestplate;
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
        if (!world.isRemote && entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            ItemStack chest = player.getEquipmentInSlot(3);

            if (player.capabilities.allowFlying && !player.capabilities.isCreativeMode) {
                if (chest == null || !(chest.getItem() instanceof ItemQuantumMythril && ((ItemQuantumMythril)chest.getItem()).armorType == 1)) {
                    disableFlight(player);
                }
            }
        }
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) { return 50000000.0D; }

    @Override
    public int getTier(ItemStack itemStack) { return 4; }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 50000.0D;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);

        if (this.armorType == 1) {
            handleFlight(player, itemStack);
        } else if (this.armorType == 2) {
            handleLeggingsSpeedAndTrail(world, player, itemStack);
        }
    }

    private void handleFlight(EntityPlayer player, ItemStack stack) {
        double currentEnergy = ElectricItem.manager.getCharge(stack);
        boolean hasEnergy = currentEnergy > 1000;

        if (hasEnergy) {
            player.capabilities.allowFlying = true;
            if (player.capabilities.isFlying) {
                ElectricItem.manager.discharge(stack, 50.0D, Integer.MAX_VALUE, true, false, false);
            }
        } else {
            disableFlight(player);
        }
    }

    private void disableFlight(EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.sendPlayerAbilities();
        }
    }

    private static final String[] MOVE_RELATIVE_NAMES = {"moveRelative", "func_70060_a"};

    static {
        for (String name : MOVE_RELATIVE_NAMES) {
            try {
                moveRelativeMethod = Entity.class.getDeclaredMethod(name, float.class, float.class, float.class);
                moveRelativeMethod.setAccessible(true);
                break;
            } catch (Exception e) {
            }
        }
    }

    private void handleLeggingsSpeedAndTrail(World world, EntityPlayer player, ItemStack stack) {
        boolean canAccelerate = (player.onGround || player.capabilities.isFlying);

        if (ElectricItem.manager.getCharge(stack) > 100 && canAccelerate && player.isSprinting()) {
            try {
                if (moveRelativeMethod != null) {
                    moveRelativeMethod.invoke(player, 0.0F, 1.0F, 0.10F);
                }
            } catch (Exception e) {
            }
            if (world.isRemote && world.getTotalWorldTime() % 2 == 0) {
                spawnEnderParticles(world, player);
            }
            ElectricItem.manager.discharge(stack, 20.0D, Integer.MAX_VALUE, true, false, false);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnEnderParticles(World world, EntityPlayer player) {
        for (int i = 0; i < 6; i++) {
            double px = player.posX + (world.rand.nextDouble() - 0.5D) * 0.5D;

            double py = player.posY - 0.2D + (world.rand.nextDouble() * 0.2D);

            double pz = player.posZ + (world.rand.nextDouble() - 0.5D) * 0.5D;

            double vx = (world.rand.nextDouble() - 0.5D) * 0.1D;
            double vy = (world.rand.nextDouble() * 0.05D);
            double vz = (world.rand.nextDouble() - 0.5D) * 0.1D;

            world.spawnParticle("portal", px, py, pz, vx, vy, vz);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return EnumChatFormatting.DARK_RED + "" + EnumChatFormatting.BOLD + super.getItemStackDisplayName(stack);
    }

    @Override
    public int getVisDiscount(ItemStack stack, net.minecraft.entity.player.EntityPlayer player, thaumcraft.api.aspects.Aspect aspect) {
        return armorType == 0 ? 7 : 6;
    }

    @Override
    public boolean showNodes(ItemStack stack, net.minecraft.entity.EntityLivingBase player) {
        return armorType == 0;
    }

    @Override
    public boolean showIngamePopups(ItemStack stack, net.minecraft.entity.EntityLivingBase player) {
        return armorType == 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        String name = "";
        switch (armorType) {
            case 0: name = "mythril_quantum_helmet"; break;
            case 1: name = "mythril_quantum_chest"; break;
            case 2: name = "mythril_quantum_legs"; break;
            case 3: name = "mythril_quantum_boots"; break;
        }
        this.itemIcon = ir.registerIcon(AkadaemonAddon.MODID + ":" + name);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (slot == 2) {
            return AkadaemonAddon.MODID + ":textures/models/mythril_quantum_2.png";
        }
        return AkadaemonAddon.MODID + ":textures/models/mythril_quantum_1.png";
    }
}