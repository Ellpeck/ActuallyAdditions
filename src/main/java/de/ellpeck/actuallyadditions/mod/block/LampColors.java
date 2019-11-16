/*
 * This file ("TheColoredLampColors.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.block;

import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import org.codehaus.plexus.util.StringUtils;

import java.util.Collection;

public enum LampColors implements IStringSerializable {
    
    WHITE("White", "white"), ORANGE("Orange", "orange"), MAGENTA("Magenta", "magenta"), LIGHT_BLUE("LightBlue", "light_blue"), YELLOW("Yellow", "yellow"), LIME("Lime", "lime"), PINK("Pink", "pink"), GRAY("Gray", "gray"), LIGHT_GRAY("LightGray", "light_gray"), CYAN("Cyan", "cyan"), PURPLE("Purple", "purple"), BLUE("Blue", "blue"), BROWN("Brown", "brown"), GREEN("Green", "green"), RED("Red", "red"), BLACK("Black", "black");
    
    public final String regName;
    public final String oreName;
    
    LampColors(String oreName, String regName) {
        this.oreName = oreName;
        this.regName = regName;
    }
    
    public static LampColors getColorFromStack(ItemStack stack) {
        Collection<ResourceLocation> owningTags = ItemTags.getCollection().getOwningTags(stack.getItem());
        String dyeColor = "";
        for (ResourceLocation rl : owningTags) {
            String path = rl.getPath();
            if (path.contains("dyes/")) {
                dyeColor = path.substring(5);
                break;
            }
        }
        if (StringUtils.isEmpty(dyeColor)) {
            return null;
        }
        return valueOf(dyeColor.toUpperCase());
    }
    
    @Override
    public String getName() {
        return this.regName;
    }
}