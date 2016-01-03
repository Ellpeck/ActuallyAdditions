/*
 * This file ("PersistentClientData.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense/
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.util.playerdata;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.ellpeck.actuallyadditions.booklet.BookletUtils;
import de.ellpeck.actuallyadditions.booklet.EntrySet;
import de.ellpeck.actuallyadditions.booklet.GuiBooklet;
import de.ellpeck.actuallyadditions.booklet.button.BookmarkButton;
import de.ellpeck.actuallyadditions.util.ModUtil;
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
            worldCompound.setTag("SavedEntry", gui.currentEntrySet.writeToNBT());
            worldCompound.setString("SearchWord", gui.searchField.getText());
        }

        //Save Bookmarks
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < gui.bookmarkButtons.length; i++){
            BookmarkButton button = (BookmarkButton)gui.bookmarkButtons[i];

            list.appendTag(button.assignedEntry.writeToNBT());
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
            EntrySet set = EntrySet.readFromNBT(worldCompound.getCompoundTag("SavedEntry"));
            if(set != null){

                BookletUtils.openIndexEntry(gui, set.entry, set.pageInIndex, true);
                if(set.chapter != null){
                    BookletUtils.openChapter(gui, set.chapter, set.page);
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
                    button.assignedEntry = EntrySet.readFromNBT(compound);
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
