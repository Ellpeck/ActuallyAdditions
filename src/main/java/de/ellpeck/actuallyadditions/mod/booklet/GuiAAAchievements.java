/*
 * This file ("GuiAAAchievements.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.stats.StatFileWriter;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

/**
 * (Partially excerpted from Botania by Vazkii with permission, thanks!)
 */
public class GuiAAAchievements extends GuiAchievements{

    public GuiAAAchievements(GuiScreen screen, StatFileWriter writer){
        super(screen, writer);
        try{
            ReflectionHelper.setPrivateValue(GuiAchievements.class, this, InitAchievements.pageNumber, 20);
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Something went wrong trying to open the Achievements GUI!", e);
        }
    }

    @Override
    public void initGui(){
        super.initGui();
        this.buttonList.get(1).displayString = InitAchievements.theAchievementPage.getName();
    }
}
