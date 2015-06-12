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

        this.addBlock(InitBlocks.blockPhantomface);
        this.addBlock(InitBlocks.blockPhantomEnergyface);
        this.addBlock(InitBlocks.blockPhantomLiquiface);
        this.addBlock(InitBlocks.blockPhantomPlacer);
        this.addBlock(InitBlocks.blockPhantomBreaker);
        this.addBlock(InitBlocks.blockPhantomBooster);
        this.addBlock(InitBlocks.blockCoffeeMachine);
        this.addBlock(InitBlocks.blockInputter);
        this.addBlock(InitBlocks.blockInputterAdvanced);

        this.addBlock(InitBlocks.blockGreenhouseGlass);
        this.addBlock(InitBlocks.blockGrinder);
        this.addBlock(InitBlocks.blockGrinderDouble);
        this.addBlock(InitBlocks.blockFurnaceDouble);
        this.addBlock(InitBlocks.blockLavaFactoryController);

        this.addBlock(InitBlocks.blockFurnaceSolar);
        this.addBlock(InitBlocks.blockHeatCollector);
        this.addBlock(InitBlocks.blockCoalGenerator);
        this.addBlock(InitBlocks.blockOilGenerator);
        this.addBlock(InitBlocks.blockItemRepairer);
        this.addBlock(InitBlocks.blockFishingNet);
        this.addBlock(InitBlocks.blockBreaker);
        this.addBlock(InitBlocks.blockPlacer);
        this.addBlock(InitBlocks.blockDropper);
        this.addBlock(InitBlocks.blockFluidPlacer);
        this.addBlock(InitBlocks.blockFluidCollector);

        this.addBlock(InitBlocks.blockMisc);
        this.addBlock(InitBlocks.blockFeeder);
        this.addBlock(InitBlocks.blockCompost);
        this.addBlock(InitBlocks.blockGiantChest);
        this.addBlock(InitBlocks.blockCanolaPress);
        this.addBlock(InitBlocks.blockFermentingBarrel);

        this.addItem(InitItems.itemPhantomConnector);
        this.addItem(InitItems.itemBucketCanolaOil);
        this.addItem(InitItems.itemBucketOil);

        this.addItem(InitItems.itemCoffeeSeed);
        this.addItem(InitItems.itemCoffeeBean);
        this.addItem(InitItems.itemRiceSeed);
        this.addItem(InitItems.itemCanolaSeed);
        this.addItem(InitItems.itemFlaxSeed);
        this.addItem(InitItems.itemHairyBall);
        this.addItem(InitItems.itemMisc);
        this.addItem(InitItems.itemResonantRice);
        this.addItem(InitItems.itemFertilizer);

        this.addItem(InitItems.itemCoffee);
        this.addItem(InitItems.itemFoods);
        this.addItem(InitItems.itemKnife);
        this.addItem(InitItems.itemCrafterOnAStick);
        this.addItem(InitItems.itemDust);
        this.addItem(InitItems.itemSpecialDrop);
        this.addItem(InitItems.itemLeafBlower);
        this.addItem(InitItems.itemLeafBlowerAdvanced);

        this.addItem(InitItems.woodenPaxel);
        this.addItem(InitItems.stonePaxel);
        this.addItem(InitItems.ironPaxel);
        this.addItem(InitItems.goldPaxel);
        this.addItem(InitItems.diamondPaxel);
        this.addItem(InitItems.emeraldPaxel);
        this.addItem(InitItems.obsidianPaxel);

        this.addItem(InitItems.itemPickaxeEmerald);
        this.addItem(InitItems.itemSwordEmerald);
        this.addItem(InitItems.itemAxeEmerald);
        this.addItem(InitItems.itemShovelEmerald);
        this.addItem(InitItems.itemHoeEmerald);

        this.addItem(InitItems.itemPickaxeObsidian);
        this.addItem(InitItems.itemSwordObsidian);
        this.addItem(InitItems.itemAxeObsidian);
        this.addItem(InitItems.itemShovelObsidian);
        this.addItem(InitItems.itemHoeObsidian);

        this.addItem(InitItems.itemPotionRing);
        this.addItem(InitItems.itemPotionRingAdvanced);

        this.addItem(InitItems.itemJams);
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
        item.getSubItems(item, this, list);
    }

    private void addBlock(Block block){
        block.getSubBlocks(new ItemStack(block).getItem(), this, list);
    }
}
