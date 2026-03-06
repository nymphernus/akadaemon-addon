package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.handler.GuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTitanDrill extends BlockContainer {

    private IIcon iconSide;
    private IIcon iconTop;
    private IIcon iconMainOff;
    private IIcon iconMainOn;

    public BlockTitanDrill() {
        super(Material.iron);
        this.setBlockName("titan_drill");
        this.setHardness(5.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeMetal);
        this.setBlockTextureName(AkadaemonAddon.MODID + ":titan_drill");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.iconSide = reg.registerIcon(AkadaemonAddon.MODID + ":titan_drill_side");
        this.iconTop = reg.registerIcon(AkadaemonAddon.MODID + ":titan_drill_top");
        this.iconMainOff = reg.registerIcon(AkadaemonAddon.MODID + ":titan_drill_main_off");
        this.iconMainOn = reg.registerIcon(AkadaemonAddon.MODID + ":titan_drill_main_on");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        boolean active = false;
        if (te instanceof TileEntityTitanDrill) {
            active = ((TileEntityTitanDrill)te).isActive;
        }
        if (side == 1) {
            return iconTop;
        }
        if (side == 0) {
            return iconSide;
        }
        return active ? iconMainOn : iconMainOff;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 1) return iconTop;
        if (side != 1 && side != 0) return iconMainOff;
        return iconSide;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityTitanDrill();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(AkadaemonAddon.instance, GuiHandler.DRILL_ID, world, x, y, z);
        }
        return true;
    }
}