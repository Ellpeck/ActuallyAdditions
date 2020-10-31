package de.ellpeck.actuallyadditions.common.blocks;

import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.common.util.StackUtil;
import de.ellpeck.actuallyadditions.common.util.StringUtil;
import de.ellpeck.actuallyadditions.common.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class BlockColoredLamp extends Block {

    // todo: replace with flattered versions
//    public static final TheColoredLampColors[] ALL_LAMP_TYPES = TheColoredLampColors.values();
//    public static final PropertyEnum<TheColoredLampColors> TYPE = PropertyEnum.create("type", TheColoredLampColors.class);
    public final boolean isOn;

    public BlockColoredLamp(boolean isOn) {
        super(Block.Properties.create(Material.REDSTONE_LIGHT)
                .hardnessAndResistance(0.5f, 3.0f)
                .harvestTool(ToolType.PICKAXE));

        this.isOn = isOn;
    }

//    @Override
//    public Item getItemDropped(Block block) {
//        return Item.getItemFromBlock(InitBlocks.blockColoredLamp);
//    }

//    @Override
//    public int damageDropped(BlockState state) {
//        return this.getMetaFromState(state);
//    }


    // todo: re-implement
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
//        ItemStack stack = player.getHeldItem(hand);
//        //Turning On
//        if (hand == EnumHand.MAIN_HAND && stack.isEmpty()) {
//            world.setBlockState(pos, (this.isOn ? InitBlocks.blockColoredLamp : InitBlocks.blockColoredLampOn).getDefaultState().withProperty(TYPE, state.getValue(TYPE)), 2);
//            world.notifyLightSet(pos);
//            return true;
//        }
//
//        if (StackUtil.isValid(stack)) {
//            //Changing Colors
//            int[] oreIDs = OreDictionary.getOreIDs(stack);
//            if (oreIDs.length > 0) {
//                for (int oreID : oreIDs) {
//                    String name = OreDictionary.getOreName(oreID);
//                    TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
//                    if (color != null) {
//                        if (this.getMetaFromState(state) != color.ordinal()) {
//                            if (!world.isRemote) {
//                                world.setBlockState(pos, this.getStateFromMeta(color.ordinal()), 2);
//                                if (!player.capabilities.isCreativeMode) {
//                                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
//                                }
//                            }
//                            return true;
//                        }
//                    }
//                }
//            }
//        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

//    @Override
//    @SideOnly(Side.CLIENT)
//    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
//        for (int j = 0; j < ALL_LAMP_TYPES.length; j++) {
//            list.add(new ItemStack(this, 1, j));
//        }
//    }


    @Override
    public int getLightValue(BlockState state) {
        return this.isOn ? 15 : 0;
    }

//    @Override
//    public void registerRendering() {
//        for (int i = 0; i < ALL_LAMP_TYPES.length; i++) {
//            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_LAMP_TYPES[i].regName);
//        }
//    }

//    @Override
//    protected BlockStateContainer createBlockState() {
//        return new BlockStateContainer(this, TYPE);
//    }
//
//    public static class TheItemBlock extends BlockItem {
//
//        public TheItemBlock(Block block) {
//            super(block);
//            this.setHasSubtypes(true);
//            this.setMaxDamage(0);
//        }
//
//        @Override
//        public String getItemStackDisplayName(ItemStack stack) {
//            if (stack.getItemDamage() >= ALL_LAMP_TYPES.length) { return StringUtil.BUGGED_ITEM_NAME; }
//            if (Util.isClient()) return super.getItemStackDisplayName(stack) + (((BlockColoredLamp) this.block).isOn ? " (" + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".onSuffix.desc") + ")" : "");
//            else return super.getItemStackDisplayName(stack);
//        }
//
//        @Override
//        public String getTranslationKey(ItemStack stack) {
//            return InitBlocks.blockColoredLamp.getTranslationKey() + "_" + ALL_LAMP_TYPES[stack.getItemDamage()].regName;
//        }
//    }
}
