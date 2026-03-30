package com.akadaemon.addon.items;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemSwordTrinity extends ItemSwordBase implements IElectricItem {

    public ItemSwordTrinity(ToolMaterial material, String unlocalizedName, EnumChatFormatting color) {
        super(material, unlocalizedName, color);
        this.setMaxDamage(27);
        this.setHasSubtypes(true);
    }

    @Override
    public Multimap getAttributeModifiers(ItemStack stack) {
        Multimap multimap = com.google.common.collect.HashMultimap.create();

        double energy = ic2.api.item.ElectricItem.manager.getCharge(stack);
        double damage = 1.0D;

        if (energy >= 1000) {
            damage = 10.0D;
            if (energy > 100000) {
                damage = 25.0D;
            }
        }
        multimap.put(net.minecraft.entity.SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new net.minecraft.entity.ai.attributes.AttributeModifier(field_111210_e, "Weapon modifier", damage, 0));

        return multimap;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        ItemStack stackEmpty = new ItemStack(item, 1, this.getMaxDamage());
        stackEmpty.addEnchantment(net.minecraft.enchantment.Enchantment.looting, 3);
        list.add(stackEmpty);

        ItemStack stackFull = new ItemStack(item, 1, 1);
        stackFull.addEnchantment(net.minecraft.enchantment.Enchantment.looting, 3);
        ElectricItem.manager.charge(stackFull, getMaxCharge(stackFull), getTier(stackFull), true, false);
        list.add(stackFull);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.stackTagCompound != null) {
            boolean mode = stack.stackTagCompound.getBoolean("FireMode");

            String statusKey = mode ? "tooltip.trinity.active" : "tooltip.trinity.inactive";
            EnumChatFormatting color = mode ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;

            String formattedStatus = color.toString() + StatCollector.translateToLocal(statusKey);
            list.add(StatCollector.translateToLocalFormatted("message.trinity.fire_module", formattedStatus));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (ic2.core.IC2.keyboard.isModeSwitchKeyDown(player)) {
            if (!world.isRemote) {
                if (!ElectricItem.manager.canUse(stack, 100000)) {
                    return stack;
                }

                if (stack.stackTagCompound == null) stack.setTagCompound(new NBTTagCompound());
                boolean mode = stack.stackTagCompound.getBoolean("FireMode");
                stack.stackTagCompound.setBoolean("FireMode", !mode);

                String statusKey = !mode ? "tooltip.trinity.active" : "tooltip.trinity.inactive";
                EnumChatFormatting color = !mode ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;

                player.addChatMessage(new net.minecraft.util.ChatComponentTranslation("message.trinity.fire_module",
                        color.toString() + StatCollector.translateToLocal(statusKey)));
            }
            return stack;
        }
        return super.onItemRightClick(stack, world, player);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        ElectricItem.manager.use(stack, 2000, attacker);
        double currentEnergy = ElectricItem.manager.getCharge(stack);

        if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("FireMode") && currentEnergy < 100000) {
            stack.stackTagCompound.setBoolean("FireMode", false);
        }

        if (currentEnergy >= 100000) {
            if (stack.hasTagCompound() && stack.stackTagCompound.getBoolean("FireMode")) {
                target.setFire(5);
            }
            target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 2));

            if (attacker instanceof EntityPlayer) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 10.0F);
            }
            return true;
        }
        return super.hitEntity(stack, target, attacker);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (stack.stackTagCompound == null) stack.setTagCompound(new NBTTagCompound());
        stack.addEnchantment(net.minecraft.enchantment.Enchantment.looting, 3);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack, int pass) {
        return ElectricItem.manager.getCharge(stack) >= 100000;
    }

    @Override
    public int getItemEnchantability() { return 0; }
    @Override
    public boolean canProvideEnergy(ItemStack stack) { return false; }
    @Override
    public double getMaxCharge(ItemStack stack) { return 3000000; }
    @Override
    public int getTier(ItemStack stack) { return 3; }
    @Override
    public double getTransferLimit(ItemStack stack) { return 5000; }
    @Override
    public Item getChargedItem(ItemStack stack) { return this; }
    @Override
    public Item getEmptyItem(ItemStack stack) { return this; }
}