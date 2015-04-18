package ellpeck.actuallyadditions.gen;

import cpw.mods.fml.common.registry.VillagerRegistry;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.items.metalists.TheJams;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Random;

public class JamVillagerTradeHandler implements VillagerRegistry.IVillageTradeHandler{

    private ArrayList<ItemStack> villagerWants = new ArrayList<ItemStack>();

    public JamVillagerTradeHandler(){
        this.addWants("ingotGold");
        this.addWants("cropWheat");
        this.addWants("dustRedstone");
        this.addWants(new ItemStack(Items.bucket));
        this.addWants(new ItemStack(Items.glass_bottle));
        this.addWants(new ItemStack(Items.potionitem));
        this.addWants("ingotIron");
        this.addWants("gemDiamond");
        this.addWants("dustGlowstone");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void manipulateTradesForVillager(EntityVillager villager, MerchantRecipeList recipeList, Random rand){
        for(int i = 0; i < villagerWants.size(); i++){
            ItemStack wantsTwo = null;
            ItemStack wantsOne = villagerWants.get(i);
            wantsOne.stackSize = rand.nextInt(10)+10;
            if(wantsOne.stackSize > wantsOne.getMaxStackSize()) wantsOne.stackSize = wantsOne.getMaxStackSize();
            if(rand.nextInt(5) == 0){
                wantsTwo = villagerWants.get(rand.nextInt(villagerWants.size()));
                wantsTwo.stackSize = rand.nextInt(10)+1;
                if(wantsTwo.stackSize > wantsTwo.getMaxStackSize()) wantsTwo.stackSize = wantsTwo.getMaxStackSize();
            }
            if(wantsOne == wantsTwo) wantsTwo = null;

            for(int j = 0; j < TheJams.values().length; j++){
                recipeList.add(new MerchantRecipe(wantsOne, wantsTwo, new ItemStack(InitItems.itemJams, rand.nextInt(3)+1, j)));
            }
        }
    }

    public void addWants(String oredictName){
        ArrayList<ItemStack> stacks = OreDictionary.getOres(oredictName);
        villagerWants.addAll(stacks);
    }

    public void addWants(ItemStack stack){
        villagerWants.add(stack);
    }
}
