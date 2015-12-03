/*
 * This file ("Util.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import cpw.mods.fml.common.FMLCommonHandler;
import ellpeck.actuallyadditions.items.lens.LensNoneRecipeHandler;
import ellpeck.actuallyadditions.recipe.CrusherRecipeRegistry;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Util{

    public static final Random RANDOM = new Random();
    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;
    public static final EnumRarity FALLBACK_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+".fallback", EnumChatFormatting.DARK_RED, ModUtil.NAME+" Fallback");

    public static void registerEvent(Object o){
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
    }

    public static void registerDispenserHandler(Item item, BehaviorDefaultDispenseItem handler){
        BlockDispenser.dispenseBehaviorRegistry.putObject(item, handler);
    }

    public static int arrayContains(Object[] array, Object obj){
        if(obj != null){
            for(int i = 0; i < array.length; i++){
                if(array[i] != null && (obj == array[i] || array[i].equals(obj))){
                    return i;
                }
            }
        }
        return -1;
    }

    public static int arrayContains(int[] array, int num){
        for(int i = 0; i < array.length; i++){
            if(num == array[i]){
                return i;
            }
        }
        return -1;
    }

    public static class GetRecipes{

        public static LensNoneRecipeHandler.Recipe lastReconstructorRecipe(){
            ArrayList<LensNoneRecipeHandler.Recipe> list = LensNoneRecipeHandler.recipes;
            return list.get(list.size()-1);
        }

        public static CrusherRecipeRegistry.CrusherRecipe lastCrusherRecipe(){
            ArrayList<CrusherRecipeRegistry.CrusherRecipe> list = CrusherRecipeRegistry.recipes;
            return list.get(list.size()-1);
        }

        public static IRecipe lastIRecipe(){
            List list = CraftingManager.getInstance().getRecipeList();
            Object recipe = list.get(list.size()-1);
            return recipe instanceof IRecipe ? (IRecipe)recipe : null;
        }
    }
}