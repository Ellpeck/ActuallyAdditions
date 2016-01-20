/*
 * This file ("BlockOilGenerator.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.proxy.ClientProxy;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityOilGenerator;
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
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockOilGenerator extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 1);

    public BlockOilGenerator(String name){
        super(Material.rock, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityOilGenerator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand){
        if(PosUtil.getMetadata(pos, world) == 1){
            for(int i = 0; i < 5; i++){
                world.spawnParticle(ClientProxy.bulletForMyValentine ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityOilGenerator generator = (TileEntityOilGenerator)world.getTileEntity(pos);
            if(generator != null){
                player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.OIL_GENERATOR.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }
}
