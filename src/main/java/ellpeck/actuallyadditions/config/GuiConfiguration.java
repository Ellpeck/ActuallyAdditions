/*
 * This file ("GuiConfiguration.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.config;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiConfiguration extends GuiConfig{

    public GuiConfiguration(GuiScreen parentScreen){
        super(parentScreen, getConfigElements(), ModUtil.MOD_ID, false, false, ModUtil.NAME);
    }

    private static List<IConfigElement> getConfigElements(){
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        for(int i = 0; i < ConfigCategories.values().length; i++){
            list.add(new ConfigElement<ConfigCategory>(ConfigurationHandler.config.getCategory(ConfigCategories.values()[i].name.toLowerCase())));
        }
        return list;
    }
}
