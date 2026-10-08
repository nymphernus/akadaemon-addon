package com.akadaemon.addon.test;

import com.akadaemon.addon.blocks.BlockThaumTransformer;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileEntityTest {
    @Test
    public void testBlockHasTileEntity() {
        BlockThaumTransformer block = new BlockThaumTransformer();
        assertTrue("BlockThaumTransformer must have TileEntity (1.7.10 API)", block.hasTileEntity());
    }
}
