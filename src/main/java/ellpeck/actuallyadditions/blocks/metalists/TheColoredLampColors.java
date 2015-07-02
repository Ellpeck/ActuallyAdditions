package ellpeck.actuallyadditions.blocks.metalists;

import ellpeck.actuallyadditions.util.INameableItem;

public enum TheColoredLampColors implements INameableItem{

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

    @Override
    public String getName(){
        return name;
    }

    public static TheColoredLampColors getColorFromDyeName(String color){
        if(color.substring(0, 3).equals("dye")){
            String actualName = color.substring(3);
            for(int i = 0; i < values().length; i++){
                if(values()[i].getName().equals(actualName)) return values()[i];
            }
        }
        return null;
    }
}
