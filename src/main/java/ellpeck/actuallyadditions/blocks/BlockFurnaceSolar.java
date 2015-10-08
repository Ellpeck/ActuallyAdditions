/*
 * This file ("BlockFurnaceSolar.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.tile.TileEntityFurnaceSolar;
import ellpeck.actuallyadditions.util.AssetUtil;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFurnaceSolar extends BlockContainerBase implements IActAddItemOrBlock{

    public BlockFurnaceSolar(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setBlockBounds(0F, 0F, 0F, 1F, 3F/16F, 1F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceSolar();
    }

    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }

    @Override
    public int getRenderType(){
        return AssetUtil.FURNACE_SOLAR_RENDER_ID;
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return this.blockIcon;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.daylight_detector.getIcon(0, 0);
    }

    @Override
    public String getName(){
        return "blockFurnaceSolar";
    }

    public static class TheItemBlock extends ItemBlock{

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
        public int getMetadata(int meta){
            return meta;
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.rare;
        }
    }
}
