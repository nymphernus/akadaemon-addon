package com.akadaemon.addon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockThaumTransformer extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconMain;

    public BlockThaumTransformer() {
        super(Material.iron);
        this.setBlockName("thaum_transformer");
        this.setCreativeTab(AkadaemonAddon.tabAkadaemon);
        this.setHardness(5.0F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg) {
        this.iconTop = reg.registerIcon(AkadaemonAddon.MODID + ":thaum_transformer_top");
        this.iconMain = reg.registerIcon(AkadaemonAddon.MODID + ":thaum_transformer_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.iconTop : this.iconMain;
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileThaumTransformer();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(AkadaemonAddon.instance, 0, world, x, y, z);
        }
        return true;
    }
}