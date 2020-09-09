//package de.ellpeck.actuallyadditions.common.blocks.base;
//
//import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.common.blocks.render.IHasModel;
//import de.ellpeck.actuallyadditions.common.util.ItemUtil;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockStairs;
//import net.minecraft.block.BlockState;
//import net.minecraft.block.StairsBlock;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.item.EnumRarity;
//import net.minecraft.item.ItemStack;
//
//@Deprecated
//public class BlockStair extends StairsBlock { //BlockStairs implements ItemBlockBase.ICustomRarity, IHasModel {
//
////    private final String name;
//
////    public BlockStair(Block block, String name) {
////
////        this(block.getDefaultState(), name);
////    }
////
////    public BlockStair(BlockState state) {
//////        super(state);
//////        this.name = name;
//////        this.setLightOpacity(0);
////
//////        this.register();
////    }
//
////    private void register() {
////        ItemUtil.registerBlock(this, this.getItemBlock(), this.getBaseName(), this.shouldAddCreative());
////    }
//
////    protected String getBaseName() {
////        return this.name;
////    }
//
////    protected ItemBlockBase getItemBlock() {
////        return new ItemBlockBase(this);
////    }
//
//    public boolean shouldAddCreative() {
//        return true;
//    }
//
////    @Override
////    public void registerRendering() {
////        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), this.getRegistryName(), "inventory");
////    }
//
////    @Override
////    public EnumRarity getRarity(ItemStack stack) {
////        return EnumRarity.COMMON;
////    }
//}
