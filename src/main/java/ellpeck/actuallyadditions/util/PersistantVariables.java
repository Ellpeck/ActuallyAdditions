/*
 * This file ("PersistantVariables.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at 
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.booklet.BookletChapter;
import ellpeck.actuallyadditions.booklet.BookletIndexEntry;
import ellpeck.actuallyadditions.booklet.GuiBooklet;
import ellpeck.actuallyadditions.booklet.InitBooklet;
import ellpeck.actuallyadditions.booklet.page.BookletPage;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@SideOnly(Side.CLIENT)
public class PersistantVariables{

    private static File theFile;

    public static void saveBookPage(BookletIndexEntry entry, BookletChapter chapter, BookletPage page, int pageInIndex){
        NBTTagCompound compound = getCompound();
        if(compound != null){
            compound.setInteger(getName("Entry"), entry == null ? -1 : InitBooklet.entries.indexOf(entry));
            compound.setInteger(getName("Chapter"), entry == null || chapter == null ? -1 : entry.chapters.indexOf(chapter));
            compound.setInteger(getName("Page"), page == null ? -1 : page.getID());
            compound.setInteger(getName("PageInIndex"), pageInIndex);
            writeCompoundToFile(compound);
        }
    }

    public static void openLastBookPage(GuiBooklet gui){
        NBTTagCompound compound = getCompound();
        if(compound != null){
            if(compound.hasKey(getName("Entry"))){
                int entry = compound.getInteger(getName("Entry"));
                int chapter = compound.getInteger(getName("Chapter"));
                int page = compound.getInteger(getName("Page"));

                BookletIndexEntry currentIndexEntry = entry == -1 ? null : InitBooklet.entries.get(entry);
                BookletChapter currentChapter = chapter == -1 || entry == -1 || currentIndexEntry.chapters.size() <= chapter ? null : currentIndexEntry.chapters.get(chapter);
                BookletPage currentPage = chapter == -1 || currentChapter == null || currentChapter.pages.length <= page-1 ? null : currentChapter.pages[page-1];
                int pageInIndex = compound.getInteger(getName("PageInIndex"));

                gui.openIndexEntry(currentIndexEntry, pageInIndex, true);
                if(currentChapter != null){
                    gui.openChapter(currentChapter, currentPage);
                }
            }
            else{
                gui.openIndexEntry(null, 1, true);
            }
        }
    }

    public static void setBoolean(String name, boolean bool){
        NBTTagCompound compound = getCompound();
        if(compound != null){
            compound.setBoolean(getName(name), bool);
            writeCompoundToFile(compound);
        }
    }

    private static String getName(String name){
        return (Minecraft.getMinecraft().isIntegratedServerRunning() ? Minecraft.getMinecraft().getIntegratedServer().getWorldName() : Minecraft.getMinecraft().func_147104_D().serverIP)+"-"+name;
    }

    public static boolean getBoolean(String name){
        NBTTagCompound compound = getCompound();
        return compound != null && compound.getBoolean(getName(name));
    }

    private static File getTheFile() throws Exception{
        if(!theFile.exists()){
            theFile.createNewFile();
        }
        return theFile;
    }

    public static void setTheFile(File file){
        theFile = file;
    }

    private static NBTTagCompound getCompound(){
        try{
            return getCompound(getTheFile());
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Couldn't read Persistant Variable!", e);
            return null;
        }
    }

    private static NBTTagCompound getCompound(File file) throws Exception{
        try{
            return CompressedStreamTools.readCompressed(new FileInputStream(file));
        }
        catch(Exception e){
            return createNewCompound(file);
        }
    }

    private static NBTTagCompound createNewCompound(File file) throws Exception{
        NBTTagCompound compound = new NBTTagCompound();
        CompressedStreamTools.writeCompressed(compound, new FileOutputStream(file));
        return getCompound(file);
    }

    private static void writeCompoundToFile(NBTTagCompound compound){
        try{
            CompressedStreamTools.writeCompressed(compound, new FileOutputStream(getTheFile()));
        }
        catch(Exception e){
            ModUtil.LOGGER.fatal("Couldn't write Persistant Variable!", e);
        }
    }
}
