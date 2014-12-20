package ellpeck.gemification.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.creative.CreativeTab;
import ellpeck.gemification.inventory.GuiHandler;
import ellpeck.gemification.tile.TileEntityCrucible;
import ellpeck.gemification.tile.TileEntityInventoryBase;
import ellpeck.gemification.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class BlockCrucible extends BlockContainerBase{

    protected BlockCrucible(){
        super(Material.rock);
        this.setBlockName("blockCrucible");
        this.setCreativeTab(CreativeTab.instance);
    }

    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityCrucible();
    }

    @SuppressWarnings("static-access")
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (!world.isRemote){
            player.openGui(Gemification.instance, GuiHandler.guiCrucible, world, x, y, z);
        }
        return true;
    }

    public int getRenderType(){
        return RenderingRegistry.getNextAvailableRenderId();
    }

    public boolean isOpaqueCube(){
        return false;
    }

    public boolean renderAsNormalBlock(){
        return false;
    }

    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = Blocks.hopper.getIcon(0, 0);
    }

    public TileEntityInventoryBase dropInventory(World world, int x, int y, int z){
        TileEntityCrucible tileEntity = (TileEntityCrucible)super.dropInventory(world, x, y, z);
        if(tileEntity.currentFluid != Util.fluidNone) world.setBlock(x, y, z, Blocks.flowing_water);
        return tileEntity;
    }

    public static class ItemBlockCrucible extends ItemBlock {

        public ItemBlockCrucible(Block block){
            super(block);
            setHasSubtypes(false);
        }

        public String getUnlocalizedName(ItemStack stack) {
            return this.getUnlocalizedName();
        }

        @SuppressWarnings("unchecked")
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
            if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName().substring(5) + ".desc"));
            else list.add(Util.shiftForInfo());
        }
    }
}
