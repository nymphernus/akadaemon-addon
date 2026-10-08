package com.akadaemon.addon.test;

import com.akadaemon.addon.blocks.TileSolarPanel;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileSolarTest {
    @Test
    public void testTileSolarExists() {
        TileSolarPanel tile = new TileSolarPanel();
        assertNotNull(tile);
    }
}
