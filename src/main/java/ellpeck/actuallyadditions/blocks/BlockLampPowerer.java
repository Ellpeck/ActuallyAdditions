/*
 * This file ("BlockLampPowerer.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import ellpeck.actuallyadditions.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockLampPowerer extends Block implements IActAddItemOrBlock{

    private IIcon frontIcon;

    public BlockLampPowerer(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side == meta){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 3){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        this.updateLamp(world, x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        this.updateLamp(world, x, y, z);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Front");
    }

    @Override
    public String getName(){
        return "blockLampPowerer";
    }

    private void updateLamp(World world, int x, int y, int z){
        if(!world.isRemote){
            WorldPos coords = WorldUtil.getCoordsFromSide(ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)), world, x, y, z, 0);
            if(coords != null && coords.getBlock() instanceof BlockColoredLamp){
                if(world.isBlockIndirectlyGettingPowered(x, y, z)){
                    if(!((BlockColoredLamp)coords.getBlock()).isOn){
                        world.setBlock(coords.getX(), coords.getY(), coords.getZ(), InitBlocks.blockColoredLampOn, world.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()), 2);
                    }
                }
                else{
                    if(((BlockColoredLamp)coords.getBlock()).isOn){
                        world.setBlock(coords.getX(), coords.getY(), coords.getZ(), InitBlocks.blockColoredLamp, world.getBlockMetadata(coords.getX(), coords.getY(), coords.getZ()), 2);
                    }
                }
            }
        }
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
            return EnumRarity.uncommon;
        }
    }
}
