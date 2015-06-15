package ellpeck.actuallyadditions.manual;

import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class ContainerManual extends Container{

    TheInventory inventory = new TheInventory();

    public ContainerManual(){
        this.addSlotToContainer(new SlotImmovable(this.inventory, 0, 199, 41));

        for(int i = 0; i < 13; i++){
            for(int j = 0; j < 4; j++){
                this.addSlotToContainer(new SlotImmovable(this.inventory, 1+j+(i*4), 5+j*18, 5+i*18));
            }
        }
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                this.addSlotToContainer(new SlotImmovable(this.inventory, 1+13*4+j+(i*3), 105+j*18, 23+i*18));
            }
        }

        for(int i = 1; i < ManualItems.list.size()+1; i++){
            if(i < 1+13*4){
                this.inventory.setInventorySlotContents(i, ManualItems.list.get(i-1).stack.copy());
            }
        }
    }

    public void scrollTo(float f){
        if(this.needsScrollBars()){
            int i = ManualItems.list.size()/4-13;
            int j = (int)((double)(f*(float)i)+0.5D);

            if(j < 0) j = 0;
            for(int k = 0; k < 13; ++k){
                for(int l = 0; l < 4; ++l){
                    int i1 = l+(k+j)*4;

                    if(i1 >= 0 && i1 < ManualItems.list.size()){
                        this.inventory.setInventorySlotContents(1+l+k*4, ManualItems.list.get(i1).stack.copy());
                    }
                    else this.inventory.setInventorySlotContents(1+l+k*4, null);
                }
            }
        }
    }

    public boolean needsScrollBars(){
        return ManualItems.list.size() > 9*13;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ItemStack slotClick(int slot, int par2, int par3, EntityPlayer player){
        if(slot > 0 && slot < 1+13*4){
            Slot theSlot = this.getSlot(slot);
            if(theSlot.getStack() != null){
                this.inventory.setInventorySlotContents(0, theSlot.getStack().copy());

                for(int i = 0; i < 9; i++) this.inventory.setInventorySlotContents(1+13*4+i, null);
                List<IRecipe> list = CraftingManager.getInstance().getRecipeList();
                for(IRecipe iRec: list){
                    if(iRec instanceof ShapedOreRecipe){
                        ShapedOreRecipe recipe = (ShapedOreRecipe)iRec;
                        if(theSlot.getStack().isItemEqual(recipe.getRecipeOutput())){

                            int width = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, recipe, 4);
                            int height = ReflectionHelper.getPrivateValue(ShapedOreRecipe.class, recipe, 5);
                            for(int x = 0; x < width; x++){
                                for(int y = 0; y < height; y++){
                                    int i = y*width+x;
                                    ItemStack stack = null;
                                    Object input = recipe.getInput()[i];
                                    if(input instanceof ItemStack) stack = ((ItemStack)input).copy();
                                    if(input instanceof ItemStack[]) stack = ((ItemStack[])input)[0];
                                    if(input instanceof List) stack = ((List<ItemStack>)input).get(0);

                                    int k = 0;
                                    if(x == 0){
                                        if(y == 0) k = 0;
                                        if(y == 1) k = 3;
                                        if(y == 2) k = 6;
                                    }
                                    if(x == 1){
                                        if(y == 0) k = 1;
                                        if(y == 1) k = 4;
                                        if(y == 2) k = 7;
                                    }
                                    if(x == 2){
                                        if(y == 0) k = 2;
                                        if(y == 1) k = 5;
                                        if(y == 2) k = 8;
                                    }
                                    this.inventory.setInventorySlotContents(1+13*4+k, stack);
                                }
                            }
                            break;
                        }
                    }
                    if(iRec instanceof ShapelessOreRecipe){
                        ShapelessOreRecipe recipe = (ShapelessOreRecipe)iRec;
                        if(theSlot.getStack().isItemEqual(recipe.getRecipeOutput())){
                            ArrayList<Object> ingredients = recipe.getInput();
                            for(int i = 0; i < ingredients.size(); i++){
                                ItemStack stack = null;
                                if(ingredients.get(i) instanceof ItemStack) stack = ((ItemStack)ingredients.get(i)).copy();
                                if(ingredients.get(i) instanceof ItemStack[]) stack = ((ItemStack[])ingredients.get(i))[0];
                                if(ingredients.get(i) instanceof List) stack = ((List<ItemStack>)ingredients.get(i)).get(0);

                                this.inventory.setInventorySlotContents(1+13*4+i, stack);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.inventory.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        return null;
    }

    public static class TheInventory implements IInventory{

        public ItemStack[] slots = new ItemStack[13*4+10];

        @Override
        public void setInventorySlotContents(int i, ItemStack stack){
            this.slots[i] = stack;
            this.markDirty();
        }

        @Override
        public int getSizeInventory(){
            return slots.length;
        }

        @Override
        public ItemStack getStackInSlot(int i){
            if(i < this.getSizeInventory()){
                return slots[i];
            }
            return null;
        }

        @Override
        public ItemStack decrStackSize(int i, int j){
            if (slots[i] != null){
                ItemStack stackAt;
                if(slots[i].stackSize <= j){
                    stackAt = slots[i];
                    slots[i] = null;
                    this.markDirty();
                    return stackAt;
                }
                else{
                    stackAt = slots[i].splitStack(j);
                    if (slots[i].stackSize == 0) slots[i] = null;
                    this.markDirty();
                    return stackAt;
                }
            }
            return null;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int i){
            return getStackInSlot(i);
        }

        @Override
        public String getInventoryName(){
            return "Actually Additions Manual";
        }

        @Override
        public boolean hasCustomInventoryName(){
            return false;
        }

        @Override
        public int getInventoryStackLimit(){
            return 1;
        }

        @Override
        public void markDirty(){

        }

        @Override
        public boolean isUseableByPlayer(EntityPlayer player){
            return true;
        }

        @Override
        public void openInventory(){

        }

        @Override
        public void closeInventory(){

        }

        @Override
        public boolean isItemValidForSlot(int i, ItemStack stack){
            return false;
        }
    }
}