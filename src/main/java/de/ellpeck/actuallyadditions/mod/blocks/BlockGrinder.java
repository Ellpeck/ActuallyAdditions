/*
 * This file ("BlockGrinder.java") is part of the Actually Additions Mod for Minecraft.
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
import de.ellpeck.actuallyadditions.mod.tile.TileEntityGrinder;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockGrinder extends BlockContainerBase{

    private static final PropertyInteger META = PropertyInteger.create("meta", 0, 1);
    private final boolean isDouble;

    public BlockGrinder(boolean isDouble, String name){
        super(Material.ROCK, name);
        this.isDouble = isDouble;
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setSoundType(SoundType.STONE);
        this.setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return this.isDouble ? new TileEntityGrinder.TileEntityGrinderDouble() : new TileEntityGrinder();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand){
        int meta = PosUtil.getMetadata(state);

        if(meta == 1){
            for(int i = 0; i < 5; i++){
                double xRand = rand.nextDouble()/0.75D-0.5D;
                double zRand = rand.nextDouble()/0.75D-0.5D;
                world.spawnParticle(EnumParticleTypes.CRIT, (double)pos.getX()+0.4F, (double)pos.getY()+0.8F, (double)pos.getZ()+0.4F, xRand, 0.5D, zRand);
            }
            world.spawnParticle(ClientProxy.bulletForMyValentine ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL, (double)pos.getX()+0.5F, (double)pos.getY()+1.0F, (double)pos.getZ()+0.5F, 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack stack, EnumFacing par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityGrinder grinder = (TileEntityGrinder)world.getTileEntity(pos);
            if(grinder != null){
                player.openGui(ActuallyAdditions.instance, this.isDouble ? GuiHandler.GuiTypes.GRINDER_DOUBLE.ordinal() : GuiHandler.GuiTypes.GRINDER.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        }
        return true;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos){
        return PosUtil.getMetadata(pos, world) == 1 ? 12 : 0;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.EPIC;
    }

    @Override
    protected PropertyInteger getMetaProperty(){
        return META;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state){
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }
}
