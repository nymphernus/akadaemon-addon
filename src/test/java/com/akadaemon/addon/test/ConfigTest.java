package com.akadaemon.addon.test;

import com.akadaemon.addon.handler.ConfigHandler;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigTest {
    @Test
    public void testConfigHandlerExists() {
        assertNotNull(ConfigHandler.class);
    }
}
