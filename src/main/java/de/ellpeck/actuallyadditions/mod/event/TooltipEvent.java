/*
 * This file ("TooltipEvent.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.event;

import de.ellpeck.actuallyadditions.mod.config.values.ConfigBoolValues;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class TooltipEvent{

    private static final String ADVANCED_INFO_TEXT_PRE = TextFormatting.DARK_GRAY+"     ";
    private static final String ADVANCED_INFO_HEADER_PRE = TextFormatting.GRAY+"  -";

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event){
        //Advanced Item Info
        if(event.getItemStack().getItem() != null){
            if(ConfigBoolValues.CTRL_EXTRA_INFO.isEnabled()){
                if(GuiScreen.isCtrlKeyDown()){
                    event.getToolTip().add(TextFormatting.DARK_GRAY+""+TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".extraInfo.desc")+":");

                    //OreDict Names
                    int[] oreIDs = OreDictionary.getOreIDs(event.getItemStack());
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".oredictName.desc")+":");
                    if(oreIDs.length > 0){
                        for(int oreID : oreIDs){
                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+OreDictionary.getOreName(oreID));
                        }
                    }
                    else{
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".noOredictNameAvail.desc"));
                    }

                    //Code Name
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".codeName.desc")+":");
                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+Item.REGISTRY.getNameForObject(event.getItemStack().getItem()));

                    //Base Item's Unlocalized Name
                    String baseName = event.getItemStack().getItem().getUnlocalizedName();
                    if(baseName != null){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".baseUnlocName.desc")+":");
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+baseName);
                    }

                    //Metadata
                    int meta = event.getItemStack().getItemDamage();
                    int max = event.getItemStack().getMaxDamage();
                    event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".meta.desc")+":");
                    event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+meta+(max > 0 ? "/"+max : ""));

                    //Unlocalized Name
                    String metaName = event.getItemStack().getItem().getUnlocalizedName(event.getItemStack());
                    if(metaName != null && baseName != null && !metaName.equals(baseName)){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".unlocName.desc")+":");
                        event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+metaName);
                    }

                    //NBT
                    NBTTagCompound compound = event.getItemStack().getTagCompound();
                    if(compound != null && !compound.hasNoTags()){
                        event.getToolTip().add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".nbt.desc")+":");
                        if(GuiScreen.isShiftKeyDown()){
                            List<String> strgList = Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(compound.toString(), 200);
                            for(String strg : strgList){
                                event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+strg);
                            }
                        }
                        else{
                            event.getToolTip().add(ADVANCED_INFO_TEXT_PRE+TextFormatting.ITALIC+"["+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".pressShift.desc")+"]");
                        }
                    }

                    //Disabling Info
                    event.getToolTip().addAll(Minecraft.getMinecraft().fontRendererObj.listFormattedStringToWidth(TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".disablingInfo.desc"), 200));

                }
                else{
                    if(ConfigBoolValues.CTRL_INFO_FOR_EXTRA_INFO.isEnabled()){
                        event.getToolTip().add(TextFormatting.DARK_GRAY+""+TextFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID+".ctrlForMoreInfo.desc"));
                    }
                }
            }
        }
    }
}
