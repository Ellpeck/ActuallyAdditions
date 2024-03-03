package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public class AABlockItem extends BlockItem {
    public AABlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public static class AASeedItem extends ItemNameBlockItem {
        public AASeedItem(Block block, Properties properties) {
            super(block, properties);
        }

        @Override
        public String getDescriptionId() {
            return this.getOrCreateDescriptionId();
        }
    }

    public static class BlockEntityEnergyItem extends AABlockItem {
        public BlockEntityEnergyItem(Block blockIn, Properties builder) {
            super(blockIn, builder);
        }

        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

            if (pStack.hasTag() && pStack.getTag().contains("BlockEntityTag")) {
                CompoundTag BET = pStack.getTag().getCompound("BlockEntityTag");
                int energy = 0;
                if (BET.contains("Energy")) {
                    energy = BET.getInt("Energy");
                }
                NumberFormat format = NumberFormat.getInstance();
                pTooltip.add(Component.translatable("misc.actuallyadditions.power_single", format.format(energy)));
            }
        }
    }
}
