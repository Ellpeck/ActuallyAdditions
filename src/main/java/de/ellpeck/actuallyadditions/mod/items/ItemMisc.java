/*
 * This file ("ItemMisc.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.fluids.InitFluids;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheMiscItems;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.OnlyIn;

public class ItemMisc extends ItemBase {

    public static final TheMiscItems[] ALL_MISC_ITEMS = TheMiscItems.values();

    public ItemMisc(String name) {
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return stack.getItemDamage() >= ALL_MISC_ITEMS.length
            ? StringUtil.BUGGED_ITEM_NAME
            : this.getTranslationKey() + "_" + ALL_MISC_ITEMS[stack.getItemDamage()].name;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_MISC_ITEMS.length
            ? EnumRarity.COMMON
            : ALL_MISC_ITEMS[stack.getItemDamage()].rarity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < ALL_MISC_ITEMS.length; j++) {
                if (j != TheMiscItems.YOUTUBE_ICON.ordinal()) {
                    list.add(new ItemStack(this, 1, j));
                }
            }
        }
    }

    @Override
    protected void registerRendering() {
        for (int i = 0; i < ALL_MISC_ITEMS.length; i++) {
            String name = this.getRegistryName() + "_" + ALL_MISC_ITEMS[i].name;
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), new ResourceLocation(name), "inventory");
        }
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entity) {
        if (!entity.world.isRemote) {
            ItemStack stack = entity.getItem();
            if (stack != null) {
                boolean isEmpowered = stack.getItemDamage() == TheMiscItems.EMPOWERED_CANOLA_SEED.ordinal();
                if (stack.getItemDamage() == TheMiscItems.CRYSTALLIZED_CANOLA_SEED.ordinal() || isEmpowered) {
                    BlockPos pos = entity.getPosition();
                    BlockState state = entity.world.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block instanceof IFluidBlock && block.getMetaFromState(state) == 0) {
                        Fluid fluid = ((IFluidBlock) block).getFluid();
                        if (fluid != null && fluid == (isEmpowered
                            ? InitFluids.fluidCrystalOil
                            : InitFluids.fluidRefinedCanolaOil)) {
                            entity.setDead();
                            entity.world.setBlockState(pos, (isEmpowered
                                ? InitFluids.blockEmpoweredOil
                                : InitFluids.blockCrystalOil).getDefaultState());
                        }
                    }
                }
            }
        }

        return super.onEntityItemUpdate(entity);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.getItemDamage() == TheMiscItems.EMPOWERED_CANOLA_SEED.ordinal();
    }

    @Override
    public int getItemBurnTime(ItemStack stack) {
        int k = stack.getMetadata();

        if (k == TheMiscItems.TINY_CHAR.ordinal()) {
            return 200;
        }
        if (k == TheMiscItems.TINY_COAL.ordinal()) {
            return 200;
        }
        if (k == TheMiscItems.BIOCOAL.ordinal()) {
            return 800;
        }

        return super.getItemBurnTime(stack);
    }
}
