package de.ellpeck.actuallyadditions.common.util.compat;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class CompatUtil {

    static boolean fb = Loader.isModLoaded("fastbench");

    @SideOnly(Side.CLIENT)
    public static Object getCrafterGuiElement(EntityPlayer player, World world, int x, int y, int z) {
        if (fb) return CompatFastBench.getFastBenchGui(player, world);
        return new GuiCrafting(player.inventory, world, new BlockPos(x, y, z));
    }

    public static Object getCrafterContainerElement(EntityPlayer player, World world, int x, int y, int z) {
        if (fb) return CompatFastBench.getFastBenchContainer(player, world);
        return new ContainerWorkbench(player.inventory, world, new BlockPos(x, y, z)) {
            @Override
            public boolean canInteractWith(EntityPlayer playerIn) {
                return true;
            }
        };
    }

    public static void registerCraftingTweaks() {
        NBTTagCompound t = new NBTTagCompound();
        if (fb) t.setString("ContainerClass", "de.ellpeck.actuallyadditions.common.util.compat.CompatFastBench$1");
        else t.setString("ContainerClass", "de.ellpeck.actuallyadditions.common.util.compat.CompatUtil$1");
        FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", t);
    }
}
