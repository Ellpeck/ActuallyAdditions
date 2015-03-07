package ellpeck.someprettyrandomstuff.crafting;

import ellpeck.someprettyrandomstuff.items.InitItems;
import ellpeck.someprettyrandomstuff.items.metalists.TheDusts;
import ellpeck.someprettyrandomstuff.recipe.GrinderRecipes;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class GrinderCrafting{

    public static void init(){

        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.iron_ore), new ItemStack(InitItems.itemDust, 2, TheDusts.IRON.ordinal()), new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()), 10);
        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10), null, 0);
        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(Items.dye, 15, 4), null, 0);

        registerFinally();
    }

    public static void registerFinally(){
        String[] names = OreDictionary.getOreNames();
        for(String name : names){
            if(name.contains("ore")){
                String nameOfOre = name.substring(3);
                ArrayList<ItemStack> allDusts = OreDictionary.getOres("dust" + nameOfOre);
                if(allDusts != null && allDusts.size() > 0){
                    ItemStack output = allDusts.get(0);
                    output.stackSize = 2;
                    ArrayList<ItemStack> allOresOfName = OreDictionary.getOres(name);
                    if(allOresOfName != null && allOresOfName.size() > 0){
                        for(ItemStack input : allOresOfName){
                            if(GrinderRecipes.instance().getOutput(input, false) == null){

                                //Special Second Outputs
                                if(name.equals("oreNickel")) GrinderRecipes.instance().registerRecipe(input, output, OreDictionary.getOres("dustPlatinum").get(0), 10);

                                else GrinderRecipes.instance().registerRecipe(input, output, null, 0);
                            }
                        }
                    }
                    else Util.SPRS_LOGGER.log(Level.ERROR, "Couldn't register Crusher Recipe! Didn't find Items registered as '" + name + "'! This shouldn't happen as there is something registered as '" + name + "' that doesn't exist!");
                }
                else Util.SPRS_LOGGER.log(Level.WARN, "Couldn't register Crusher Recipe! An Item with OreDictionary Registry 'dust" + nameOfOre + "' doesn't exist! This is not an Error, just a bit sad :(");
            }

        }
    }
}
