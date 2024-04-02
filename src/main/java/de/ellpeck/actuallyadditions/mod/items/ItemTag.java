package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.attachments.ItemTagAttachment;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ItemTag extends ItemBase {
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        var data = pStack.getExistingData(ActuallyAttachments.ITEM_TAG);
/*        data.flatMap(ItemTagAttachment::getTag).ifPresent(itemTag -> {
            BuiltInRegistries.ITEM.getTag(itemTag).ifPresent(itemTagCollection -> {
                pTooltipComponents.add(Component.literal("Valid Tag").withStyle(ChatFormatting.GREEN));
                pTooltipComponents.add(Component.literal("Tag: " + itemTag.location()).withStyle(ChatFormatting.GRAY));
                pTooltipComponents.add(Component.literal("Contains: " + itemTagCollection.stream().count() + " items").withStyle(ChatFormatting.GRAY));
            });
        });*/
        data.flatMap(ItemTagAttachment::getTag).ifPresentOrElse(
                itemTag -> pTooltipComponents.add(Component.literal("Tag: " + itemTag.location()).withStyle(ChatFormatting.GRAY)),
                () -> pTooltipComponents.add(Component.translatable("tooltip.actuallyadditions.item_tag.no_tag").withStyle(ChatFormatting.RED)
                ));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }


/*    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, @Nonnull Player pPlayer, @Nonnull InteractionHand pUsedHand) {
        if (!pLevel.isClientSide)
            pPlayer.openMenu(new SimpleMenuProvider((pId, pInventory, player) -> new ItemTagContainer(pId, pInventory), Component.translatable("container.actuallyadditions.item_tag")));

        return super.use(pLevel, pPlayer, pUsedHand);
    }*/
}
