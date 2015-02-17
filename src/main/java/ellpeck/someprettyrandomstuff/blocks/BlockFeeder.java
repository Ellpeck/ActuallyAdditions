package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.SPRS;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.tile.TileEntityFeeder;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockFeeder extends BlockContainerBase{

    private IIcon topIcon;

    public BlockFeeder(){
        super(Material.wood);
        this.setBlockName("blockFeeder");
        this.setCreativeTab(CreativeTab.instance);
    }

    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityFeeder();
    }

    public IIcon getIcon(int side, int metadata){
        return (side == 0 || side == 1) ? this.topIcon : this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.blockIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this));
        this.topIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this) + "Top");
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityFeeder feeder = (TileEntityFeeder)world.getTileEntity(x, y, z);
            if (feeder != null) player.openGui(SPRS.instance, GuiHandler.FEEDER_ID, world, x, y, z);
            return true;
        }
        return true;
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }
}
