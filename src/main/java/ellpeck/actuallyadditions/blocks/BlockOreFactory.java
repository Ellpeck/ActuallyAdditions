package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.multi.IMultiBlock;
import ellpeck.actuallyadditions.blocks.multi.MultiBlockHelper;
import ellpeck.actuallyadditions.util.BlockUtil;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockOreFactory extends BlockContainerBase implements INameableItem, IMultiBlock{

    public BlockOreFactory(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(4.5F);
        this.setResistance(20.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return null;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            //TODO
            return true;
        }
        return true;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        this.makeMultiBlock(world, x, y, z, world.getBlock(x, y, z));
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block){
        this.makeMultiBlock(world, x, y, z, block);
    }

    private void makeMultiBlock(World world, int x, int y, int z, Block block){
        if(block instanceof IMultiBlock){
            MultiBlockHelper.createMultiBlock((IMultiBlock)block, world, x, y, z);
        }
    }

    @Override
    public String getName(){
        return "blockOreFactory";
    }

    @Override
    public Block[] getNeededBlocks(){
        return new Block[]{this};
    }

    @Override
    public TileEntity getCore(){
        return null;
    }

    @Override
    public int getSizeHor(){
        return 3;
    }

    @Override
    public int getSizeVer(){
        return 5;
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
            BlockUtil.addInformation(theBlock, list, 3, "");
            //TODO
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
