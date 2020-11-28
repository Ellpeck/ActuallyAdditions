package de.ellpeck.actuallyadditions.common.items;

import com.google.common.collect.ImmutableSet;
import de.ellpeck.actuallyadditions.common.materials.ToolMaterials;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ToolSet {
    public final NamedItem pickaxe;
    public final NamedItem axe;
    public final NamedItem shovel;
    public final NamedItem sword;
    public final NamedItem hoe;
    public final NamedItem helmet;
    public final NamedItem chest;
    public final NamedItem leggings;
    public final NamedItem boots;

    public final Set<NamedItem> items;

    public final IItemTier tier;
    public final String name;

    public ToolSet(String name, IItemTier tier, IArmorMaterial armorTier, Supplier<Item.Properties> props) {
        this.pickaxe = new NamedItem("pickaxe", "Pickaxe", new PickaxeItem(tier, 1, -2.8f, props.get()));
        this.axe = new NamedItem("axe", "Axe", new AxeItem(tier, 6, -3.0f, props.get()));
        this.shovel = new NamedItem("shovel", "Shovel", new ShovelItem(tier, 1, -3.0f, props.get()));
        this.sword = new NamedItem("sword", "Sword", new SwordItem(tier, 1, ToolMaterials.RESTONIA.getAttackDamage() + 1.f, props.get()));
        this.hoe = new NamedItem("hoe", "Hoe", new HoeItem(tier, 1, ToolMaterials.RESTONIA.getAttackDamage() + 1.f, props.get()));
        this.helmet = new NamedItem("helmet", "Helmet", new ArmorItem(armorTier, EquipmentSlotType.HEAD, props.get()));
        this.chest = new NamedItem("chest", "Chestplate", new ArmorItem(armorTier, EquipmentSlotType.CHEST, props.get()));
        this.leggings = new NamedItem("leggings", "Leggings", new ArmorItem(armorTier, EquipmentSlotType.LEGS, props.get()));
        this.boots = new NamedItem("boots", "Boots", new ArmorItem(armorTier, EquipmentSlotType.FEET, props.get()));

        this.items = ImmutableSet.of(pickaxe, axe, shovel, sword, hoe, helmet, chest, leggings, boots);

        this.name = name;
        this.tier = tier;
    }

    public void register(DeferredRegister<Item> register) {
        this.items.forEach(item -> register.register(String.format("%s_%s", this.name, item.getInternal()), item::getItem));
    }

    public Set<Item> getItemGroup() {
        return this.items.stream().map(NamedItem::getItem).collect(Collectors.toSet());
    }

    public static final class NamedItem {
        private final String internal;
        private final String pretty;
        private final Item item;

        public NamedItem(String internal, String pretty, Item item) {
            this.internal = internal;
            this.pretty = pretty;
            this.item = item;
        }

        public String getInternal() {
            return internal;
        }

        public String getPretty() {
            return pretty;
        }

        public Item getItem() {
            return item;
        }
    }
}
