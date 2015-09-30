/*
 * This file ("EntityConstructingEvent.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.playerdata.PersistentServerData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;

public class EntityConstructingEvent{

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event){
        if(event.entity instanceof EntityPlayer){
            if(PersistentServerData.get((EntityPlayer)event.entity) == null){
                event.entity.registerExtendedProperties(ModUtil.MOD_ID, new PersistentServerData());
            }
        }
    }
}
