package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

public class TooltipEvent{

    @SubscribeEvent
    public void onTooltipEvent(ItemTooltipEvent event){
        if(KeyUtil.isControlPressed()){
            int[] oreIDs = OreDictionary.getOreIDs(event.itemStack);
            if(oreIDs.length > 0){
                event.toolTip.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".oredictName.desc") + ":");
                for(int oreID : oreIDs){
                    event.toolTip.add(StringUtil.GRAY + "-" + OreDictionary.getOreName(oreID));
                }
            }
            else event.toolTip.add(StringUtil.GRAY + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".noOredictNameAvail.desc"));
        }
    }
}
