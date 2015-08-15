package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityOilGenerator;
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
import java.util.Random;

public class BlockOilGenerator extends BlockContainerBase implements INameableItem{

    private IIcon topIcon;
    private IIcon bottomIcon;

    public BlockOilGenerator(){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        int meta = world.getBlockMetadata(x, y, z);

        if (meta == 1){
            for(int i = 0; i < 5; i++){
                world.spawnParticle("smoke", (double)x+0.5F, (double)y + 1.0F, (double)z+0.5F, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityOilGenerator();
    }

    @Override
    public IIcon getIcon(int side, int meta){
        return side <= 1 ? (side == 0 ? this.bottomIcon : this.topIcon) : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.topIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Top");
        this.bottomIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Bottom");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityOilGenerator generator = (TileEntityOilGenerator)world.getTileEntity(x, y, z);
            if (generator != null) player.openGui(ActuallyAdditions.instance, GuiHandler.GuiTypes.OIL_GENERATOR.ordinal(), world, x, y, z);
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
        return "blockOilGenerator";
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
            BlockUtil.addPowerProductionInfo(list, ConfigIntValues.OIL_GEN_ENERGY_PRODUCED.getValue());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
