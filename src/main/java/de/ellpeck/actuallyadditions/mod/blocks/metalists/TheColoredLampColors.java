package de.ellpeck.actuallyadditions.mod.blocks.metalists;

import net.minecraft.util.IStringSerializable;

public enum TheColoredLampColors implements IStringSerializable {

    WHITE("White", "white"),
    ORANGE("Orange", "orange"),
    MAGENTA("Magenta", "magenta"),
    LIGHT_BLUE("LightBlue", "light_blue"),
    YELLOW("Yellow", "yellow"),
    LIME("Lime", "lime"),
    PINK("Pink", "pink"),
    GRAY("Gray", "gray"),
    LIGHT_GRAY("LightGray", "light_gray"),
    CYAN("Cyan", "cyan"),
    PURPLE("Purple", "purple"),
    BLUE("Blue", "blue"),
    BROWN("Brown", "brown"),
    GREEN("Green", "green"),
    RED("Red", "red"),
    BLACK("Black", "black");

    public final String regName;
    public final String oreName;

    TheColoredLampColors(String oreName, String regName) {
        this.oreName = oreName;
        this.regName = regName;
    }

    public static TheColoredLampColors getColorFromDyeName(String color) {
        if (color.startsWith("dye")) {
            String actualName = color.substring(3);
            for (int i = 0; i < values().length; i++) {
                String aName = values()[i].oreName;
                if (aName != null) {
                    if (aName.equalsIgnoreCase(actualName)) { return values()[i]; }
                }
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.regName;
    }
}
