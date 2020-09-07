package de.ellpeck.actuallyadditions.mod.blocks.base;

import net.minecraft.block.BushBlock;
import net.minecraft.block.SoundType;

public class BlockBushBase extends BushBlock {
    public BlockBushBase(Properties properties) {
        super(properties.sound(SoundType.PLANT));
//        this.register();
    }

//    private void register() {
//        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
//    }

//    protected ItemBlockBase getItemBlock() {
//        return new ItemBlockBase(this);
//    }

    public boolean shouldAddCreative() {
        return true;
    }

//    @Override
//    public void registerRendering() {
//        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
//    }

//    @Override
//    public EnumRarity getRarity(ItemStack stack) {
//        return EnumRarity.COMMON;
//    }
}
