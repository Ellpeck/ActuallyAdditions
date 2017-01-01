/*
 * This file ("ItemWaterRemovalRing.java") is part of the Actually Additions mod for Minecraft.
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
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemWaterRemovalRing extends ItemEnergy{

    public ItemWaterRemovalRing(String name){
        super(800000, 1000, name);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote || entity.isSneaking()){
            return;
        }

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getHeldItemMainhand();

        int energyUse = 150;
        if(StackUtil.isValid(equipped) && equipped == stack && this.getEnergyStored(stack) >= energyUse){

            //Setting everything to air
            int range = 3;
            for(int x = -range; x < range+1; x++){
                for(int z = -range; z < range+1; z++){
                    for(int y = -range; y < range+1; y++){
                        int theX = MathHelper.floor(player.posX+x);
                        int theY = MathHelper.floor(player.posY+y);
                        int theZ = MathHelper.floor(player.posZ+z);

                        //Remove Water
                        BlockPos pos = new BlockPos(theX, theY, theZ);
                        Block block = world.getBlockState(pos).getBlock();
                        if((block == Blocks.WATER || block == Blocks.FLOWING_WATER) && this.getEnergyStored(stack) >= energyUse){
                            world.setBlockToAir(pos);

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergyInternal(stack, energyUse, false);
                            }
                        }
                        //Remove Lava
                        else if((block == Blocks.LAVA || block == Blocks.FLOWING_LAVA) && this.getEnergyStored(stack) >= energyUse*2){
                            world.setBlockToAir(pos);

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergyInternal(stack, energyUse*2, false);
                            }
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
