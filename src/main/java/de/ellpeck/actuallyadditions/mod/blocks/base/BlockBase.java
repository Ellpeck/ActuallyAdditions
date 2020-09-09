package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.Block;

public class BlockBase extends Block {

    private final String name;

    public BlockBase(Properties properties, String name) {
        super(properties);
        this.name = name;

        this.register();
    }

    @Deprecated
    private void register() {
        //ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
    }

    protected String getBaseName() {
        return this.name;
    }

    protected BlockItemBase getItemBlock() {
        return new BlockItemBase(this);
    }

    public boolean shouldAddCreative() {
        return true;
    }

    /*@Override
    public void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
    }
    
    @Override
    public Rarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }*/
}
