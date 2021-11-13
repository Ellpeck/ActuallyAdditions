package de.ellpeck.actuallyadditions.mod.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.IActuallyItem;
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
            new Properties()
                .addToolType(ToolType.AXE, tier.getLevel())
                .addToolType(ToolType.HOE, tier.getLevel())
                .addToolType(ToolType.SHOVEL, tier.getLevel())
                .addToolType(ToolType.PICKAXE, tier.getLevel())
                .durability(tier.getUses() * 4)
                .tab(ActuallyAdditions.GROUP)
        );

        this.tier = tier;
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        // How, no idea, possible, most likely :cry:
        if (context.getPlayer() == null) {
            return ActionResultType.FAIL;
        }

        // Player not sneaking? Act as a Hoe to the block, else, Act as a shovel
        if (!context.getPlayer().isCrouching()) {
            return Items.IRON_HOE.useOn(context);
        }

        return Items.IRON_SHOVEL.useOn(context);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Items.DIAMOND_SWORD);
    }
}
