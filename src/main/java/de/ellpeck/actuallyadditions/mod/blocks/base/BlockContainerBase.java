/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.tile.*;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Random;

public abstract class BlockContainerBase extends BlockContainer{

    private String name;

    public BlockContainerBase(Material material, String name){
        super(material);
        this.name = name;

        this.register();
    }

    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }

        Util.ITEMS_AND_BLOCKS.add(this);
    }

    protected String getBaseName(){
        return this.name;
    }

    protected Class<? extends ItemBlockBase> getItemBlock(){
        return ItemBlockBase.class;
    }

    public boolean shouldAddCreative(){
        return true;
    }

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
            if(stack.hasTagCompound()){
                entityItem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
            }
            float factor = 0.05F;
            entityItem.motionX = Util.RANDOM.nextGaussian()*factor;
            entityItem.motionY = Util.RANDOM.nextGaussian()*factor+0.2F;
            entityItem.motionZ = Util.RANDOM.nextGaussian()*factor;
            world.spawnEntityInWorld(entityItem);
        }
        tile.setInventorySlotContents(i, null);
    }

    @Override
    public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock){
        this.updateRedstoneState(world, pos);
    }

    public void updateRedstoneState(World world, BlockPos pos){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof TileEntityBase){
                boolean powered = world.isBlockIndirectlyGettingPowered(pos) > 0;
                boolean wasPowered = ((TileEntityBase)tile).isRedstonePowered;
                if(powered && !wasPowered){
                    if(tile instanceof IRedstoneToggle && ((IRedstoneToggle)tile).isPulseMode()){
                        world.scheduleUpdate(pos, this, this.tickRate(world));
                    }
                    ((TileEntityBase)tile).setRedstonePowered(true);
                }
                else if(!powered && wasPowered){
                    ((TileEntityBase)tile).setRedstonePowered(false);
                }
            }
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random){
        if(!world.isRemote){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof IRedstoneToggle && ((IRedstoneToggle)tile).isPulseMode()){
                ((IRedstoneToggle)tile).activateOnPulse();
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase entity, ItemStack stack){
        if(stack.getTagCompound() != null){
            TileEntity tile = world.getTileEntity(pos);

            if(tile instanceof IEnergySaver){
                ((IEnergySaver)tile).setEnergy(stack.getTagCompound().getInteger("Energy"));
            }

            if(tile instanceof IFluidSaver){
                int amount = stack.getTagCompound().getInteger("FluidAmount");

                if(amount > 0){
                    FluidStack[] fluids = new FluidStack[amount];

                    for(int i = 0; i < amount; i++){
                        NBTTagCompound compound = stack.getTagCompound().getCompoundTag("Fluid"+i);
                        if(compound != null){
                            fluids[i] = FluidStack.loadFluidStackFromNBT(compound);
                        }
                    }

                    ((IFluidSaver)tile).setFluids(fluids);
                }
            }
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        if(!player.capabilities.isCreativeMode){
            this.dropBlockAsItem(world, pos, state, 0);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(){
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, BlockPos pos){
        TileEntity tile = world.getTileEntity(pos);
        if(tile instanceof IInventory){
            return Container.calcRedstoneFromInventory((IInventory)tile);
        }
        return 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        TileEntity tile = world.getTileEntity(pos);
        if(tile != null){
            ItemStack stack = new ItemStack(this.getItemDropped(state, Util.RANDOM, fortune), 1, this.damageDropped(state));

            if(tile instanceof IEnergySaver){
                int energy = ((IEnergySaver)tile).getEnergy();
                if(energy > 0){
                    if(stack.getTagCompound() == null){
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    stack.getTagCompound().setInteger("Energy", energy);
                }
            }

            if(tile instanceof IFluidSaver){
                FluidStack[] fluids = ((IFluidSaver)tile).getFluids();

                if(fluids != null && fluids.length > 0){
                    if(stack.getTagCompound() == null){
                        stack.setTagCompound(new NBTTagCompound());
                    }

                    stack.getTagCompound().setInteger("FluidAmount", fluids.length);
                    for(int i = 0; i < fluids.length; i++){
                        if(fluids[i] != null && fluids[i].amount > 0){
                            NBTTagCompound compound = new NBTTagCompound();
                            fluids[i].writeToNBT(compound);
                            stack.getTagCompound().setTag("Fluid"+i, compound);
                        }
                    }
                }
            }

            drops.add(stack);
        }

        return drops;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        this.updateRedstoneState(world, pos);
    }

    public boolean tryToggleRedstone(World world, BlockPos pos, EntityPlayer player){
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch){
            TileEntity tile = world.getTileEntity(pos);
            if(tile instanceof IRedstoneToggle){
                if(!world.isRemote){
                    ((IRedstoneToggle)tile).toggle(!((IRedstoneToggle)tile).isPulseMode());
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
    protected BlockState createBlockState(){
        return this.getMetaProperty() == null ? super.createBlockState() : new BlockState(this, this.getMetaProperty());
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getMetaProperty() == null ? super.getStateFromMeta(meta) : this.getDefaultState().withProperty(this.getMetaProperty(), meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return this.getMetaProperty() == null ? super.getMetaFromState(state) : state.getValue(this.getMetaProperty());
    }

    protected PropertyInteger getMetaProperty(){
        return null;
    }

    @Override
    public int getRenderType(){
        return 3;
    }
}
