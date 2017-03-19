package de.ellpeck.actuallyadditions.mod.util.compat;

import de.ellpeck.actuallyadditions.mod.inventory.ContainerCrafter;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CraftingTweaksCompat{

    private static final String MOD_ID = "craftingtweaks";

    public static void register(){
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("ContainerClass", ContainerCrafter.class.getName());
        tagCompound.setString("AlignToGrid", "left");
        FMLInterModComms.sendMessage(MOD_ID, "RegisterProvider", tagCompound);
    }

}
