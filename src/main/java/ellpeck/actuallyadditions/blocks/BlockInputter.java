package ellpeck.actuallyadditions.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.ActuallyAdditions;
import ellpeck.actuallyadditions.inventory.GuiHandler;
import ellpeck.actuallyadditions.tile.TileEntityInputter;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockInputter extends BlockContainerBase implements INameableItem{

    public static final int NAME_FLAVOUR_AMOUNTS = 15;

    public boolean isAdvanced;

    public BlockInputter(boolean isAdvanced){
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeStone);
        this.setTickRandomly(true);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public String getOredictName(){
        return this.getName();
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityInputter(this.isAdvanced);
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
            TileEntityInputter inputter = (TileEntityInputter)world.getTileEntity(x, y, z);
            if (inputter != null) player.openGui(ActuallyAdditions.instance, this.isAdvanced ? GuiHandler.INPUTTER_ADVANCED_ID : GuiHandler.INPUTTER_ID, world, x, y, z);
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
        return this.isAdvanced ? "blockInputterAdvanced" : "blockInputter";
    }

    public static class TheItemBlock extends ItemBlock{

        private Block theBlock;

        private long lastSysTime;
        private int toPick;

        public TheItemBlock(Block block){
            super(block);
            this.theBlock = block;
            this.setHasSubtypes(false);
            this.setMaxDamage(0);
        }

        @Override
        public EnumRarity getRarity(ItemStack stack){
            return ((BlockInputter)theBlock).isAdvanced ? EnumRarity.epic : EnumRarity.rare;
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack){
            Random rand = new Random();
            long sysTime = System.currentTimeMillis();

            if(this.lastSysTime+5000 < sysTime){
                this.lastSysTime = sysTime;
                this.toPick = rand.nextInt(NAME_FLAVOUR_AMOUNTS+1);
            }

            return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") + " (" + StatCollector.translateToLocal("tile." + ModUtil.MOD_ID_LOWER + ".blockInputter.add." + this.toPick + ".name") + ")";
        }

        @Override
        public String getUnlocalizedName(ItemStack stack){
            return this.getUnlocalizedName();
        }

        @Override
        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            if(KeyUtil.isShiftPressed()){
                list.add(StatCollector.translateToLocalFormatted("tooltip." + ModUtil.MOD_ID_LOWER + ".blockInputter.desc." + 1, StringUtil.OBFUSCATED, StringUtil.LIGHT_GRAY));
                for(int i = 1; i < 6; i++){
                    list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".blockInputter.desc." + (i + 1)));
                }
                if((((BlockInputter)theBlock).isAdvanced)) list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + ((INameableItem)theBlock).getName() + ".desc"));
                BlockUtil.addOredictName(theBlock, list);
            }
            else list.add(ItemUtil.shiftForInfo());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
