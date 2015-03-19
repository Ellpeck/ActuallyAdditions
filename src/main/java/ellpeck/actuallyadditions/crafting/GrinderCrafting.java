package ellpeck.actuallyadditions.crafting;

import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheDusts;
import ellpeck.actuallyadditions.recipe.GrinderRecipes;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class GrinderCrafting{

    public static void init(){
        Util.logInfo("Initializing Grinder Recipes...");

        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.iron_ore), new ItemStack(InitItems.itemDust, 2, TheDusts.IRON.ordinal()), new ItemStack(InitItems.itemDust, 1, TheDusts.GOLD.ordinal()), 10);
        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone, 10), null, 0);
        GrinderRecipes.instance().registerRecipe(new ItemStack(Blocks.lapis_ore), new ItemStack(InitItems.itemDust, 12, TheDusts.LAPIS.ordinal()), null, 0);

        registerFinally();
    }

    public static void registerFinally(){
        String[] names = OreDictionary.getOreNames();
        for(String name : names){

            int resultAmount = 1;
            String nameOfOre = null;

            if(name.length() > 3 && name.substring(0, 3).equals("ore")){
                nameOfOre = name.substring(3);
                resultAmount = 2;
            }
            if(name.length() > 9 && name.substring(0, 9).equals("oreNether")){
                nameOfOre = name.substring(9);
                resultAmount = 2;
            }
            if(name.length() > 8 && name.substring(0, 8).equals("denseore")){
                nameOfOre = name.substring(8);
                resultAmount = 6;
            }
            if(name.length() > 3 && name.substring(0, 3).equals("gem")) nameOfOre = name.substring(3);
            if(name.length() > 5 && name.substring(0, 5).equals("ingot")) nameOfOre = name.substring(5);

            if(nameOfOre != null){
                ArrayList<ItemStack> allDusts;
                String nameToGetFrom = "dust" + nameOfOre;

                allDusts = OreDictionary.getOres(nameToGetFrom);

                if(allDusts != null && allDusts.size() > 0){
                    ArrayList<ItemStack> allOresOfName = OreDictionary.getOres(name);
                    if(allOresOfName != null && allOresOfName.size() > 0){
                        for(ItemStack output : allDusts){
                            output.stackSize = resultAmount;
                            for(ItemStack input : allOresOfName){
                                if(GrinderRecipes.instance().getOutput(input, false) == null){

                                    if(name.equals("oreNickel")){
                                        ArrayList<ItemStack> specialStacks = OreDictionary.getOres("dustPlatinum");
                                        for(ItemStack theSpecial : specialStacks) GrinderRecipes.instance().registerRecipe(input, output, theSpecial, 10);
                                    }

                                    else GrinderRecipes.instance().registerRecipe(input, output, null, 0);
                                }
                            }
                        }
                    }
                    else Util.AA_LOGGER.log(Level.ERROR, "Couldn't register Crusher Recipe! Didn't find Items registered as '" + name + "'! This shouldn't happen as there is something registered as '" + name + "' that doesn't exist!");
                }
                else if(!name.equals("ingotBrick") && !name.equals("ingotBrickNether")) Util.AA_LOGGER.log(Level.WARN, "Couldn't register Crusher Recipe! An Item with OreDictionary Registry '" + nameToGetFrom + "' doesn't exist! It should correspond to '" + name + "'! This is not an Error, just a bit sad :(");
            }
        }
    }
}
