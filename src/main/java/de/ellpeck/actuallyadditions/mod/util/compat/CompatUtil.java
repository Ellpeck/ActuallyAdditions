/*
 * This file ("CompatUtil.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.util.compat;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerCrafter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public final class CompatUtil{

    public static void registerCraftingTweaksCompat(){
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("ContainerClass", ContainerCrafter.class.getName());
        tagCompound.setString("AlignToGrid", "left");
        FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", tagCompound);
    }
}
