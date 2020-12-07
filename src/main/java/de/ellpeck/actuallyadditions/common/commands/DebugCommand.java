package de.ellpeck.actuallyadditions.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import de.ellpeck.actuallyadditions.common.blocks.ActuallyBlocks;
import net.minecraft.block.Block;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;

public class DebugCommand {
    public static LiteralArgumentBuilder<CommandSource> register() {
        return Commands.literal("debug-spawn")
                .requires(commandSource -> commandSource.hasPermissionLevel(2))
                .executes(DebugCommand::execute);
    }

    private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().asPlayer();

        Collection<RegistryObject<Block>> entries = ActuallyBlocks.BLOCKS.getEntries();

        int x = player.getPosition().getX(), z = player.getPosition().getZ();
        int row = 0, col = 0;
        for (RegistryObject<Block> block: entries) {
            if (row > 10) {
                col += 1;
                row = 0;
            }

            context.getSource().getWorld().setBlockState(new BlockPos(
                    x + (2 * row), player.getPosition().getY(), z + (2 * col)
            ), block.get().getDefaultState());

            row ++;
        }

        return 1;
    }
}
