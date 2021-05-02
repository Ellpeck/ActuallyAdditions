/*
 * This file ("ItemBooklet.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.booklet.misc.BookletUtils;
import de.ellpeck.actuallyadditions.mod.inventory.GuiHandler;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBooklet extends ItemBase implements IHudDisplay {

    @OnlyIn(Dist.CLIENT)
    public static IBookletPage forcedPage;

    public ItemBooklet() {
        super(ActuallyItems.defaultProps().maxStackSize(1));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (context.getPlayer().isSneaking()) {
            BlockState state = context.getWorld().getBlockState(context.getPos());
            Block block = state.getBlock();
            ItemStack blockStack = new ItemStack(block);
            IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
            if (page != null) {
                if (context.getWorld().isRemote) {
                    forcedPage = page;
                }
                this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand());
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BOOK.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);

        if (!world.isRemote) {
            //TheAchievements.OPEN_BOOKLET.get(player);
            //TheAchievements.OPEN_BOOKLET_MILESTONE.get(player);
        }
        return ActionResult.resultSuccess(player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        tooltip.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + "." + this.getName().getString() + ".desc"));

        // TODO: this is bad
        for (int i = 1; i <= 4; i++) {
            tooltip.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + "." + this.getName().getString() + ".sub." + i).mergeStyle(i == 4
                ? TextFormatting.GOLD
                : TextFormatting.RESET).mergeStyle(i == 4
                ? TextFormatting.ITALIC
                : TextFormatting.RESET));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
        if (rayCast != null && rayCast.getBlockPos() != null) {
            BlockState state = minecraft.world.getBlockState(rayCast.getBlockPos());
            Block block = state.getBlock();
            if (block != null && !block.isAir(minecraft.world.getBlockState(rayCast.getBlockPos()), minecraft.world, rayCast.getBlockPos())) {
                ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
                int height = resolution.getScaledHeight() / 5 * 3;
                if (player.isSneaking()) {
                    IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
                    if (page != null) {
                        String strg1 = page.getChapter().getLocalizedName();
                        String strg2 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.page") + " " + (page.getChapter().getPageIndex(page) + 1);
                        String strg3 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.open");

                        AssetUtil.renderStackToGui(StackUtil.isValid(page.getChapter().getDisplayItemStack())
                            ? page.getChapter().getDisplayItemStack()
                            : new ItemStack(ActuallyItems.ITEM_BOOKLET.get()), resolution.getScaledWidth() / 2 - 10, height + 41, 1F);
                        minecraft.fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg1, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg1) / 2, height + 20, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRenderer.drawStringWithShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg2, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg2) / 2, height + 30, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRenderer.drawStringWithShadow(TextFormatting.GOLD + strg3, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg3) / 2, height + 60, StringUtil.DECIMAL_COLOR_WHITE);
                    } else {
                        String strg1 = TextFormatting.DARK_RED + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo");
                        String strg2 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo.desc.1");
                        String strg3 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo.desc.2");

                        minecraft.fontRenderer.drawStringWithShadow(strg1, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg1) / 2, height + 30, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRenderer.drawStringWithShadow(strg2, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg2) / 2, height + 50, StringUtil.DECIMAL_COLOR_WHITE);
                        minecraft.fontRenderer.drawStringWithShadow(strg3, resolution.getScaledWidth() / 2 - minecraft.fontRenderer.getStringWidth(strg3) / 2, height + 60, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                }
            }
        }
    }
}
