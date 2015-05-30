package ellpeck.actuallyadditions.network;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityFluidCollector;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PacketFluidCollectorToClient implements IMessage{

    private boolean hasFluid;
    private int fluidID;
    private int fluidAmount;
    private int x;
    private int y;
    private int z;

    @SuppressWarnings("unused")
    public PacketFluidCollectorToClient(){

    }

    public PacketFluidCollectorToClient(FluidStack fluid, TileEntity tile){
        if(fluid != null){
            this.hasFluid = true;
            this.fluidID = fluid.getFluidID();
            this.fluidAmount = fluid.amount;
        }
        else this.hasFluid = false;

        this.x = tile.xCoord;
        this.y = tile.yCoord;
        this.z = tile.zCoord;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.hasFluid = buf.readBoolean();
        this.fluidID = buf.readInt();
        this.fluidAmount = buf.readInt();
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeBoolean(this.hasFluid);
        buf.writeInt(this.fluidID);
        buf.writeInt(this.fluidAmount);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
    }

    public static class Handler implements IMessageHandler<PacketFluidCollectorToClient, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketFluidCollectorToClient message, MessageContext ctx){
            World world = FMLClientHandler.instance().getClient().theWorld;
            TileEntity tile = world.getTileEntity(message.x, message.y, message.z);

            if(tile instanceof TileEntityFluidCollector){
                TileEntityFluidCollector collector = (TileEntityFluidCollector)tile;
                if(message.hasFluid){
                    collector.tank.setFluid(new FluidStack(FluidRegistry.getFluid(message.fluidID), message.fluidAmount));
                }
                else collector.tank.setFluid(null);
            }
            return null;
        }
    }
}
