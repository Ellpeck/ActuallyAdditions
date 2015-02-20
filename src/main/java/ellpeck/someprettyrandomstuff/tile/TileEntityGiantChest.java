package ellpeck.someprettyrandomstuff.tile;


import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileEntityGiantChest extends TileEntityInventoryBase{

    public TileEntityGiantChest(){
        super(169, "tileEntityGiantChest");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        NBTTagList tagList = new NBTTagList();
        for(int currentIndex = 0; currentIndex < slots.length; ++currentIndex){
            if (slots[currentIndex] != null){
                NBTTagCompound tagCompound = new NBTTagCompound();
                //Too many slots to get saved as Byte
                tagCompound.setShort("Slot", (short)currentIndex);
                slots[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        compound.setTag("Items", tagList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList("Items", 10);
        for (int i = 0; i < tagList.tagCount(); ++i){
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            //Too many slots to get saved as Byte
            short slotIndex = tagCompound.getShort("Slot");
            if (slotIndex >= 0 && slotIndex < slots.length){
                slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
    }
}
