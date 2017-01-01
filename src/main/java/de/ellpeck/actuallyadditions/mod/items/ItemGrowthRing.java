/*
 * This file ("ItemGrowthRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;
import java.util.List;

public class ItemGrowthRing extends ItemEnergy{

    public ItemGrowthRing(String name){
        super(1000000, 2000, name);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote || entity.isSneaking()){
            return;
        }

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getHeldItemMainhand();

        int energyUse = 300;
        if(StackUtil.isValid(equipped) && equipped == stack && this.getEnergyStored(stack) >= energyUse){
            List<BlockPos> blocks = new ArrayList<BlockPos>();

            //Adding all possible Blocks
            if(player.world.getTotalWorldTime()%30 == 0){
                int range = 3;
                for(int x = -range; x < range+1; x++){
                    for(int z = -range; z < range+1; z++){
                        for(int y = -range; y < range+1; y++){
                            int theX = MathHelper.floor(player.posX+x);
                            int theY = MathHelper.floor(player.posY+y);
                            int theZ = MathHelper.floor(player.posZ+z);
                            BlockPos posInQuestion = new BlockPos(theX, theY, theZ);
                            Block theBlock = world.getBlockState(posInQuestion).getBlock();
                            if((theBlock instanceof IGrowable || theBlock instanceof IPlantable) && !(theBlock instanceof BlockGrass)){
                                blocks.add(posInQuestion);
                            }
                        }
                    }
                }

                //Fertilizing the Blocks
                if(!blocks.isEmpty()){
                    for(int i = 0; i < 45; i++){
                        if(this.getEnergyStored(stack) >= energyUse){
                            BlockPos pos = blocks.get(world.rand.nextInt(blocks.size()));

                            IBlockState state = world.getBlockState(pos);
                            Block block = state.getBlock();
                            int metaBefore = block.getMetaFromState(state);
                            block.updateTick(world, pos, world.getBlockState(pos), world.rand);

                            //Show Particles if Metadata changed
                            IBlockState newState = world.getBlockState(pos);
                            if(newState.getBlock().getMetaFromState(newState) != metaBefore){
                                world.playEvent(2005, pos, 0);
                            }

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergyInternal(stack, energyUse, false);
                            }
                        }
                        else{
                            break;
                        }
                    }
                }
            }
        }
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
