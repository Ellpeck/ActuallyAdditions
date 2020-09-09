package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.base.BlockItemBase;
import de.ellpeck.actuallyadditions.common.items.metalists.TheCrystals;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
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
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystal extends Block {

    public static final TheCrystals[] ALL_CRYSTALS = TheCrystals.values();
    private static final PropertyEnum<TheCrystals> TYPE = PropertyEnum.create("type", TheCrystals.class);

    private final boolean isEmpowered;

    public BlockCrystal(boolean isEmpowered) {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f, 10.0f)
                .harvestTool(ToolType.PICKAXE)
                .sound(SoundType.STONE));

        this.isEmpowered = isEmpowered;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < ALL_CRYSTALS.length; j++) {
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected BlockItemBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public void registerRendering() {
        for (int i = 0; i < ALL_CRYSTALS.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_CRYSTALS[i].name);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, TheCrystals.values()[meta]);
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
            return stack.getItemDamage() >= ALL_CRYSTALS.length ? StringUtil.BUGGED_ITEM_NAME : this.getTranslationKey() + "_" + ALL_CRYSTALS[stack.getItemDamage()].name;
        }

        @Override
        public boolean hasEffect(ItemStack stack) {
            return this.block instanceof BlockCrystal && ((BlockCrystal) this.block).isEmpowered;
        }
    }
}
