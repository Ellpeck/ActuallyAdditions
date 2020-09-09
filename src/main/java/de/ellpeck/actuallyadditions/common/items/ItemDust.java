package de.ellpeck.actuallyadditions.common.items;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.base.ItemBase;
import de.ellpeck.actuallyadditions.common.items.metalists.TheDusts;
import de.ellpeck.actuallyadditions.common.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDust extends ItemBase implements IColorProvidingItem {

    public static final TheDusts[] ALL_DUSTS = TheDusts.values();

    public ItemDust(String name) {
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return stack.getItemDamage() >= ALL_DUSTS.length ? StringUtil.BUGGED_ITEM_NAME : this.getTranslationKey() + "_" + ALL_DUSTS[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_DUSTS.length ? EnumRarity.COMMON : ALL_DUSTS[stack.getItemDamage()].rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < ALL_DUSTS.length; j++) {
                list.add(new ItemStack(this, 1, j));
            }
        }
    }

    @Override
    protected void registerRendering() {
        for (int i = 0; i < ALL_DUSTS.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), "inventory");
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IItemColor getItemColor() {
        return (stack, pass) -> stack.getItemDamage() >= ALL_DUSTS.length ? 0xFFFFFF : ALL_DUSTS[stack.getItemDamage()].color;
    }

    @Override
    public int getItemBurnTime(ItemStack stack) {
        if (stack.getMetadata() == 6) return 1200;
        return super.getItemBurnTime(stack);
    }
}
