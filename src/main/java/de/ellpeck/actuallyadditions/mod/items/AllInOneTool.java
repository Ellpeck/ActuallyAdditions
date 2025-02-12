package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nonnull;
import java.util.List;

public class AllInOneTool extends DiggerItem {

    private static final List<ItemAbility> ACTIONS = List.of(
        ItemAbilities.AXE_DIG,
        ItemAbilities.HOE_DIG,
        ItemAbilities.PICKAXE_DIG,
        ItemAbilities.SHOVEL_DIG,
        ItemAbilities.HOE_TILL,
        ItemAbilities.SHOVEL_FLATTEN,
        ItemAbilities.AXE_STRIP
    );

    public AllInOneTool(Tier tier) {
        super(tier,
            ActuallyTags.Blocks.MINEABLE_WITH_AIO,
            new Properties()
                .durability(tier.getUses() * 4)
                .component(DataComponents.TOOL, tier.createToolProperties(ActuallyTags.Blocks.MINEABLE_WITH_AIO))
                .attributes(createAttributes(tier))
        );
    }

    private static ItemAttributeModifiers createAttributes(Tier tier) {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        builder.add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID,4.0f + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        builder.add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.0f, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
        return builder.build();
    }

    @Override
    public boolean canPerformAction(@Nonnull ItemStack stack, @Nonnull ItemAbility toolAction) {
        if (ACTIONS.contains(toolAction))
            return true;
        return super.canPerformAction(stack, toolAction);
    }

    @Nonnull
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

/*    @Override //TODO help, enchantments are weird now.
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return super.canApplyAtEnchantingTable(stack, enchantment) || enchantment.category.canEnchant(Items.DIAMOND_SWORD);
    }*/
}
