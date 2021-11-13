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

import com.mojang.blaze3d.platform.GlStateManager;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.blocks.IHudDisplay;
import de.ellpeck.actuallyadditions.mod.config.ConfigValues;
import de.ellpeck.actuallyadditions.mod.data.WorldData;
import de.ellpeck.actuallyadditions.mod.inventory.gui.EnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.IEnergyDisplay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    private static final ITextComponent ADVANCED_INFO_TEXT_PRE = new StringTextComponent("  -").withStyle(TextFormatting.DARK_GRAY);
    private static final ITextComponent ADVANCED_INFO_HEADER_PRE = new StringTextComponent("  -").withStyle(TextFormatting.GRAY);

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
    public void onGameOverlay(RenderGameOverlayEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL && minecraft.screen == null) {
            PlayerEntity player = minecraft.player;
            if (player == null) {
                return;
            }

            RayTraceResult posHit = minecraft.hitResult;
            FontRenderer font = minecraft.font;
            ItemStack stack = player.getMainHandItem();

            if (StackUtil.isValid(stack)) {
                if (stack.getItem() instanceof IHudDisplay) {
                    ((IHudDisplay) stack.getItem()).displayHud(event.getMatrixStack(), minecraft, player, stack, posHit, event.getWindow());
                }
            }

            if (posHit != null && posHit.getType() == RayTraceResult.Type.BLOCK) {
                BlockRayTraceResult rayCast = (BlockRayTraceResult) posHit;
                Block blockHit = minecraft.level.getBlockState(rayCast.getBlockPos()).getBlock();
                TileEntity tileHit = minecraft.level.getBlockEntity(rayCast.getBlockPos());

                if (blockHit instanceof IHudDisplay) {
                    ((IHudDisplay) blockHit).displayHud(event.getMatrixStack(), minecraft, player, stack, posHit, event.getWindow());
                }

                if (tileHit instanceof TileEntityBase) {
                    TileEntityBase base = (TileEntityBase) tileHit;
                    if (base.isRedstoneToggle()) {
                        String strg = String.format("%s: %s", StringUtil.localize("info." + ActuallyAdditions.MODID + ".redstoneMode.name"), TextFormatting.DARK_RED + StringUtil.localize("info." + ActuallyAdditions.MODID + ".redstoneMode." + (base.isPulseMode
                            ? "pulse"
                            : "deactivation")) + TextFormatting.RESET);
                        font.drawShadow(event.getMatrixStack(), strg, event.getWindow().getGuiScaledWidth() / 2f + 5, event.getWindow().getGuiScaledHeight() / 2f + 5, StringUtil.DECIMAL_COLOR_WHITE);

                        String expl;
                        if (StackUtil.isValid(stack) && stack.getItem() == ConfigValues.itemRedstoneTorchConfigurator) {
                            expl = TextFormatting.GREEN + StringUtil.localize("info." + ActuallyAdditions.MODID + ".redstoneMode.validItem");
                        } else {
                            expl = TextFormatting.GRAY.toString() + TextFormatting.ITALIC + StringUtil.localizeFormatted("info." + ActuallyAdditions.MODID + ".redstoneMode.invalidItem", StringUtil.localize(ConfigValues.itemRedstoneTorchConfigurator.getDescriptionId() + ".name"));
                        }
                        font.drawShadow(event.getMatrixStack(), expl, event.getWindow().getGuiScaledWidth() / 2f + 5, event.getWindow().getGuiScaledHeight() / 2f + 15, StringUtil.DECIMAL_COLOR_WHITE);
                    }
                }

                if (tileHit instanceof IEnergyDisplay) {
                    IEnergyDisplay display = (IEnergyDisplay) tileHit;
                    if (!display.needsHoldShift() || player.isShiftKeyDown()) {
                        if (energyDisplay == null) {
                            energyDisplay = new EnergyDisplay(0, 0, null);
                        }
                        energyDisplay.setData(2, event.getWindow().getGuiScaledHeight() - 96, display.getEnergyStorage(), true, true);

                        GlStateManager._pushMatrix();
                        GlStateManager._color4f(1F, 1F, 1F, 1F);
                        energyDisplay.draw(event.getMatrixStack());
                        GlStateManager._popMatrix();
                    }
                }
            }
        }
    }

}
