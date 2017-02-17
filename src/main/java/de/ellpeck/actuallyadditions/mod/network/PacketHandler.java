/*
 * This file ("PacketHandler.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.network;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.achievement.TheAchievements;
import de.ellpeck.actuallyadditions.mod.data.PlayerData;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.network.gui.IButtonReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.INumberReactor;
import de.ellpeck.actuallyadditions.mod.network.gui.IStringReactor;
import de.ellpeck.actuallyadditions.mod.particle.ParticleLaserItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public final class PacketHandler{

    public static final List<IDataHandler> DATA_HANDLERS = new ArrayList<IDataHandler>();
    public static final IDataHandler LASER_HANDLER = new IDataHandler(){
        @Override
        @SideOnly(Side.CLIENT)
        public void handleData(NBTTagCompound compound, MessageContext context){
            AssetUtil.spawnLaserWithTimeClient(compound.getDouble("StartX"), compound.getDouble("StartY"), compound.getDouble("StartZ"), compound.getDouble("EndX"), compound.getDouble("EndY"), compound.getDouble("EndZ"), new float[]{compound.getFloat("Color1"), compound.getFloat("Color2"), compound.getFloat("Color3")}, compound.getInteger("MaxAge"), compound.getDouble("RotationTime"), compound.getFloat("Size"), compound.getFloat("Alpha"));
        }
    };
    public static final IDataHandler TILE_ENTITY_HANDLER = new IDataHandler(){
        @Override
        @SideOnly(Side.CLIENT)
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = Minecraft.getMinecraft().world;
            if(world != null){
                TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));
                if(tile instanceof TileEntityBase){
                    ((TileEntityBase)tile).readSyncableNBT(compound.getCompoundTag("Data"), TileEntityBase.NBTType.SYNC);
                }
            }
        }
    };
    public static final IDataHandler LASER_PARTICLE_HANDLER = new IDataHandler(){
        @Override
        @SideOnly(Side.CLIENT)
        public void handleData(NBTTagCompound compound, MessageContext context){
            Minecraft mc = Minecraft.getMinecraft();
            ItemStack stack = new ItemStack(compound);

            double inX = compound.getDouble("InX")+0.5;
            double inY = compound.getDouble("InY")+0.78;
            double inZ = compound.getDouble("InZ")+0.5;

            double outX = compound.getDouble("OutX")+0.5;
            double outY = compound.getDouble("OutY")+0.525;
            double outZ = compound.getDouble("OutZ")+0.5;

            if(mc.player.getDistance(outX, outY, outZ) <= 16){
                Particle fx = new ParticleLaserItem(mc.world, outX, outY, outZ, stack, 0.025, inX, inY, inZ);
                mc.effectRenderer.addEffect(fx);
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_TILE_HANDLER = new IDataHandler(){
        @Override
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
            TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));

            if(tile instanceof IButtonReactor){
                IButtonReactor reactor = (IButtonReactor)tile;
                Entity entity = world.getEntityByID(compound.getInteger("PlayerID"));
                if(entity instanceof EntityPlayer){
                    reactor.onButtonPressed(compound.getInteger("ButtonID"), (EntityPlayer)entity);
                }
            }
        }
    };
    public static final IDataHandler GUI_BUTTON_TO_CONTAINER_HANDLER = new IDataHandler(){
        @Override
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
            Entity entity = world.getEntityByID(compound.getInteger("PlayerID"));
            if(entity instanceof EntityPlayer){
                Container container = ((EntityPlayer)entity).openContainer;
                if(container instanceof IButtonReactor){
                    ((IButtonReactor)container).onButtonPressed(compound.getInteger("ButtonID"), (EntityPlayer)entity);
                }
            }
        }
    };
    public static final IDataHandler GUI_NUMBER_TO_TILE_HANDLER = new IDataHandler(){
        @Override
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
            TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));

            if(tile instanceof INumberReactor){
                INumberReactor reactor = (INumberReactor)tile;
                reactor.onNumberReceived(compound.getDouble("Number"), compound.getInteger("NumberID"), (EntityPlayer)world.getEntityByID(compound.getInteger("PlayerID")));
            }
        }
    };
    public static final IDataHandler GUI_STRING_TO_TILE_HANDLER = new IDataHandler(){
        @Override
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = DimensionManager.getWorld(compound.getInteger("WorldID"));
            TileEntity tile = world.getTileEntity(new BlockPos(compound.getInteger("X"), compound.getInteger("Y"), compound.getInteger("Z")));

            if(tile instanceof IStringReactor){
                IStringReactor reactor = (IStringReactor)tile;
                reactor.onTextReceived(compound.getString("Text"), compound.getInteger("TextID"), (EntityPlayer)world.getEntityByID(compound.getInteger("PlayerID")));
            }
        }
    };
    public static final IDataHandler SYNC_PLAYER_DATA = new IDataHandler(){
        @Override
        @SideOnly(Side.CLIENT)
        public void handleData(NBTTagCompound compound, MessageContext context){
            NBTTagCompound dataTag = compound.getCompoundTag("Data");
            EntityPlayer player = ActuallyAdditions.proxy.getCurrentPlayer();

            if(player != null){
                PlayerData.getDataFromPlayer(player).readFromNBT(dataTag, false);

                if(compound.getBoolean("Log")){
                    ModUtil.LOGGER.info("Receiving (new or changed) Player Data for player "+player.getName()+".");
                }
            }
            else{
                ModUtil.LOGGER.error("Tried to receive Player Data for the current player, but he doesn't seem to be present!");
            }
        }
    };
    public static final IDataHandler PLAYER_DATA_TO_SERVER = new IDataHandler(){
        @Override
        public void handleData(NBTTagCompound compound, MessageContext context){
            World world = DimensionManager.getWorld(compound.getInteger("World"));
            EntityPlayer player = world.getPlayerEntityByUUID(compound.getUniqueId("UUID"));
            if(player != null){
                PlayerData.PlayerSave data = PlayerData.getDataFromPlayer(player);

                int type = compound.getInteger("Type");
                if(type == 0){
                    data.loadBookmarks(compound.getTagList("Bookmarks", 8));
                }
                else if(type == 1){
                    data.didBookTutorial = compound.getBoolean("DidBookTutorial");
                }
                else if(type == 2){
                    data.loadTrials(compound.getTagList("Trials", 8));

                    if(compound.getBoolean("Achievement")){
                        TheAchievements.COMPLETE_TRIALS.get(player);
                    }
                }
                WorldData.get(world).markDirty();

                if(compound.getBoolean("Log")){
                    ModUtil.LOGGER.info("Receiving changed Player Data for player "+player.getName()+".");
                }
            }
            else{
                ModUtil.LOGGER.error("Tried to receive Player Data for player "+player.getName()+", but he doesn't seem to be present!");
            }
        }
    };

    public static SimpleNetworkWrapper theNetwork;

    public static void init(){
        theNetwork = NetworkRegistry.INSTANCE.newSimpleChannel(ModUtil.MOD_ID);
        theNetwork.registerMessage(PacketServerToClient.Handler.class, PacketServerToClient.class, 0, Side.CLIENT);
        theNetwork.registerMessage(PacketClientToServer.Handler.class, PacketClientToServer.class, 1, Side.SERVER);

        DATA_HANDLERS.add(LASER_HANDLER);
        DATA_HANDLERS.add(TILE_ENTITY_HANDLER);
        DATA_HANDLERS.add(GUI_BUTTON_TO_TILE_HANDLER);
        DATA_HANDLERS.add(GUI_STRING_TO_TILE_HANDLER);
        DATA_HANDLERS.add(GUI_NUMBER_TO_TILE_HANDLER);
        DATA_HANDLERS.add(SYNC_PLAYER_DATA);
        DATA_HANDLERS.add(GUI_BUTTON_TO_CONTAINER_HANDLER);
        DATA_HANDLERS.add(LASER_PARTICLE_HANDLER);
        DATA_HANDLERS.add(PLAYER_DATA_TO_SERVER);
    }
}
