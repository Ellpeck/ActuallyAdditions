/*
 * This file ("ItemGrowthRing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.api.Position;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import java.util.ArrayList;

public class ItemGrowthRing extends ItemEnergy{

    public ItemGrowthRing(String name){
        super(1000000, 5000, name);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote || entity.isSneaking()){
            return;
        }

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getCurrentEquippedItem();

        int energyUse = 300;
        if(equipped != null && equipped == stack && this.getEnergyStored(stack) >= energyUse){
            ArrayList<Position> blocks = new ArrayList<Position>();

            if(stack.stackTagCompound == null){
                stack.setTagCompound(new NBTTagCompound());
            }
            int waitTime = stack.stackTagCompound.getInteger("WaitTime");

            //Adding all possible Blocks
            if(waitTime >= 30){
                int range = 3;
                for(int x = -range; x < range+1; x++){
                    for(int z = -range; z < range+1; z++){
                        for(int y = -range; y < range+1; y++){
                            int theX = MathHelper.floor_double(player.posX+x);
                            int theY = MathHelper.floor_double(player.posY+y);
                            int theZ = MathHelper.floor_double(player.posZ+z);
                            Block theBlock = world.getBlock(theX, theY, theZ);
                            if((theBlock instanceof IGrowable || theBlock instanceof IPlantable) && !(theBlock instanceof BlockGrass)){
                                blocks.add(new Position(theX, theY, theZ));
                            }
                        }
                    }
                }

                //Fertilizing the Blocks
                if(!blocks.isEmpty()){
                    for(int i = 0; i < 45; i++){
                        if(this.getEnergyStored(stack) >= energyUse){
                            Position pos = blocks.get(Util.RANDOM.nextInt(blocks.size()));

                            int metaBefore = pos.getMetadata(world);
                            pos.getBlock(world).updateTick(world, pos.getX(), pos.getY(), pos.getZ(), Util.RANDOM);

                            //Show Particles if Metadata changed
                            if(pos.getMetadata(world) != metaBefore){
                                world.playAuxSFX(2005, pos.getX(), pos.getY(), pos.getZ(), 0);
                            }

                            if(!player.capabilities.isCreativeMode){
                                this.extractEnergy(stack, energyUse, false);
                            }
                        }
                        else{
                            break;
                        }
                    }
                }

                stack.stackTagCompound.setInteger("WaitTime", 0);
            }
            else{
                stack.stackTagCompound.setInteger("WaitTime", waitTime+1);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }
}
