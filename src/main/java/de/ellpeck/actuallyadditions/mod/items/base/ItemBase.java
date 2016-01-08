/*
 * This file ("ItemBase.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items.base;

import de.ellpeck.actuallyadditions.mod.creative.CreativeTab;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class ItemBase extends Item{

    private String name;

    public ItemBase(String name){
        this.name = name;

        this.register();
    }

    private void register(){
        this.setUnlocalizedName(ModUtil.MOD_ID_LOWER+"."+this.getBaseName());
        GameRegistry.registerItem(this, this.getBaseName());
        if(this.shouldAddCreative()){
            this.setCreativeTab(CreativeTab.instance);
        }
        else{
            this.setCreativeTab(null);
        }

        Util.ITEMS_AND_BLOCKS.add(this);
    }

    protected String getBaseName(){
        return this.name;
    }

    public boolean shouldAddCreative(){
        return true;
    }
}
