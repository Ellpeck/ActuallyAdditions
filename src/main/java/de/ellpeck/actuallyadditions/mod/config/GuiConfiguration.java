/*
 * This file ("GuiConfiguration.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.config;

import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiConfiguration extends GuiConfig{

    public GuiConfiguration(GuiScreen parentScreen){
        super(parentScreen, getConfigElements(), ModUtil.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    private static List<IConfigElement> getConfigElements(){
        List<IConfigElement> list = new ArrayList<IConfigElement>();
        for(int i = 0; i < ConfigCategories.values().length; i++){
            ConfigCategories cat = ConfigCategories.values()[i];
            ConfigurationHandler.config.setCategoryComment(cat.name, cat.comment);
            list.add(new ConfigElement(ConfigurationHandler.config.getCategory(cat.name)));
        }
        return list;
    }
}
