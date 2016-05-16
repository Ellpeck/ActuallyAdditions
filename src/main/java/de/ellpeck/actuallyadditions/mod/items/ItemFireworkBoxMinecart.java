package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.entity.EntityFireworkBoxMinecart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

public class ItemFireworkBoxMinecart extends ItemMinecartAA{

    public ItemFireworkBoxMinecart(String name){
        super(name);
    }

    @Override
    public EntityMinecart createCart(World world, double x, double y, double z){
        EntityMinecart entity = new EntityFireworkBoxMinecart(world);
        entity.setPosition(x, y, z);
        return entity;
    }
}
