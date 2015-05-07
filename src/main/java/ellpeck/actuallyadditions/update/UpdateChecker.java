package ellpeck.actuallyadditions.update;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateChecker{

    public boolean doneChecking = false;
    public boolean checkFailed = false;
    public boolean notified = false;
    public String onlineVersion;

    public void init(){
        Util.logInfo("Initializing Update Checker...");
        Util.registerEvent(this);
        new UpdateCheckThread();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(doneChecking && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !notified){
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if(checkFailed){
                player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocal("info." + ModUtil.MOD_ID_LOWER + ".update.failed.desc")));
            }
            else if(onlineVersion.length() > 0){
                int update = Integer.parseInt(onlineVersion.replace("-", "").replace(".", ""));
                int client = Integer.parseInt(ModUtil.VERSION.replace("-", "").replace(".", ""));

                if(update > client){
                    String notice1 = "info." + ModUtil.MOD_ID_LOWER + ".update.generic.desc";
                    String notice2 = "info." + ModUtil.MOD_ID_LOWER + ".update.versionComp.desc";
                    String notice3 = "info." + ModUtil.MOD_ID_LOWER + ".update.download.desc";
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocal(notice1)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocalFormatted(notice2, ModUtil.VERSION, this.onlineVersion)));
                    player.addChatComponentMessage(IChatComponent.Serializer.func_150699_a(StatCollector.translateToLocalFormatted(notice3, "http://minecraft.curseforge.com/mc-mods/228404-actually-additions/files")));
                }

            }
            notified = true;
        }
    }

    public class UpdateCheckThread extends Thread{

        public UpdateCheckThread(){
            this.setName(ModUtil.MOD_ID + " Update Checker");
            this.setDaemon(true);
            this.start();
        }

        @Override
        public void run(){
            Util.logInfo("Starting Update Check...");
            try{
                URL url = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/newestVersion.txt");
                BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));
                onlineVersion = r.readLine();
                r.close();
                Util.logInfo("Update Check done!");
            }
            catch(Exception e){
                ModUtil.AA_LOGGER.log(Level.ERROR, "Update Check failed!");
                checkFailed = true;
                e.printStackTrace();
            }
            doneChecking = true;
        }
    }
}
