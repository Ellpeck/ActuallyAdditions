package ellpeck.someprettyrandomstuff.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.SomePrettyRandomStuff;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.inventory.GuiHandler;
import ellpeck.someprettyrandomstuff.tile.TileEntityGiantChest;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGiantChest extends BlockContainerBase implements IName{

    private IIcon topIcon;

    public BlockGiantChest(){
        super(Material.wood);
        this.setBlockName(Util.getNamePrefix() + this.getName());
        this.setCreativeTab(CreativeTab.instance);
        this.setHarvestLevel("axe", 0);
        this.setHardness(1.0F);
        this.setStepSound(soundTypeWood);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2){
        return new TileEntityGiantChest();
    }

    @Override
    public IIcon getIcon(int side, int metadata){
        return side == 1 ? this.topIcon : this.blockIcon;
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
            TileEntityGiantChest chest = (TileEntityGiantChest)world.getTileEntity(x, y, z);
            if (chest != null) player.openGui(SomePrettyRandomStuff.instance, GuiHandler.GIANT_CHEST_ID, world, x, y, z);
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
        return "blockGiantChest";
    }
}
