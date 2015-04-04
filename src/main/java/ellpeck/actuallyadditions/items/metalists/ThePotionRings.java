package ellpeck.actuallyadditions.items.metalists;

import ellpeck.actuallyadditions.util.INameableItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public enum ThePotionRings implements INameableItem{

    SPEED("Speed", 8171462, 1, 0, 3, 10, false, EnumRarity.uncommon, new ItemStack(Items.sugar)),
    //TODO Slowness
    HASTE("Haste", 14270531, 3, 0, 3, 10, false, EnumRarity.epic, new ItemStack(Items.repeater)),
    //TODO Mining Fatigue
    STRENGTH("Strength", 9643043, 5, 0, 3, 10, false, EnumRarity.rare, new ItemStack(Items.blaze_powder)),
    //Health (Not Happening)
    //TODO Damage
    JUMP_BOOST("JumpBoost", 7889559, 8, 0, 3, 10, false, EnumRarity.rare, new ItemStack(Blocks.piston)),
    //TODO Nausea
    REGEN("Regen", 13458603, 10, 0, 3, 50, true, EnumRarity.rare, new ItemStack(Items.ghast_tear)),
    RESISTANCE("Resistance", 10044730, 11, 0, 3, 10, false, EnumRarity.epic, new ItemStack(Items.slime_ball)),
    FIRE_RESISTANCE("FireResistance", 14981690, 12, 0, 0, 10, false, EnumRarity.uncommon, new ItemStack(Items.magma_cream)),
    WATER_BREATHING("WaterBreathing", 3035801, 13, 0, 0, 10, false, EnumRarity.rare, new ItemStack(Items.fish, 1, 3)),
    INVISIBILITY("Invisibility", 8356754, 14, 0, 0, 10, false, EnumRarity.epic, new ItemStack(Items.fermented_spider_eye)),
    //TODO Blindness
    NIGHT_VISION("NightVision", 2039713, 16, 0, 0, 300, false, EnumRarity.rare, new ItemStack(Items.golden_carrot)),
    //TODO Hunger
    //TODO Weakness
    //TODO Poison
    //TODO Withering
    //Health Boost (Not Happening)
    //Absorption (Not Happening)
    SATURATION("Saturation", 16262179, 23, 0, 3, 10, false, EnumRarity.epic, new ItemStack(Items.cooked_beef));

    public final String name;
    public final int color;
    public final EnumRarity rarity;
    public final int effectID;
    public final int normalAmplifier;
    public final int advancedAmplifier;
    public final int activeTime;
    public final boolean needsWaitBeforeActivating;
    public final ItemStack craftingItem;

    ThePotionRings(String name, int color, int effectID, int normalAmplifier, int advancedAmplifier, int activeTime, boolean needsWaitBeforeActivating, EnumRarity rarity, ItemStack craftingItem){
        this.name = name;
        this.color = color;
        this.rarity = rarity;
        this.effectID = effectID;
        this.normalAmplifier = normalAmplifier;
        this.advancedAmplifier = advancedAmplifier;
        this.activeTime = activeTime;
        this.needsWaitBeforeActivating = needsWaitBeforeActivating;
        this.craftingItem = craftingItem;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getOredictName(){
        return "itemPotionRing";
    }
}