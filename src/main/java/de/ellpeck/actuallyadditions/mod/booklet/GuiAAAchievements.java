/*
 * This file ("GuiAAAchievements.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.booklet;

import de.ellpeck.actuallyadditions.mod.achievement.InitAchievements;
import net.minecraft.client.gui.GuiButton;
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
        ReflectionHelper.setPrivateValue(GuiAchievements.class, this, InitAchievements.pageNumber, 21);
    }

    @Override
    public void initGui(){
        super.initGui();
        ((GuiButton)buttonList.get(1)).displayString = InitAchievements.theAchievementPage.getName();
    }
}
