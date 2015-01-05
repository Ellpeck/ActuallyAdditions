package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ellpeck.someprettytechystuff.Gemification;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.inventory.GuiHandler;
import ellpeck.someprettytechystuff.tile.TileEntityCrucible;
import ellpeck.someprettytechystuff.tile.TileEntityInventoryBase;
import ellpeck.someprettytechystuff.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrucible extends BlockContainerBase{

    protected BlockCrucible(){
        super(Material.rock);
        this.setBlockName("blockCrucible");
        this.setCreativeTab(CreativeTab.instance);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 2);
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
}
