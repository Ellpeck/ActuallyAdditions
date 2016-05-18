/*
 * This file ("BlockImpureIron.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.gen.cave.CaveWorldType;
import de.ellpeck.actuallyadditions.mod.items.InitItems;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheDusts;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

public class BlockImpureIron extends BlockGeneric{

    public BlockImpureIron(String name){
        super(name, Material.ROCK, SoundType.STONE, 3.5F, 12.5F, "pickaxe", 1);
    }

    @Override
    protected ItemBlockBase getItemBlock(){
        return new TheItemBlock(this);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }

        @Override
        public boolean onEntityItemUpdate(EntityItem item){
            if(item != null && CaveWorldType.isCave(item.getEntityWorld())){
                if(item.isInWater()){
                    if(!item.getEntityWorld().isRemote){
                        ItemStack stack = item.getEntityItem();
                        if(stack != null){
                            if(!stack.hasTagCompound()){
                                stack.setTagCompound(new NBTTagCompound());
                            }
                            NBTTagCompound compound = stack.getTagCompound();

                            int conversionTimer = compound.getInteger("ConversionTimer");
                            if(conversionTimer >= 2000){
                                item.setEntityItemStack(new ItemStack(InitItems.itemDust, 1, TheDusts.IRON.ordinal()));

                                if(item.getEntityWorld() instanceof WorldServer){
                                    ((WorldServer)item.getEntityWorld()).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, item.posX, item.posY, item.posZ, 30, 0D, 0D, 0D, 0.05D);
                                }
                            }
                            else{
                                compound.setInteger("ConversionTimer", conversionTimer+1);
                            }
                        }
                    }
                    else{
                        if(item.getEntityWorld().getTotalWorldTime()%20 == 0){
                            item.getEntityWorld().spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, MathHelper.floor_double(item.posX)+Util.RANDOM.nextDouble(), MathHelper.floor_double(item.posY)+Util.RANDOM.nextDouble(), MathHelper.floor_double(item.posZ)+Util.RANDOM.nextDouble(), 0D, 0D, 0D);
                        }
                    }
                }
            }
            return false;
        }
    }
}
