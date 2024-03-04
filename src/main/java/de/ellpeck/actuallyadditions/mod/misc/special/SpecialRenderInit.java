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

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
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

    // TODO: [port][note] ensure that this still works with the special people stuff
    public static void parse(Properties properties) {
        for (String key : properties.stringPropertyNames()) {
            String[] values = properties.getProperty(key).split("@");
            if (values.length > 0) {
                String itemName = values[0];

                int meta;
                try {
                    meta = Integer.parseInt(values[1]);
                } catch (Exception e) {
                    meta = 0;
                }

                // TODO: remove tolowercase hack
                ResourceLocation resLoc = new ResourceLocation(itemName.toLowerCase());
                ItemStack stack = findItem(resLoc);

                //TODO Remove this block once the transition to 1.11 is done and the special people stuff file has been converted to snake_case
                if (!StackUtil.isValid(stack)) {
                    String convertedItemName = "";
                    for (char c : itemName.toCharArray()) {
                        if (Character.isUpperCase(c)) {
                            convertedItemName += "_";
                            convertedItemName += Character.toLowerCase(c);
                        } else {
                            convertedItemName += c;
                        }
                    }
                    stack = findItem(new ResourceLocation(convertedItemName));
                }

                if (StackUtil.isValid(stack)) {
                    SPECIAL_LIST.put(key.toLowerCase(Locale.ROOT), new RenderSpecial(stack));
                }
            }
        }
    }

    private static ItemStack findItem(ResourceLocation resLoc) {
        if (BuiltInRegistries.ITEM.containsKey(resLoc)) {
            Item item = BuiltInRegistries.ITEM.get(resLoc);
            if (item != null) {
                return new ItemStack(item);
            }
        } else if (BuiltInRegistries.BLOCK.containsKey(resLoc)) {
            Block block = BuiltInRegistries.BLOCK.get(resLoc);
            if (block != null) {
                return new ItemStack(block);
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
