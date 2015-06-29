package ellpeck.actuallyadditions.recipe;

import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

public class GrinderRecipeAutoRegistry{

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
                    ArrayList<ItemStack> allOresOfInitialInputName = (ArrayList<ItemStack>)OreDictionary.getOres(inputName, false);
                    ArrayList<ItemStack> allOresWithDustPrefix = (ArrayList<ItemStack>)OreDictionary.getOres(inputWithDustPrefix, false);
                    if(allOresOfInitialInputName != null && allOresOfInitialInputName.size() > 0){
                        if(allOresWithDustPrefix != null && allOresWithDustPrefix.size() > 0){
                            for(ItemStack theInput : allOresOfInitialInputName){
                                for(ItemStack theDust : allOresWithDustPrefix){
                                    ItemStack input = theInput.copy();
                                    ItemStack output = theDust.copy();
                                    output.stackSize = resultAmount;
                                    if(!GrinderRecipeManualRegistry.hasRecipe(input, output)){
                                        GrinderRecipeManualRegistry.registerRecipe(input, output, null, 0);
                                    }
                                }
                            }
                        }
                        else{
                            if(ConfigBoolValues.DO_CRUSHER_SPAM.isEnabled())
                                ModUtil.LOGGER.log(Level.INFO, "Couldn't register Crusher Recipe! An Item with OreDictionary Registry '" + inputWithDustPrefix + "' doesn't exist! It should correspond to '" + inputName + "'! This is not an Error, just a bit sad :(");
                        }

                    }
                    else{
                        if(ConfigBoolValues.DO_CRUSHER_SPAM.isEnabled())
                            ModUtil.LOGGER.log(Level.WARN, "Couldn't register Crusher Recipe! Didn't find Items registered as '" + inputName + "'! This shouldn't happen as there is something registered as '" + inputName + "' that doesn't exist!");
                    }
                }
            }
        }
    }
}