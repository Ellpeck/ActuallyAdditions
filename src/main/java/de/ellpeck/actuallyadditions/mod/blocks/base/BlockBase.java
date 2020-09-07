//package de.ellpeck.actuallyadditions.mod.blocks.base;
//
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.blocks.render.IHasModel;
//import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
//import net.minecraft.block.Block;
//import net.minecraft.block.material.Material;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Rarity;
//import net.minecraftforge.common.IRarity;
//
//public class BlockBase extends Block implements ItemBlockBase.ICustomRarity, IHasModel {
//
//    private final String name;
//
//    public BlockBase(Material material, String name) {
//        super(material);
//        this.name = name;
//
//        this.register();
//    }
//
//    private void register() {
//        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
//    }
//
//    protected String getBaseName() {
//        return this.name;
//    }
//
//    protected ItemBlockBase getItemBlock() {
//        return new ItemBlockBase(this);
//    }
//
//    public boolean shouldAddCreative() {
//        return true;
//    }
//
//    @Override
//    public void registerRendering() {
//        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
//    }
//
//
//
//    @Override
//    public Rarity getRarity(ItemStack stack) {
//        return EnumRarity.COMMON;
//    }
//}
