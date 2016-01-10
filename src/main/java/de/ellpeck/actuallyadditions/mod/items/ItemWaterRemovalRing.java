/*
 * This file ("ItemWaterRemovalRing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;


import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWaterRemovalRing extends ItemEnergy{

    public ItemWaterRemovalRing(String name){
        super(1000000, 5000, name);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote || entity.isSneaking()){
            return;
        }

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getCurrentEquippedItem();

        int energyUse = 350;
        if(equipped != null && equipped == stack && this.getEnergyStored(stack) >= energyUse){

            //Setting everything to air
            int range = 3;
            for(int x = -range; x < range+1; x++){
                for(int z = -range; z < range+1; z++){
                    for(int y = -range; y < range+1; y++){
                        int theX = MathHelper.floor_double(player.posX+x);
                        int theY = MathHelper.floor_double(player.posY+y);
                        int theZ = MathHelper.floor_double(player.posZ+z);

                        //Remove Water
                        BlockPos pos = new BlockPos(theX, theY, theZ);
                        Block block = PosUtil.getBlock(pos, world);
                        if((block == Blocks.water || block == Blocks.flowing_water) && this.getEnergyStored(stack) >= energyUse){
                            world.setBlockToAir(pos);

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergy(stack, energyUse, false);
                            }
                        }
                        //Remove Lava
                        else if((block == Blocks.lava || block == Blocks.flowing_lava) && this.getEnergyStored(stack) >= energyUse*2){
                            world.setBlockToAir(pos);

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergy(stack, energyUse*2, false);
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
