/*
 * This file ("CrusherRecipeAutoRegistry.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.config.ConfigValues;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class CrusherRecipeAutoRegistry{

    public static ArrayList<SearchCase> searchCases = new ArrayList<SearchCase>();

    public static void registerFinally(){
        String[] names = OreDictionary.getOreNames();
        for(String inputName : names){

            if(!hasException(inputName)){
                int resultAmount = 1;
                String inputNameWithoutPrefix = null;
                String replacer = null;

                for(SearchCase searchCase : searchCases){
                    String toSearch = searchCase.name;
                    if(inputName.length() > toSearch.length() && inputName.substring(0, toSearch.length()).equals(toSearch)){
                        inputNameWithoutPrefix = inputName.substring(toSearch.length());
                        resultAmount = searchCase.resultAmount;
                        replacer = searchCase.replacer;
                        break;
                    }
                }

                if(inputNameWithoutPrefix != null && replacer != null){
                    String inputWithDustPrefix = replacer+inputNameWithoutPrefix;
                    CrusherRecipeManualRegistry.registerRecipe(inputName, inputWithDustPrefix, resultAmount);
                }
            }
        }
    }

    private static boolean hasException(String name){
        for(String except : ConfigValues.crusherRecipeExceptions){
            if(except.equals(name)){
                return true;
            }
        }
        return false;
    }

    public static class SearchCase{

        public final String name;
        public final int resultAmount;
        public final String replacer;

        public SearchCase(String name, int resultAmount){
            this(name, resultAmount, "dust");
        }

        public SearchCase(String name, int resultAmount, String replacer){
            this.name = name;
            this.resultAmount = resultAmount;
            this.replacer = replacer;
        }
    }
}