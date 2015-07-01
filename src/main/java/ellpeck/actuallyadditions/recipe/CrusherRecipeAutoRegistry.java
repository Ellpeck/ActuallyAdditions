package ellpeck.actuallyadditions.recipe;

import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class CrusherRecipeAutoRegistry{

    public static ArrayList<SearchCase> searchCases = new ArrayList<SearchCase>();
    public static ArrayList<String> exceptions = new ArrayList<String>();

    public static class SearchCase{

        public final String name;
        public final int resultAmount;
        public final String replacer;

        public SearchCase(String name, int resultAmount, String replacer){
            this.name = name;
            this.resultAmount = resultAmount;
            this.replacer = replacer;
        }

        public SearchCase(String name, int resultAmount){
            this(name, resultAmount, "dust");
        }
    }

    public static void registerFinally(){
        String[] names = OreDictionary.getOreNames();
        for(String inputName : names){
            
            if(!exceptions.contains(inputName)){
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
}