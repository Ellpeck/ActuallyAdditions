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
