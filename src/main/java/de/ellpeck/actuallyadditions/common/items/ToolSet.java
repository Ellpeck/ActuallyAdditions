package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.materials.ToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ToolSet {
    public final Pair<String, Item> pickaxe;
    public final Pair<String, Item> axe;
    public final Pair<String, Item> shovel;
    public final Pair<String, Item> sword;
    public final Pair<String, Item> hoe;
    public final Pair<String, Item> helmet;
    public final Pair<String, Item> chest;
    public final Pair<String, Item> leggins;
    public final Pair<String, Item> boots;

    public final Set<Pair<String, Item>> items;

    public final IItemTier tier;
    public final String name;

    public ToolSet(String name, IItemTier tier, IArmorMaterial armorTier, Supplier<Item.Properties> props) {
        this.pickaxe = Pair.of("pickaxe", new PickaxeItem(tier, 1, -2.8f, props.get()));
        this.axe = Pair.of("axe", new AxeItem(tier, 6, -3.0f, props.get()));
        this.shovel = Pair.of("shovel", new ShovelItem(tier, 1, -3.0f, props.get()));
        this.sword = Pair.of("sword", new SwordItem(tier, 1, ToolMaterials.RESTONIA.getAttackDamage() + 1.f, props.get()));
        this.hoe = Pair.of("hoe", new HoeItem(tier, 1, ToolMaterials.RESTONIA.getAttackDamage() + 1.f, props.get()));
        this.helmet = Pair.of("helmet", new ArmorItem(armorTier, EquipmentSlotType.HEAD, props.get()));
        this.chest = Pair.of("chest", new ArmorItem(armorTier, EquipmentSlotType.CHEST, props.get()));
        this.leggins = Pair.of("leggings", new ArmorItem(armorTier, EquipmentSlotType.LEGS, props.get()));
        this.boots = Pair.of("boots", new ArmorItem(armorTier, EquipmentSlotType.FEET, props.get()));

        this.items = ImmutableSet.of(pickaxe, axe, shovel, sword, hoe, helmet, chest, leggins, boots);

        this.name = name;
        this.tier = tier;
    }

    public void register(DeferredRegister<Item> register) {
        this.items.forEach(item -> register.register(String.format("%s_%s", this.name, item.getKey()), item::getValue));
    }

    public Set<Item> getItemGroup() {
        return this.items.stream().map(Pair::getValue).collect(Collectors.toSet());
    }
}
