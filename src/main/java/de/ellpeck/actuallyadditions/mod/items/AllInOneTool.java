package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

import java.util.List;

public class AllInOneTool extends DiggerItem {
    private final Tier tier;

    private static List<ToolAction> ACTIONS = List.of(
        ToolActions.AXE_DIG,
        ToolActions.HOE_DIG,
        ToolActions.PICKAXE_DIG,
        ToolActions.SHOVEL_DIG,
        ToolActions.HOE_TILL,
        ToolActions.SHOVEL_FLATTEN,
        ToolActions.AXE_STRIP
    );

    public AllInOneTool(Tier tier) {
        super(
            4.0f,
            -2f,
            tier,
                ActuallyTags.Blocks.MINEABLE_WITH_AIO,
            new Properties()
                .durability(tier.getUses() * 4)
                
        );

        this.tier = tier;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        if (ACTIONS.contains(toolAction))
            return true;
        return super.canPerformAction(stack, toolAction);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // How, no idea, possible, most likely :cry:
        if (context.getPlayer() == null) {
            return InteractionResult.FAIL;
        }

        // Player not sneaking? Act as a Hoe to the block, else, Act as a shovel
        if (context.getPlayer().isCrouching()) {
            return Items.IRON_SHOVEL.useOn(context);
        }
        InteractionResult tmp = Items.IRON_AXE.useOn(context);
        if (tmp == InteractionResult.SUCCESS)
            return tmp;
        return Items.IRON_HOE.useOn(context);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Items.DIAMOND_SWORD);
    }
}
