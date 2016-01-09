/*
 * This file ("BlockCoffeeMachine.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityCoffeeMachine;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCoffeeMachine extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 3);

    public BlockCoffeeMachine(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);

        float f = 1/16F;
        this.setBlockBounds(f, 0F, f, 1F-f, 1F-2*f, 1F-f);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.TESR_RENDER_ID;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing f6, float f7, float f8, float f9){
        if(!world.isRemote){
            TileEntityCoffeeMachine machine = (TileEntityCoffeeMachine)world.getTileEntity(pos);
            if(machine != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.COFFEE_MACHINE.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityCoffeeMachine();
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos){
        int meta = PosUtil.getMetadata(pos, world);
        float f = 0.0625F;

        if(meta == 0){
            this.setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F-f*3F);
        }
        if(meta == 1){
            this.setBlockBounds(0F, 0F, 0F, 1F-f*3F, 1F, 1F);
        }
        if(meta == 2){
            this.setBlockBounds(0F, 0F, f*3F, 1F, 1F, 1F);
        }
        if(meta == 3){
            this.setBlockBounds(f*3F, 0F, 0F, 1F, 1F, 1F);
        }
    }
}
