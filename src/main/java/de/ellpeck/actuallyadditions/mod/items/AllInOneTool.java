package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

public class AllInOneTool extends DiggerItem {
    private final Tier tier;

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
        if (toolAction == ToolActions.AXE_DIG || toolAction == ToolActions.HOE_DIG || toolAction == ToolActions.PICKAXE_DIG || toolAction == ToolActions.SHOVEL_DIG)
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
