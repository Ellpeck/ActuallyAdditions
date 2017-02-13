/*
 * This file ("ItemInfraredGoggles.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemArmorAA;
import de.ellpeck.actuallyadditions.mod.material.InitArmorMaterials;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemInfraredGoggles extends ItemArmorAA{

    public ItemInfraredGoggles(String name){
        super(name, InitArmorMaterials.armorMaterialGoggles, 0, StackUtil.getNull());
        this.setMaxDamage(0);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event){
        EntityPlayer player = ActuallyAdditions.proxy.getCurrentPlayer();
        if(player != null && isWearing(player)){
            double innerRange = 8;
            double remRange = innerRange+2;

            AxisAlignedBB aabb = new AxisAlignedBB(player.posX-remRange, player.posY-remRange, player.posZ-remRange, player.posX+remRange, player.posY+remRange, player.posZ+remRange);
            List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, aabb);

            for(Entity entity : entities){
                if(entity != player){
                    if(entity.getDistanceSq(player.posX, player.posY, player.posZ) <= innerRange*innerRange){
                        entity.setGlowing(true);
                    }
                    else{
                        entity.setGlowing(false);
                    }
                }
            }
        }
    }

    public static boolean isWearing(EntityPlayer player){
        ItemStack face = player.inventory.armorInventory.get(3);
        return StackUtil.isValid(face) && face.getItem() == InitItems.itemInfraredGoggles;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.RARE;
    }
}
