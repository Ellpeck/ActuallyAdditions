package de.ellpeck.actuallyadditions.common.items.useables;

import de.ellpeck.actuallyadditions.common.items.ActuallyItem;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class ManualItem extends ActuallyItem {
    public ManualItem() {
        super(baseProps().maxStackSize(0).setNoRepair().rarity(Rarity.EPIC));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(Help.trans("tooltip.booklet.manual.one").mergeStyle(TextFormatting.GRAY));
        tooltip.add(Help.trans("tooltip.booklet.manual.two"));
        tooltip.add(Help.trans("tooltip.booklet.manual.three").mergeStyle(TextFormatting.GOLD));
    }
}
