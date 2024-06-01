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

import com.mojang.blaze3d.platform.Window;
import de.ellpeck.actuallyadditions.api.booklet.IBookletPage;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBooklet extends ItemBase implements IHudDisplay {

    @OnlyIn(Dist.CLIENT)
    public static IBookletPage forcedPage;

    public ItemBooklet() {
        super(ActuallyItems.defaultProps().stacksTo(1));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
//        if (context.getPlayer().isShiftKeyDown()) {
//            BlockState state = context.getLevel().getBlockState(context.getClickedPos());
//            Block block = state.getBlock();
//            ItemStack blockStack = new ItemStack(block);
//            IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
//            if (page != null) {
//                if (context.getLevel().isClientSide) {
//                    forcedPage = page;
//                }
//                this.use(context.getLevel(), context.getPlayer(), context.getHand());
//                return ActionResultType.SUCCESS;
//            }
//        }
        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            PlayerAdvancements advancements = serverPlayer.getAdvancements();
            ServerAdvancementManager manager = player.getServer().getAdvancements();
            AdvancementHolder advancement = manager.get(new ResourceLocation(ActuallyAdditions.MODID, "root"));
            if (advancement != null && !advancements.getOrStartProgress(advancement).isDone()) {
                advancements.award(advancement, "right_click");
            }
        } else {
            vazkii.patchouli.api.PatchouliAPI.get().openBookGUI(new ResourceLocation(ActuallyAdditions.MODID, "booklet"));
        }
//        player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BOOK.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//
//        if (!world.isClientSide) {
//            //TheAchievements.OPEN_BOOKLET.get(player);
//            //TheAchievements.OPEN_BOOKLET_MILESTONE.get(player);
//        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level playerIn, List<Component> tooltip, TooltipFlag advanced) {
        tooltip.add(Component.translatable("tooltip.actuallyadditions.item_booklet.desc").withStyle(ChatFormatting.GRAY));

        // TODO: this is bad
        for (int i = 1; i <= 4; i++) {
            tooltip.add(Component.translatable("tooltip.actuallyadditions.item_booklet.sub." + i).withStyle(i == 4
                    ? ChatFormatting.GOLD
                    : ChatFormatting.RESET).withStyle(i == 4
                    ? ChatFormatting.ITALIC
                    : ChatFormatting.RESET));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(GuiGraphics guiGraphics, Minecraft minecraft, Player player, ItemStack stack, HitResult rayCast, Window resolution) {
//        if (rayCast != null && rayCast.getBlockPos() != null) {
//            BlockState state = minecraft.level.getBlockState(rayCast.getBlockPos());
//            Block block = state.getBlock();
//            if (block != null && !block.isAir(minecraft.level.getBlockState(rayCast.getBlockPos()), minecraft.level, rayCast.getBlockPos())) {
//                ItemStack blockStack = new ItemStack(block, 1, block.getMetaFromState(state));
//                int height = resolution.getGuiScaledHeight() / 5 * 3;
//                if (player.isShiftKeyDown()) {
//                    IBookletPage page = BookletUtils.findFirstPageForStack(blockStack);
//                    if (page != null) {
//                        String strg1 = page.getChapter().getLocalizedName();
//                        String strg2 = StringUtil.localize("info.actuallyadditions.booklet.hudDisplay.page") + " " + (page.getChapter().getPageIndex(page) + 1);
//                        String strg3 = StringUtil.localize("info.actuallyadditions.booklet.hudDisplay.open");
//
//                        AssetUtil.renderStackToGui(StackUtil.isValid(page.getChapter().getDisplayItemStack())
//                            ? page.getChapter().getDisplayItemStack()
//                            : new ItemStack(ActuallyItems.ITEM_BOOKLET.get()), resolution.getGuiScaledWidth() / 2 - 10, height + 41, 1F);
//                        minecraft.font.drawShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg1, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg1) / 2, height + 20, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg2, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg2) / 2, height + 30, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(TextFormatting.GOLD + strg3, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg3) / 2, height + 60, StringUtil.DECIMAL_COLOR_WHITE);
//                    } else {
//                        String strg1 = TextFormatting.DARK_RED + StringUtil.localize("info.actuallyadditions.booklet.hudDisplay.noInfo");
//                        String strg2 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info.actuallyadditions.booklet.hudDisplay.noInfo.desc.1");
//                        String strg3 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info.actuallyadditions.booklet.hudDisplay.noInfo.desc.2");
//
//                        minecraft.font.drawShadow(strg1, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg1) / 2, height + 30, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(strg2, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg2) / 2, height + 50, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(strg3, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg3) / 2, height + 60, StringUtil.DECIMAL_COLOR_WHITE);
//                    }
//                }
//            }
//        }
    }
}
