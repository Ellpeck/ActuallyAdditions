/*
 * This file ("JamVillagerTradeHandler.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.gen;

import cpw.mods.fml.common.registry.VillagerRegistry;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheJams;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class JamVillagerTradeHandler implements VillagerRegistry.IVillageTradeHandler{

    private ArrayList<Trade> trades = new ArrayList<Trade>();

    public JamVillagerTradeHandler(){
        this.addWants("ingotGold", 5, 7);
        this.addWants("cropWheat", 15, 25);
        this.addWants("dustRedstone", 25, 40);
        this.addWants(new ItemStack(Items.bucket), 5, 9);
        this.addWants(new ItemStack(Items.glass_bottle), 12, 17);
        this.addWants(new ItemStack(Items.potionitem), 1, 1);
        this.addWants("ingotIron", 10, 15);
        this.addWants("gemDiamond", 1, 2);
        this.addWants("dustGlowstone", 12, 22);
    }

    public void addWants(String oredictName, int minSize, int maxSize){
        ArrayList<ItemStack> stacks = (ArrayList<ItemStack>)OreDictionary.getOres(oredictName, false);
        trades.add(new Trade(stacks, minSize, maxSize));
    }

    public void addWants(ItemStack stack, int minSize, int maxSize){
        trades.add(new Trade(stack, minSize, maxSize));
    }

    @Override
    @SuppressWarnings("all")
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random rand){
        for(int trade = 0; trade < trades.size(); trade++){
            for(int want = 0; want < trades.get(trade).wants.size(); want++){
                ItemStack wantsOne = trades.get(trade).wants.get(want);
                wantsOne.stackSize = MathHelper.getRandomIntegerInRange(rand, trades.get(trade).minStackSize, trades.get(trade).maxStackSize);

                ItemStack wantsTwo = null;
                if(rand.nextInt(3) == 0){
                    int randomSecondTrade = rand.nextInt(trades.size());
                    for(int randomSecondWant = 0; randomSecondWant < trades.get(randomSecondTrade).wants.size(); randomSecondWant++){
                        wantsTwo = trades.get(randomSecondTrade).wants.get(randomSecondWant);
                        wantsTwo.stackSize = MathHelper.getRandomIntegerInRange(rand, trades.get(randomSecondTrade).minStackSize, trades.get(randomSecondTrade).maxStackSize);
                    }
                }
                if(wantsOne == wantsTwo){
                    wantsTwo = null;
                }

                for(int k = 0; k < TheJams.values().length; k++){
                    recipeList.add(new MerchantRecipe(wantsOne, wantsTwo, new ItemStack(InitItems.itemJams, rand.nextInt(3)+1, k)));
                }
            }
        }
    }

    public static class Trade{

        public final ArrayList<ItemStack> wants = new ArrayList<ItemStack>();
        public final int minStackSize;
        public final int maxStackSize;

        public Trade(ArrayList<ItemStack> wants, int minStackSize, int maxStackSize){
            this.wants.addAll(wants);
            this.minStackSize = minStackSize <= 0 ? 1 : minStackSize;
            this.maxStackSize = maxStackSize <= 0 ? 1 : maxStackSize;
        }

        public Trade(ItemStack want, int minStackSize, int maxStackSize){
            this.wants.add(want);
            this.minStackSize = minStackSize <= 0 ? 1 : minStackSize;
            this.maxStackSize = maxStackSize <= 0 ? 1 : maxStackSize;
        }

    }
}
