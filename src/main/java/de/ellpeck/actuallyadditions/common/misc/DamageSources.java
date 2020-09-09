package de.ellpeck.actuallyadditions.misc;

import de.ellpeck.actuallyadditions.ActuallyAdditions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSources extends DamageSource {

    public static final DamageSource DAMAGE_ATOMIC_RECONSTRUCTOR = new DamageSources("atomicReconstructor", 5).setDamageBypassesArmor();

    private final int messageCount;

    public DamageSources(String name, int messageCount) {
        super(name);
        this.messageCount = messageCount;
    }

    @Override
    public ITextComponent getDeathMessage(EntityLivingBase entity) {
        String locTag = "death." + ActuallyAdditions.MODID + "." + this.damageType + "." + (entity.world.rand.nextInt(this.messageCount) + 1);
        return new TextComponentTranslation(locTag, entity.getName());
    }
}
