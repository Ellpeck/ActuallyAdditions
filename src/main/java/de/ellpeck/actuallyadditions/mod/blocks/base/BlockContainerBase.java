/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class BlockContainerBase extends BlockContainer implements ItemBlockBase.ICustomRarity{

    private final String name;

    public BlockContainerBase(Material material, String name){
        super(material);
        this.name = name;

        this.register();
    }

    private void register(){
        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName(){
        return this.name;
    }

    protected ItemBlockBase getItemBlock(){
        return new ItemBlockBase(this);
    }

    public boolean shouldAddCreative(){
        return true;
    }

    protected void registerRendering(){
        ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.COMMON;
    }

    public void dropInventory(World world, BlockPos position){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(position);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                if(tile.getSizeInventory() > 0){
                    for(int i = 0; i < tile.getSizeInventory(); i++){
                        this.dropSlotFromInventory(i, tile, world, position);
                    }
                }
            }
        }
    }

    public void dropSlotFromInventory(int i, TileEntityInventoryBase tile, World world, BlockPos pos){
        ItemStack stack = tile.getStackInSlot(i);
        if(stack != null && stack.stackSize > 0){
            float dX = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dY = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dZ = Util.RANDOM.nextFloat()*0.8F+0.1F;
            EntityItem entityItem = new EntityItem(world, pos.getX()+dX, pos.getY()+dY, pos.getZ()+dZ, stack.copy());
            float factor = 0.05F;
            entityItem.motionX = Util.RANDOM.nextGaussian()*factor;
            entityItem.motionY = Util.RANDOM.nextGaussian()*factor+0.2F;
            entityItem.motionZ = Util.RANDOM.nextGaussian()*factor;
            world.spawnEntityInWorld(entityItem);
        }
    }

    public boolean tryToggleRedstone(World world, BlockPos pos, EntityPlayer player){
        ItemStack stack = player.getHeldItemMainhand();
        if(stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityBase){
                TileEntityBase base = (TileEntityBase)tile;
                if(!world.isRemote && base.isRedstoneToggle()){
                    base.isPulseMode = !base.isPulseMode;
                    tile.markDirty();

                    if(tile instanceof TileEntityBase){
                        ((TileEntityBase)tile).sendUpdate();
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getMetaProperty() == null ? super.getStateFromMeta(meta) : this.getDefaultState().withProperty(this.getMetaProperty(), meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return this.getMetaProperty() == null ? super.getMetaFromState(state) : state.getValue(this.getMetaProperty());
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityBase){
                TileEntityBase base = (TileEntityBase)tile;
                if(base.isRedstoneToggle() && base.isPulseMode){
                    base.activateOnPulse();
                }
            }
        }
    }

    public void neighborsChangedCustom(World world, BlockPos pos){
        this.updateRedstoneState(world, pos);

        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityBase){
            TileEntityBase base = (TileEntityBase)tile;
            if(base.shouldSaveDataOnChangeOrWorldStart()){
                base.saveDataOnChangeOrWorldStart();
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        super.neighborChanged(state, worldIn, pos, blockIn);
        this.neighborsChangedCustom(worldIn, pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor){
        super.onNeighborChange(world, pos, neighbor);
        if(world instanceof World){
            this.neighborsChangedCustom((World)world, pos);
        }
    }

    public void updateRedstoneState(World world, BlockPos pos){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityBase){
                TileEntityBase base = (TileEntityBase)tile;
                boolean powered = world.isBlockIndirectlyGettingPowered(pos) > 0;
                boolean wasPowered = base.isRedstonePowered;
                if(powered && !wasPowered){
                    if(base.isRedstoneToggle() && base.isPulseMode){
                        world.scheduleUpdate(pos, this, this.tickRate(world));
                    }
                    base.setRedstonePowered(true);
                }
                else if(!powered && wasPowered){
                    base.setRedstonePowered(false);
                }
            }
        }
    }

    protected boolean checkFailUseItemOnTank(EntityPlayer player, ItemStack heldItem, FluidTank tank){
        return heldItem == null || !FluidUtil.interactWithFluidHandler(heldItem, tank, player);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        this.updateRedstoneState(world, pos);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        if(stack.hasTagCompound()){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityBase){
                TileEntityBase base = (TileEntityBase)tile;
                NBTTagCompound compound = stack.getTagCompound().getCompoundTag("Data");
                base.readSyncableNBT(compound, TileEntityBase.NBTType.SAVE_BLOCK);
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        if(!player.capabilities.isCreativeMode){
            this.dropBlockAsItem(world, pos, state, 0);
            //dirty workaround because of Forge calling Item.onBlockStartBreak() twice
            world.setBlockToAir(pos);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state){
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof IInventory){
            return Container.calcRedstoneFromInventory((IInventory)tile);
        }
        return 0;
    }


    @Override
    protected BlockStateContainer createBlockState(){
        return this.getMetaProperty() == null ? super.createBlockState() : new BlockStateContainer(this, this.getMetaProperty());
    }


    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof TileEntityBase){
            TileEntityBase base = (TileEntityBase)tile;
            NBTTagCompound data = new NBTTagCompound();
            base.writeSyncableNBT(data, TileEntityBase.NBTType.SAVE_BLOCK);

            //Remove unnecessarily saved default values to avoid unstackability
            List<String> keysToRemove = new ArrayList<String>();
            for(String key : data.getKeySet()){
                NBTBase tag = data.getTag(key);
                //Remove only ints because they are the most common ones
                //Add else if below here to remove more types
                if(tag instanceof NBTTagInt){
                    if(((NBTTagInt)tag).getInt() == 0){
                        keysToRemove.add(key);
                    }
                }
            }
            for(String key : keysToRemove){
                data.removeTag(key);
            }

            ItemStack stack = new ItemStack(this.getItemDropped(state, Util.RANDOM, fortune), 1, this.damageDropped(state));
            if(!data.hasNoTags()){
                stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setTag("Data", data);
            }

            drops.add(stack);
        }

        return drops;
    }

    protected PropertyInteger getMetaProperty(){
        return null;
    }


    @Override
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }
}
