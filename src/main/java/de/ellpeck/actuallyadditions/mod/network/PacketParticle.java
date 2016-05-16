/*
 * This file ("PacketParticle.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
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

    private double startX;
    private double startY;
    private double startZ;
    private double endX;
    private double endY;
    private double endZ;
    private float[] color;
    private int particleAmount;
    private float particleSize;

    @SuppressWarnings("unused")
    public PacketParticle(){

    }

    public PacketParticle(double startX, double startY, double startZ, double endX, double endY, double endZ, float[] color, int particleAmount, float particleSize){
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
    public static void renderParticlesFromAToB(double startX, double startY, double startZ, double endX, double endY, double endZ, int particleAmount, float particleSize, float[] color, float ageMultiplier){
        World world = Minecraft.getMinecraft().theWorld;

        if(Minecraft.getMinecraft().thePlayer.getDistance(startX, startY, startZ) <= 64 || Minecraft.getMinecraft().thePlayer.getDistance(endX, endY, endZ) <= 64){
            double difX = startX-endX;
            double difY = startY-endY;
            double difZ = startZ-endZ;
            double distance = new Vec3d(startX, startY, startZ).distanceTo(new Vec3d(endX, endY, endZ));

            for(int times = 0; times < Math.max(particleAmount/2, 1); times++){
                for(double i = 0; i <= 1; i += 1/(distance*particleAmount)){
                    EntityColoredParticleFX fx = new EntityColoredParticleFX(world, (difX*i)+endX+0.5, (difY*i)+endY+0.5, (difZ*i)+endZ+0.5, particleSize, color[0], color[1], color[2], ageMultiplier);
                    Minecraft.getMinecraft().effectRenderer.addEffect(fx);
                }
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf){
        this.startX = buf.readDouble();
        this.startY = buf.readDouble();
        this.startZ = buf.readDouble();
        this.endX = buf.readDouble();
        this.endY = buf.readDouble();
        this.endZ = buf.readDouble();
        this.particleAmount = buf.readInt();
        this.particleSize = buf.readFloat();

        this.color = new float[3];
        for(int i = 0; i < this.color.length; i++){
            this.color[i] = buf.readFloat();
        }
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeDouble(this.startX);
        buf.writeDouble(this.startY);
        buf.writeDouble(this.startZ);
        buf.writeDouble(this.endX);
        buf.writeDouble(this.endY);
        buf.writeDouble(this.endZ);
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
