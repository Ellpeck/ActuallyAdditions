/*
 * This file ("DamageSources.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;

public class MultiMessageDamageSource extends DamageSource {

	private final int messageCount;

	public MultiMessageDamageSource(Holder<DamageType> damageTypeHolder, int messageCount) {
		super(damageTypeHolder);
		this.messageCount = messageCount;
	}

	@Override
	public Component getLocalizedDeathMessage(LivingEntity entity) {
		String locTag = "death." + this.getMsgId() + "." + (entity.level().random.nextInt(this.messageCount) + 1);
		return Component.translatable(locTag, entity.getName());
	}
}
