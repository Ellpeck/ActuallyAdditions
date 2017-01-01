/*
 * This file ("BlockEnergizer.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnergizer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityEnervator;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockEnergizer extends BlockContainerBase{

    private final boolean isEnergizer;

    public BlockEnergizer(boolean isEnergizer, String name){
        super(Material.ROCK, name);
        this.isEnergizer = isEnergizer;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isEnergizer ? new TileEntityEnergizer() : new TileEntityEnervator();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            if(this.isEnergizer){
                TileEntityEnergizer energizer = (TileEntityEnergizer)world.getTileEntity(pos);
                if(energizer != null){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.ENERGIZER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            else{
                TileEntityEnervator energizer = (TileEntityEnervator)world.getTileEntity(pos);
                if(energizer != null){
                    player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.ENERVATOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }
}
