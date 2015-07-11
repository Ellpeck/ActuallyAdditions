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

        add(InitBlocks.blockPhantomface);
        add(InitBlocks.blockPhantomEnergyface);
        add(InitBlocks.blockPhantomLiquiface);
        add(InitBlocks.blockPhantomPlacer);
        add(InitBlocks.blockPhantomBreaker);
        add(InitBlocks.blockInputter);
        add(InitBlocks.blockInputterAdvanced);
        add(InitBlocks.blockPhantomBooster);
        add(InitBlocks.blockCoffeeMachine);

        add(InitBlocks.blockGreenhouseGlass);
        add(InitBlocks.blockGrinder);
        add(InitBlocks.blockGrinderDouble);
        add(InitBlocks.blockFurnaceDouble);
        add(InitBlocks.blockLavaFactoryController);

        add(InitBlocks.blockEnergizer);
        add(InitBlocks.blockEnervator);

        add(InitBlocks.blockFurnaceSolar);
        add(InitBlocks.blockHeatCollector);
        add(InitBlocks.blockCoalGenerator);
        add(InitBlocks.blockOilGenerator);
        add(InitBlocks.blockItemRepairer);
        add(InitBlocks.blockFishingNet);
        add(InitBlocks.blockBreaker);
        add(InitBlocks.blockPlacer);
        add(InitBlocks.blockDropper);
        add(InitBlocks.blockFluidPlacer);
        add(InitBlocks.blockFluidCollector);

        add(InitBlocks.blockMisc);
        add(InitBlocks.blockFeeder);
        add(InitBlocks.blockCompost);
        add(InitBlocks.blockGiantChest);
        add(InitBlocks.blockCanolaPress);
        add(InitBlocks.blockFermentingBarrel);

        add(InitBlocks.blockTestifiBucksGreenWall);
        add(InitBlocks.blockTestifiBucksWhiteWall);
        add(InitBlocks.blockTestifiBucksGreenStairs);
        add(InitBlocks.blockTestifiBucksWhiteStairs);
        add(InitBlocks.blockTestifiBucksGreenSlab);
        add(InitBlocks.blockTestifiBucksWhiteSlab);
        add(InitBlocks.blockColoredLamp);
        add(InitBlocks.blockLampPowerer);
        add(InitBlocks.blockTreasureChest);

        add(InitItems.itemDrill);
        add(InitItems.itemDrillUpgradeSpeed);
        add(InitItems.itemDrillUpgradeSpeedII);
        add(InitItems.itemDrillUpgradeSpeedIII);
        add(InitItems.itemDrillUpgradeSilkTouch);
        add(InitItems.itemDrillUpgradeFortune);
        add(InitItems.itemDrillUpgradeFortuneII);
        add(InitItems.itemDrillUpgradeThreeByThree);
        add(InitItems.itemDrillUpgradeFiveByFive);
        add(InitItems.itemDrillUpgradeBlockPlacing);
        add(InitItems.itemBattery);
        add(InitItems.itemTeleStaff);
        
        add(InitItems.itemPhantomConnector);
        add(InitItems.itemBucketCanolaOil);
        add(InitItems.itemBucketOil);

        add(InitItems.itemCoffeeSeed);
        add(InitItems.itemCoffeeBean);
        add(InitItems.itemRiceSeed);
        add(InitItems.itemCanolaSeed);
        add(InitItems.itemFlaxSeed);
        add(InitItems.itemHairyBall);
        add(InitItems.itemMisc);
        add(InitItems.itemResonantRice);
        add(InitItems.itemFertilizer);

        add(InitItems.itemCoffee);
        add(InitItems.itemFoods);
        add(InitItems.itemKnife);
        add(InitItems.itemCrafterOnAStick);
        add(InitItems.itemDust);
        add(InitItems.itemSpecialDrop);
        add(InitItems.itemLeafBlower);
        add(InitItems.itemLeafBlowerAdvanced);

        add(InitItems.woodenPaxel);
        add(InitItems.stonePaxel);
        add(InitItems.ironPaxel);
        add(InitItems.goldPaxel);
        add(InitItems.diamondPaxel);
        add(InitItems.emeraldPaxel);
        add(InitItems.obsidianPaxel);

        add(InitItems.itemPickaxeEmerald);
        add(InitItems.itemSwordEmerald);
        add(InitItems.itemAxeEmerald);
        add(InitItems.itemShovelEmerald);
        add(InitItems.itemHoeEmerald);
        
        add(InitItems.itemHelmEmerald);
        add(InitItems.itemChestEmerald);
        add(InitItems.itemPantsEmerald);
        add(InitItems.itemBootsEmerald);

        add(InitItems.itemPickaxeObsidian);
        add(InitItems.itemSwordObsidian);
        add(InitItems.itemAxeObsidian);
        add(InitItems.itemShovelObsidian);
        add(InitItems.itemHoeObsidian);

        add(InitItems.itemHelmObsidian);
        add(InitItems.itemChestObsidian);
        add(InitItems.itemPantsObsidian);
        add(InitItems.itemBootsObsidian);

        add(InitItems.itemScubaHelm);
        add(InitItems.itemScubaChest);
        add(InitItems.itemScubaPants);
        add(InitItems.itemScubaBoots);

        add(InitItems.itemJams);

        add(InitItems.itemPotionRing);
        add(InitItems.itemPotionRingAdvanced);
    }

    @Override
    public Item getTabIconItem(){
        return Item.getItemFromBlock(InitBlocks.blockPhantomLiquiface);
    }

    @Override
    public ItemStack getIconItemStack(){
        return new ItemStack(this.getTabIconItem());
    }

    private void add(Item item){
        item.getSubItems(item, instance, list);
    }

    private void add(Block block){
        block.getSubBlocks(new ItemStack(block).getItem(), instance, list);
    }
}
