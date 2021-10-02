/*
 * This file ("ItemAllToolAA.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.Sets;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.config.values.ConfigStringListValues;
import de.ellpeck.actuallyadditions.mod.items.base.ItemToolAA;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashSet;
import java.util.Set;

public class ItemAllToolAA extends ItemToolAA implements IColorProvidingItem {

    public final int color;

    public ItemAllToolAA(IItemTier toolMat) {
        super(4.0F, -2F, toolMat, this.repairItem, unlocalizedName, this.rarity, new HashSet<>());
        this.color = this.color;

        this.setMaxDamage(toolMat.getUses() * 4);
        this.setHarvestLevels(toolMat.getLevel());
    }

    private void setHarvestLevels(int amount) {
        for (String s : this.getToolClasses(null)) {
            this.setHarvestLevel(s, amount);
        }
    }

    @Override
    protected void registerRendering() {
        ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this), new ResourceLocation(ActuallyAdditions.MODID, "item_paxel"), "inventory");
    }

    @Override
    public ActionResultType onItemUse(PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, Direction side, float hitX, float hitY, float hitZ) {
        if (!playerIn.isShiftKeyDown()) {
            return Items.IRON_HOE.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
        }
        return Items.IRON_SHOVEL.onItemUse(playerIn, worldIn, pos, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public boolean canHarvestBlock(BlockState state, ItemStack stack) {
        return this.hasExtraWhitelist(state.getBlock()) || state.getMaterial().isToolNotRequired() || state.getBlock() == Blocks.SNOW_LAYER || state.getBlock() == Blocks.SNOW || (state.getBlock() == Blocks.OBSIDIAN
            ? this.toolMaterial.getHarvestLevel() >= 3
            : state.getBlock() != Blocks.DIAMOND_BLOCK && state.getBlock() != Blocks.DIAMOND_ORE
                ? state.getBlock() != Blocks.EMERALD_ORE && state.getBlock() != Blocks.EMERALD_BLOCK
                ? state.getBlock() != Blocks.GOLD_BLOCK && state.getBlock() != Blocks.GOLD_ORE
                ? state.getBlock() != Blocks.IRON_BLOCK && state.getBlock() != Blocks.IRON_ORE
                ? state.getBlock() != Blocks.LAPIS_BLOCK && state.getBlock() != Blocks.LAPIS_ORE
                ? state.getBlock() != Blocks.REDSTONE_ORE && state.getBlock() != Blocks.LIT_REDSTONE_ORE
                ? state.getMaterial() == Material.STONE || state.getMaterial() == Material.METAL || state.getMaterial() == Material.HEAVY_METAL
                : this.toolMaterial.getHarvestLevel() >= 2
                : this.toolMaterial.getHarvestLevel() >= 1
                : this.toolMaterial.getHarvestLevel() >= 1
                : this.toolMaterial.getHarvestLevel() >= 2
                : this.toolMaterial.getHarvestLevel() >= 2
                : this.toolMaterial.getHarvestLevel() >= 2);
    }

    private boolean hasExtraWhitelist(Block block) {
        String name = block.getRegistryName().toString();
        for (String list : ConfigStringListValues.PAXEL_EXTRA_MINING_WHITELIST.getValue()) {
            if (list.equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return Sets.newHashSet("pickaxe", "axe", "shovel");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getBlock() == Blocks.COBWEB) {
            return 15.0F;
        } else {
            return this.hasExtraWhitelist(state.getBlock()) || state.getBlock().getHarvestTool(state) == null || state.getBlock().getHarvestTool(state).isEmpty() || this.getToolClasses(stack).contains(state.getBlock().getHarvestTool(state))
                ? this.speed
                : 1.0F;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public IItemColor getItemColor() {
        return (stack, pass) -> pass > 0
            ? ItemAllToolAA.this.color
            : 0xFFFFFF;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Items.DIAMOND_SWORD);
    }
}
