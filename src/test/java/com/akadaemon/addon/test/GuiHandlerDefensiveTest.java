package com.akadaemon.addon.test;

import com.akadaemon.addon.handler.GuiHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Covers the defensive null-guard path added to GuiHandler: a null player/world must
 * never propagate into World#getTileEntity.
 */
public class GuiHandlerDefensiveTest {

    @Test
    public void serverGuiReturnsNullForNullPlayer() {
        GuiHandler handler = new GuiHandler();
        assertNull(handler.getServerGuiElement(GuiHandler.THAUM_TRANSFORMER_ID, null, null, 0, 0, 0));
    }

    @Test
    public void clientGuiReturnsNullForNullWorld() {
        GuiHandler handler = new GuiHandler();
        assertNull(handler.getClientGuiElement(GuiHandler.DRILL_ID, null, null, 0, 0, 0));
    }

    @Test
    public void handlerIsInstantiableAndIdsAreDistinct() {
        GuiHandler handler = new GuiHandler();
        assertNotNull(handler);
        assertEquals(0, GuiHandler.THAUM_TRANSFORMER_ID);
        assertEquals(1, GuiHandler.DRILL_ID);
    }
}