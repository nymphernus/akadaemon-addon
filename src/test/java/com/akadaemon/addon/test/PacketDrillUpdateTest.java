package com.akadaemon.addon.test;

import com.akadaemon.addon.handler.PacketDrillUpdate;
import org.junit.Test;
import static org.junit.Assert.*;

public class PacketDrillUpdateTest {
    @Test
    public void testPacketSerialization() {
        PacketDrillUpdate msg = new PacketDrillUpdate(10, 20, 30, 5, true, false);
        assertNotNull(msg);
    }
}
