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
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.config.CommonConfig;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.inventory.gui.EnergyDisplay;
import de.ellpeck.actuallyadditions.mod.items.DrillItem;
import de.ellpeck.actuallyadditions.mod.items.ItemDrillUpgrade;
import de.ellpeck.actuallyadditions.mod.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.client.gui.overlay.GuiOverlayManager;
import net.neoforged.neoforge.event.TickEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

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
        //                    //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.oredictName.desc") + ":");
        //                    //                    if (oreIDs.length > 0) {
        //                    //                        for (int oreID : oreIDs) {
        //                    //                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + OreDictionary.getOreName(oreID));
        //                    //                        }
        //                    //                    } else {
        //                    //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + StringUtil.localize("tooltip.actuallyadditions.noOredictNameAvail.desc"));
        //                    //                    }
        //
        //                    //Code Name
        //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.codeName.desc") + ":");
        //                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + Item.REGISTRY.getNameForObject(event.getItemStack().getItem()));
        //
        //                    //Base Item's Unlocalized Name
        //                    String baseName = event.getItemStack().getItem().getTranslationKey();
        //                    if (baseName != null) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.baseUnlocName.desc") + ":");
        //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + baseName);
        //                    }
        //
        //                    //Metadata
        //                    int meta = event.getItemStack().getItemDamage();
        //                    int max = event.getItemStack().getMaxDamage();
        //                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.meta.desc") + ":");
        //                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + meta + (max > 0
        //                        ? "/" + max
        //                        : ""));
        //
        //                    //Unlocalized Name
        //                    String metaName = event.getItemStack().getItem().getTranslationKey(event.getItemStack());
        //                    if (metaName != null && baseName != null && !metaName.equals(baseName)) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.unlocName.desc") + ":");
        //                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + metaName);
        //                    }
        //
        //                    //NBT
        //                    CompoundNBT compound = event.getItemStack().getTagCompound();
        //                    if (compound != null && !compound.isEmpty()) {
        //                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE + StringUtil.localize("tooltip.actuallyadditions.nbt.desc") + ":");
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
        //                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE + TextFormatting.ITALIC + "[" + StringUtil.localize("tooltip.actuallyadditions.pressShift.desc") + "]");
        //                        }
        //                    }
        //
        //                    //Disabling Info
        //                    event.getToolTip().add(TextFormatting.ITALIC + StringUtil.localize("tooltip.actuallyadditions.disablingInfo.desc"));
        //
        //                } else {
        //                    if (ConfigBoolValues.CTRL_INFO_FOR_EXTRA_INFO.isEnabled()) {
        //                        event.getToolTip().add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + StringUtil.localize("tooltip.actuallyadditions.ctrlForMoreInfo.desc"));
        //                    }
        //                }
        //            }
        //        }
    }

    @SubscribeEvent
    public void onGameOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.getOverlay() == GuiOverlayManager.findOverlay(new ResourceLocation("portal")) && minecraft.screen == null) { //ALL
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
                        Component component = Component.translatable("info.actuallyadditions.redstoneMode").append(": ")
                                .append(Component.translatable("info.actuallyadditions.redstoneMode." + (base.isPulseMode
                            ? "pulse"
                            : "deactivation")).withStyle(ChatFormatting.DARK_RED));
                        guiGraphics.drawString(font, component, (int) (event.getWindow().getGuiScaledWidth() / 2f + 5), (int) (event.getWindow().getGuiScaledHeight() / 2f + 5), 0xFFFFFF);

                        Component expl;
                        if (!stack.isEmpty() && stack.getItem() == CommonConfig.Other.redstoneConfigureItem) {
                            expl = Component.translatable("info.actuallyadditions.redstoneMode.validItem").withStyle(ChatFormatting.GREEN);
                        } else {
                            expl = Component.translatable("info.actuallyadditions.redstoneMode.invalidItem", Component.translatable(CommonConfig.Other.redstoneConfigureItem.asItem().getDescriptionId()).getString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC);
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

    @SubscribeEvent
    public void renderBlockHighlight(RenderHighlightEvent.Block event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (mc.player == null)
            return;

        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() instanceof DrillItem drillItem) {
            if (!player.isShiftKeyDown() && drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                Level level = player.level();
                Vec3 vec3 = event.getCamera().getPosition();
                double d0 = vec3.x();
                double d1 = vec3.y();
                double d2 = vec3.z();
                BlockHitResult blockHitResult = event.getTarget();
                BlockPos targetPos = blockHitResult.getBlockPos();
                BlockState blockState = level.getBlockState(targetPos);
                VertexConsumer lineConsumer = event.getMultiBufferSource().getBuffer(RenderType.lines());
                if (stack.isCorrectToolForDrops(blockState)) {
                    int radius = 0;
                    if (drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.FIVE_BY_FIVE)) {
                        radius = 2;
                    } else if (drillItem.getHasUpgrade(stack, ItemDrillUpgrade.UpgradeType.THREE_BY_THREE)) {
                        radius = 1;
                    }
                    if (radius == 0) return; //No radius, no need to render extra hitboxes

                    List<BlockPos> coords = drillItem.gatherBreakingPositions(stack, radius, level, targetPos, blockHitResult.getDirection(), player);
                    for (BlockPos blockPos : coords) {
                        if (blockPos.equals(targetPos)) continue; //Let the original event draw this one!
                        AssetUtil.renderHitOutline(event.getPoseStack(), lineConsumer, player, d0, d1, d2, level, blockPos, level.getBlockState(blockPos));
                    }
                }
            }
        }
    }



/*    @SubscribeEvent //TODO someday move the laser rendering to a new system
    public void onRenderStage(final RenderLevelStageEvent event) {
        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_SOLID_BLOCKS) {
            AssetUtil.renderLaser();
        }
    }*/

}
