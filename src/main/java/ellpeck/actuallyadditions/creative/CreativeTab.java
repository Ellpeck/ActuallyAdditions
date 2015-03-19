package ellpeck.actuallyadditions.creative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.blocks.InitBlocks;
import ellpeck.actuallyadditions.items.InitItems;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CreativeTab extends CreativeTabs{

    public static CreativeTab instance = new CreativeTab();
    private List list;

    public CreativeTab(){
        super(Util.MOD_ID_LOWER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllReleventItems(List list){
        this.list = list;

        this.addBlock(InitBlocks.blockInputter);
        this.addBlock(InitBlocks.blockGrinder);
        this.addBlock(InitBlocks.blockGrinderDouble);
        this.addBlock(InitBlocks.blockFurnaceDouble);

        this.addBlock(InitBlocks.blockMisc);
        this.addBlock(InitBlocks.blockFeeder);
        this.addBlock(InitBlocks.blockCompost);
        this.addBlock(InitBlocks.blockGiantChest);

        this.addItem(InitItems.itemMisc);
        this.addItem(InitItems.itemFertilizer);
        this.addItem(InitItems.itemFoods);
        this.addItem(InitItems.itemKnife);
        this.addItem(InitItems.itemCrafterOnAStick);
        this.addItem(InitItems.itemDust);
        this.addItem(InitItems.itemSpecialDrop);

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
    }

    @Override
    public Item getTabIconItem(){
        return Item.getItemFromBlock(InitBlocks.blockInputter);
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
