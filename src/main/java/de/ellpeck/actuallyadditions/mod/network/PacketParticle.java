/*
 * This file ("PacketParticle.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.misc.EntityColoredParticleFX;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketParticle implements IMessage{

    private int startX;
    private int startY;
    private int startZ;
    private int endX;
    private int endY;
    private int endZ;
    private float[] color;
    private int particleAmount;
    private float particleSize;

    @SuppressWarnings("unused")
    public PacketParticle(){

    }

    public PacketParticle(int startX, int startY, int startZ, int endX, int endY, int endZ, float[] color, int particleAmount, float particleSize){
        this.startX = startX;
        this.startY = startY;
        this.startZ = startZ;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.color = color;
        this.particleAmount = particleAmount;
        this.particleSize = particleSize;
    }

    @SideOnly(Side.CLIENT)
    public static void renderParticlesFromAToB(int startX, int startY, int startZ, int endX, int endY, int endZ, int particleAmount, float particleSize, float[] color, float ageMultiplier){
        World world = Minecraft.getMinecraft().theWorld;

        if(Minecraft.getMinecraft().thePlayer.getDistance(startX, startY, startZ) <= 64 || Minecraft.getMinecraft().thePlayer.getDistance(endX, endY, endZ) <= 64){
            int difX = startX-endX;
            int difY = startY-endY;
            int difZ = startZ-endZ;
            double distance = new Vec3d(startX, startY, startZ).distanceTo(new Vec3d(endX, endY, endZ));

            for(int times = 0; times < particleAmount/2; times++){
                for(double i = 0; i <= 1; i += 1/(distance*particleAmount)){
                    EntityColoredParticleFX fx = new EntityColoredParticleFX(world, (difX*i)+endX+0.5, (difY*i)+endY+0.5, (difZ*i)+endZ+0.5, particleSize, color[0], color[1], color[2], ageMultiplier);
                    Minecraft.getMinecraft().effectRenderer.addEffect(fx);
                }
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.startX = buf.readInt();
        this.startY = buf.readInt();
        this.startZ = buf.readInt();
        this.endX = buf.readInt();
        this.endY = buf.readInt();
        this.endZ = buf.readInt();
        this.particleAmount = buf.readInt();
        this.particleSize = buf.readFloat();

        this.color = new float[3];
        for(int i = 0; i < this.color.length; i++){
            this.color[i] = buf.readFloat();
        }
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeInt(this.startX);
        buf.writeInt(this.startY);
        buf.writeInt(this.startZ);
        buf.writeInt(this.endX);
        buf.writeInt(this.endY);
        buf.writeInt(this.endZ);
        buf.writeInt(this.particleAmount);
        buf.writeFloat(this.particleSize);

        for(float aColor : this.color){
            buf.writeFloat(aColor);
        }
    }

    public static class Handler implements IMessageHandler<PacketParticle, IMessage>{

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(PacketParticle message, MessageContext ctx){
            renderParticlesFromAToB(message.startX, message.startY, message.startZ, message.endX, message.endY, message.endZ, message.particleAmount, message.particleSize, message.color, 1);
            return null;
        }
    }
}
