package ellpeck.gemification.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.creative.CreativeTab;
import ellpeck.gemification.inventory.GuiHandler;
import ellpeck.gemification.tile.TileEntityCrucibleFire;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrucibleFire extends BlockContainer{

    protected BlockCrucibleFire(){
        super(Material.rock);
        this.setBlockName("blockCrucibleFire");
        this.setCreativeTab(CreativeTab.instance);
        this.setTickRandomly(true);
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

    public void breakBlock(World world, int x, int y, int z, Block block, int meta){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    public void dropInventory(World world, int x, int y, int z){
        TileEntityCrucibleFire tileEntity = (TileEntityCrucibleFire)world.getTileEntity(x, y, z);
        for (int i = 0; i < tileEntity.getSizeInventory(); i++){
            ItemStack itemStack = tileEntity.getStackInSlot(i);
            if (itemStack != null && itemStack.stackSize > 0){
                Random rand = new Random();
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());
                if(itemStack.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
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