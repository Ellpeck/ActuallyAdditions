package ellpeck.actuallyadditions.recipe;

import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class CrusherRecipeAutoRegistry{

    public static ArrayList<SearchCase> searchCases = new ArrayList<SearchCase>();
    public static ArrayList<String> exceptions = new ArrayList<String>();

    public static class SearchCase{

        public final String name;
        public final int resultAmount;

        public SearchCase(String name, int resultAmount){
            this.name = name;
            this.resultAmount = resultAmount;
        }
    }

    public static void registerFinally(){
        String[] names = OreDictionary.getOreNames();
        for(String inputName : names){
            
            if(!exceptions.contains(inputName)){
                int resultAmount = 1;
                String inputNameWithoutPrefix = null;

                for(SearchCase searchCase : searchCases){
                    String toSearch = searchCase.name;
                    if(inputName.length() > toSearch.length() && inputName.substring(0, toSearch.length()).equals(toSearch)){
                        inputNameWithoutPrefix = inputName.substring(toSearch.length());
                        resultAmount = searchCase.resultAmount;
                        break;
                    }
                }

                if(inputNameWithoutPrefix != null){
                    String inputWithDustPrefix = "dust" + inputNameWithoutPrefix;
                    CrusherRecipeManualRegistry.registerRecipe(inputName, inputWithDustPrefix, resultAmount);
                }
            }
        }
    }
}