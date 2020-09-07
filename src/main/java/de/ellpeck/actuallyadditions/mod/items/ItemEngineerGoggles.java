package de.ellpeck.actuallyadditions.mod.items;

import java.util.List;
import java.util.Set;

import de.ellpeck.actuallyadditions.api.misc.IGoggles;
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

public class ItemEngineerGoggles extends ItemArmorAA implements IGoggles {

    private final Set<Entity> cachedGlowingEntities = new ConcurrentSet<>();

    private final boolean displayMobs;

    public ItemEngineerGoggles(String name, boolean displayMobs) {
        super(name, InitArmorMaterials.armorMaterialGoggles, 0, StackUtil.getEmpty());
        this.displayMobs = displayMobs;
        this.setMaxDamage(0);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isWearing(EntityPlayer player) {
        ItemStack face = player.inventory.armorInventory.get(3);
        return StackUtil.isValid(face) && face.getItem() instanceof IGoggles;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        EntityPlayer player = ActuallyAdditions.PROXY.getCurrentPlayer();
        if (player != null && isWearing(player)) {
            ItemStack face = player.inventory.armorInventory.get(3);
            if (((IGoggles) face.getItem()).displaySpectralMobs()) {
                double range = 8;
                AxisAlignedBB aabb = new AxisAlignedBB(player.posX - range, player.posY - range, player.posZ - range, player.posX + range, player.posY + range, player.posZ + range);
                List<Entity> entities = player.world.getEntitiesWithinAABB(Entity.class, aabb);
                if (entities != null && !entities.isEmpty()) {
                    this.cachedGlowingEntities.addAll(entities);
                }

                if (!this.cachedGlowingEntities.isEmpty()) {
                    for (Entity entity : this.cachedGlowingEntities) {
                        if (entity.isDead || entity.getDistanceSq(player.posX, player.posY, player.posZ) > range * range) {
                            entity.setGlowing(false);

                            this.cachedGlowingEntities.remove(entity);
                        } else {
                            entity.setGlowing(true);
                        }
                    }
                }

                return;
            }
        }

        if (!this.cachedGlowingEntities.isEmpty()) {
            for (Entity entity : this.cachedGlowingEntities) {
                if (!entity.isDead) {
                    entity.setGlowing(false);
                }
            }
            this.cachedGlowingEntities.clear();
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }

    @Override
    public boolean displaySpectralMobs() {
        return this.displayMobs;
    }
}
