/*
 * This file ("TooltipEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.config.values.ConfigBoolValues;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

public class TooltipEvent{

    private static final String ADVANCED_INFO_TEXT_PRE = EnumChatFormatting.DARK_GRAY+"     ";
    private static final String ADVANCED_INFO_HEADER_PRE = EnumChatFormatting.GRAY+"  -";

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event){
        //Advanced Item Info
        if(event.itemStack.getItem() != null){
            if(ConfigBoolValues.CTRL_EXTRA_INFO.isEnabled()){
                if(KeyUtil.isControlPressed()){
                    event.toolTip.add(EnumChatFormatting.DARK_GRAY+""+EnumChatFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".extraInfo.desc")+":");

                    //OreDict Names
                    int[] oreIDs = OreDictionary.getOreIDs(event.itemStack);
                    event.toolTip.add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".oredictName.desc")+":");
                    if(oreIDs.length > 0){
                        for(int oreID : oreIDs){
                            event.toolTip.add(ADVANCED_INFO_TEXT_PRE+OreDictionary.getOreName(oreID));
                        }
                    }
                    else{
                        event.toolTip.add(ADVANCED_INFO_TEXT_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".noOredictNameAvail.desc"));
                    }

                    //Code Name
                    event.toolTip.add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".codeName.desc")+":");
                    event.toolTip.add(ADVANCED_INFO_TEXT_PRE+Item.itemRegistry.getNameForObject(event.itemStack.getItem()));

                    //Base Item's Unlocalized Name
                    String baseName = event.itemStack.getItem().getUnlocalizedName();
                    if(baseName != null){
                        event.toolTip.add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".baseUnlocName.desc")+":");
                        event.toolTip.add(ADVANCED_INFO_TEXT_PRE+baseName);
                    }

                    //Unlocalized Name
                    String metaName = event.itemStack.getItem().getUnlocalizedName(event.itemStack);
                    if(metaName != null && baseName != null && !metaName.equals(baseName)){
                        event.toolTip.add(ADVANCED_INFO_HEADER_PRE+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".unlocName.desc")+":");
                        event.toolTip.add(ADVANCED_INFO_TEXT_PRE+metaName);
                    }

                    //Disabling Info
                    event.toolTip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(EnumChatFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".disablingInfo.desc"), 200));
                }
                else{
                    if(ConfigBoolValues.CTRL_INFO_FOR_EXTRA_INFO.isEnabled()){
                        event.toolTip.add(EnumChatFormatting.DARK_GRAY+""+EnumChatFormatting.ITALIC+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".ctrlForMoreInfo.desc"));
                    }
                }
            }
        }
    }
}
