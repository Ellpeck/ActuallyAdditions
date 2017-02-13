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
import io.netty.util.internal.ConcurrentSet;
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
import java.util.Set;

public class ItemInfraredGoggles extends ItemArmorAA{

    private final Set<Entity> cachedGlowingEntities = new ConcurrentSet<Entity>();

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
            double range = 8;
            AxisAlignedBB aabb = new AxisAlignedBB(player.posX-range, player.posY-range, player.posZ-range, player.posX+range, player.posY+range, player.posZ+range);
            List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, aabb);
            if(entities != null && !entities.isEmpty()){
                this.cachedGlowingEntities.addAll(entities);
            }

            if(!this.cachedGlowingEntities.isEmpty()){
                for(Entity entity : this.cachedGlowingEntities){
                    if(entity.getDistanceSq(player.posX, player.posY, player.posZ) > range*range){
                        entity.setGlowing(false);

                        this.cachedGlowingEntities.remove(entity);
                    }
                    else{
                        entity.setGlowing(true);
                    }
                }
            }
        }
        else{
            if(!this.cachedGlowingEntities.isEmpty()){
                for(Entity entity : this.cachedGlowingEntities){
                    if(!entity.isDead){
                        entity.setGlowing(false);
                    }
                }
                this.cachedGlowingEntities.clear();
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
