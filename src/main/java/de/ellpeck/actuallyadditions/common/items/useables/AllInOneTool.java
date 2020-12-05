package de.ellpeck.actuallyadditions.common.items.useables;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.ActuallyAdditions;
import de.ellpeck.actuallyadditions.common.items.IActuallyItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.*;
import net.minecraft.util.ActionResultType;
import net.minecraftforge.common.ToolType;

public class AllInOneTool extends ToolItem implements IActuallyItem {
    private final IItemTier tier;

    public AllInOneTool(IItemTier tier) {
        super(
                4.0f,
                -2f,
                tier,
                ImmutableSet.of(),
                new Item.Properties()
                        .addToolType(ToolType.AXE, tier.getHarvestLevel())
                        .addToolType(ToolType.HOE, tier.getHarvestLevel())
                        .addToolType(ToolType.SHOVEL, tier.getHarvestLevel())
                        .addToolType(ToolType.PICKAXE, tier.getHarvestLevel())
                        .maxDamage(tier.getMaxUses() * 4)
                        .group(ActuallyAdditions.ACTUALLY_GROUP)
        );

        this.tier = tier;
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        // How, no idea, possible, most likely :cry:
        if (context.getPlayer() == null) {
            return ActionResultType.FAIL;
        }

        // Player not sneaking? Act as a Hoe to the block, else, Act as a shovel
        if (!context.getPlayer().isSneaking()) {
            return Items.IRON_HOE.onItemUse(context);
        }

        return Items.IRON_SHOVEL.onItemUse(context);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.type.canEnchantItem(Items.DIAMOND_SWORD);
    }
}
