/*
 * This file ("BlockContainerBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.base;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.tile.*;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockRedstoneTorch;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;

public abstract class BlockContainerBase extends BlockContainer{

    private String name;

    public BlockContainerBase(Material material, String name){
        super(material);
        this.name = name;

        this.register();
    }

    private void register(){
        this.setBlockName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerBlock(this, this.getItemBlock(), this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }
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
        return EnumRarity.common;
    }

    public void dropInventory(World world, int x, int y, int z){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(x, y, z);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                if(tile.getSizeInventory() > 0){
                    for(int i = 0; i < tile.getSizeInventory(); i++){
                        this.dropSlotFromInventory(i, tile, world, x, y, z);
                    }
                }
            }
        }
    }

    public void dropSlotFromInventory(int i, TileEntityInventoryBase tile, World world, int x, int y, int z){
        ItemStack stack = tile.getStackInSlot(i);
        if(stack != null && stack.stackSize > 0){
            float dX = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dY = Util.RANDOM.nextFloat()*0.8F+0.1F;
            float dZ = Util.RANDOM.nextFloat()*0.8F+0.1F;
            EntityItem entityItem = new EntityItem(world, x+dX, y+dY, z+dZ, stack.copy());
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        this.updateRedstoneState(world, x, y, z);
    }

    public void updateRedstoneState(World world, int x, int y, int z){
        TileEntity tile = world.getTileEntity(x, y, z);
        boolean powered = world.isBlockIndirectlyGettingPowered(x, y, z);
        if(tile instanceof TileEntityBase){
            ((TileEntityBase)tile).setRedstonePowered(powered);
            tile.markDirty();
        }
        if(tile instanceof IRedstoneToggle){
            if(((IRedstoneToggle)tile).isPulseMode() && powered){
                ((IRedstoneToggle)tile).activateOnPulse();
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
        if(stack.getTagCompound() != null){
            TileEntity tile = world.getTileEntity(x, y, z);

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
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player){
        if(!player.capabilities.isCreativeMode){
            this.dropBlockAsItem(world, x, y, z, meta, 0);
        }
    }

    @Override
    public boolean hasComparatorInputOverride(){
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int meta){
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile instanceof IInventory){
            return Container.calcRedstoneFromInventory((IInventory)tile);
        }
        return 0;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        ArrayList<ItemStack> drops = new ArrayList<ItemStack>();

        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile != null){
            ItemStack stack = new ItemStack(this.getItemDropped(metadata, Util.RANDOM, fortune), 1, this.damageDropped(metadata));

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
    public void onBlockAdded(World world, int x, int y, int z){
        this.updateRedstoneState(world, x, y, z);
    }

    public boolean tryToggleRedstone(World world, int x, int y, int z, EntityPlayer player){
        ItemStack stack = player.getCurrentEquippedItem();
        if(stack != null && Block.getBlockFromItem(stack.getItem()) instanceof BlockRedstoneTorch){
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof IRedstoneToggle){
                if(!world.isRemote){
                    ((IRedstoneToggle)tile).toggle(!((IRedstoneToggle)tile).isPulseMode());

                    if(tile instanceof TileEntityBase){
                        ((TileEntityBase)tile).sendUpdate();
                    }
                }
                return true;
            }
        }
        return false;
    }

}
