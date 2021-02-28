/*
 * This file ("BlockColoredLamp.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.base.ItemBlockBase;
import de.ellpeck.actuallyadditions.mod.blocks.metalists.TheColoredLampColors;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.relauncher.OnlyIn;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Random;

public class BlockColoredLamp extends BlockBase {

    public static final TheColoredLampColors[] ALL_LAMP_TYPES = TheColoredLampColors.values();
    public static final PropertyEnum<TheColoredLampColors> TYPE = PropertyEnum.create("type", TheColoredLampColors.class);
    public final boolean isOn;

    public BlockColoredLamp(boolean isOn) {
        super(Material.REDSTONE_LIGHT, name);
        this.setHarvestLevel("pickaxe", 0);
        this.setHardness(0.5F);
        this.setResistance(3.0F);
        this.isOn = isOn;
    }

    @Override
    public Item getItemDropped(BlockState state, Random rand, int par3) {
        return Item.getItemFromBlock(ActuallyBlocks.blockColoredLamp);
    }

    @Override
    public int damageDropped(BlockState state) {
        return this.getMetaFromState(state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, BlockState state, PlayerEntity player, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        //Turning On
        if (hand == Hand.MAIN_HAND && stack.isEmpty()) {
            world.setBlockState(pos, (this.isOn
                ? ActuallyBlocks.blockColoredLamp
                : ActuallyBlocks.blockColoredLampOn).getDefaultState().withProperty(TYPE, state.getValue(TYPE)), 2);
            world.notifyLightSet(pos);
            return true;
        }

        if (StackUtil.isValid(stack)) {
            //Changing Colors
            int[] oreIDs = OreDictionary.getOreIDs(stack);
            if (oreIDs.length > 0) {
                for (int oreID : oreIDs) {
                    String name = OreDictionary.getOreName(oreID);
                    TheColoredLampColors color = TheColoredLampColors.getColorFromDyeName(name);
                    if (color != null) {
                        if (this.getMetaFromState(state) != color.ordinal()) {
                            if (!world.isRemote) {
                                world.setBlockState(pos, this.getStateFromMeta(color.ordinal()), 2);
                                if (!player.capabilities.isCreativeMode) {
                                    player.inventory.decrStackSize(player.inventory.currentItem, 1);
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        for (int j = 0; j < ALL_LAMP_TYPES.length; j++) {
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockAccess world, BlockPos pos) {
        return this.isOn
            ? 15
            : 0;
    }

    @Override
    protected ItemBlockBase getItemBlock() {
        return new TheItemBlock(this);
    }

    @Override
    public void registerRendering() {
        for (int i = 0; i < ALL_LAMP_TYPES.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), TYPE.getName() + "=" + ALL_LAMP_TYPES[i].regName);
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, TheColoredLampColors.values()[meta]);
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return state.getValue(TYPE).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE);
    }

    public static class TheItemBlock extends ItemBlockBase {

        public TheItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(true);
            this.setMaxDamage(0);
        }

        @Override
        public String getItemStackDisplayName(ItemStack stack) {
            if (stack.getItemDamage() >= ALL_LAMP_TYPES.length) {
                return StringUtil.BUGGED_ITEM_NAME;
            }
            if (Util.isClient()) {
                return super.getItemStackDisplayName(stack) + (((BlockColoredLamp) this.block).isOn
                    ? " (" + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".onSuffix.desc") + ")"
                    : "");
            } else {
                return super.getItemStackDisplayName(stack);
            }
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return ActuallyBlocks.blockColoredLamp.getTranslationKey() + "_" + ALL_LAMP_TYPES[stack.getItemDamage()].regName;
        }
    }
}
