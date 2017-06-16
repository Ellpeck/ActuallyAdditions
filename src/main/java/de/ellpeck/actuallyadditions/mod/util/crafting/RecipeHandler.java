/*
 Copyright 2014-2017, the Biomes O' Plenty Team

 This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.

 To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.

 Original: https://github.com/Glitchfiend/BiomesOPlenty/blob/0f8be0526e01d918cf8f22d4904a3b74981dee6f/src/main/java/biomesoplenty/common/util/inventory/CraftingUtil.java
 (edited to work with multiple mods)
 */
package de.ellpeck.actuallyadditions.mod.util.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.crafting.RecipeBioMash;
import de.ellpeck.actuallyadditions.mod.crafting.RecipePotionRingCharging;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreIngredient;

import java.util.*;

@SuppressWarnings("all")
public final class RecipeHandler{

    public static IRecipe lastRecipe;

    public static void addOreDictRecipe(ItemStack output, Object... inputs){
        addShapedRecipe(output, inputs);
    }

    public static void addShapelessOreDictRecipe(ItemStack output, Object... inputs){
        addShapelessRecipe(output, inputs);
    }

    public static void addShapelessRecipe(ItemStack output, Object... inputs){
        String namespace = getNamespace();
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for(Object input : inputs){
            ingredients.add(asIngredient(input));
        }

        if(ingredients.isEmpty()){
            throw new IllegalArgumentException("No ingredients for shapeless recipe");
        }
        else if(ingredients.size() > 9){
            throw new IllegalArgumentException("Too many ingredients for shapeless recipe");
        }

        ShapelessRecipes recipe = new ShapelessRecipes(outputGroup(namespace, output), output, ingredients);
        addRecipe(unusedLocForOutput(namespace, output), recipe);
    }

    public static void addShapedRecipe(ItemStack output, Object... inputs){
        String namespace = getNamespace();
        ArrayList<String> pattern = Lists.newArrayList();
        Map<String, Ingredient> key = Maps.newHashMap();
        Iterator itr = Arrays.asList(inputs).iterator();

        while(itr.hasNext()){
            Object obj = itr.next();

            if(obj instanceof String){
                String str = (String)obj;

                if(str.length() > 3){
                    throw new IllegalArgumentException("Invalid string length for recipe "+str.length());
                }

                if(pattern.size() <= 2){
                    pattern.add(str);
                }
                else{
                    throw new IllegalArgumentException("Recipe has too many crafting rows!");
                }
            }
            else if(obj instanceof Character){
                key.put(((Character)obj).toString(), asIngredient(itr.next()));
            }
            else{
                throw new IllegalArgumentException("Unexpected argument of type "+obj.getClass().toString());
            }
        }

        int width = pattern.get(0).length();
        int height = pattern.size();

        try{
            key.put(" ", Ingredient.field_193370_a);
            Object ingredients = prepareMaterials(pattern.toArray(new String[pattern.size()]), key, width, height);
            ShapedRecipes recipe = new ShapedRecipes(outputGroup(namespace, output), width, height, (NonNullList<Ingredient>)ingredients, output);
            addRecipe(unusedLocForOutput(namespace, output), recipe);
        }
        catch(Throwable e){
            throw new RuntimeException(e);
        }
    }

    // copy from vanilla
    private static NonNullList<Ingredient> prepareMaterials(String[] p_192402_0_, Map<String, Ingredient> p_192402_1_, int p_192402_2_, int p_192402_3_){
        NonNullList<Ingredient> nonnulllist = NonNullList.<Ingredient>withSize(p_192402_2_*p_192402_3_, Ingredient.field_193370_a);
        Set<String> set = Sets.newHashSet(p_192402_1_.keySet());
        set.remove(" ");

        for(int i = 0; i < p_192402_0_.length; ++i){
            for(int j = 0; j < p_192402_0_[i].length(); ++j){
                String s = p_192402_0_[i].substring(j, j+1);
                Ingredient ingredient = p_192402_1_.get(s);

                set.remove(s);
                nonnulllist.set(j+p_192402_2_*i, ingredient);
            }
        }

        return nonnulllist;
    }

    public static void addRecipe(ResourceLocation res, IRecipe recipe){
        if(!(recipe instanceof RecipePotionRingCharging) && !(recipe instanceof RecipeBioMash)){
            if(recipe.getRecipeOutput().isEmpty()){
                throw new IllegalArgumentException("Illegal recipe output");
            }
        }

        recipe.setRegistryName(res);
        GameRegistry.register(recipe);
        lastRecipe = recipe;
    }

    private static Ingredient asIngredient(Object object){
        if(object instanceof Item){
            return Ingredient.func_193367_a((Item)object);
        }

        else if(object instanceof Block){
            return Ingredient.func_193369_a(new ItemStack((Block)object));
        }

        else if(object instanceof ItemStack){
            return Ingredient.func_193369_a((ItemStack)object);
        }

        else if(object instanceof String){
            return new OreIngredient((String)object);
        }

        throw new IllegalArgumentException("Cannot convert object of type "+object.getClass().toString()+" to an Ingredient!");
    }

    private static ResourceLocation unusedLocForOutput(String namespace, ItemStack output){
        ResourceLocation baseLoc = new ResourceLocation(namespace, output.getItem().getRegistryName().getResourcePath());
        ResourceLocation recipeLoc = baseLoc;
        int index = 0;

        // find unused recipe name
        while(CraftingManager.field_193380_a.containsKey(recipeLoc)){
            index++;
            recipeLoc = new ResourceLocation(namespace, baseLoc.getResourcePath()+"_"+index);
        }

        return recipeLoc;
    }

    private static String outputGroup(String namespace, ItemStack output){
        Item item = output.getItem();
        if(item instanceof IRecipeGrouped){
            return namespace+":"+((IRecipeGrouped)item).getRecipeGroup();
        }
        if(item instanceof ItemBlock){
            Block block = ((ItemBlock)item).block;
            if(block instanceof IRecipeGrouped){
                return namespace+":"+((IRecipeGrouped)block).getRecipeGroup();
            }
        }

        return output.getItem().getRegistryName().toString();
    }

    private static String getNamespace(){
        return Loader.instance().activeModContainer().getModId();
    }


}