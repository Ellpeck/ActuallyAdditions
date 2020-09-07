package de.ellpeck.actuallyadditions.mod.config;

import java.util.ArrayList;
import java.util.List;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiConfiguration extends GuiConfig {

    public GuiConfiguration(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), ActuallyAdditions.MODID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();
        for (int i = 0; i < ConfigCategories.values().length; i++) {
            ConfigCategories cat = ConfigCategories.values()[i];
            ConfigurationHandler.config.setCategoryComment(cat.name, cat.comment);
            list.add(new ConfigElement(ConfigurationHandler.config.getCategory(cat.name)));
        }
        return list;
    }
}
