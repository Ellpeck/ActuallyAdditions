/*
 * This file ("ItemWaterBowl.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ItemWaterBowl extends ItemBase{

    public ItemWaterBowl(String name){
        super(name);
        this.setMaxStackSize(1);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickItem event){
        if(event.getWorld() != null){
            if(ConfigBoolValues.WATER_BOWL.isEnabled()){
                if(StackUtil.isValid(event.getItemStack()) && event.getItemStack().getItem() == Items.BOWL){
                    RayTraceResult trace = WorldUtil.getNearestBlockWithDefaultReachDistance(event.getWorld(), event.getEntityPlayer(), true, false, false);
                    ActionResult<ItemStack> result = ForgeEventFactory.onBucketUse(event.getEntityPlayer(), event.getWorld(), event.getItemStack(), trace);
                    if(result == null && trace != null && trace.getBlockPos() != null){
                        if(event.getEntityPlayer().canPlayerEdit(trace.getBlockPos().offset(trace.sideHit), trace.sideHit, event.getItemStack())){
                            IBlockState state = event.getWorld().getBlockState(trace.getBlockPos());
                            Block block = state.getBlock();

                            if((block == Blocks.WATER || block == Blocks.FLOWING_WATER) && state.getValue(BlockLiquid.LEVEL) == 0){
                                event.getEntityPlayer().playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

                                if(!event.getWorld().isRemote){
                                    event.getWorld().setBlockState(trace.getBlockPos(), Blocks.AIR.getDefaultState(), 11);
                                    ItemStack reduced = StackUtil.addStackSize(event.getItemStack(), -1);

                                    ItemStack bowl = new ItemStack(InitItems.itemWaterBowl);
                                    if(!StackUtil.isValid(reduced)){
                                        event.getEntityPlayer().setHeldItem(event.getHand(), bowl);
                                    }
                                    else if(!event.getEntityPlayer().inventory.addItemStackToInventory(bowl.copy())){
                                        EntityItem entityItem = new EntityItem(event.getWorld(), event.getEntityPlayer().posX, event.getEntityPlayer().posY, event.getEntityPlayer().posZ, bowl.copy());
                                        entityItem.setPickupDelay(0);
                                        event.getWorld().spawnEntity(entityItem);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack stack = player.getHeldItem(hand);

        RayTraceResult trace = WorldUtil.getNearestBlockWithDefaultReachDistance(world, player);
        ActionResult<ItemStack> result = ForgeEventFactory.onBucketUse(player, world, stack, trace);
        if(result != null){
            return result;
        }

        if(trace == null){
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }
        else if(trace.typeOfHit != RayTraceResult.Type.BLOCK){
            return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
        }
        else{
            BlockPos pos = trace.getBlockPos();

            if(!world.isBlockModifiable(player, pos)){
                return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
            }
            else{
                BlockPos pos1 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) && trace.sideHit == EnumFacing.UP ? pos : pos.offset(trace.sideHit);

                if(!player.canPlayerEdit(pos1, trace.sideHit, stack)){
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                }
                else if(this.tryPlaceContainedLiquid(player, world, pos1, false)){
                    return !player.capabilities.isCreativeMode ? new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(Items.BOWL)) : new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
                }
                else{
                    return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected){
        if(!world.isRemote){
            if(ConfigBoolValues.WATER_BOWL_LOSS.isEnabled()){
                if(world.getTotalWorldTime()%10 == 0 && world.rand.nextFloat() >= 0.5F){
                    int lastX = 0;
                    int lastY = 0;

                    if(stack.hasTagCompound()){
                        NBTTagCompound compound = stack.getTagCompound();
                        lastX = compound.getInteger("lastX");
                        lastY = compound.getInteger("lastY");
                    }

                    boolean change = false;
                    if((lastX != 0 && lastX != (int)entity.posX) || (lastY != 0 && lastY != (int)entity.posY)){
                        if(!entity.isSneaking()){
                            if(entity instanceof EntityPlayer){
                                EntityPlayer player = (EntityPlayer)entity;
                                if(this.tryPlaceContainedLiquid(player, world, player.getPosition(), true)){
                                   checkReplace(player, stack, new ItemStack(Items.BOWL), itemSlot);
                                }
                            }
                        }
                        change = true;
                    }

                    if(change || lastX == 0 || lastY == 0){
                        if(!stack.hasTagCompound()){
                            stack.setTagCompound(new NBTTagCompound());
                        }

                        NBTTagCompound compound = stack.getTagCompound();
                        compound.setInteger("lastX", (int)entity.posX);
                        compound.setInteger("lastY", (int)entity.posY);
                    }
                }
            }
        }
    }
    
    private void checkReplace(EntityPlayer player, ItemStack old, ItemStack stack, int slot) {
        if(player.inventory.getStackInSlot(slot) == old) player.inventory.setInventorySlotContents(slot, stack);
        else if(player.inventory.offHandInventory.get(slot) == old) player.inventory.offHandInventory.set(slot, stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        return !ItemStack.areItemsEqual(oldStack, newStack);
    }

    public boolean tryPlaceContainedLiquid(EntityPlayer player, World world, BlockPos pos, boolean finite){
        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        boolean nonSolid = !material.isSolid();
        boolean replaceable = state.getBlock().isReplaceable(world, pos);

        if(!world.isAirBlock(pos) && !nonSolid && !replaceable){
            return false;
        }
        else{
            if(world.provider.doesWaterVaporize()){
                world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F+(world.rand.nextFloat()-world.rand.nextFloat())*0.8F);

                for(int k = 0; k < 8; k++){
                    world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, (double)pos.getX()+Math.random(), (double)pos.getY()+Math.random(), (double)pos.getZ()+Math.random(), 0.0D, 0.0D, 0.0D);
                }
            }
            else{
                if(!world.isRemote && (nonSolid || replaceable) && !material.isLiquid()){
                    world.destroyBlock(pos, true);
                }

                world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);

                IBlockState placeState;
                if(finite){
                    placeState = Blocks.FLOWING_WATER.getDefaultState();
                }
                else{
                    placeState = Blocks.FLOWING_WATER.getDefaultState();
                }
                world.setBlockState(pos, placeState, 3);
            }

            return true;
        }
    }
}
