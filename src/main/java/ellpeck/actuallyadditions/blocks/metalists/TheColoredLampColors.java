/*
 * This file ("TheColoredLampColors.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.util.IActAddItemOrBlock;

public enum TheColoredLampColors implements IActAddItemOrBlock{

    WHITE("White"),
    ORANGE("Orange"),
    MAGENTA("Magenta"),
    LIGHT_BLUE("LightBlue"),
    YELLOW("Yellow"),
    LIME("Lime"),
    PINK("Pink"),
    GRAY("Gray"),
    LIGHT_GRAY("LightGray"),
    CYAN("Cyan"),
    PURPLE("Purple"),
    BLUE("Blue"),
    BROWN("Brown"),
    GREEN("Green"),
    RED("Red"),
    BLACK("Black");

    public String name;

    TheColoredLampColors(String name){
        this.name = name;
    }

    public static TheColoredLampColors getColorFromDyeName(String color){
        if(color.substring(0, 3).equals("dye")){
            String actualName = color.substring(3);
            for(int i = 0; i < values().length; i++){
                if(values()[i].getName().equals(actualName)){
                    return values()[i];
                }
            }
        }
        return null;
    }

    @Override
    public String getName(){
        return name;
    }
}
