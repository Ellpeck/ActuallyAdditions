package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityFurnaceDouble;
import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
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

public class BlockFurnaceDouble extends BlockContainerBase implements IName{

    private IIcon topIcon;
    private IIcon onIcon;
    private IIcon frontIcon;

    public BlockFurnaceDouble(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
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
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFurnaceDouble();
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z){
        return world.getBlockMetadata(x, y, z) > 3 ? 12 : 0;
    }

    @Override
    public IIcon getIcon(int side, int meta){
        if(side == 1) return this.topIcon;
        if(side == meta+2 && meta <= 3) return this.frontIcon;
        else if(side == meta-2 && meta > 3) return this.onIcon;
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Top");
        this.onIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "On");
        this.frontIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Front");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFurnaceDouble furnace = (TileEntityFurnaceDouble)world.getTileEntity(x, y, z);
            if (furnace != null) player.openGui(ActuallyAdditions.instance, GuiHandler.FURNACE_DOUBLE_ID, world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        int meta = world.getBlockMetadata(x, y, z);

        if (meta > 3){
            float f = (float)x + 0.5F;
            float f1 = (float)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

            if(meta == 6){
                world.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 7){
                world.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 4){
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            if(meta == 5){
                world.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }

            for(int i = 0; i < 5; i++){
                world.spawnParticle("smoke", (double)x+0.5F, (double)y + 1.0F, (double)z+0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public String getName(){
        return "blockFurnaceDouble";
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
            if(KeyUtil.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + ".desc"));
            else list.add(ItemUtil.shiftForInfo());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
