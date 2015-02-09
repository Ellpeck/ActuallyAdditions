package ellpeck.someprettyrandomstuff.blocks;

import ellpeck.someprettyrandomstuff.tile.TileEntityInventoryBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public abstract class BlockContainerBase extends BlockContainer{

    public BlockContainerBase(Material mat){
        super(mat);
    }

    public TileEntityInventoryBase dropInventory(World world, BlockPos blockPos){
        TileEntityInventoryBase tileEntity = (TileEntityInventoryBase) world.getTileEntity(blockPos);
        for (int i = 0; i < tileEntity.getSizeInventory(); i++){
            ItemStack itemStack = tileEntity.getStackInSlot(i);
            if (itemStack != null && itemStack.stackSize > 0) {
                Random rand = new Random();
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, blockPos.getX() + dX, blockPos.getY() + dY, blockPos.getZ() + dZ, itemStack.copy());
                if (itemStack.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
        return tileEntity;
    }
}
