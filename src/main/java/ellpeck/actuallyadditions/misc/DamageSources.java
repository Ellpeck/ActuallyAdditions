/*
 * This file ("DamageSources.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.misc;

import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IChatComponent;

public class DamageSources extends DamageSource{

    public static final DamageSource DAMAGE_ATOMIC_RECONSTRUCTOR = new DamageSources("atomicReconstructor", 5).setDamageBypassesArmor();

    private int messageCount;

    public DamageSources(String name, int messageCount){
        super(name);
        this.messageCount = messageCount;
    }

    @Override
    public IChatComponent func_151519_b(EntityLivingBase entity){
        String locTag = "death."+ModUtil.MOD_ID_LOWER+"."+this.damageType+"."+(Util.RANDOM.nextInt(this.messageCount)+1);
        return new ChatComponentText(StringUtil.localizeFormatted(locTag, entity.getCommandSenderName()));
    }
}
