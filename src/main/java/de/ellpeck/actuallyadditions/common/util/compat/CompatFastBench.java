package de.ellpeck.actuallyadditions.common.util.compat;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import shadows.fastbench.gui.ContainerFastBench;
import shadows.fastbench.gui.GuiFastBench;

public class CompatFastBench {

    public static Container getFastBenchContainer(PlayerEntity p, World world) {
        return new ContainerFastBench(0, p, world, BlockPos.ZERO) {
            @Override
            public boolean canInteractWith(PlayerEntity playerIn) {
                return true;
            }
        };
    }

    @OnlyIn(Dist.CLIENT)
    public static Screen getFastBenchGui(PlayerEntity p, World world) {
        return new GuiFastBench(p.inventory, world, BlockPos.ZERO);
    }

}
