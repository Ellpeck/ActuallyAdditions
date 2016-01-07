/*
 * This file ("Util.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.api.recipe.CrusherRecipe;
import de.ellpeck.actuallyadditions.api.recipe.LensNoneRecipe;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public class Util{

    public static final Random RANDOM = new Random();
    public static final int WILDCARD = OreDictionary.WILDCARD_VALUE;

    public static final EnumRarity CRYSTAL_RED_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalRed", EnumChatFormatting.DARK_RED, ModUtil.NAME+" Red Crystal");
    public static final EnumRarity CRYSTAL_BLUE_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalBlue", EnumChatFormatting.DARK_BLUE, ModUtil.NAME+" Blue Crystal");
    public static final EnumRarity CRYSTAL_LIGHT_BLUE_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalLightBlue", EnumChatFormatting.BLUE, ModUtil.NAME+" Light Blue Crystal");
    public static final EnumRarity CRYSTAL_BLACK_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalBlack", EnumChatFormatting.DARK_GRAY, ModUtil.NAME+" Black Crystal");
    public static final EnumRarity CRYSTAL_GREEN_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalGreen", EnumChatFormatting.DARK_GREEN, ModUtil.NAME+" Green Crystal");
    public static final EnumRarity CRYSTAL_WHITE_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+"crystalWhite", EnumChatFormatting.GRAY, ModUtil.NAME+" White Crystal");

    public static final EnumRarity FALLBACK_RARITY = EnumHelper.addRarity(ModUtil.MOD_ID_LOWER+".fallback", EnumChatFormatting.STRIKETHROUGH, ModUtil.NAME+" Fallback");

    public static void registerEvent(Object o){
        MinecraftForge.EVENT_BUS.register(o);
        FMLCommonHandler.instance().bus().register(o);
    }

    public static boolean isDevVersion(){
        return ModUtil.VERSION.equals("@VERSION@");
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

        public static LensNoneRecipe lastReconstructorRecipe(){
            List<LensNoneRecipe> list = ActuallyAdditionsAPI.reconstructorLensNoneRecipes;
            return list.get(list.size()-1);
        }

        public static CrusherRecipe lastCrusherRecipe(){
            List<CrusherRecipe> list = ActuallyAdditionsAPI.crusherRecipes;
            return list.get(list.size()-1);
        }

        public static IRecipe lastIRecipe(){
            List list = CraftingManager.getInstance().getRecipeList();
            Object recipe = list.get(list.size()-1);
            return recipe instanceof IRecipe ? (IRecipe)recipe : null;
        }
    }
}