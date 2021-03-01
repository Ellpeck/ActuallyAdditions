package de.ellpeck.actuallyadditions.mod.util.compat;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import shadows.fastbench.gui.ContainerFastBench;
import shadows.fastbench.gui.GuiFastBench;

public class CompatFastBench {

    public static Container getFastBenchContainer(PlayerEntity p, World world) {
        return new ContainerFastBench(p, world, BlockPos.ORIGIN) {
            @Override
            public boolean canInteractWith(PlayerEntity playerIn) {
                return true;
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static Gui getFastBenchGui(PlayerEntity p, World world) {
        return new GuiFastBench(p.inventory, world, BlockPos.ORIGIN);
    }

}
