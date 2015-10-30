/*
 * This file ("PersistentClientData.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at 
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util.playerdata;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.booklet.BookletChapter;
import ellpeck.actuallyadditions.booklet.BookletIndexEntry;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SideOnly(Side.CLIENT)
public class PersistentClientData{

    private static File theFile;

    public static void saveBookPage(BookletIndexEntry entry, BookletChapter chapter, BookletPage page, int pageInIndex, String searchWord){
        NBTTagCompound baseCompound = getBaseCompound();
        NBTTagCompound worldCompound = getCompoundForWorld(baseCompound);
        if(worldCompound != null){
            worldCompound.setInteger("Entry", entry == null ? -1 : InitBooklet.entries.indexOf(entry));
            worldCompound.setInteger("Chapter", entry == null || chapter == null ? -1 : entry.chapters.indexOf(chapter));
            worldCompound.setInteger("Page", page == null ? -1 : page.getID());
            worldCompound.setInteger("PageInIndex", pageInIndex);
            worldCompound.setString("SearchWord", searchWord);
            writeCompound(baseCompound, worldCompound);
        }
    }

    private static NBTTagCompound getBaseCompound(){
        try{
            return CompressedStreamTools.readCompressed(new FileInputStream(getTheFile()));
        }
        catch(Exception e){
            return new NBTTagCompound();
        }
    }

    private static String getName(){
        return Minecraft.getMinecraft().isIntegratedServerRunning() ? Minecraft.getMinecraft().getIntegratedServer().getFolderName() : Minecraft.getMinecraft().func_147104_D().serverIP;
    }

    private static NBTTagCompound getCompoundForWorld(NBTTagCompound mainCompound){
        return mainCompound.getCompoundTag(getName());
    }

    private static void writeCompound(NBTTagCompound baseCompound, NBTTagCompound worldCompound){
        baseCompound.setTag(getName(), worldCompound);
        try{
            CompressedStreamTools.writeCompressed(baseCompound, new FileOutputStream(getTheFile()));
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Couldn't write Persistent Variable!", e);
        }
    }

    public static File getTheFile(){
        try{
            if(!theFile.exists()){
                theFile.createNewFile();
            }
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Couldn't create Persistent Variables file!", e);
        }
        return theFile;
    }

    public static void setTheFile(File file){
        theFile = file;
    }

    public static void openLastBookPage(GuiBooklet gui){
        NBTTagCompound worldCompound = getCompoundForWorld(getBaseCompound());
        if(worldCompound != null && worldCompound.hasKey("Entry")){
            int entry = worldCompound.getInteger("Entry");
            int chapter = worldCompound.getInteger("Chapter");
            int page = worldCompound.getInteger("Page");

            BookletIndexEntry currentIndexEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
            BookletChapter currentChapter = chapter == -1 || entry == -1 || currentIndexEntry.chapters.size() <= chapter ? null : currentIndexEntry.chapters.get(chapter);
            BookletPage currentPage = chapter == -1 || currentChapter == null || currentChapter.pages.length <= page-1 ? null : currentChapter.pages[page-1];
            int pageInIndex = worldCompound.getInteger("PageInIndex");

            gui.openIndexEntry(currentIndexEntry, pageInIndex, true);
            if(currentChapter != null){
                gui.openChapter(currentChapter, currentPage);
            }

            String searchText = worldCompound.getString("SearchWord");
            if(!searchText.isEmpty()){
                gui.searchField.setText(searchText);
                gui.updateSearchBar();
            }
        }
        else{
            //If everything fails, initialize the front page
            gui.openIndexEntry(null, 1, true);
        }
    }

    public static void setBoolean(String name, boolean bool){
        NBTTagCompound baseCompound = getBaseCompound();
        NBTTagCompound worldCompound = getCompoundForWorld(baseCompound);
        if(worldCompound != null){
            worldCompound.setBoolean(name, bool);
            writeCompound(baseCompound, worldCompound);
        }
    }

    public static boolean getBoolean(String name){
        NBTTagCompound worldCompound = getCompoundForWorld(getBaseCompound());
        return worldCompound != null && worldCompound.getBoolean(name);
    }
}
