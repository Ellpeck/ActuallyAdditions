package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.SomePrettyRandomStuff;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.tile.TileEntityFeeder;
import ellpeck.someprettyrandomstuff.util.IInformation;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
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

public class BlockFeeder extends BlockContainerBase implements IName, IInformation{

    private IIcon topIcon;

    public BlockFeeder(){
        super(Material.wood);
        this.setBlockName(Util.setUnlocalizedName(this));
        this.setCreativeTab(CreativeTab.instance);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFeeder();
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return (side == 0 || side == 1) ? this.topIcon : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName());
        this.topIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName() + "Top");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFeeder feeder = (TileEntityFeeder)world.getTileEntity(x, y, z);
            if (feeder != null) player.openGui(SomePrettyRandomStuff.instance, GuiHandler.FEEDER_ID, world, x, y, z);
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
        return "blockFeeder";
    }

    @Override
    public String[] getInformationString(){
        return new String[]{"This is a thing and it is a test and bla bla bla"};
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
            if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + ((IName)theBlock).getName() + ".desc"));
            else list.add(Util.shiftForInfo());
        }

        @Override
        public int getMetadata(int damage){
            return damage;
        }
    }
}
