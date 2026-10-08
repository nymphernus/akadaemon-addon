package com.akadaemon.addon.test;

import com.akadaemon.addon.fluids.ModFluids;
import org.junit.Test;
import static org.junit.Assert.*;

public class FluidInitTest {
    @Test
    public void testModFluidsExists() {
        assertNotNull(ModFluids.class);
    }
}
