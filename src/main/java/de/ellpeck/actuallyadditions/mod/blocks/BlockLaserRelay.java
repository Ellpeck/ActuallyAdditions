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

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayEnergy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItem;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelayItemWhitelist;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLaserRelay extends BlockContainerBase{

    //This took way too much fiddling around. I'm not good with numbers.
    private static final float F = 1/16F;
    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(2*F, 0, 2*F, 1-2*F, 1-F, 1-2*F);
    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(2*F, F, 2*F, 1-2*F, 1, 1-2*F);
    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(2*F, F, F, 1-2*F, 1-F, 1);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0, F, 2*F, 1-F, 1-F, 1-2*F);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(2*F, F, 0, 1-2*F, 1-F, 1-F);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(F, F, 2*F, 1, 1-F, 1-2*F);

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 5);
    private final Type type;

    public BlockLaserRelay(String name, Type type){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);

        this.type = type;
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
        if(player.isSneaking()){
            TileEntityLaserRelay relay = (TileEntityLaserRelay)world.getTileEntity(pos);
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityLaserRelayItem){
                ((TileEntityLaserRelayItem)tile).saveAllHandlersAround();
                System.out.println("------------Saving around on change " + ((TileEntityLaserRelayItem)tile).handlersAround);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i){
        switch(this.type){
            case ITEM:
                return new TileEntityLaserRelayItem();
            case ITEM_WHITELIST:
                return new TileEntityLaserRelayItemWhitelist();
            default:
                return new TileEntityLaserRelayEnergy();
        }
    }

    public enum Type{
        ENERGY,
        ITEM,
        ITEM_WHITELIST
    }
}