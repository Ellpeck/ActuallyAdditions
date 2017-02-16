/*
 * This file ("BlockLaserRelay.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.api.laser.Network;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.ItemEngineerGoggles;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserRelayUpgrade;
import de.ellpeck.actuallyadditions.mod.items.ItemLaserWrench;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockLaserRelay extends BlockContainerBase implements IHudDisplay{

    //This took way too much fiddling around. I'm not good with numbers.
    private static final float F = 1/16F;
    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(2*F, 0, 2*F, 1-2*F, 10*F, 1-2*F);
    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(2*F, 6*F, 2*F, 1-2*F, 1, 1-2*F);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(2*F, 2*F, 6*F, 1-2*F, 1-2*F, 1);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, 2*F, 2*F, 1-6*F, 1-2*F, 1-2*F);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(2*F, 2*F, 0, 1-2*F, 1-2*F, 1-6*F);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(6*F, 2*F, 2*F, 1, 1-2*F, 1-2*F);

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
                if(player.isSneaking()){
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
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, int meta, EntityLivingBase base){
        return this.getStateFromMeta(side.ordinal());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(BlockDirectional.FACING, EnumFacing.getFront(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, BlockDirectional.FACING);
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror){
        return this.withRotation(state, mirror.toRotation(state.getValue(BlockDirectional.FACING)));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        ItemStack stack = player.getHeldItem(hand);
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityLaserRelay){
            TileEntityLaserRelay relay = (TileEntityLaserRelay)tile;

            if(StackUtil.isValid(stack)){
                if(stack.getItem() instanceof ItemLaserWrench){
                    return false;
                }
                else if(stack.getItem() instanceof ItemCompass){
                    if(!world.isRemote){
                        relay.onCompassAction(player);

                        Network network = relay.getNetwork();
                        if(network != null){
                            network.changeAmount++;
                        }

                        relay.markDirty();
                        relay.sendUpdate();
                    }

                    return true;
                }
                else if(stack.getItem() instanceof ItemLaserRelayUpgrade){
                    ItemStack inRelay = relay.slots.getStackInSlot(0);
                    if(!StackUtil.isValid(inRelay)){
                        if(!world.isRemote){
                            if(!player.isCreative()){
                                player.setHeldItem(hand, StackUtil.addStackSize(stack, -1));
                            }

                            ItemStack set = StackUtil.validateCopy(stack);
                            relay.slots.setStackInSlot(0, StackUtil.setStackSize(set, 1));
                        }
                        return true;
                    }

                }
            }

            if(player.isSneaking()){
                ItemStack inRelay = StackUtil.validateCopy(relay.slots.getStackInSlot(0));
                if(StackUtil.isValid(inRelay)){
                    if(!world.isRemote){
                        relay.slots.setStackInSlot(0, StackUtil.getNull());

                        if(!player.inventory.addItemStackToInventory(inRelay)){
                            player.entityDropItem(inRelay, 0);
                        }
                    }
                    return true;
                }
            }

            if(relay instanceof TileEntityLaserRelayItemWhitelist){
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
    @SideOnly(Side.CLIENT)
    public void displayHud(Minecraft minecraft, EntityPlayer player, ItemStack stack, RayTraceResult posHit, ScaledResolution resolution){
        if(posHit != null && posHit.getBlockPos() != null && minecraft.world != null){
            boolean wearing = ItemEngineerGoggles.isWearing(player);
            if(wearing || StackUtil.isValid(stack)){
                boolean compass = stack.getItem() instanceof ItemCompass;
                if(wearing || compass || stack.getItem() instanceof ItemLaserWrench){
                    TileEntity tile = minecraft.world.getTileEntity(posHit.getBlockPos());
                    if(tile instanceof TileEntityLaserRelay){
                        TileEntityLaserRelay relay = (TileEntityLaserRelay)tile;

                        String strg = relay.getExtraDisplayString();
                        minecraft.fontRendererObj.drawStringWithShadow(strg, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+5, StringUtil.DECIMAL_COLOR_WHITE);

                        String expl;
                        if(compass){
                            expl = relay.getCompassDisplayString().replaceAll("\\\\n", "\n");
                        }
                        else{
                            expl = TextFormatting.GRAY.toString()+TextFormatting.ITALIC+StringUtil.localize("info."+ModUtil.MOD_ID+".laserRelay.mode.noCompasss");
                        }

                        StringUtil.drawSplitString(minecraft.fontRendererObj, expl, resolution.getScaledWidth()/2+5, resolution.getScaledHeight()/2+15, Integer.MAX_VALUE, StringUtil.DECIMAL_COLOR_WHITE, true);
                    }
                }
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