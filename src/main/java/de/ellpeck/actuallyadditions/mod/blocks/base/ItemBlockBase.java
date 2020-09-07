package de.ellpeck.actuallyadditions.mod.blocks.base;

import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

public class ItemBlockBase extends ItemBlock {

    public ItemBlockBase(Block block) {
        super(block);
        this.setHasSubtypes(false);
        this.setMaxDamage(0);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return this.getTranslationKey();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        if (this.block instanceof ICustomRarity) {
            return ((ICustomRarity) this.block).getRarity(stack);
        } else {
            return Util.FALLBACK_RARITY;
        }
    }

    public interface ICustomRarity {

        IRarity getRarity(ItemStack stack);

    }
}