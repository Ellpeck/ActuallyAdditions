///*
// * This file ("PageTrials.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.page;
//
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.data.PlayerData;
//import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
//import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//public class PageTrials extends BookletPage {
//
//    private final int buttonId;
//    @OnlyIn(Dist.CLIENT)
//    private Button button;
//
//    public PageTrials(int localizationKey, boolean button, boolean text) {
//        super(localizationKey);
//
//        if (!text) {
//            this.setNoText();
//        }
//
//        if (button) {
//            this.buttonId = PageLinkButton.nextButtonId;
//            PageLinkButton.nextButtonId++;
//        } else {
//            this.buttonId = -1;
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void init(GuiBookletBase gui, int startX, int startY) {
//        super.init(gui, startX, startY);
//
//        if (this.buttonId >= 0) {
//            this.button = new Button(this.buttonId, startX + 125 / 2 - 50, startY + 120, 100, 20, "");
//            gui.getButtonList().add(this.button);
//            this.updateButton();
//        }
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void drawScreenPre(GuiBookletBase gui, int startX, int startY, int mouseX, int mouseY, float partialTicks) {
//        super.drawScreenPre(gui, startX, startY, mouseX, mouseY, partialTicks);
//        PageTextOnly.renderTextToPage(gui, this, startX + 6, startY + 5);
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    protected String getLocalizationKey() {
//        return "booklet.actuallyadditions.trials." + this.chapter.getIdentifier() + ".text." + this.localizationKey;
//    }
//
//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void actionPerformed(GuiBookletBase gui, Button button) {
//        if (this.buttonId >= 0 && button.id == this.buttonId) {
//            PlayerEntity player = Minecraft.getInstance().player;
//            PlayerSave data = PlayerData.getDataFromPlayer(player);
//            String id = this.chapter.getIdentifier();
//
//            boolean completed = data.completedTrials.contains(id);
//            if (completed) {
//                data.completedTrials.remove(id);
//            } else {
//                data.completedTrials.add(id);
//            }
//            this.updateButton();
//
//            PacketHandlerHelper.sendPlayerDataToServer(false, 2);
//        } else {
//            super.actionPerformed(gui, button);
//        }
//    }
//
//    @OnlyIn(Dist.CLIENT)
//    private void updateButton() {
//        if (this.buttonId >= 0 && this.button != null) {
//            PlayerEntity player = Minecraft.getInstance().player;
//            PlayerSave data = PlayerData.getDataFromPlayer(player);
//
//            boolean completed = data.completedTrials.contains(this.chapter.getIdentifier());
//            if (completed) {
//                this.button.displayString = TextFormatting.DARK_GREEN + StringUtil.localize("booklet.actuallyadditions.trialFinishButton.completed.name");
//            } else {
//                this.button.displayString = TextFormatting.DARK_RED + StringUtil.localize("booklet.actuallyadditions.trialFinishButton.uncompleted.name");
//            }
//
//        }
//    }
//}
