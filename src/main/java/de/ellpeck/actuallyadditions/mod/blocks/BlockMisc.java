package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockItemBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheMiscBlocks;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ToolType;

public class BlockMisc extends Block {

    public static final TheMiscBlocks[] ALL_MISC_BLOCKS = TheMiscBlocks.values();
    public static final PropertyEnum<TheMiscBlocks> TYPE = PropertyEnum.create("type", TheMiscBlocks.class);

    public BlockMisc() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < ALL_MISC_BLOCKS.length; j++) {
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected BlockItemBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public void registerRendering() {
        for (int i = 0; i < ALL_MISC_BLOCKS.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_MISC_BLOCKS[i].name);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_MISC_BLOCKS.length ? EnumRarity.COMMON : ALL_MISC_BLOCKS[stack.getItemDamage()].rarity;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, TheMiscBlocks.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    public static class TheItemBlock extends BlockItemBase {

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return stack.getItemDamage() >= ALL_MISC_BLOCKS.length ? StringUtil.BUGGED_ITEM_NAME : this.getTranslationKey() + "_" + ALL_MISC_BLOCKS[stack.getItemDamage()].name;
        }

        @Override
        public int getItemBurnTime(ItemStack stack) {
            if (stack.getMetadata() == TheMiscBlocks.CHARCOAL_BLOCK.ordinal()) return 16000;
            return super.getItemBurnTime(stack);
        }
    }
}
