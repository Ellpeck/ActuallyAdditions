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
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;

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

                ResourceLocation resLoc = new ResourceLocation(itemName);
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
        if (ForgeRegistries.ITEMS.containsKey(resLoc)) {
            Item item = ForgeRegistries.ITEMS.getValue(resLoc);
            if (item != null) {
                return new ItemStack(item);
            }
        } else if (ForgeRegistries.BLOCKS.containsKey(resLoc)) {
            Block block = ForgeRegistries.BLOCKS.getValue(resLoc);
            if (block != null) {
                return new ItemStack(block);
            }
        }
        return StackUtil.getEmpty();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (event.getPlayer() != null) {
            String name = event.getPlayer().getName().getString();
            String lower = name.toLowerCase(Locale.ROOT);
            if (SPECIAL_LIST.containsKey(lower)) {
                RenderSpecial render = SPECIAL_LIST.get(lower);
                if (render != null) {
                    render.render(event.getMatrixStack(), event.getBuffers(), event.getLight(), event.getPlayer(), event.getPartialRenderTick());
                }
            }
        }
    }

}
