package de.ellpeck.actuallyadditions.common.items;

import de.ellpeck.actuallyadditions.common.capability.CrystalFluxProvider;
import de.ellpeck.actuallyadditions.common.capability.CrystalFluxStorage;
import de.ellpeck.actuallyadditions.common.utilities.Help;
import de.ellpeck.actuallyadditions.common.utilities.VisualHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;
import java.text.NumberFormat;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class CrystalFluxItem extends ActuallyItem {
    // Handles modifying an int to a string of either alt ? 100,000 : 100K
    private static final BiFunction<Integer, Boolean, String> PRETTY = (value, alt) ->
            alt ? NumberFormat.getIntegerInstance().format(value) : Help.compressedValue(value);

    private final Supplier<Integer> maxFlux;

    /**
     * We use a supplier here to allow for config values to be passed around so we are able to
     * call the config values get method so we're always getting the most up-to-date value.
     *
     * @param maxFlux max energy this item can store
     */
    public CrystalFluxItem(Properties properties, Supplier<Integer> maxFlux) {
        super(properties);

        this.maxFlux = maxFlux;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new CrystalFluxProvider(stack, maxFlux.get());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(energy ->
                tooltip.add(this.getEnergyPretty(energy, Screen.hasShiftDown()).mergeStyle(TextFormatting.GRAY)));
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (!isInGroup(group)) {
            return;
        }

        ItemStack chargedItem = new ItemStack(this);
        chargedItem.getOrCreateTag().putInt(CrystalFluxStorage.NBT_TAG, this.maxFlux.get());
        items.add(chargedItem);
    }

    /**
     * Returns a tidy string either as 100,000 or 100K for example.
     *
     * @param energy energy capability
     * @return either a pretty value or a pretty compressed value
     */
    private TranslationTextComponent getEnergyPretty(IEnergyStorage energy, boolean showCompressed) {
        return Help.trans(
                "storage.crystal-flux",
                PRETTY.apply(energy.getEnergyStored(), showCompressed),
                PRETTY.apply(energy.getMaxEnergyStored(), showCompressed)
        );
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        ClientWorld clientWorld = Minecraft.getInstance().world;
        if (clientWorld != null) {
            float[] color = VisualHelper.getWheelColor(clientWorld.getGameTime() % 256);
            return MathHelper.rgb(color[0] / 255F, color[1] / 255F, color[2] / 255F);
        }

        return super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map(energy -> 1D - (energy.getEnergyStored() / (double) energy.getMaxEnergyStored()))
                .orElse(0D);
    }
}
