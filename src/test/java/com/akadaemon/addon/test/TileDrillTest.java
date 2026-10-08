package com.akadaemon.addon.test;

import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileDrillTest {
    @Test
    public void testTileDrillExists() {
        TileEntityTitanDrill tile = new TileEntityTitanDrill();
        assertNotNull(tile);
    }
}
