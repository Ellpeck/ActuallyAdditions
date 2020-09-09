package de.ellpeck.actuallyadditions.common.items.base;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.util.ItemUtil;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemFoodBase extends ItemFood {

    private final String name;

    public ItemFoodBase(int heal, float saturation, boolean wolfFood, String name) {
        super(heal, saturation, wolfFood);
        this.name = name;

        this.register();
    }

    private void register() {
        ItemUtil.registerItem(this, this.getBaseName(), this.shouldAddCreative());

        this.registerRendering();
    }

    protected String getBaseName() {
        return this.name;
    }

    public boolean shouldAddCreative() {
        return true;
    }

    protected void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }
}
