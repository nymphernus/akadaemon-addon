package com.akadaemon.addon.handler;

import com.akadaemon.addon.blocks.TileEntityTitanDrill;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class PacketDrillUpdate implements IMessage {
    private int x, y, z, depth;
    private boolean silk;
    private boolean active;

    public PacketDrillUpdate() {}

    public PacketDrillUpdate(int x, int y, int z, int depth, boolean silk, boolean active) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.depth = depth;
        this.silk = silk;
        this.active = active;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        depth = buf.readInt();
        silk = buf.readBoolean();
        active = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(depth);
        buf.writeBoolean(silk);
        buf.writeBoolean(active);
    }

    public static class Handler implements IMessageHandler<PacketDrillUpdate, IMessage> {
        @Override
        public IMessage onMessage(PacketDrillUpdate message, MessageContext ctx) {
            TileEntity te = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (te instanceof TileEntityTitanDrill) {
                TileEntityTitanDrill drill = (TileEntityTitanDrill) te;
                drill.depthLimit = message.depth;
                drill.silkTouch = message.silk;
                drill.isActive = message.active;
                drill.markDirty();
                drill.getWorldObj().markBlockForUpdate(message.x, message.y, message.z);
            }
            return null;
        }
    }
}