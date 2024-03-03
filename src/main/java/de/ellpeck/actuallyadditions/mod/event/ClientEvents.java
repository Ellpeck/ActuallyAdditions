/*
 * This file ("ClientEvents.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.inventory.gui.EnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    private static final Component ADVANCED_INFO_TEXT_PRE = Component.literal("  -").withStyle(ChatFormatting.DARK_GRAY);
    private static final Component ADVANCED_INFO_HEADER_PRE = Component.literal("  -").withStyle(ChatFormatting.GRAY);

    private static EnergyDisplay energyDisplay;

    // TODO: [port] the fuck?
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.level == null) {
                WorldData.clear();
            }
        }
    }

    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event) {
        // TODO: [port] ADD BACK AS NEEDED

        //        ItemStack stack = event.getItemStack();
        //        if (StackUtil.isValid(stack)) {
        //            //Be da bland
        //            if (ConfigBoolValues.MOST_BLAND_PERSON_EVER.isEnabled()) {
        //                ResourceLocation regName = stack.getItem().getRegistryName();
        //                if (regName != null) {
        //                    if (regName.toString().toLowerCase(Locale.ROOT).contains(ActuallyAdditions.MODID)) {
        //                        if (event.getToolTip().size() > 0) {
        //                            event.getToolTip().set(0, TextFormatting.RESET + TextFormatting.WHITE.toString() + event.getToolTip().get(0));
        //                        }
        //                    }
        //                }
        //            }
        //
        //            if (ItemWingsOfTheBats.THE_BAT_BAT.equalsIgnoreCase(stack.getDisplayName()) && stack.getItem() instanceof SwordItem) {
        //                event.getToolTip().set(0, TextFormatting.GOLD + event.getToolTip().get(0));
        //                event.getToolTip().add(1, TextFormatting.RED.toString() + TextFormatting.ITALIC + "That's a really bat pun");
        //            }
        //        }
        //
        //        //Advanced Item Info
        //        if (event.getFlags().isAdvanced() && StackUtil.isValid(event.getItemStack())) {
        //            if (ConfigBoolValues.CTRL_EXTRA_INFO.isEnabled()) {
        //                if (Screen.hasControlDown()) {
        //                    event.getToolTip().add(Help.Trans("tooltip.", ".extraInfo.desc").mergeStyle(TextFormatting.DARK_GRAY).mergeStyle(TextFormatting.ITALIC).append(new StringTextComponent(":")));
        //
        //                    // TODO: [port] come back to this and see if we can re-add it
        //                    //OreDict Names
        //                    //                    int[] oreIDs = OreDictionary.getOreIDs(event.getItemStack());
        //                    //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".oredictName.desc") + ":");
        //                    //                    if (oreIDs.length > 0) {
        //                    //                        for (int oreID : oreIDs) {
        //                    //                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + OreDictionary.getOreName(oreID));
        //                    //                        }
        //                    //                    } else {
        //                    //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".noOredictNameAvail.desc"));
        //                    //                    }
        //
        //                    //Code Name
        //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".codeName.desc") + ":");
        //                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + Item.REGISTRY.getNameForObject(event.getItemStack().getItem()));
        //
        //                    //Base Item's Unlocalized Name
        //                    String baseName = event.getItemStack().getItem().getTranslationKey();
        //                    if (baseName != null) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".baseUnlocName.desc") + ":");
        //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + baseName);
        //                    }
        //
        //                    //Metadata
        //                    int meta = event.getItemStack().getItemDamage();
        //                    int max = event.getItemStack().getMaxDamage();
        //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".meta.desc") + ":");
        //                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + meta + (max > 0
        //                        ? "/" + max
        //                        : ""));
        //
        //                    //Unlocalized Name
        //                    String metaName = event.getItemStack().getItem().getTranslationKey(event.getItemStack());
        //                    if (metaName != null && baseName != null && !metaName.equals(baseName)) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".unlocName.desc") + ":");
        //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + metaName);
        //                    }
        //
        //                    //NBT
        //                    CompoundNBT compound = event.getItemStack().getTagCompound();
        //                    if (compound != null && !compound.isEmpty()) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".nbt.desc") + ":");
        //                        if (Screen.hasShiftDown()) {
        //                            int limit = ConfigIntValues.CTRL_INFO_NBT_CHAR_LIMIT.getValue();
        //                            String compoundStrg = compound.toString();
        //                            int compoundStrgLength = compoundStrg.length();
        //
        //                            String compoundDisplay;
        //                            if (limit > 0 && compoundStrgLength > limit) {
        //                                compoundDisplay = compoundStrg.substring(0, limit) + TextFormatting.GRAY + " (" + (compoundStrgLength - limit) + " more characters...)";
        //                            } else {
        //                                compoundDisplay = compoundStrg;
        //                            }
        //                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + compoundDisplay);
        //                        } else {
        //                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + TextFormatting.ITALIC + "[" + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".pressShift.desc") + "]");
        //                        }
        //                    }
        //
        //                    //Disabling Info
        //                    event.getToolTip().add(TextFormatting.ITALIC + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".disablingInfo.desc"));
        //
        //                } else {
        //                    if (ConfigBoolValues.CTRL_INFO_FOR_EXTRA_INFO.isEnabled()) {
        //                        event.getToolTip().add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + StringUtil.localize("tooltip." + ActuallyAdditions.MODID + ".ctrlForMoreInfo.desc"));
        //                    }
        //                }
        //            }
        //        }
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.getOverlay() == GuiOverlayManager.findOverlay(new ResourceLocation("crosshair")) && minecraft.screen == null) { //ALL
            GuiGraphics guiGraphics = event.getGuiGraphics();
            Player player = minecraft.player;
            if (player == null) {
                return;
            }

            HitResult posHit = minecraft.hitResult;
            Font font = minecraft.font;
            ItemStack stack = player.getMainHandItem();

            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof IHudDisplay) {
                    ((IHudDisplay) stack.getItem()).displayHud(guiGraphics, minecraft, player, stack, posHit, event.getWindow());
                }
            }

            if (posHit != null && posHit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult rayCast = (BlockHitResult) posHit;
                Block blockHit = minecraft.level.getBlockState(rayCast.getBlockPos()).getBlock();
                BlockEntity tileHit = minecraft.level.getBlockEntity(rayCast.getBlockPos());

                if (blockHit instanceof IHudDisplay) {
                    ((IHudDisplay) blockHit).displayHud(guiGraphics, minecraft, player, stack, posHit, event.getWindow());
                }

                if (tileHit instanceof TileEntityBase base) {
	                if (base.isRedstoneToggle()) {
                        String strg = String.format("%s: %s", I18n.get("info." + ActuallyAdditions.MODID + ".redstoneMode"), ChatFormatting.DARK_RED + I18n.get("info." + ActuallyAdditions.MODID + ".redstoneMode." + (base.isPulseMode
                            ? "pulse"
                            : "deactivation")) + ChatFormatting.RESET);
                        guiGraphics.drawString(font, strg, (int) (event.getWindow().getGuiScaledWidth() / 2f + 5), (int) (event.getWindow().getGuiScaledHeight() / 2f + 5), 0xFFFFFF);

                        String expl;
                        if (!stack.isEmpty() && stack.getItem() == CommonConfig.Other.redstoneConfigureItem) {
                            expl = ChatFormatting.GREEN + I18n.get("info." + ActuallyAdditions.MODID + ".redstoneMode.validItem");
                        } else {
                                expl = ChatFormatting.GRAY.toString() + ChatFormatting.ITALIC + I18n.get("info." + ActuallyAdditions.MODID + ".redstoneMode.invalidItem", I18n.get(CommonConfig.Other.redstoneConfigureItem.asItem().getDescriptionId()));
                        }
                        guiGraphics.drawString(font, expl, (int) (event.getWindow().getGuiScaledWidth() / 2f + 5), (int) (event.getWindow().getGuiScaledHeight() / 2f + 15), 0xFFFFFF);
                    }
                }

                if (tileHit instanceof IEnergyDisplay display) {
	                if (!display.needsHoldShift() || player.isShiftKeyDown()) {
                        if (energyDisplay == null) {
                            energyDisplay = new EnergyDisplay(0, 0, null);
                        }
                        energyDisplay.setData(2, event.getWindow().getGuiScaledHeight() - 96, display.getEnergyStorage(), true, true);

                        PoseStack matrices = guiGraphics.pose();
                        matrices.pushPose();
//                        GlStateManager._color4f(1F, 1F, 1F, 1F);
                        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
                        energyDisplay.draw(guiGraphics);
                        matrices.popPose();
                    }
                }
            }
        }
    }

}
