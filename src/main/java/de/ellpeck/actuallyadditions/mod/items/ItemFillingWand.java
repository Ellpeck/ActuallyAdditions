/*
 * This file ("ItemFillingWand.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class ItemFillingWand extends ItemEnergy{

    public ItemFillingWand(String name){
        super(800000, 2000, name);
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!world.isRemote && player.getItemInUseCount() <= 0){
            if(player.isSneaking()){
                IBlockState state = world.getBlockState(pos);
                saveBlock(state, stack);
                return EnumActionResult.SUCCESS;
            }
            else if(loadBlock(stack) != null){
                if(!stack.hasTagCompound()){
                    stack.setTagCompound(new NBTTagCompound());
                }
                NBTTagCompound compound = stack.getTagCompound();

                if(compound.getInteger("CurrX") == 0 && compound.getInteger("CurrY") == 0 && compound.getInteger("CurrZ") == 0){
                    compound.setInteger("FirstX", pos.getX());
                    compound.setInteger("FirstY", pos.getY());
                    compound.setInteger("FirstZ", pos.getZ());

                    player.setActiveHand(hand);
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entity, int timeLeft){
        if(!world.isRemote){
            boolean clear = true;
            if(entity instanceof EntityPlayer){
                RayTraceResult result = WorldUtil.getNearestBlockWithDefaultReachDistance(world, (EntityPlayer)entity);
                if(result != null && result.getBlockPos() != null){
                    if(!stack.hasTagCompound()){
                        stack.setTagCompound(new NBTTagCompound());
                    }
                    NBTTagCompound compound = stack.getTagCompound();

                    BlockPos pos = result.getBlockPos();
                    compound.setInteger("SecondX", pos.getX());
                    compound.setInteger("SecondY", pos.getY());
                    compound.setInteger("SecondZ", pos.getZ());

                    clear = false;
                }
            }

            if(clear){
                ItemPhantomConnector.clearStorage(stack, "FirstX", "FirstY", "FirstZ");
            }
        }

        super.onPlayerStoppedUsing(stack, world, entity, timeLeft);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
        super.onUpdate(stack, world, entity, itemSlot, isSelected);

        if(!world.isRemote){
            boolean shouldClear = false;

            if(isSelected){
                if(entity instanceof EntityPlayer && stack.hasTagCompound()){
                    EntityPlayer player = (EntityPlayer)entity;
                    boolean creative = player.capabilities.isCreativeMode;

                    NBTTagCompound compound = stack.getTagCompound();

                    BlockPos firstPos = new BlockPos(compound.getInteger("FirstX"), compound.getInteger("FirstY"), compound.getInteger("FirstZ"));
                    BlockPos secondPos = new BlockPos(compound.getInteger("SecondX"), compound.getInteger("SecondY"), compound.getInteger("SecondZ"));

                    if(!BlockPos.ORIGIN.equals(firstPos) && !BlockPos.ORIGIN.equals(secondPos)){
                        int energyUse = 1500;

                        IBlockState replaceState = loadBlock(stack);
                        if(replaceState != null && (creative || this.getEnergyStored(stack) >= energyUse)){
                            int lowestX = Math.min(firstPos.getX(), secondPos.getX());
                            int lowestY = Math.min(firstPos.getY(), secondPos.getY());
                            int lowestZ = Math.min(firstPos.getZ(), secondPos.getZ());

                            int currX = compound.getInteger("CurrX");
                            int currY = compound.getInteger("CurrY");
                            int currZ = compound.getInteger("CurrZ");

                            BlockPos pos = new BlockPos(lowestX+currX, lowestY+currY, lowestZ+currZ);
                            IBlockState state = world.getBlockState(pos);

                            if(state.getBlock().isReplaceable(world, pos) && replaceState.getBlock().canPlaceBlockAt(world, pos)){
                                if(creative || removeFittingItem(replaceState, player)){
                                    world.setBlockState(pos, replaceState, 2);

                                    SoundType sound = replaceState.getBlock().getSoundType(replaceState, world, pos, player);
                                    world.playSound(null, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume()+1.0F)/2.0F, sound.getPitch()*0.8F);

                                    if(!creative){
                                        this.extractEnergy(stack, energyUse, false);
                                    }
                                }
                                else{
                                    shouldClear = true;
                                }
                            }

                            int distX = Math.abs(secondPos.getX()-firstPos.getX());
                            int distY = Math.abs(secondPos.getY()-firstPos.getY());
                            int distZ = Math.abs(secondPos.getZ()-firstPos.getZ());

                            currX++;
                            if(currX > distX){
                                currX = 0;
                                currY++;
                                if(currY > distY){
                                    currY = 0;
                                    currZ++;
                                    if(currZ > distZ){
                                        shouldClear = true;
                                    }
                                }
                            }

                            if(!shouldClear){
                                compound.setInteger("CurrX", currX);
                                compound.setInteger("CurrY", currY);
                                compound.setInteger("CurrZ", currZ);
                            }
                        }
                        else{
                            shouldClear = true;
                        }
                    }
                }
            }
            else{
                shouldClear = true;
            }

            if(shouldClear){
                ItemPhantomConnector.clearStorage(stack, "FirstX", "FirstY", "FirstZ", "SecondX", "SecondY", "SecondZ", "CurrX", "CurrY", "CurrZ");
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool){
        super.addInformation(stack, player, list, bool);

        String display = "None";

        IBlockState state = loadBlock(stack);
        if(state != null){
            Block block = state.getBlock();
            ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
            if(blockStack.getItem() != null){
                display = blockStack.getDisplayName();
            }
        }

        list.add("Selected Block: "+display);
    }

    private static boolean removeFittingItem(IBlockState state, EntityPlayer player){
        Block block = state.getBlock();
        ItemStack stack = new ItemStack(block, 1, block.getMetaFromState(state));

        if(stack != null && stack.getItem() != null){
            for(int i = 0; i < player.inventory.getSizeInventory(); i++){
                ItemStack slot = player.inventory.getStackInSlot(i);
                if(slot != null && slot.isItemEqual(stack) && slot.stackSize > 0){
                    slot.stackSize--;
                    if(slot.stackSize <= 0){
                        player.inventory.setInventorySlotContents(i, null);
                    }

                    return true;
                }
            }
        }

        return false;
    }

    private static void saveBlock(IBlockState state, ItemStack stack){
        if(!stack.hasTagCompound()){
            stack.setTagCompound(new NBTTagCompound());
        }
        NBTTagCompound compound = stack.getTagCompound();

        Block block = state.getBlock();
        compound.setString("Block", block.getRegistryName().toString());
        compound.setInteger("Meta", block.getMetaFromState(state));
    }

    private static IBlockState loadBlock(ItemStack stack){
        if(stack.hasTagCompound()){
            NBTTagCompound compound = stack.getTagCompound();
            String blockName = compound.getString("Block");
            int meta = compound.getInteger("Meta");

            Block block = Block.getBlockFromName(blockName);
            if(block != null){
                return block.getStateFromMeta(meta);
            }
        }
        return null;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return Integer.MAX_VALUE;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
