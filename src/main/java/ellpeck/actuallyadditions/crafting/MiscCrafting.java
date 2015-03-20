package ellpeck.actuallyadditions.crafting;

import cpw.mods.fml.common.registry.GameRegistry;
import ellpeck.actuallyadditions.config.ConfigValues;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheMiscItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MiscCrafting{

    public static void init(){

        //Dough
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.DOUGH.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 2, TheMiscItems.DOUGH.ordinal()),
                    new ItemStack(Items.wheat), new ItemStack(Items.wheat));

        //Paper Cone
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.PAPER_CONE.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.PAPER_CONE.ordinal()),
                    "P P", " P ",
                    'P', new ItemStack(Items.paper));

        //Knife Handle
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.KNIFE_HANDLE.ordinal()])
            GameRegistry.addShapelessRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_HANDLE.ordinal()),
                    new ItemStack(Items.stick),
                    new ItemStack(Items.leather));

        //Knife Blade
        if(ConfigValues.enabledMiscRecipes[TheMiscItems.KNIFE_BLADE.ordinal()])
            GameRegistry.addRecipe(new ItemStack(InitItems.itemMisc, 1, TheMiscItems.KNIFE_BLADE.ordinal()),
                    "KF",
                    'K', new ItemStack(Items.iron_ingot),
                    'F', new ItemStack(Items.flint));

    }

}
