/*
 * This file ("SpecialRenderInit.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.special;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class SpecialRenderInit {

    public static final HashMap<String, RenderSpecial> SPECIAL_LIST = new HashMap<>();

    public SpecialRenderInit() {
        new ThreadSpecialFetcher();
    }

    public static void parse(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            if (!value.isEmpty()) {
                ResourceLocation resLoc = ResourceLocation.tryParse(value);
                ItemStack stack = findItem(resLoc);

                if (!stack.isEmpty()) {
                    SPECIAL_LIST.put(key.toLowerCase(Locale.ROOT), new RenderSpecial(stack));
                }
            }
        }
    }

    private static ItemStack findItem(ResourceLocation resLoc) {
        if (BuiltInRegistries.ITEM.containsKey(resLoc)) {
            var item = BuiltInRegistries.ITEM.getOptional(resLoc);
            if (item.isPresent()) {
                return new ItemStack(item.get());
            }
        } else if (BuiltInRegistries.BLOCK.containsKey(resLoc)) {
            var block = BuiltInRegistries.BLOCK.getOptional(resLoc);
            if (block.isPresent()) {
                return new ItemStack(block.get());
            }
        }
        return ItemStack.EMPTY;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (event.getEntity() != null) {
            String name = event.getEntity().getName().getString();
            String lower = name.toLowerCase(Locale.ROOT);
            if (SPECIAL_LIST.containsKey(lower)) {
                RenderSpecial render = SPECIAL_LIST.get(lower);
                if (render != null) {
                    render.render(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getEntity(), event.getPartialTick());
                }
            }
        }
    }

}
