package de.ellpeck.actuallyadditions.mod.blocks;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
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
        public void appendHoverText(ItemStack pStack, @Nullable TooltipContext pContext, List<Component> pTooltip, TooltipFlag pFlag) {
            super.appendHoverText(pStack, pContext, pTooltip, pFlag);

            if (pStack.has(DataComponents.BLOCK_ENTITY_DATA)) {
                CustomData customData = pStack.get(DataComponents.BLOCK_ENTITY_DATA);
                int energy = 0;
                if (customData.contains("Energy")) {
                    energy = customData.copyTag().getInt("Energy");
                }
                NumberFormat format = NumberFormat.getInstance();
                pTooltip.add(Component.translatable("misc.actuallyadditions.power_single", format.format(energy)));
            }
        }
    }
}
