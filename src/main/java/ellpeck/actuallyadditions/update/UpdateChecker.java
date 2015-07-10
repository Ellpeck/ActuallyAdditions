package ellpeck.actuallyadditions.update;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class UpdateChecker{

    public static boolean doneChecking = false;
    public static boolean checkFailed = false;
    private static boolean notified = false;
    public static String updateVersionS;
    public static int updateVersion;
    public static int clientVersion;
    public static String changelog;

    public static final String DOWNLOAD_LINK = "http://minecraft.curseforge.com/mc-mods/228404-actually-additions/files";

    public void init(){
        ModUtil.LOGGER.info("Initializing Update Checker...");
        Util.registerEvent(this);
        new ThreadUpdateChecker();
    }

    @SubscribeEvent(receiveCanceled = true)
    public void onTick(TickEvent.ClientTickEvent event){
        if(!notified && doneChecking && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(checkFailed){
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".update.failed.desc")));
            }
            else{
                if(updateVersion > clientVersion){
                    String notice1 = "info."+ModUtil.MOD_ID_LOWER+".update.generic.desc";
                    String notice2 = "info."+ModUtil.MOD_ID_LOWER+".update.versionComp.desc";
                    String notice3 = "info."+ModUtil.MOD_ID_LOWER+".update.changelog.desc";
                    String notice4 = "info."+ModUtil.MOD_ID_LOWER+".update.download.desc";
                    player.addChatComponentMessage(new ChatComponentText(""));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocal(notice1)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocalFormatted(notice2, ModUtil.VERSION, updateVersionS)));
                    player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocalFormatted(notice3, changelog)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocalFormatted(notice4, DOWNLOAD_LINK)));
                    player.addChatComponentMessage(new ChatComponentText(""));
                }
            }
            notified = true;
        }
    }


}
