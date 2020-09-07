package de.ellpeck.actuallyadditions.mod.item;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class BurnableItem extends Item {
    
    private static final Properties PROPERTIES = new Properties().group(ActuallyAdditions.GROUP);
    
    private final int burnTime;
    
    public BurnableItem(ResourceLocation registryName, int burnTime){
        super(PROPERTIES);
        
        this.burnTime = burnTime;
        
        this.setRegistryName(registryName);
    }
    
    @Override
    public int getBurnTime(ItemStack itemStack){
        return this.burnTime;
    }
}
