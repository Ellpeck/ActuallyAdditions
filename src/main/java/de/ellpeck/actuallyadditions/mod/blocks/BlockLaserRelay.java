/*
 * This file ("BlockLaserRelay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockLaserRelay extends BlockContainerBase implements IHudDisplay{

    //This took way too much fiddling around. I'm not good with numbers.
    private static final float F = 1/16F;
    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(2*F, 0, 2*F, 1-2*F, 10*F, 1-2*F);
    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(2*F, 6*F, 2*F, 1-2*F, 1, 1-2*F);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(2*F, 2*F, 6*F, 1-2*F, 1-2*F, 1);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 2*F, 2*F, 1-6*F, 1-2*F, 1-2*F);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(2*F, 2*F, 0, 1-2*F, 1-2*F, 1-6*F);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(6*F, 2*F, 2*F, 1, 1-2*F, 1-2*F);

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);
    private final Type type;

    public BlockLaserRelay(String name, Type type){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);

        this.type = type;

        if(this.type.ordinal() == 0){
            MinecraftForge.EVENT_BUS.register(this);
        }
    }

    @SubscribeEvent
    public void onBlockRightClick(PlayerInteractEvent.RightClickBlock event){
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();

        if(player != null && world != null && StackUtil.isValid(stack) && pos != null){
            IBlockState state = event.getWorld().getBlockState(pos);
            if(state != null && state.getBlock() instanceof BlockLaserRelay){
                if(stack.getItem() instanceof ItemCompass && player.isSneaking()){
                    event.setUseBlock(Event.Result.ALLOW);
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        switch(this.getMetaFromState(state)){
            case 1:
                return AABB_UP;
            case 2:
                return AABB_NORTH;
            case 3:
                return AABB_SOUTH;
            case 4:
                return AABB_WEST;
            case 5:
                return AABB_EAST;
            default:
                return AABB_DOWN;
        }
    }

    @Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state){
        return false;
    }


    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase base){
        return this.getStateFromMeta(side.ordinal());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        TileEntityLaserRelay tile = (TileEntityLaserRelay)world.getTileEntity(pos);
        if(tile instanceof TileEntityLaserRelayItem){
            TileEntityLaserRelayItem relay = (TileEntityLaserRelayItem)tile;

            if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemCompass){
                if(player.isSneaking()){
                    relay.priority--;
                }
                else{
                    relay.priority++;
                }

                Network network = ActuallyAdditionsAPI.connectionHandler.getNetworkFor(relay.getPos(), relay.getWorld());
                if(network != null){
                    network.changeAmount++;
                }

                relay.markDirty();
                relay.sendUpdate();

                return true;
            }
            else if(relay instanceof TileEntityLaserRelayItemWhitelist && player.isSneaking()){
                if(!world.isRemote){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.LASER_RELAY_ITEM_WHITELIST.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        switch(this.type){
            case ITEM:
                return new TileEntityLaserRelayItem();
            case ITEM_WHITELIST:
                return new TileEntityLaserRelayItemWhitelist();
            case ENERGY_ADVANCED:
                return new TileEntityLaserRelayEnergyAdvanced();
            case ENERGY_EXTREME:
                return new TileEntityLaserRelayEnergyExtreme();
            case FLUIDS:
                return new TileEntityLaserRelayFluids();
            default:
                return new TileEntityLaserRelayEnergy();
        }
    }

    @Override
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        if(posHit != null && posHit.getBlockPos() != null && minecraft.theWorld != null){
            TileEntity tile = minecraft.theWorld.getTileEntity(posHit.getBlockPos());
            if(tile instanceof TileEntityLaserRelayItem){
                TileEntityLaserRelayItem relay = (TileEntityLaserRelayItem)tile;

                String strg = "Priority: "+TextFormatting.DARK_RED+relay.getPriority()+TextFormatting.RESET;
                minecraft.fontRendererObj.drawStringWithShadow(strg, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);

                String expl;
                if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemCompass){
                    expl = TextFormatting.GREEN+"Right-Click to increase! \nSneak-Right-Click to decrease!";
                }
                else{
                    expl = TextFormatting.GRAY.toString()+TextFormatting.ITALIC+"Hold a Compass to modify!";
                }

                StringUtil.drawSplitString(minecraft.fontRendererObj, expl, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+15, Integer.MAX_VALUE, StringUtil.DECIMAL_COLOR_WHITE, true);
            }
        }
    }

    public enum Type{
        ENERGY_BASIC,
        ENERGY_ADVANCED,
        ENERGY_EXTREME,
        FLUIDS,
        ITEM,
        ITEM_WHITELIST
    }
}