package ellpeck.actuallyadditions.update;

import ellpeck.actuallyadditions.util.ModUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class ThreadUpdateChecker extends Thread{

    public ThreadUpdateChecker(){
        this.setName(ModUtil.MOD_ID + " Update Checker");
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run(){
        ModUtil.LOGGER.info("Starting Update Check...");
        try{
            URL newestURL = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/update/newestVersion.txt");
            BufferedReader newestReader = new BufferedReader(new InputStreamReader(newestURL.openStream()));
            UpdateChecker.updateVersionS = newestReader.readLine();
            newestReader.close();

            URL changeURL = new URL("https://raw.githubusercontent.com/Ellpeck/ActuallyAdditions/master/update/changelog.txt");
            BufferedReader changeReader = new BufferedReader(new InputStreamReader(changeURL.openStream()));
            UpdateChecker.changelog = changeReader.readLine();
            changeReader.close();

            ModUtil.LOGGER.info("Update Check done!");
        }
        catch(Exception e){
            ModUtil.LOGGER.error("Update Check failed!", e);
            UpdateChecker.checkFailed = true;
        }

        if(!UpdateChecker.checkFailed){
            try{
                UpdateChecker.updateVersion = Integer.parseInt(UpdateChecker.updateVersionS.replace("-", "").replace(".", ""));
                UpdateChecker.clientVersion = Integer.parseInt(ModUtil.VERSION.replace("-", "").replace(".", ""));
            }
            catch(Exception e){
                ModUtil.LOGGER.error("Comparing the newest and the current Version failed!", e);
                UpdateChecker.checkFailed = true;
            }

            if(!UpdateChecker.checkFailed){
                if(UpdateChecker.updateVersion > UpdateChecker.clientVersion){
                    ModUtil.LOGGER.info("There is an Update for "+ModUtil.MOD_ID+" available!");
                    ModUtil.LOGGER.info("The installed Version is "+ModUtil.VERSION+", but the newest Version is "+UpdateChecker.updateVersionS+"!");
                    ModUtil.LOGGER.info("The Changes are: "+UpdateChecker.changelog);
                    ModUtil.LOGGER.info("Download the newest Version at "+UpdateChecker.DOWNLOAD_LINK);
                }
                else{
                    ModUtil.LOGGER.info("There is no new Update for "+ModUtil.MOD_ID+" available!");
                    ModUtil.LOGGER.info("That's cool. You're really up to date, you have all of the latest awesome Features!");
                }
            }
        }

        UpdateChecker.doneChecking = true;
    }
}