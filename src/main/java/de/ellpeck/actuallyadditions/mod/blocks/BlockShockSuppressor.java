/*
 * This file ("BlockShockSuppressor.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityShockSuppressor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlockShockSuppressor extends BlockContainerBase{

    public BlockShockSuppressor(String name){
        super(Material.ROCK, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(20.0F);
        this.setResistance(2000.0F);
        this.setSoundType(SoundType.STONE);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onExplosion(ExplosionEvent.Detonate event){
        World world = event.getWorld();
        if(!world.isRemote){
            List<BlockPos> affectedBlocks = event.getAffectedBlocks();
            List<Entity> affectedEntities = event.getAffectedEntities();

            int rangeSq = TileEntityShockSuppressor.RANGE*TileEntityShockSuppressor.RANGE;
            int use = TileEntityShockSuppressor.USE_PER;

            for(TileEntityShockSuppressor suppressor : TileEntityShockSuppressor.SUPPRESSORS){
                if(!suppressor.isRedstonePowered){
                    BlockPos supPos = suppressor.getPos();

                    List<Entity> entitiesToRemove = new ArrayList<Entity>();
                    List<BlockPos> posesToRemove = new ArrayList<BlockPos>();

                    for(BlockPos pos : affectedBlocks){
                        if(pos.distanceSq(supPos) <= rangeSq){
                            posesToRemove.add(pos);
                        }
                    }
                    for(Entity entity : affectedEntities){
                        if(entity.getPositionVector().squareDistanceTo(supPos.getX(), supPos.getY(), supPos.getZ()) <= rangeSq){
                            entitiesToRemove.add(entity);
                        }
                    }

                    Collections.shuffle(entitiesToRemove);
                    Collections.shuffle(posesToRemove);

                    for(BlockPos pos : posesToRemove){
                        if(suppressor.storage.getEnergyStored() >= use){
                            suppressor.storage.extractEnergyInternal(use, false);
                            affectedBlocks.remove(pos);
                        }
                        else{
                            break;
                        }
                    }
                    for(Entity entity : entitiesToRemove){
                        if(suppressor.storage.getEnergyStored() >= use){
                            suppressor.storage.extractEnergyInternal(use, false);
                            affectedEntities.remove(entity);
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

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityShockSuppressor();
    }
}
