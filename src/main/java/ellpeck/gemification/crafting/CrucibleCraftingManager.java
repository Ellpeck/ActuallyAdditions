package ellpeck.gemification.crafting;

import java.util.ArrayList;
import java.util.HashMap;

import ellpeck.gemification.GemType;
import ellpeck.gemification.Util;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CrucibleCraftingManager{

    public static final CrucibleCraftingManager instance = new CrucibleCraftingManager();
    public static ArrayList<CrucibleRecipe> recipes = new ArrayList<CrucibleRecipe>();

    @SuppressWarnings("all")
    public void initRecipes(){
        this.addRecipe(new ItemStack(Blocks.acacia_stairs), Util.fluidNone, 200, new Object[]{"ccc", "cgc", "ccc", 'c', Blocks.cobblestone, 'g', new ItemStack(Items.stick)});
    }

    @SuppressWarnings("static-access, unchecked")
    public void addRecipe(ItemStack output, GemType fluidNeeded, int processTimeNeeded, Object ... recipe){
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (recipe[i] instanceof String[]){
            String[] strg = ((String[])recipe[i++]);
            for(String s1 : strg) {
                k++;
                j = s1.length();
                s = s + s1;
            }
        }
        else{
            while (recipe[i] instanceof String){
                String s2 = (String)recipe[i++];
                k++;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap map;
        for (map = new HashMap(); i < recipe.length; i += 2){
            Character character = (Character)recipe[i];
            ItemStack stack1 = null;

            if (recipe[i + 1] instanceof Item){
                stack1 = new ItemStack((Item)recipe[i + 1], 1, 32767);
            }
            else if (recipe[i + 1] instanceof Block){
                stack1 = new ItemStack((Block)recipe[i + 1], 1, 32767);
            }
            else if (recipe[i + 1] instanceof ItemStack){
                stack1 = (ItemStack)recipe[i + 1];
            }
            map.put(character, stack1);
        }

        ItemStack[] stack2 = new ItemStack[j * k];
        for (int i1 = 0; i1 < j * k; ++i1){
            char c0 = s.charAt(i1);
            if (map.containsKey(Character.valueOf(c0))){
                stack2[i1] = ((ItemStack)map.get(Character.valueOf(c0))).copy();
            }
            else{
                stack2[i1] = null;
            }
        }

        this.recipes.add(new CrucibleRecipe(stack2, output, fluidNeeded, processTimeNeeded));
    }

    @SuppressWarnings("static-access")
    public ItemStack getCraftingResult(ItemStack[] slots, int minSlot, int maxSlot, GemType currentFluid){
        for (CrucibleRecipe recipe : this.recipes) {
            ItemStack[] inputs = recipe.recipeItems;
            int k = 0;
            for (int j = 0; j < maxSlot - minSlot + 1; j++) {
                if (slots[minSlot + j] != null && inputs[j] != null && slots[minSlot + j].getItem() == inputs[j].getItem()){
                    if(inputs[j].getItemDamage() == 32767 || inputs[j].getItemDamage() == slots[minSlot + j].getItemDamage()) {
                        k++;
                    }
                }
            }
            if (k == maxSlot - minSlot + 1) {
                if(currentFluid == recipe.fluidNeeded) {
                    return recipe.recipeOutput;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("static-access")
    public int getProcessTimeNeeded(ItemStack stack){
        for(CrucibleRecipe recipe : this.recipes){
            if(recipe.recipeOutput == stack){
                return recipe.processTimeNeeded;
            }
        }
        return 0;
    }
}