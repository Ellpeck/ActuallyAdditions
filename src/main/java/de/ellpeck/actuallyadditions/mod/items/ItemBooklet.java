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
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementManager;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
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
        super(ActuallyItems.defaultProps().stacksTo(1));
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
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
        return ActionResultType.FAIL;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            PlayerAdvancements advancements = serverPlayer.getAdvancements();
            AdvancementManager manager = player.getServer().getAdvancements();
            Advancement advancement = manager.getAdvancement(new ResourceLocation(ActuallyAdditions.MODID, "root"));
            if (advancement != null && !advancements.getOrStartProgress(advancement).isDone()) {
                advancements.award(advancement, "right_click");
            }
        }
//        player.openGui(ActuallyAdditions.INSTANCE, GuiHandler.GuiTypes.BOOK.ordinal(), world, (int) player.posX, (int) player.posY, (int) player.posZ);
//
//        if (!world.isClientSide) {
//            //TheAchievements.OPEN_BOOKLET.get(player);
//            //TheAchievements.OPEN_BOOKLET_MILESTONE.get(player);
//        }
        return ActionResult.success(player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        tooltip.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + "." + this.getDescription().getString() + ".desc"));

        // TODO: this is bad
        for (int i = 1; i <= 4; i++) {
            tooltip.add(new TranslationTextComponent("tooltip." + ActuallyAdditions.MODID + "." + this.getDescription().getString() + ".sub." + i).withStyle(i == 4
                    ? TextFormatting.GOLD
                    : TextFormatting.RESET).withStyle(i == 4
                    ? TextFormatting.ITALIC
                    : TextFormatting.RESET));
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void displayHud(MatrixStack matrices, Minecraft minecraft, PlayerEntity player, ItemStack stack, RayTraceResult rayCast, MainWindow resolution) {
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
//                        String strg2 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.page") + " " + (page.getChapter().getPageIndex(page) + 1);
//                        String strg3 = StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.open");
//
//                        AssetUtil.renderStackToGui(StackUtil.isValid(page.getChapter().getDisplayItemStack())
//                            ? page.getChapter().getDisplayItemStack()
//                            : new ItemStack(ActuallyItems.ITEM_BOOKLET.get()), resolution.getGuiScaledWidth() / 2 - 10, height + 41, 1F);
//                        minecraft.font.drawShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg1, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg1) / 2, height + 20, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(TextFormatting.YELLOW + "" + TextFormatting.ITALIC + strg2, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg2) / 2, height + 30, StringUtil.DECIMAL_COLOR_WHITE);
//                        minecraft.font.drawShadow(TextFormatting.GOLD + strg3, resolution.getGuiScaledWidth() / 2 - minecraft.font.width(strg3) / 2, height + 60, StringUtil.DECIMAL_COLOR_WHITE);
//                    } else {
//                        String strg1 = TextFormatting.DARK_RED + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo");
//                        String strg2 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo.desc.1");
//                        String strg3 = TextFormatting.DARK_GREEN + "" + TextFormatting.ITALIC + StringUtil.localize("info." + ActuallyAdditions.MODID + ".booklet.hudDisplay.noInfo.desc.2");
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
