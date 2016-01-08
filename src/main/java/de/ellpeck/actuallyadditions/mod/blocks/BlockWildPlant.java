/*
 * This file ("BlockWildPlant.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;


import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBushBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockPlant;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheWildPlants;
import de.ellpeck.actuallyadditions.mod.util.PosUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockWildPlant extends BlockBushBase{

    public static final TheWildPlants[] allWildPlants = TheWildPlants.values();

    public BlockWildPlant(String name){
        super(name);
        this.setStepSound(soundTypeGrass);
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state){
        BlockPos offset = PosUtil.offset(pos, 0, -1, 0);
        return PosUtil.getMetadata(pos, world) == TheWildPlants.RICE.ordinal() ? PosUtil.getMaterial(offset, world) == Material.water : PosUtil.getBlock(offset, world).canSustainPlant(world, offset, EnumFacing.UP, this);
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
        return stack.getItemDamage() >= allWildPlants.length ? EnumRarity.COMMON : allWildPlants[stack.getItemDamage()].rarity;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, BlockPos pos){
        int metadata = PosUtil.getMetadata(pos, world);
        return metadata >= allWildPlants.length ? null : ((BlockPlant)allWildPlants[metadata].wildVersionOf).seedItem;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allWildPlants.length; j++){
            list.add(new ItemStack(item, 1, j));
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune){
        int metadata = PosUtil.getMetadata(pos, world);
        return metadata >= allWildPlants.length ? null : allWildPlants[metadata].wildVersionOf.getDrops(world, pos, allWildPlants[metadata].wildVersionOf.getStateFromMeta(7), fortune);
    }

    public static class TheItemBlock extends ItemBlockBase{

        public TheItemBlock(Block block){
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return stack.getItemDamage() >= allWildPlants.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allWildPlants[stack.getItemDamage()].name;
        }
    }
}
