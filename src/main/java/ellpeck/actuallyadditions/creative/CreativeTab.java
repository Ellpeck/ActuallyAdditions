package ellpeck.actuallyadditions.creative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();
    private List list;

    public CreativeTab(){
        super(ModUtil.MOD_ID_LOWER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List list){
        this.list = list;

        addBlock(InitBlocks.blockPhantomface);
        addBlock(InitBlocks.blockPhantomEnergyface);
        addBlock(InitBlocks.blockPhantomLiquiface);
        addBlock(InitBlocks.blockPhantomPlacer);
        addBlock(InitBlocks.blockPhantomBreaker);
        addBlock(InitBlocks.blockPhantomBooster);
        addBlock(InitBlocks.blockCoffeeMachine);
        addBlock(InitBlocks.blockInputter);
        addBlock(InitBlocks.blockInputterAdvanced);

        addBlock(InitBlocks.blockGreenhouseGlass);
        addBlock(InitBlocks.blockGrinder);
        addBlock(InitBlocks.blockGrinderDouble);
        addBlock(InitBlocks.blockFurnaceDouble);
        addBlock(InitBlocks.blockLavaFactoryController);

        addBlock(InitBlocks.blockFurnaceSolar);
        addBlock(InitBlocks.blockHeatCollector);
        addBlock(InitBlocks.blockCoalGenerator);
        addBlock(InitBlocks.blockOilGenerator);
        addBlock(InitBlocks.blockItemRepairer);
        addBlock(InitBlocks.blockFishingNet);
        addBlock(InitBlocks.blockBreaker);
        addBlock(InitBlocks.blockPlacer);
        addBlock(InitBlocks.blockDropper);
        addBlock(InitBlocks.blockFluidPlacer);
        addBlock(InitBlocks.blockFluidCollector);

        addBlock(InitBlocks.blockMisc);
        addBlock(InitBlocks.blockFeeder);
        addBlock(InitBlocks.blockCompost);
        addBlock(InitBlocks.blockGiantChest);
        addBlock(InitBlocks.blockCanolaPress);
        addBlock(InitBlocks.blockFermentingBarrel);

        //addItem(InitItems.itemDrill);
        addItem(InitItems.itemPhantomConnector);
        addItem(InitItems.itemBucketCanolaOil);
        addItem(InitItems.itemBucketOil);

        addItem(InitItems.itemCoffeeSeed);
        addItem(InitItems.itemCoffeeBean);
        addItem(InitItems.itemRiceSeed);
        addItem(InitItems.itemCanolaSeed);
        addItem(InitItems.itemFlaxSeed);
        addItem(InitItems.itemHairyBall);
        addItem(InitItems.itemMisc);
        addItem(InitItems.itemResonantRice);
        addItem(InitItems.itemFertilizer);

        addItem(InitItems.itemCoffee);
        addItem(InitItems.itemFoods);
        addItem(InitItems.itemKnife);
        addItem(InitItems.itemCrafterOnAStick);
        addItem(InitItems.itemDust);
        addItem(InitItems.itemSpecialDrop);
        addItem(InitItems.itemLeafBlower);
        addItem(InitItems.itemLeafBlowerAdvanced);

        addItem(InitItems.woodenPaxel);
        addItem(InitItems.stonePaxel);
        addItem(InitItems.ironPaxel);
        addItem(InitItems.goldPaxel);
        addItem(InitItems.diamondPaxel);
        addItem(InitItems.emeraldPaxel);
        addItem(InitItems.obsidianPaxel);

        addItem(InitItems.itemPickaxeEmerald);
        addItem(InitItems.itemSwordEmerald);
        addItem(InitItems.itemAxeEmerald);
        addItem(InitItems.itemShovelEmerald);
        addItem(InitItems.itemHoeEmerald);

        addItem(InitItems.itemPickaxeObsidian);
        addItem(InitItems.itemSwordObsidian);
        addItem(InitItems.itemAxeObsidian);
        addItem(InitItems.itemShovelObsidian);
        addItem(InitItems.itemHoeObsidian);

        addItem(InitItems.itemPotionRing);
        addItem(InitItems.itemPotionRingAdvanced);

        addItem(InitItems.itemJams);
    }

    @Override
    public Item getTabIconItem(){
        return Item.getItemFromBlock(InitBlocks.blockPhantomLiquiface);
    }

    @Override
    public ItemStack getIconItemStack(){
        return new ItemStack(this.getTabIconItem());
    }

    private void addItem(Item item){
        item.getSubItems(item, instance, list);
    }

    private void addBlock(Block block){
        block.getSubBlocks(new ItemStack(block).getItem(), instance, list);
    }
}
