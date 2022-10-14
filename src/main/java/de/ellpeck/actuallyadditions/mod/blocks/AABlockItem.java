package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;

public class AABlockItem extends BlockItem {
    public AABlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    public static class AASeedItem extends BlockNamedItem {
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
        public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
            super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

            if (pStack.hasTag() && pStack.getTag().contains("BlockEntityTag")) {
                CompoundNBT BET = pStack.getTag().getCompound("BlockEntityTag");
                int energy = 0;
                if (BET.contains("Energy")) {
                    energy = BET.getInt("Energy");
                }
                NumberFormat format = NumberFormat.getInstance();
                pTooltip.add(new TranslationTextComponent("misc.actuallyadditions.power_single", format.format(energy)));
            }
        }
    }
}
