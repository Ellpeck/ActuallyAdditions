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
import ellpeck.actuallyadditions.booklet.BookletUtils;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.button.BookmarkButton;
import ellpeck.actuallyadditions.booklet.chapter.BookletChapter;
import ellpeck.actuallyadditions.booklet.entry.BookletEntry;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SideOnly(Side.CLIENT)
public class PersistentClientData{

    private static File theFile;

    public static void saveBookPage(GuiBooklet gui){
        NBTTagCompound baseCompound = getBaseCompound();
        NBTTagCompound worldCompound = getCompoundForWorld(baseCompound);
        //Save Entry etc.
        if(worldCompound != null){
            worldCompound.setInteger("Entry", gui.currentIndexEntry == null ? -1 : InitBooklet.entries.indexOf(gui.currentIndexEntry));
            worldCompound.setInteger("Chapter", gui.currentIndexEntry == null || gui.currentChapter == null ? -1 : gui.currentIndexEntry.chapters.indexOf(gui.currentChapter));
            worldCompound.setInteger("Page", gui.currentPage == null ? -1 : gui.currentPage.getID());
            worldCompound.setInteger("PageInIndex", gui.pageOpenInIndex);
            worldCompound.setString("SearchWord", gui.searchField.getText());
        }

        //Save Bookmarks
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < gui.bookmarkButtons.length; i++){
            BookmarkButton button = (BookmarkButton)gui.bookmarkButtons[i];

            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("Entry", button.assignedEntry == null ? -1 : InitBooklet.entries.indexOf(button.assignedEntry));
            compound.setInteger("Chapter", button.assignedEntry == null || button.assignedChapter == null ? -1 : button.assignedEntry.chapters.indexOf(button.assignedChapter));
            compound.setInteger("Page", button.assignedPage == null ? -1 : button.assignedPage.getID());
            compound.setInteger("PageInIndex", button.assignedPageInIndex);
            list.appendTag(compound);
        }
        worldCompound.setTag("Bookmarks", list);

        writeCompound(baseCompound, worldCompound);
    }

    private static NBTTagCompound getBaseCompound(){
        try{
            return CompressedStreamTools.readCompressed(new FileInputStream(getTheFile()));
        }
        catch(Exception e){
            return new NBTTagCompound();
        }
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

    private static String getName(){
        if(Minecraft.getMinecraft().theWorld != null){
            return Minecraft.getMinecraft().isIntegratedServerRunning() ? Minecraft.getMinecraft().getIntegratedServer().getFolderName() : Minecraft.getMinecraft().func_147104_D().serverIP;
        }
        else{
            return "Invalid";
        }
    }

    public static void setTheFile(File file){
        theFile = file;
    }

    public static void openLastBookPage(GuiBooklet gui){
        NBTTagCompound worldCompound = getCompoundForWorld(getBaseCompound());
        if(worldCompound != null){
            //Open Entry etc.
            if(worldCompound.hasKey("Entry")){
                int entry = worldCompound.getInteger("Entry");
                int chapter = worldCompound.getInteger("Chapter");
                int page = worldCompound.getInteger("Page");

                BookletEntry currentIndexEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
                BookletChapter currentChapter = chapter == -1 || entry == -1 || currentIndexEntry.chapters.size() <= chapter ? null : currentIndexEntry.chapters.get(chapter);
                BookletPage currentPage = chapter == -1 || currentChapter == null || currentChapter.pages.length <= page-1 ? null : currentChapter.pages[page-1];
                int pageInIndex = worldCompound.getInteger("PageInIndex");

                BookletUtils.openIndexEntry(gui, currentIndexEntry, pageInIndex, true);
                if(currentChapter != null){
                    BookletUtils.openChapter(gui, currentChapter, currentPage);
                }

                String searchText = worldCompound.getString("SearchWord");
                if(!searchText.isEmpty()){
                    gui.searchField.setText(searchText);
                    BookletUtils.updateSearchBar(gui);
                }
            }
            else{
                //If everything fails, initialize the front page
                BookletUtils.openIndexEntry(gui, null, 1, true);
            }

            //Load Bookmarks
            NBTTagList list = worldCompound.getTagList("Bookmarks", 10);
            if(list != null){
                for(int i = 0; i < list.tagCount(); i++){
                    BookmarkButton button = (BookmarkButton)gui.bookmarkButtons[i];
                    NBTTagCompound compound = list.getCompoundTagAt(i);

                    int entry = compound.getInteger("Entry");
                    int chapter = compound.getInteger("Chapter");
                    int page = compound.getInteger("Page");

                    button.assignedEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
                    button.assignedChapter = chapter == -1 || entry == -1 || button.assignedEntry.chapters.size() <= chapter ? null : button.assignedEntry.chapters.get(chapter);
                    button.assignedPage = chapter == -1 || button.assignedChapter == null || button.assignedChapter.pages.length <= page-1 ? null : button.assignedChapter.pages[page-1];
                    button.assignedPageInIndex = compound.getInteger("PageInIndex");
                }
            }
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
