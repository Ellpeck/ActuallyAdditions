package de.ellpeck.actuallyadditions.mod;

import de.ellpeck.actuallyadditions.mod.attachments.ActuallyAttachments;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import de.ellpeck.actuallyadditions.mod.items.base.ItemEnergy;
import de.ellpeck.actuallyadditions.mod.tile.CustomEnergyStorage;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ActuallyTabs {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ActuallyAdditions.MODID);

	public static final Supplier<CreativeModeTab> GROUP = CREATIVE_MODE_TABS.register("tab", () -> CreativeModeTab.builder()
			.icon(() -> ActuallyItems.ITEM_BOOKLET.get().getDefaultInstance())
			.withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
			.title(Component.translatable("itemGroup.actuallyadditions"))
			.displayItems((parameters, output) -> {
				List<ItemStack> stacks = ActuallyItems.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get())).collect(Collectors.toList());

				//Add charged versions of all energy items
				List<ItemStack> charged = ActuallyItems.ITEMS.getEntries().stream().map(reg -> new ItemStack(reg.get()))
						.filter(stack -> stack.getItem() instanceof ItemEnergy).toList();
				charged.forEach(stack -> {
					if(stack.getItem() instanceof ItemEnergy itemEnergy) {
						CustomEnergyStorage storage = new CustomEnergyStorage(itemEnergy.maxPower, itemEnergy.transfer, itemEnergy.transfer);
						storage.setEnergyStored(itemEnergy.maxPower);
						stack.setData(ActuallyAttachments.ENERGY_STORAGE.get(), storage);
					}
				});
				stacks.addAll(charged);

				output.acceptAll(stacks);
			}).build());

	public static void init(IEventBus evt) {
		CREATIVE_MODE_TABS.register(evt);
	}
}
