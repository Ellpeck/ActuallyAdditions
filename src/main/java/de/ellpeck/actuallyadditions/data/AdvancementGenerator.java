package de.ellpeck.actuallyadditions.data;

import de.ellpeck.actuallyadditions.api.ActuallyTags;
import de.ellpeck.actuallyadditions.mod.blocks.ActuallyBlocks;
import de.ellpeck.actuallyadditions.mod.items.ActuallyItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.data.AdvancementProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public class AdvancementGenerator extends AdvancementProvider {
	public AdvancementGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, exFileHelper);
	}

	@Override
	protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper exFileHelper) {
		Advancement root = Advancement.Builder.advancement()
				.display(new DisplayInfo(ActuallyItems.ITEM_BOOKLET.get().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.openBooklet"),
						new TranslationTextComponent("achievement.actuallyadditions.openBooklet.desc"),
						new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), FrameType.TASK, true, true, false))
				.addCriterion("right_click", new ImpossibleTrigger.Instance())
				.save(consumer, "actuallyadditions:root");

		//TODO: Underwater Treasure Chest Advancement?

		Advancement phantomFace = Advancement.Builder.advancement()
				.parent(root)
				.display(new DisplayInfo(ActuallyBlocks.PHANTOM_ITEMFACE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftPhantomface"),
						new TranslationTextComponent("achievement.actuallyadditions.craftPhantomface.desc"),
						new ResourceLocation("textures/blocks/stone.png"), FrameType.TASK, true, true, false))
				.addCriterion("phantom_face", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.PHANTOM_ITEMFACE.get()))
				.save(consumer, "actuallyadditions:craft_phantom_face");


		Advancement.Builder.advancement()
				.parent(phantomFace)
				.display(new DisplayInfo(ActuallyBlocks.PHANTOM_LIQUIFACE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLiquiface"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLiquiface.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("liquiface", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.PHANTOM_LIQUIFACE.get()))
				.save(consumer, "actuallyadditions:craft_liquiface");

		Advancement.Builder.advancement()
				.parent(phantomFace)
				.display(new DisplayInfo(ActuallyBlocks.PHANTOM_ENERGYFACE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftEnergyface"),
						new TranslationTextComponent("achievement.actuallyadditions.craftEnergyface.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("energyface", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.PHANTOM_ENERGYFACE.get()))
				.save(consumer, "actuallyadditions:craft_energyface");


		Advancement coalGenerator = Advancement.Builder.advancement()
				.parent(root)
				.display(new DisplayInfo(ActuallyBlocks.COAL_GENERATOR.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftCoalGen"),
						new TranslationTextComponent("achievement.actuallyadditions.craftCoalGen.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("coal_generator", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.COAL_GENERATOR.get()))
				.save(consumer, "actuallyadditions:craft_coal_generator");

		Advancement.Builder.advancement()
				.parent(coalGenerator)
				.display(new DisplayInfo(ActuallyBlocks.LEAF_GENERATOR.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLeafGen"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLeafGen.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("leaf_generator", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.LEAF_GENERATOR.get()))
				.save(consumer, "actuallyadditions:craft_leaf_generator");

		Advancement coffeeBeans = Advancement.Builder.advancement()
				.parent(coalGenerator)
				.display(new DisplayInfo(ActuallyItems.COFFEE_BEANS.get().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.pickUpCoffee"),
						new TranslationTextComponent("achievement.actuallyadditions.pickUpCoffee.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("coffee_beans", InventoryChangeTrigger.Instance.hasItems(ActuallyItems.COFFEE_BEANS.get()))
				.save(consumer, "actuallyadditions:pickup_coffee");

		Advancement.Builder.advancement()
				.parent(coffeeBeans)
				.display(new DisplayInfo(ActuallyBlocks.COFFEE_MACHINE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftCoffeeMachine"),
						new TranslationTextComponent("achievement.actuallyadditions.craftCoffeeMachine.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("coffee_machine", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.COFFEE_MACHINE.get()))
				.save(consumer, "actuallyadditions:craft_coffee_machine");

		Advancement reconstructor = Advancement.Builder.advancement()
				.parent(coalGenerator)
				.display(new DisplayInfo(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftReconstructor"),
						new TranslationTextComponent("achievement.actuallyadditions.craftReconstructor.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("atomic_reconstructor", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.ATOMIC_RECONSTRUCTOR.get()))
				.save(consumer, "actuallyadditions:craft_reconstructor");

		Advancement makeFirstCrystal = Advancement.Builder.advancement()
				.parent(reconstructor)
				.display(new DisplayInfo(ActuallyItems.EMERADIC_CRYSTAL.get().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.makeCrystal"),
						new TranslationTextComponent("achievement.actuallyadditions.makeCrystal.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("crystal", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyTags.Items.CRYSTALS).build())
				)
				.save(consumer, "actuallyadditions:make_first_crystal");

		Advancement.Builder.advancement()
				.parent(makeFirstCrystal)
				.display(new DisplayInfo(ActuallyBlocks.EMPOWERER.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftEmpowerer"),
						new TranslationTextComponent("achievement.actuallyadditions.craftEmpowerer.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("empowerer", InventoryChangeTrigger.Instance.hasItems(ActuallyBlocks.EMPOWERER.get()))
				.save(consumer, "actuallyadditions:craft_empowerer");

		Advancement craftCrusher = Advancement.Builder.advancement()
				.parent(reconstructor)
				.display(new DisplayInfo(ActuallyBlocks.CRUSHER.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftCrusher"),
						new TranslationTextComponent("achievement.actuallyadditions.craftCrusher.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("crystal", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.CRUSHER.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_crusher");

		Advancement craftDoubleCrusher = Advancement.Builder.advancement()
				.parent(craftCrusher)
				.display(new DisplayInfo(ActuallyBlocks.CRUSHER_DOUBLE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftDoubleCrusher"),
						new TranslationTextComponent("achievement.actuallyadditions.craftDoubleCrusher.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("crystal", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.CRUSHER_DOUBLE.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_double_crusher");

		Advancement craftLaserRelay = Advancement.Builder.advancement()
				.parent(reconstructor)
				.display(new DisplayInfo(ActuallyBlocks.LASER_RELAY.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelay"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelay.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("laser_relay", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.LASER_RELAY.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_laser_relay");

		Advancement craftLaserRelayAdvanced = Advancement.Builder.advancement()
				.parent(craftLaserRelay)
				.display(new DisplayInfo(ActuallyBlocks.LASER_RELAY_ADVANCED.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayAdvanced"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayAdvanced.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("laser_relay_advanced", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.LASER_RELAY_ADVANCED.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_laser_relay_advanced");

		Advancement craftLaserRelayExtreme = Advancement.Builder.advancement()
				.parent(craftLaserRelayAdvanced)
				.display(new DisplayInfo(ActuallyBlocks.LASER_RELAY_EXTREME.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayExtreme"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayExtreme.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("laser_relay_extreme", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.LASER_RELAY_EXTREME.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_laser_relay_extreme");

		Advancement craftLaserRelayItem = Advancement.Builder.advancement()
				.parent(craftLaserRelay)
				.display(new DisplayInfo(ActuallyBlocks.LASER_RELAY_ITEM.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayItem"),
						new TranslationTextComponent("achievement.actuallyadditions.craftLaserRelayItem.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("laser_relay_item", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.LASER_RELAY_ITEM.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_laser_relay_item");

		Advancement craftItemInterface = Advancement.Builder.advancement()
				.parent(craftLaserRelay)
				.display(new DisplayInfo(ActuallyBlocks.ITEM_INTERFACE.get().asItem().getDefaultInstance(),
						new TranslationTextComponent("achievement.actuallyadditions.craftItemInterface"),
						new TranslationTextComponent("achievement.actuallyadditions.craftItemInterface.desc"),
						null, FrameType.TASK, true, true, false))
				.addCriterion("item_interface", InventoryChangeTrigger.Instance.hasItems(
						ItemPredicate.Builder.item().of(ActuallyBlocks.ITEM_INTERFACE.get()).build())
				)
				.save(consumer, "actuallyadditions:craft_item_interface");
	}
}
