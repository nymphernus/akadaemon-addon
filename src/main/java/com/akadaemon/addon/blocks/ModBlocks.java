package com.akadaemon.addon.blocks;

import com.akadaemon.addon.AkadaemonAddon;
import com.akadaemon.addon.fluids.ModFluids;
import com.akadaemon.addon.handler.ConfigHandler;
import com.akadaemon.addon.items.ItemBlockBase;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
    public static Block chunkLoader;
    public static Block blockMythril;
    public static Block blockTitan;
    public static Block blockAdamantit;
    public static Block thaumTransformer;
    public static Block titanDrill; // Из твоего кода выше
    public static Block amberFiber;
    public static Block barleyCrop;
    public static Block blockGlacialQuicksilver;
    public static Block blockEtherealPhoton;
    public static Block blockRubyFlux;
    public static Block compressedPanel;
    public static Block compressedSuperPanel;
    public static Block aetherTorch;

    public static void init() {
        initializeBlocks();
        registerBlocks();
    }

    private static void initializeBlocks() {
        blockMythril = new BlockMaterial(Material.iron)
                .setBlockName("block_mythril")
                .setBlockTextureName(AkadaemonAddon.MODID + ":block_mythril")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(AkadaemonAddon.tabAkadaemon);
        blockTitan = new BlockMaterial(Material.iron)
                .setBlockName("block_titan")
                .setBlockTextureName(AkadaemonAddon.MODID + ":block_titan")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(AkadaemonAddon.tabAkadaemon);
        blockAdamantit = new BlockMaterial(Material.iron)
                .setBlockName("block_adamantit")
                .setBlockTextureName(AkadaemonAddon.MODID + ":block_adamantit")
                .setHardness(10.0F)
                .setResistance(20.0F)
                .setStepSound(Block.soundTypeMetal)
                .setCreativeTab(AkadaemonAddon.tabAkadaemon);

        compressedPanel = new BlockSolarPanel(1)
                .setBlockName("compressed_panel")
                .setBlockTextureName(AkadaemonAddon.MODID + ":compressed_panel");

        compressedSuperPanel = new BlockSolarPanel(2)
                .setBlockName("compressed_super_panel")
                .setBlockTextureName(AkadaemonAddon.MODID + ":compressed_super_panel");

        thaumTransformer = new BlockThaumTransformer();
        amberFiber = new BlockAmberFiber();
        titanDrill = new BlockTitanDrill();
        barleyCrop = new BlockBarley();
        chunkLoader = new BlockChunkLoader();
        aetherTorch = new BlockAetherTorch();

        blockGlacialQuicksilver = new BlockFluid(ModFluids.fluidGlacialQuicksilver, Material.water, 6, 20)
                .setBlockName("glacial_quicksilver_block")
                .setBlockTextureName(AkadaemonAddon.MODID +":fluids/glacial_quicksilver_still");
        blockEtherealPhoton = new BlockFluid(ModFluids.fluidEtherealPhoton, Material.water, 6, 20)
                .setBlockName("ethereal_photon_block")
                .setBlockTextureName(AkadaemonAddon.MODID +":fluids/ethereal_photon_still");
        blockRubyFlux = new BlockFluid(ModFluids.fluidRubyFlux, Material.water, 6, 20)
                .setBlockName("ruby_flux")
                .setBlockTextureName(AkadaemonAddon.MODID +":fluids/ruby_flux_still");
    }
    private static void registerBlocks() {
        regBlock(blockMythril, "block_mythril", EnumChatFormatting.LIGHT_PURPLE, EnumChatFormatting.RESET);
        regBlock(blockTitan, "block_titan", EnumChatFormatting.GRAY, EnumChatFormatting.RESET);
        regBlock(blockAdamantit, "block_adamantit", EnumChatFormatting.RED, EnumChatFormatting.RESET);
        regBlock(thaumTransformer, "thaum_transformer", EnumChatFormatting.AQUA, EnumChatFormatting.RESET);
        regBlock(amberFiber, "amber_fiber", EnumChatFormatting.YELLOW, EnumChatFormatting.RESET);
        regBlock(titanDrill, "titan_drill", EnumChatFormatting.DARK_GRAY, EnumChatFormatting.RESET);
        regBlock(barleyCrop, "barley_crop",  EnumChatFormatting.WHITE, EnumChatFormatting.RESET);
        regBlock(chunkLoader, "chunk_loader",  EnumChatFormatting.YELLOW, EnumChatFormatting.RESET);

        regBlock(blockGlacialQuicksilver, "glacial_quicksilver_block", EnumChatFormatting.AQUA, EnumChatFormatting.RESET);
        regBlock(blockEtherealPhoton, "ethereal_photon_block", EnumChatFormatting.YELLOW, EnumChatFormatting.RESET);
        regBlock(blockRubyFlux, "ruby_flux", EnumChatFormatting.RED, EnumChatFormatting.RESET);

        regBlock(compressedPanel, "compressed_panel", EnumChatFormatting.RED, EnumChatFormatting.RESET);
        regBlock(compressedSuperPanel, "compressed_super_panel", EnumChatFormatting.AQUA, EnumChatFormatting.RESET);

        regBlock(aetherTorch, "aether_torch",  EnumChatFormatting.YELLOW, EnumChatFormatting.RESET);

        for (String name : ConfigHandler.mythrilBlockNames) { OreDictionary.registerOre(name, blockMythril); }

        OreDictionary.registerOre("blockTitan", blockTitan);
        OreDictionary.registerOre("blockAdamantit", blockAdamantit);
    }

    private static void regBlock(Block block, String name, EnumChatFormatting color, EnumChatFormatting style) { GameRegistry.registerBlock(block, ItemBlockBase.class, name, color, style); }
}
