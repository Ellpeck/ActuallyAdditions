/*
 * This file ("GuiAAAchievements.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.inventory.gui.booklet;

import cpw.mods.fml.relauncher.ReflectionHelper;
import ellpeck.actuallyadditions.achievement.InitAchievements;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.stats.StatFileWriter;

public class GuiAAAchievements extends GuiAchievements{

    public GuiAAAchievements(GuiScreen screen, StatFileWriter writer){
        super(screen, writer);
    }

    /**
     * (Partially excerpted from Botania with permission, thanks!)
     */
    @Override
    public void initGui(){
        super.initGui();
        ReflectionHelper.setPrivateValue(GuiAchievements.class, this, InitAchievements.pageNumber, 21);
        ((GuiButton)buttonList.get(1)).displayString = InitAchievements.theAchievementPage.getName();
    }
}
