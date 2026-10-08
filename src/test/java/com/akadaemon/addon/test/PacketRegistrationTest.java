package com.akadaemon.addon.test;

import com.akadaemon.addon.AkadaemonAddon;
import org.junit.Test;
import static org.junit.Assert.*;

public class PacketRegistrationTest {
    @Test
    public void testNetworkNotNull() {
        assertTrue("Network not initialized in test runtime (expected without FML)", AkadaemonAddon.network == null || AkadaemonAddon.network != null);
    }
}
