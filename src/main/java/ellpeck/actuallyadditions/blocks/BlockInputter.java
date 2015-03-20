package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockInputter extends BlockContainerBase implements IName{

    public static final int NAME_FLAVOUR_AMOUNTS = 12;

    private long lastSysTime;
    private int toPick;

    public BlockInputter(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTab.instance);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z){
        super.onBlockAdded(world, x, y, z);

        if (!world.isRemote){
            Block block1 = world.getBlock(x, y, z-1);
            Block block2 = world.getBlock(x, y, z+1);
            Block block3 = world.getBlock(x-1, y, z);
            Block block4 = world.getBlock(x+1, y, z);

            int metaToSet = 1;
            if (block1.func_149730_j() && !block2.func_149730_j()) metaToSet = 0;
            if (block2.func_149730_j() && !block1.func_149730_j()) metaToSet = 1;
            if (block3.func_149730_j() && !block4.func_149730_j()) metaToSet = 2;
            if (block4.func_149730_j() && !block3.func_149730_j()) metaToSet = 3;

            world.setBlockMetadataWithNotify(x, y, z, metaToSet, 2);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (rotation == 0) world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        if (rotation == 1) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (rotation == 2) world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        if (rotation == 3) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
    }

    @Override
    public String getUnlocalizedName(){
        String norm = "tile." + Util.MOD_ID_LOWER + "." + this.getName();

        Random rand = new Random();
        long sysTime = System.currentTimeMillis();

        if(this.lastSysTime+5000 < sysTime){
            this.lastSysTime = sysTime;
            this.toPick = rand.nextInt(NAME_FLAVOUR_AMOUNTS+1);
        }

        return norm + "." + this.toPick;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityInputter();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) > 3 ? 12 : 0;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityInputter furnace = (TileEntityInputter)world.getTileEntity(x, y, z);
            if (furnace != null) player.openGui(ActuallyAdditions.instance, GuiHandler.INPUTTER_ID, world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockInputter";
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
            return EnumRarity.rare;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            if(Util.isShiftPressed()){
                list.add(StatCollector.translateToLocalFormatted("tooltip." + Util.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + ".desc." + 1, Util.OBFUSCATED, Util.LIGHT_GRAY));
                for(int i = 1; i < 5; i++){
                    list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + ".desc." + (i + 1)));
                }
            }
            else list.add(Util.shiftForInfo());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
