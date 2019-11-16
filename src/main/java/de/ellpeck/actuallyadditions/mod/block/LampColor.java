/*
 * This file ("TheColoredLampColors.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.block;

import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.Tags;

public enum LampColor {

	WHITE(Tags.Items.DYES_WHITE),
	ORANGE(Tags.Items.DYES_ORANGE),
	MAGENTA(Tags.Items.DYES_MAGENTA),
	LIGHT_BLUE(Tags.Items.DYES_LIGHT_BLUE),
	YELLOW(Tags.Items.DYES_YELLOW),
	LIME(Tags.Items.DYES_LIME),
	PINK(Tags.Items.DYES_PINK),
	GRAY(Tags.Items.DYES_GRAY),
	LIGHT_GRAY(Tags.Items.DYES_LIGHT_GRAY),
	CYAN(Tags.Items.DYES_CYAN),
	PURPLE(Tags.Items.DYES_PURPLE),
	BLUE(Tags.Items.DYES_BLUE),
	BROWN(Tags.Items.DYES_BROWN),
	GREEN(Tags.Items.DYES_GREEN),
	RED(Tags.Items.DYES_RED),
	BLACK(Tags.Items.DYES_BLACK);

	Tag<Item> tag;

	LampColor(Tag<Item> tag) {
		this.tag = tag;
	}

	private static final LampColor[] values = values();

	@Nullable
	public static LampColor getColorFromStack(ItemStack stack) {
		for (LampColor c : values) {
			if (c.tag.contains(stack.getItem())) return c;
		}
		return null;
	}

}