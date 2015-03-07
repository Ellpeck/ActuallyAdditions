package ellpeck.someprettyrandomstuff.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemCrafting{

    public static void init(){

        //Knife
        if(ConfigValues.enableKnifeRecipe)
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemKnife),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()));

        //Crafter on a Stick
        if(ConfigValues.enableCrafterRecipe)
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemCrafterOnAStick),
                    new ItemStack(Blocks.crafting_table),
                    new ItemStack(Items.sign),
                    new ItemStack(Items.slime_ball));

        //Mashed Food
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.MASHED_FOOD.ordinal()])
            initMashedFoodRecipes();

    }

    public static void initMashedFoodRecipes(){
        for(Object nextIterator : Item.itemRegistry){
            if(nextIterator instanceof ItemFood){
                ItemStack ingredient = new ItemStack((Item)nextIterator, 1, Util.WILDCARD);
                GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 4, TheMiscItems.MASHED_FOOD.ordinal()), ingredient, ingredient, ingredient, ingredient, new ItemStack(InitItems.itemKnife, 1, Util.WILDCARD));
            }
        }
    }
}
