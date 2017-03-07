/*
 * This file ("ItemCrystalChisel.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.blocks.BlockCrystalCluster;
import de.ellpeck.actuallyadditions.mod.cave.WorldTypeCave;
import de.ellpeck.actuallyadditions.mod.gen.WorldGenLushCaves;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.FakePlayer;
import org.apache.commons.lang3.ArrayUtils;

public class ItemCrystalChisel extends ItemBase{

    public ItemCrystalChisel(String name){
        super(name);

        this.setMaxStackSize(1);
        this.setMaxDamage(64);
    }

    @Override
    public boolean canHarvestBlock(IBlockState block){
        return block.getBlock() instanceof BlockCrystalCluster;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player){
        if(WorldTypeCave.is(player.world) && !(player instanceof FakePlayer)){
            IBlockState state = player.world.getBlockState(pos);
            if(state.getBlock() instanceof BlockCrystalCluster){
                int index = ArrayUtils.indexOf(WorldGenLushCaves.CRYSTAL_CLUSTERS, state.getBlock());
                if(index >= 0){
                    if(!player.world.isRemote){
                        if(player.world.rand.nextBoolean()){
                            ItemStack drop = new ItemStack(InitItems.itemCrystalShard, player.world.rand.nextInt(3)+1, index);
                            EntityItem item = new EntityItem(player.world, pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5, drop);
                            player.world.spawnEntity(item);
                        }

                        stack.damageItem(1, player);
                    }

                    return true;
                }
            }
        }
        return false;
    }
}
