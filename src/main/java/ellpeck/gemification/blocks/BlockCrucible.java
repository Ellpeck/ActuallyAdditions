package ellpeck.gemification.blocks;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ellpeck.gemification.CreativeTab;
import ellpeck.gemification.Gemification;
import ellpeck.gemification.Util;
import ellpeck.gemification.tile.TileEntityCrucible;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCrucible extends BlockContainer{

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
            TileEntityCrucible tileCrucible = (TileEntityCrucible)world.getTileEntity(x, y, z);
            if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.water_bucket && tileCrucible.currentFluid == Util.fluidNone){
                tileCrucible.currentFluid = Util.fluidWater;
                if(!player.capabilities.isCreativeMode) player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.bucket));
            }
            else player.openGui(Gemification.instance, Gemification.guiCrucible, world, x, y, z);
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
        TileEntityCrucible tileEntity = (TileEntityCrucible)world.getTileEntity(x, y, z);
        for (int i = 0; i < tileEntity.getSizeInventory(); i++){
            ItemStack itemStack = tileEntity.getStackInSlot(i);
            if (itemStack != null && itemStack.stackSize > 0){
                Random rand = new Random();
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());
                if (itemStack.hasTagCompound()) entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
        if(tileEntity.currentFluid != Util.fluidNone) world.setBlock(x, y, z, Blocks.flowing_water);
    }

}
