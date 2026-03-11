package com.akadaemon.addon.items;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.entity.EntityFocusPearl;
import com.akadaemon.addon.handler.ConfigHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.wands.ItemFocusBasic;
import thaumcraft.common.items.wands.ItemWandCasting;

public class ItemFocusTeleport extends ItemFocusBasic {

    public ItemFocusTeleport() {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName("focus_teleport");
        this.setTextureName(AkadaemonAddon.MODID + ":focus_teleport");
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

        if (wand.consumeAllVis(wandstack, player, getVisCost(wandstack), true, false)) {

            world.playSoundAtEntity(player, "thaumcraft:fly", 0.7F, 0.6F / (itemRand.nextFloat() * 0.4F + 0.8F));

            if (!world.isRemote) {
                EntityFocusPearl pearl = new EntityFocusPearl(world, player);

                pearl.motionX *= ConfigHandler.teleportSpeed;
                pearl.motionY *= ConfigHandler.teleportSpeed;
                pearl.motionZ *= ConfigHandler.teleportSpeed;

                world.spawnEntityInWorld(pearl);
            }

            player.swingItem();
        }
        return wandstack;
    }

    @Override
    public int getFocusColor(ItemStack focusstack) {
        return 0x22AAFF;
    }

    @Override
    public AspectList getVisCost(ItemStack focusstack) {
        return new AspectList().add(Aspect.AIR, 100).add(Aspect.WATER, 100);
    }
}