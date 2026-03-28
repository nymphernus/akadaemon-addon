package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemSwordBase extends ItemSword {
    private final ToolMaterial localMaterial;
    private final EnumChatFormatting color;

    public ItemSwordBase(ToolMaterial material, String unlocalizedName, EnumChatFormatting color) {
        super(material);
        this.localMaterial = material;
        this.setUnlocalizedName(unlocalizedName);
        this.setTextureName(AkadaemonAddon.MODID + ":" + unlocalizedName);
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.color = color;
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return color.toString() + super.getItemStackDisplayName(stack);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (this.localMaterial == ModItems.toolMatTitan) return repair.getItem() == ModItems.ingotTitan;
        if (this.localMaterial == ModItems.toolMatAdamantit) return repair.getItem() == ModItems.ingotAdamantit;
        if (this.localMaterial == ModItems.toolMatMythril) return repair.getItem() == ModItems.ingotMythril;

        return super.getIsRepairable(toRepair, repair);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (this.localMaterial == ModItems.toolMatAdamantit) {
            target.setFire(5);
        }
        if (this.localMaterial == ModItems.toolMatMythril) {
            if (attacker instanceof EntityPlayer) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 2.0F);
            }
        }
        if (this.localMaterial == ModItems.toolMatTitan) {
            target.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 80, 1));
        }

        return super.hitEntity(stack, target, attacker);
    }
}