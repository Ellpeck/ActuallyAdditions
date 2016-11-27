/*
 * This file ("SpecialRenderInit.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.special;

import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class SpecialRenderInit{

    private static final HashMap<String, RenderSpecial> SPECIAL_LIST = new HashMap<String, RenderSpecial>();

    public SpecialRenderInit(){
        new ThreadSpecialFetcher();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void parse(Properties properties){
        for(String key : properties.stringPropertyNames()){
            String[] values = properties.getProperty(key).split("@");
            if(values.length > 0){
                String itemName = values[0];

                int meta;
                try{
                    meta = Integer.parseInt(values[1]);
                }
                catch(Exception e){
                    meta = 0;
                }

                ItemStack stack = StackUtil.getNull();
                //Get the Item from the String
                ResourceLocation resLoc = new ResourceLocation(itemName);
                if(Item.REGISTRY.containsKey(resLoc)){
                    stack = new ItemStack(Item.REGISTRY.getObject(resLoc), 1, meta);
                }
                else{
                    if(Block.REGISTRY.containsKey(resLoc)){
                        stack = new ItemStack(Block.REGISTRY.getObject(resLoc), 1, meta);
                    }
                }

                //Add a new Special Renderer to the list
                if(StackUtil.isValid(stack)){
                    SPECIAL_LIST.put(key.toLowerCase(Locale.ROOT), new RenderSpecial(stack));
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerRender(RenderPlayerEvent.Pre event){
        if(event.getEntityPlayer() != null){
            String name = event.getEntityPlayer().getName();
            if(name != null){
                String lower = name.toLowerCase(Locale.ROOT);
                if(SPECIAL_LIST.containsKey(lower)){
                    RenderSpecial render = SPECIAL_LIST.get(lower);
                    if(render != null){
                        render.render(event.getEntityPlayer(), event.getPartialRenderTick());
                    }
                }
            }
        }
    }

}
