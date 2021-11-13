/*
 * This file ("ItemFoods.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemFoodBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.TheFoods;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class ItemFoods extends ItemFoodBase {

    public static final TheFoods[] ALL_FOODS = TheFoods.values();

    public ItemFoods() {
        super(0, 0.0F, false, name);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        TheFoods.setReturnItems();
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase player) {
        ItemStack stackToReturn = super.finishUsingItem(stack, world, player);
        ItemStack returnItem = stack.getItemDamage() >= ALL_FOODS.length
            ? null
            : ALL_FOODS[stack.getItemDamage()].returnItem;
        if (StackUtil.isValid(returnItem) && player instanceof PlayerEntity) {
            if (!((PlayerEntity) player).inventory.add(returnItem.copy())) {
                if (!world.isClientSide) {
                    ItemEntity entityItem = new ItemEntity(player.world, player.posX, player.posY, player.posZ, returnItem.copy());
                    entityItem.setPickUpDelay(0);
                    player.world.addEntity(entityItem);
                }
            }
        }
        return stackToReturn;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? 0
            : ALL_FOODS[stack.getItemDamage()].useDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? EnumAction.EAT
            : ALL_FOODS[stack.getItemDamage()].getsDrunken
                ? EnumAction.DRINK
                : EnumAction.EAT;
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? 0
            : ALL_FOODS[stack.getItemDamage()].healAmount;
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? 0
            : ALL_FOODS[stack.getItemDamage()].saturation;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? StringUtil.BUGGED_ITEM_NAME
            : this.getDescriptionId() + "_" + ALL_FOODS[stack.getItemDamage()].name;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_FOODS.length
            ? Rarity.COMMON
            : ALL_FOODS[stack.getItemDamage()].rarity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < ALL_FOODS.length; j++) {
                list.add(new ItemStack(this, 1, j));
            }
        }
    }

    @Override
    protected void registerRendering() {
        for (int i = 0; i < ALL_FOODS.length; i++) {
            String name = this.getRegistryName() + "_" + ALL_FOODS[i].name;
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), new ModelResourceLocation(name), "inventory");
        }
    }
}
