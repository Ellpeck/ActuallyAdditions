package de.ellpeck.actuallyadditions.mod.inventory.gui;

import de.ellpeck.actuallyadditions.mod.inventory.ItemTagContainer;
import de.ellpeck.actuallyadditions.mod.network.PacketClientToServer;
import de.ellpeck.actuallyadditions.mod.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ItemTagScreen extends AAScreen<ItemTagContainer> {
    private EditBox tagBox;
    private StringWidget testText;
    private boolean validTag = false;
    public ItemTagScreen(ItemTagContainer container, Inventory inventory, Component pTitle) {
        super(container, inventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();

        this.testText = new StringWidget(this.leftPos + 7, this.topPos + 27, 162, 20, Component.literal(""), font);
        this.addRenderableWidget(this.testText);

        this.tagBox = new EditBox(this.font, this.leftPos + 7, this.topPos + 7, 162, 20, Component.nullToEmpty("Tag"));
        this.tagBox.setMaxLength(128);
        this.tagBox.setCanLoseFocus(false);
        setInitialFocus(this.tagBox);
        this.tagBox.setValue("");
        this.tagBox.setResponder(this::textChanged);

        this.addRenderableWidget(this.tagBox);
    }

    @Override
    protected void renderBg(@Nonnull GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

    }

    private void textChanged(String text) {
        boolean valid = validateTag(text);

        if (valid) {
            this.tagBox.setTextColor(0x00FF00);
            TagKey<Item> tagKey = TagKey.create(Registries.ITEM, new ResourceLocation(text));
            Optional<HolderSet.Named<Item>> optionalNamed = BuiltInRegistries.ITEM.getTag(tagKey);

            if (optionalNamed.isPresent()) {
                this.testText.setMessage(Component.literal("Valid Tag"));
                this.testText.setColor(0x00FF00);
                validTag = true;
            } else {
                this.testText.setMessage(Component.literal("Invalid Tag"));
                this.testText.setColor(0xFF0000);
            }
        }
        else {
            tagBox.setTextColor(0xFF0000);
            validTag = false;
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.player.closeContainer();
        }
        if (pKeyCode == GLFW.GLFW_KEY_ENTER && validTag) {
            CompoundTag data = new CompoundTag();
            data.putInt("ButtonID", 0);
            data.putInt("PlayerID", Minecraft.getInstance().player.getId());
            data.putString("WorldID", Minecraft.getInstance().level.dimension().location().toString());
            data.putString("Tag", tagBox.getValue());
            PacketDistributor.SERVER.noArg().send(new PacketClientToServer(data, PacketHandler.GUI_BUTTON_TO_CONTAINER_HANDLER));
            this.minecraft.player.closeContainer();
        }

        return tagBox.keyPressed(pKeyCode, pScanCode, pModifiers) || tagBox.canConsumeInput() || super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    private boolean validateTag(String tag) {
        return ResourceLocation.isValidResourceLocation(tag);
        //return !tag.matches("^[a-z0-9_\\-]+:[a-z0-9_\\-]+(/[a-z0-9_\\-]+)*$");
    }
}
