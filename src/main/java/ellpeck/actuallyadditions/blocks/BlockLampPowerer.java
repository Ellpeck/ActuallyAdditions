package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockLampPowerer extends Block implements INameableItem{

    private IIcon frontIcon;
    private IIcon topIcon;

    public BlockLampPowerer(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public String getName(){
        return "blockLampPowerer";
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = BlockPistonBase.determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 0 || side == 1) return this.topIcon;
        if(side == 3) return this.frontIcon;
        return this.blockIcon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side != meta && (side == 0 || side == 1)) return this.topIcon;
        if(side == meta) return this.frontIcon;
        return this.blockIcon;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        this.updateLamp(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        this.updateLamp(world, x, y, z);
    }

    private void updateLamp(World world, int x, int y, int z){
        if(!world.isRemote){
            WorldPos coords = WorldUtil.getCoordsFromSide(ForgeDirection.getOrientation(world.getBlockMetadata(x, y, z)), world, x, y, z);
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

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Front");
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Top");
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return EnumRarity.uncommon;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            BlockUtil.addInformation(theBlock, list, 1, "");
        }

        @Override
        public int getMetadata(int meta){
            return meta;
        }
    }
}
