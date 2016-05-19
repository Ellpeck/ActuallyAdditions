/*
 * This file ("ItemLeafBlower.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;

public class ItemLeafBlower extends ItemBase{

    private final boolean isAdvanced;

    public ItemLeafBlower(boolean isAdvanced, String name){
        super(name);
        this.isAdvanced = isAdvanced;
        this.setMaxStackSize(1);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand){
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        //Cuz you won't hold it for that long right-clicking anyways
        return Integer.MAX_VALUE;
    }

    @Nonnull
    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.isAdvanced ? EnumRarity.EPIC : EnumRarity.RARE;
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int time){
        if(!player.worldObj.isRemote){
            if(time <= this.getMaxItemUseDuration(stack) && (this.isAdvanced || time%3 == 0)){
                //Breaks the Blocks
                this.breakStuff(player.worldObj, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ));
                //Plays a Minecart sounds (It really sounds like a Leaf Blower!)
                if(!ConfigValues.lessSound){
                    player.worldObj.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_MINECART_RIDING, SoundCategory.PLAYERS, 0.3F, 0.001F);
                }
            }
        }
    }

    /**
     * Breaks (harvests) Grass and Leaves around
     *
     * @param world The World
     * @param x     The X Position of the Player
     * @param y     The Y Position of the Player
     * @param z     The Z Position of the Player
     */
    public void breakStuff(World world, int x, int y, int z){
        ArrayList<BlockPos> breakPositions = new ArrayList<BlockPos>();

        int rangeSides = 5;
        int rangeUp = 1;
        for(int reachX = -rangeSides; reachX < rangeSides+1; reachX++){
            for(int reachZ = -rangeSides; reachZ < rangeSides+1; reachZ++){
                for(int reachY = (this.isAdvanced ? -rangeSides : -rangeUp); reachY < (this.isAdvanced ? rangeSides : rangeUp)+1; reachY++){
                    //The current Block to break
                    BlockPos pos = new BlockPos(x+reachX, y+reachY, z+reachZ);
                    Block block = PosUtil.getBlock(pos, world);
                    if(block != null && (block instanceof BlockBush || (this.isAdvanced && block.isLeaves(world.getBlockState(pos), world, pos)))){
                        breakPositions.add(pos);
                    }
                }
            }
        }

        if(!breakPositions.isEmpty()){
            Collections.shuffle(breakPositions);

            BlockPos theCoord = breakPositions.get(0);
            Block theBlock = PosUtil.getBlock(theCoord, world);

            ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
            //Gets all of the Drops the Block should have
            drops.addAll(theBlock.getDrops(world, theCoord, world.getBlockState(theCoord), 0));

            //Plays the Breaking Sound
            if(!ConfigValues.lessBlockBreakingEffects){
                world.playBroadcastSound(2001, theCoord, Block.getStateId(world.getBlockState(theCoord)));
            }

            //Deletes the Block
            world.setBlockToAir(theCoord);

            for(ItemStack theDrop : drops){
                //Drops the Items into the World
                world.spawnEntityInWorld(new EntityItem(world, theCoord.getX()+0.5, theCoord.getY()+0.5, theCoord.getZ()+0.5, theDrop));
            }
        }
    }
}
