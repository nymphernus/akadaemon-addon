package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, java.util.List list, boolean par4) {
        String key = "tooltip.akadaemon.armor." + this.armorType;
        list.add(net.minecraft.util.StatCollector.translateToLocal(key));
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

        if (this.armorType == 0) {
            handleSanityProtection(world, player, itemStack);
        } else if (this.armorType == 1) {
            handleFlight(player, itemStack);
        } else if (this.armorType == 2) {
            handleLeggingsSpeedAndTrail(world, player, itemStack);
        } else if (this.armorType == 3) {
            handleBootsStep(player, itemStack);
        }
    }

    private void handleBootsStep(EntityPlayer player, ItemStack stack) {
        if (ElectricItem.manager.getCharge(stack) > 1000) {
            player.stepHeight = 1.0F;
        } else {
            player.stepHeight = 0.5F;
        }
    }

    private void handleSanityProtection(World world, EntityPlayer player, ItemStack stack) {
        double charge = ElectricItem.manager.getCharge(stack);
        if (charge > 5000) {
            if (!world.isRemote && world.getTotalWorldTime() % 600 == 0) {
                thaumcraft.api.ThaumcraftApi.internalMethods.addWarpToPlayer(player, -1, true);

                if (world.rand.nextInt(3) == 0) {
                    thaumcraft.api.ThaumcraftApi.internalMethods.addWarpToPlayer(player, -1, false);
                    thaumcraft.api.ThaumcraftApi.internalMethods.addStickyWarpToPlayer(player, -1);
                }
                ElectricItem.manager.discharge(stack, 15000.0D, Integer.MAX_VALUE, true, false, false);
            }

            if (world.getTotalWorldTime() % 40 == 0) {
                int[] badEffects = {19, 20, 9, 15, 17, 18};

                for (int id : badEffects) {
                    if (player.isPotionActive(id)) {
                        player.removePotionEffect(id);
                        ElectricItem.manager.discharge(stack, 1000.0D, Integer.MAX_VALUE, true, false, false);
                    }
                }
            }
            if (world.getTotalWorldTime() % 100 == 0) {
                java.util.Collection collection = player.getActivePotionEffects();
                if (collection != null && !collection.isEmpty()) {
                    java.util.Iterator iterator = collection.iterator();
                    while (iterator.hasNext()) {
                        net.minecraft.potion.PotionEffect effect = (net.minecraft.potion.PotionEffect) iterator.next();
                        int id = effect.getPotionID();
                        String name = effect.getEffectName();
                        if (name.toLowerCase().contains("warp") || name.toLowerCase().contains("thaum") || id >= 32) {
                            player.removePotionEffect(id);
                            ElectricItem.manager.discharge(stack, 5000.0D, Integer.MAX_VALUE, true, false, false);
                            break;
                        }
                    }
                }
            }
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

        if (currentEnergy > 100000) {
            ItemStack heldItem = player.getCurrentEquippedItem();
            if (heldItem != null && heldItem.getItem() instanceof ic2.api.item.IElectricItem) {
                double charged = ElectricItem.manager.charge(heldItem, 10000.0D, 4, false, false);
                if (charged > 0) {
                    ElectricItem.manager.discharge(stack, charged, Integer.MAX_VALUE, true, false, false);
                }
            }
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