/*
 * This file ("BlockWildPlant.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.blocks.base.BlockBushBase;
import ellpeck.actuallyadditions.blocks.base.BlockPlant;
import ellpeck.actuallyadditions.blocks.base.ItemBlockBase;
import ellpeck.actuallyadditions.blocks.metalists.TheWildPlants;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

public class BlockWildPlant extends BlockBushBase{

    public static final TheWildPlants[] allWildPlants = TheWildPlants.values();

    public BlockWildPlant(String name){
        super(name);
        this.setStepSound(soundTypeGrass);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) == TheWildPlants.RICE.ordinal() ? world.getBlock(x, y-1, z).getMaterial() == Material.water : world.getBlock(x, y-1, z).canSustainPlant(world, x, y-1, z, ForgeDirection.UP, this);
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock(){
        return TheItemBlock.class;
    }

    @Override
    public boolean shouldAddCreative(){
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allWildPlants.length ? EnumRarity.common : allWildPlants[stack.getItemDamage()].rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata){
        return metadata >= allWildPlants.length ? null : allWildPlants[metadata].wildVersionOf.getIcon(0, 7);
    }

    @Override
    public boolean canSilkHarvest(){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z){
        int meta = world.getBlockMetadata(x, y, z);
        return meta >= allWildPlants.length ? null : ((BlockPlant)allWildPlants[meta].wildVersionOf).seedItem;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allWildPlants.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){

    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune){
        return metadata >= allWildPlants.length ? null : allWildPlants[metadata].wildVersionOf.getDrops(world, x, y, z, 7, fortune);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public IIcon getIconFromDamage(int meta){
            return this.field_150939_a.getIcon(0, meta);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return stack.getItemDamage() >= allWildPlants.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allWildPlants[stack.getItemDamage()].name;
        }
    }
}
