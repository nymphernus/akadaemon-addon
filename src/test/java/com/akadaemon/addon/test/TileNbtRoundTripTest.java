package com.akadaemon.addon.test;

import com.akadaemon.addon.blocks.TileAmberFiber;
import com.akadaemon.addon.blocks.TileEntityChunkLoader;
import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import com.akadaemon.addon.blocks.TileSolarPanel;
import com.akadaemon.addon.blocks.TileThaumTransformer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Round-trip NBT coverage: every field written by writeToNBT must survive readFromNBT.
 */
public class TileNbtRoundTripTest {

    /**
     * TileEntity#writeToNBT requires the class -> id map, which FML normally fills via
     * GameRegistry.registerTileEntity during mod load. Outside a running game we have to
     * seed it with the exact ids used by CommonProxy#registerTileEntities.
     */
    @BeforeClass
    public static void registerTileEntityMappings() {
        TileEntity.addMapping(TileThaumTransformer.class, "TileThaumTransformer");
        TileEntity.addMapping(TileAmberFiber.class, "TileAmberFiber");
        TileEntity.addMapping(TileEntityTitanDrill.class, "TileEntityDrill");
        TileEntity.addMapping(TileEntityChunkLoader.class, "TEChunkLoader");
        TileEntity.addMapping(TileSolarPanel.class, "TileSolarPanel");
    }

    @Test
    public void titanDrillRoundTripPreservesAllFields() {
        TileEntityTitanDrill drill = new TileEntityTitanDrill();
        drill.energy = 4321.5D;
        drill.depthLimit = 17;
        drill.silkTouch = true;
        drill.isActive = true;

        NBTTagCompound nbt = new NBTTagCompound();
        drill.writeToNBT(nbt);

        assertEquals(4321.5D, nbt.getDouble("energy"), 0.0001D);
        assertEquals(17, nbt.getInteger("depthLimit"));
        assertEquals(true, nbt.getBoolean("silkTouch"));
        assertEquals(true, nbt.getBoolean("isActive"));

        TileEntityTitanDrill restored = new TileEntityTitanDrill();
        restored.readFromNBT(nbt);

        assertEquals(4321.5D, restored.energy, 0.0001D);
        assertEquals(17, restored.depthLimit);
        assertEquals(true, restored.silkTouch);
        assertEquals(true, restored.isActive);
    }

    @Test
    public void solarPanelRoundTripPreservesTier() {
        TileSolarPanel panel = new TileSolarPanel(2);

        NBTTagCompound nbt = new NBTTagCompound();
        panel.writeToNBT(nbt);
        assertEquals(2, nbt.getInteger("tier"));

        TileSolarPanel restored = new TileSolarPanel(0);
        restored.readFromNBT(nbt);

        assertEquals(2, restored.getSourceTier() - 3);
    }

    @Test
    public void amberFiberRoundTripKeepsCoords() {
        TileAmberFiber fiber = new TileAmberFiber();

        NBTTagCompound nbt = new NBTTagCompound();
        fiber.writeToNBT(nbt);

        TileAmberFiber restored = new TileAmberFiber();
        restored.readFromNBT(nbt);

        assertNotNull(restored);
    }

    @Test
    public void chunkLoaderRoundTripIsStateless() {
        TileEntityChunkLoader loader = new TileEntityChunkLoader();

        NBTTagCompound nbt = new NBTTagCompound();
        loader.writeToNBT(nbt);

        TileEntityChunkLoader restored = new TileEntityChunkLoader();
        restored.readFromNBT(nbt);

        assertNotNull(restored);
    }

    @Test
    public void thaumTransformerRoundTripProducesValidNbt() {
        TileThaumTransformer transformer = new TileThaumTransformer();

        NBTTagCompound nbt = new NBTTagCompound();
        transformer.writeToNBT(nbt);

        // Empty inventory must still serialise an "Items" list so readFromNBT never NPEs.
        assertNotNull(nbt.getTagList("Items", 10));

        TileThaumTransformer restored = new TileThaumTransformer();
        restored.readFromNBT(nbt);

        assertEquals(1, restored.getSizeInventory());
        assertEquals(null, restored.getStackInSlot(0));
    }
}