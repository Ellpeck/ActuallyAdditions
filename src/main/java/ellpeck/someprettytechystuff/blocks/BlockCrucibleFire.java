package ellpeck.someprettytechystuff.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettytechystuff.Gemification;
import ellpeck.someprettytechystuff.creative.CreativeTab;
import ellpeck.someprettytechystuff.inventory.GuiHandler;
import ellpeck.someprettytechystuff.tile.TileEntityCrucibleFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrucibleFire extends BlockContainerBase{

    protected BlockCrucibleFire(){
        super(Material.rock);
        this.setBlockName("blockCrucibleFire");
        this.setCreativeTab(CreativeTab.instance);
        this.setTickRandomly(true);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setHarvestLevel("pickaxe", 2);
    }

    public TileEntity createNewTileEntity(World world, int i){
        return new TileEntityCrucibleFire();
    }

    @SuppressWarnings("static-access")
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
        if (!world.isRemote){
            player.openGui(Gemification.instance, GuiHandler.guiCrucibleFire, world, x, y, z);
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

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand){
        if(((TileEntityCrucibleFire)world.getTileEntity(x, y, z)).isBurning()){
            for(int i = 0; i < 8; i++){
                world.spawnParticle("flame", (double) x + rand.nextFloat() * 0.5F + 0.25F, (double) y + 0.55F, (double) z + rand.nextFloat() * 0.5F + 0.25F, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("smoke", (double) x + rand.nextFloat() * 0.5F + 0.25F, (double) y + 0.55F, (double) z + rand.nextFloat() * 0.5F + 0.25F, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}