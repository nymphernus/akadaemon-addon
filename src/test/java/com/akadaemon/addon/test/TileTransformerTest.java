package com.akadaemon.addon.test;

import com.akadaemon.addon.blocks.TileThaumTransformer;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTransformerTest {
    @Test
    public void testTileTransformerExists() {
        TileThaumTransformer tile = new TileThaumTransformer();
        assertNotNull(tile);
    }
}
