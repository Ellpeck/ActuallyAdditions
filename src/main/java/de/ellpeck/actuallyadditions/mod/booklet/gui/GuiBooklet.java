///*
// * This file ("GuiBooklet.java") is part of the Actually Additions mod for Minecraft.
// * It is created and owned by Ellpeck and distributed
// * under the Actually Additions License to be found at
// * http://ellpeck.de/actaddlicense
// * View the source code at https://github.com/Ellpeck/ActuallyAdditions
// *
// * © 2015-2017 Ellpeck
// */
//
//package de.ellpeck.actuallyadditions.mod.booklet.gui;
//
//import com.mojang.blaze3d.matrix.MatrixStack;
//import com.mojang.blaze3d.platform.GlStateManager;
//import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
//import de.ellpeck.actuallyadditions.api.booklet.internal.GuiBookletBase;
//import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
//import de.ellpeck.actuallyadditions.mod.booklet.button.BookmarkButton;
//import de.ellpeck.actuallyadditions.mod.booklet.button.TrialsButton;
//import de.ellpeck.actuallyadditions.mod.config.values.ConfigIntValues;
//import de.ellpeck.actuallyadditions.mod.data.PlayerData;
//import de.ellpeck.actuallyadditions.mod.data.PlayerData.PlayerSave;
//import de.ellpeck.actuallyadditions.mod.inventory.gui.TexturedButton;
//import de.ellpeck.actuallyadditions.mod.network.PacketHandlerHelper;
//import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
//import de.ellpeck.actuallyadditions.mod.util.StringUtil;
//import net.minecraft.client.gui.IGuiEventListener;
//import net.minecraft.client.gui.screen.Screen;
//import net.minecraft.client.gui.widget.TextFieldWidget;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.StringTextComponent;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@OnlyIn(Dist.CLIENT)
//public abstract class GuiBooklet extends GuiBookletBase {
//
//    public static final int BUTTONS_PER_PAGE = 12;
//    public static final ResourceLocation RES_LOC_GUI = AssetUtil.getBookletGuiLocation("gui_booklet");
//    public static final ResourceLocation RES_LOC_GADGETS = AssetUtil.getBookletGuiLocation("gui_booklet_gadgets");
//    protected final BookmarkButton[] bookmarkButtons = new BookmarkButton[12];
//    public Screen previousScreen;
//    public GuiBookletBase parentPage;
//    public TextFieldWidget searchField;
//    protected int xSize;
//    protected int ySize;
//    protected int guiLeft;
//    protected int guiTop;
//    private Button buttonLeft;
//    private Button buttonRight;
//    private Button buttonBack;
//
//    private Button buttonTrials;
//
//    private float smallFontSize;
//    private float mediumFontSize;
//    private float largeFontSize;
//
//    public GuiBooklet(Screen previousScreen, GuiBookletBase parentPage) {
//        super(StringComponent.empty());
//
//        this.previousScreen = previousScreen;
//        this.parentPage = parentPage;
//
//        this.xSize = 281;
//        this.ySize = 180;
//    }
//
//    private static float getFontSize(String lang, ConfigIntValues config, float defaultValue) {
//        int conf = config.getValue();
//        if (conf <= 0) {
//            try {
//                return Float.parseFloat(StringUtil.localize("booklet.actuallyadditions.fontSize." + lang));
//            } catch (Exception e) {
//                return defaultValue;
//            }
//        } else {
//            return conf / 100F;
//        }
//    }
//
//    @Override
//    public void init() {
//        super.init();
//
//        this.guiLeft = (this.width - this.xSize) / 2;
//        this.guiTop = (this.height - this.ySize) / 2;
//
//        this.smallFontSize = getFontSize("small", ConfigIntValues.FONT_SIZE_SMALL, 0.5F);
//        this.mediumFontSize = getFontSize("medium", ConfigIntValues.FONT_SIZE_MEDIUM, 0.75F);
//        this.largeFontSize = getFontSize("large", ConfigIntValues.FONT_SIZE_LARGE, 0.8F);
//
//        if (this.hasPageLeftButton()) {
//            List<String> hoverText = Arrays.asList(TextFormatting.GOLD + "Previous Page", TextFormatting.ITALIC + "Or scroll up");
//            this.buttonLeft = new TexturedButton(RES_LOC_GADGETS, this.guiLeft - 12, this.guiTop + this.ySize - 8, 18, 54, 18, 10, hoverText, btn -> this.onPageLeftButtonPressed());
//            this.addButton(this.buttonLeft);
//        }
//
//        if (this.hasPageRightButton()) {
//            List<String> hoverText = Arrays.asList(TextFormatting.GOLD + "Next Page", TextFormatting.ITALIC + "Or scroll down");
//            this.buttonRight = new TexturedButton(RES_LOC_GADGETS, this.guiLeft + this.xSize - 6, this.guiTop + this.ySize - 8, 0, 54, 18, 10, hoverText, btn -> this.onPageRightButtonPressed());
//            this.addButton(this.buttonRight);
//        }
//
//        if (this.hasBackButton()) {
//            List<String> hoverText = Arrays.asList(TextFormatting.GOLD + "Go Back", TextFormatting.ITALIC + "Or right-click", TextFormatting.ITALIC.toString() + TextFormatting.GRAY + "Hold Shift for Main Page");
//            this.buttonBack = new TexturedButton(RES_LOC_GADGETS, this.guiLeft - 15, this.guiTop - 3, 36, 54, 18, 10, hoverText, btn -> this.onBackButtonPressed());
//            this.addButton(this.buttonBack);
//        }
//
//        if (this.hasSearchBar()) {
//            this.searchField = new TextFieldWidget(this.font, this.guiLeft + this.xSize + 2, this.guiTop + this.ySize - 40 + 2, 64, 12, StringComponent.empty());
//            this.searchField.setMaxLength(50);
//            this.searchField.setBordered(false);
//            this.children.add(this.searchField);
//        }
//
//        if (this.hasBookmarkButtons()) {
//            PlayerSave data = PlayerData.getDataFromPlayer(this.getMinecraft().player);
//
//            int xStart = this.guiLeft + this.xSize / 2 - 16 * this.bookmarkButtons.length / 2;
//            for (int i = 0; i < this.bookmarkButtons.length; i++) {
//                this.bookmarkButtons[i] = new BookmarkButton(xStart + i * 16, this.guiTop + this.ySize, this);
//                this.addButton(this.bookmarkButtons[i]);
//
//                if (data.bookmarks[i] != null) {
//                    this.bookmarkButtons[i].assignedPage = data.bookmarks[i];
//                }
//            }
//        }
//
//        this.buttonTrials = new TrialsButton(this, btn -> this.getMinecraft().setScreen(new GuiEntry(this.previousScreen, this, ActuallyAdditionsAPI.entryTrials, 0, "", false)));
//        this.addButton(this.buttonTrials);
//    }
//
//    @Override
//    public void removed() {
//        super.removed();
//
//        //Don't cache the parent GUI, otherwise it opens again when you close the cached book!
//        this.previousScreen = null;
//
//        if (this.getMinecraft().player == null) {
//            return;
//        }
//        PlayerSave data = PlayerData.getDataFromPlayer(this.getMinecraft().player);
//        data.lastOpenBooklet = this;
//
//        boolean change = false;
//        for (int i = 0; i < this.bookmarkButtons.length; i++) {
//            if (data.bookmarks[i] != this.bookmarkButtons[i].assignedPage) {
//                data.bookmarks[i] = this.bookmarkButtons[i].assignedPage;
//                change = true;
//            }
//        }
//
//        if (change) {
//            PacketHandlerHelper.sendPlayerDataToServer(true, 0);
//        }
//    }
//
//    @Override
//    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        this.drawScreenPre(matrixStack, mouseX, mouseY, partialTicks);
//        super.render(matrixStack, mouseX, mouseY, partialTicks);
//        this.drawScreenPost(matrixStack, mouseX, mouseY, partialTicks);
//    }
//
//    public void drawScreenPre(MatrixStack matrices, int mouseX, int mouseY, float partialTicks) {
//        GlStateManager._color4f(1F, 1F, 1F, 1F);
//        this.getMinecraft().getTextureManager().bind(RES_LOC_GUI);
//        blit(matrices, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512, 512);
//
//        if (this.hasSearchBar()) {
//            this.getMinecraft().getTextureManager().bind(RES_LOC_GADGETS);
//            guiGraphics.blit(matrices, this.guiLeft + this.xSize, this.guiTop + this.ySize - 40, 188, 0, 68, 14);
//
//            //            boolean unicodeBefore = this.font.getUnicodeFlag();
//            //            this.font.setUnicodeFlag(true);
//
//            if (!this.searchField.isFocused() && (this.searchField.getValue() == null || this.searchField.getValue().isEmpty())) {
//                this.font.draw(matrices, TextFormatting.ITALIC + StringUtil.localize("info.actuallyadditions.booklet.searchField"), this.guiLeft + this.xSize + 2, this.guiTop + this.ySize - 40 + 2, 0xFFFFFF);
//            }
//
//            this.searchField.render(matrices, mouseX, mouseY, partialTicks);
//
//            //            this.font.setUnicodeFlag(unicodeBefore);
//        }
//    }
//
//    public void drawScreenPost(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
//        for (IGuiEventListener button : this.children) {
//            if (button instanceof BookmarkButton) {
//                ((BookmarkButton) button).drawHover(matrixStack, mouseX, mouseY);
//            } else if (button instanceof TexturedButton) {
//                ((TexturedButton) button).drawHover(matrixStack, mouseX, mouseY);
//            }
//        }
//    }
//
//    @Override
//    public boolean mouseClicked(double mouseX, double mouseY, int button) {
//        return super.mouseClicked(mouseX, mouseY, button);
//    }
//
//    // TODO: Ensure replacement works
//    @Override
//    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
//        if (delta < 0) {
//            if (this.hasPageRightButton()) {
//                this.onPageRightButtonPressed();
//            }
//        } else if (delta > 0) {
//            if (this.hasPageLeftButton()) {
//                this.onPageLeftButtonPressed();
//            }
//        }
//        return super.mouseScrolled(mouseX, mouseY, delta);
//    }
//
//    //    @Override
//    //    public void handleMouseInput() throws IOException {
//    //        int wheel = Mouse.getEventDWheel();
//    //        if (wheel != 0) {
//    //            if (wheel < 0) {
//    //                if (this.hasPageRightButton()) {
//    //                    this.onPageRightButtonPressed();
//    //                }
//    //            } else if (wheel > 0) {
//    //                if (this.hasPageLeftButton()) {
//    //                    this.onPageLeftButtonPressed();
//    //                }
//    //            }
//    //        }
//    //        super.handleMouseInput();
//    //    }
//
//
//    @Override
//    public void tick() {
//        if (this.hasSearchBar()) {
//            this.searchField.tick();
//        }
//    }
//
//    @Override
//    public boolean isPauseScreen() {
//        return false;
//    }
//
//    public boolean hasPageLeftButton() {
//        return false;
//    }
//
//    public void onPageLeftButtonPressed() {
//
//    }
//
//    public boolean hasPageRightButton() {
//        return false;
//    }
//
//    public void onPageRightButtonPressed() {
//
//    }
//
//    public boolean areTrialsOpened() {
//        return false;
//    }
//
//    public boolean hasBackButton() {
//        return false;
//    }
//
//    public void onBackButtonPressed() {
//        this.getMinecraft().setScreen(new GuiMainPage(this.previousScreen));
//    }
//
//    public boolean hasSearchBar() {
//        return true;
//    }
//
//    public boolean hasBookmarkButtons() {
//        return true;
//    }
//
//    @Override
//    public float getSmallFontSize() {
//        return this.smallFontSize;
//    }
//
//    @Override
//    public float getMediumFontSize() {
//        return this.mediumFontSize;
//    }
//
//    @Override
//    public float getLargeFontSize() {
//        return this.largeFontSize;
//    }
//
//    // TODO: Check if not being used
//    public void onSearchBarChanged(String searchBarText) {
//        GuiBookletBase parent = !(this instanceof GuiEntry)
//            ? this
//            : this.parentPage;
//        this.getMinecraft().setScreen(new GuiEntry(this.previousScreen, parent, ActuallyAdditionsAPI.entryAllAndSearch, 0, searchBarText, true));
//    }
//
//    // TODO: ensure typing still works
//
//    //    @Override
//    //    protected void keyTyped(char typedChar, int key) throws IOException {
//    //        if (key == Keyboard.KEY_ESCAPE || key == this.mc.gameSettings.keyBindInventory.getKeyCode() && (!this.hasSearchBar() || !this.searchField.isFocused())) {
//    //            this.mc.displayGuiScreen(this.previousScreen);
//    //        } else if (this.hasSearchBar() & this.searchField.isFocused()) {
//    //            String lastText = this.searchField.getText();
//    //
//    //            this.searchField.textboxKeyTyped(typedChar, key);
//    //
//    //            if (!lastText.equals(this.searchField.getText())) {
//    //                this.onSearchBarChanged(this.searchField.getText());
//    //            }
//    //        } else {
//    //            super.keyTyped(typedChar, key);
//    //        }
//    //    }
//
//    @Override
//    public void renderScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale) {
//        StringUtil.renderScaledAsciiString(this.font, text, x, y, color, shadow, scale);
//    }
//
//    @Override
//    public void renderSplitScaledAsciiString(String text, int x, int y, int color, boolean shadow, float scale, int length) {
//        StringUtil.renderSplitScaledAsciiString(this.font, text, x, y, color, shadow, scale, length);
//    }
//
//    @Override
//    public List<IGuiEventListener> getButtonList() {
//        return this.children.stream().filter(e -> e instanceof Button).collect(Collectors.toList());
//    }
//
//    @Override
//    public int getGuiLeft() {
//        return this.guiLeft;
//    }
//
//    @Override
//    public int getGuiTop() {
//        return this.guiTop;
//    }
//
//    @Override
//    public int getSizeX() {
//        return this.xSize;
//    }
//
//    @Override
//    public int getSizeY() {
//        return this.ySize;
//    }
//}
