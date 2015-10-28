/*
 * This file ("BlockGreenhouseGlass.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.tile.TileEntityGreenhouseGlass;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGreenhouseGlass extends BlockContainerBase implements IActAddItemOrBlock{

    public BlockGreenhouseGlass(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int meta){
        return world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x-Facing.offsetsXForSide[meta], y-Facing.offsetsYForSide[meta], z-Facing.offsetsZForSide[meta]) || (world.getBlock(x, y, z) != this && super.shouldSideBeRendered(world, x, y, z, meta));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass(){
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return "blockGreenhouseGlass";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityGreenhouseGlass();
    }

}
