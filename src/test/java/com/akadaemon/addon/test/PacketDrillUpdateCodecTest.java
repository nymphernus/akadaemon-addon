package com.akadaemon.addon.test;

import com.akadaemon.addon.handler.PacketDrillUpdate;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Verifies that every field of PacketDrillUpdate survives a full ByteBuf round trip,
 * so client state sent to the server is never silently dropped.
 */
public class PacketDrillUpdateCodecTest {

    private PacketDrillUpdate roundTrip(PacketDrillUpdate original) {
        ByteBuf buf = Unpooled.buffer();
        try {
            original.toBytes(buf);
            PacketDrillUpdate decoded = new PacketDrillUpdate();
            decoded.fromBytes(buf);
            return decoded;
        } finally {
            buf.release();
        }
    }

    @Test
    public void allFieldsSurviveRoundTrip() {
        PacketDrillUpdate decoded = roundTrip(new PacketDrillUpdate(-120, 64, 340, 12, true, true));

        assertEquals(-120, decoded.getX());
        assertEquals(64, decoded.getY());
        assertEquals(340, decoded.getZ());
        assertEquals(12, decoded.getDepthLimit());
        assertTrue(decoded.isSilkTouch());
        assertTrue(decoded.isActive());
    }

    @Test
    public void defaultsSurviveRoundTrip() {
        PacketDrillUpdate decoded = roundTrip(new PacketDrillUpdate(0, 0, 0, 0, false, false));

        assertEquals(0, decoded.getX());
        assertEquals(0, decoded.getY());
        assertEquals(0, decoded.getZ());
        assertEquals(0, decoded.getDepthLimit());
        assertFalse(decoded.isSilkTouch());
        assertFalse(decoded.isActive());
    }

    @Test
    public void extremeCoordinatesSurviveRoundTrip() {
        PacketDrillUpdate decoded =
                roundTrip(new PacketDrillUpdate(Integer.MIN_VALUE, Integer.MAX_VALUE, -1, 30, false, true));

        assertEquals(Integer.MIN_VALUE, decoded.getX());
        assertEquals(Integer.MAX_VALUE, decoded.getY());
        assertEquals(-1, decoded.getZ());
        assertEquals(30, decoded.getDepthLimit());
        assertFalse(decoded.isSilkTouch());
        assertTrue(decoded.isActive());
    }

    @Test
    public void defaultConstructorLeavesSafeDefaults() {
        PacketDrillUpdate empty = new PacketDrillUpdate();

        assertEquals(0, empty.getX());
        assertEquals(0, empty.getY());
        assertEquals(0, empty.getZ());
        assertEquals(0, empty.getDepthLimit());
        assertFalse(empty.isSilkTouch());
        assertFalse(empty.isActive());
    }
}