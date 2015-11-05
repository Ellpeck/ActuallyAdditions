/*
 * This file ("Special.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc.special;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SpecialRenderInit{

    public static HashMap<String, RenderSpecial> specialList = new HashMap<String, RenderSpecial>();

    public static void init(){
        new ThreadSpecialFetcher();
        Util.registerEvent(new SpecialRenderInit());
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRender(RenderPlayerEvent.Specials.Pre event){
        if(!specialList.isEmpty()){
            for(Map.Entry<String, RenderSpecial> entry : specialList.entrySet()){
                //Does the player have one of the names from the list?
                if(StringUtil.equalsToLowerCase(entry.getKey(), event.entityPlayer.getDisplayName())){
                    //Render the special Item/Block
                    entry.getValue().render(event.entityPlayer);
                    break;
                }
            }
        }
    }

    public static void parse(Properties properties){
        for(String key : properties.stringPropertyNames()){
            String value = properties.getProperty(key);

            ItemStack stack = null;
            //Get the Item from the String
            if(Item.itemRegistry.containsKey(value)){
                stack = new ItemStack((Item)Item.itemRegistry.getObject(value));
            }
            else{
                if(Block.blockRegistry.containsKey(value)){
                    stack = new ItemStack((Block)Block.blockRegistry.getObject(value));
                }
            }

            //Add a new Special Renderer to the list
            if(stack != null){
                specialList.put(key, new RenderSpecial(stack));
            }
        }
    }

}
