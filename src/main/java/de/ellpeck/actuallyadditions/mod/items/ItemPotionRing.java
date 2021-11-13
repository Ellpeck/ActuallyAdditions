/*
 * This file ("ItemPotionRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2017 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.ThePotionRings;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPotionRing extends ItemBase implements IDisplayStandItem {

    public static final ThePotionRings[] ALL_RINGS = ThePotionRings.values();

    public static final int MAX_BLAZE = 800;
    private final boolean isAdvanced;

    public ItemPotionRing(boolean isAdvanced) {
        super(ActuallyItems.defaultProps().stacksTo(1));
        this.isAdvanced = isAdvanced;
    }

    public static int getStoredBlaze(ItemStack stack) {
        if (!StackUtil.isValid(stack) || !stack.hasTag()) {
            return 0;
        } else {
            return stack.getOrCreateTag().getInt("Blaze");
        }
    }

    public static void setStoredBlaze(ItemStack stack, int amount) {
        if (StackUtil.isValid(stack)) {
            stack.getOrCreateTag().putInt("Blaze", amount);
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        double diff = MAX_BLAZE - getStoredBlaze(stack);
        return diff / MAX_BLAZE;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        int curr = getStoredBlaze(stack);
        return MathHelper.hsvToRgb(Math.max(0.0F, (float) curr / MAX_BLAZE) / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack) {
        return true;
    }

     @Override
    public void inventoryTick(ItemStack stack, World world, Entity player, int par4, boolean par5) {
        super.inventoryTick(stack, world, player, par4, par5);
/*
        if (!world.isClientSide && stack.getItemDamage() < ALL_RINGS.length) {
            if (player instanceof PlayerEntity) {
                PlayerEntity thePlayer = (PlayerEntity) player;

                int storedBlaze = getStoredBlaze(stack);
                if (storedBlaze > 0) {
                    ItemStack equippedStack = thePlayer.getMainHandItem();
                    ItemStack offhandStack = thePlayer.getOffhandItem();

                    if (this.effectEntity(thePlayer, stack, StackUtil.isValid(equippedStack) && stack == equippedStack || StackUtil.isValid(offhandStack) && stack == offhandStack)) {
                        if (world.getTotalWorldTime() % 10 == 0) {
                            setStoredBlaze(stack, storedBlaze - 1);
                        }
                    }
                }
            }
        }

 */
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || !ItemStack.isSame(oldStack, newStack);
    }
/*
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (Util.isClient()) {
            String standardName = StringUtil.localize(this.getDescriptionId() + ".name");
            if (stack.getItemDamage() < ALL_RINGS.length) {
                String effect = StringUtil.localize(ALL_RINGS[stack.getItemDamage()].name);
                return standardName + " " + effect;
            }
            return standardName;
        }
        String standardName = StringUtil.localizeIllegallyOnTheServerDontUseMePls(this.getDescriptionId() + ".name");
        if (stack.getItemDamage() < ALL_RINGS.length) {
            String effect = StringUtil.localizeIllegallyOnTheServerDontUseMePls(ALL_RINGS[stack.getItemDamage()].name);
            return standardName + " " + effect;
        }
        return standardName;
    }

 */
/*
    @Override
    public Rarity getRarity(ItemStack stack) {
        return stack.getItemDamage() >= ALL_RINGS.length
                ? EnumRarity.COMMON
                : ALL_RINGS[stack.getItemDamage()].rarity;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (this.isInCreativeTab(tab)) {
            for (int j = 0; j < ALL_RINGS.length; j++) {
                list.add(new ItemStack(this, 1, j));

                ItemStack full = new ItemStack(this, 1, j);
                setStoredBlaze(full, MAX_BLAZE);
                list.add(full);
            }
        }
    }

    @Override
    protected void registerRendering() {
        for (int i = 0; i < ALL_RINGS.length; i++) {
            ActuallyAdditions.PROXY.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), "inventory");
        }
    }

 */

    @Override
    public boolean update(ItemStack stack, TileEntity tile, int elapsedTicks) {
        boolean advanced = ((ItemPotionRing) stack.getItem()).isAdvanced;
        int range = advanced
                ? 48
                : 16;
        List<LivingEntity> entities = tile.getLevel().getEntitiesOfClass(LivingEntity.class, new AxisAlignedBB(tile.getBlockPos().getX() - range, tile.getBlockPos().getY() - range, tile.getBlockPos().getZ() - range, tile.getBlockPos().getX() + range, tile.getBlockPos().getY() + range, tile.getBlockPos().getZ() + range));
        if (entities != null && !entities.isEmpty()) {
            if (advanced) {
                //Give all entities the effect
                for (LivingEntity entity : entities) {
                    this.effectEntity(entity, stack, true);
                }
                return true;
            } else {/*
                Potion potion = Potion.byName(ThePotionRings.values()[stack.getItemDamage()].effectID);
                for (EntityLivingBase entity : entities) {
                    if (entity.isPotionActive(potion)) {
                        //Sometimes make the effect switch to someone else
                        if (tile.getLevel().random.nextInt(100) <= 0) {
                            entity.removePotionEffect(potion);
                            break;
                        } else {
                            //Continue giving the entity that already has the potion effect the effect
                            //Otherwise, it will randomly switch around to other entities
                            this.effectEntity(entity, stack, true);
                            return true;
                        }
                    }
                    */
                return true;
                }

                //Give the effect to someone new if no one had it or it randomly switched
                //Collections.shuffle(entities);
                //this.effectEntity(entities.get(0), stack, true);
                //return true;
            }

        return false;
    }

    @Override
    public int getUsePerTick(ItemStack stack, TileEntity tile, int elapsedTicks) {
        return 325;
    }

    private boolean effectEntity(LivingEntity thePlayer, ItemStack stack, boolean canUseBasic) {
        /*
        ThePotionRings effect = ThePotionRings.values()[stack.getItemDamage()];
        Potion potion = Potion.getPotionById(effect.effectID);
        PotionEffect activeEffect = thePlayer.getActivePotionEffect(potion);
        if (!effect.needsWaitBeforeActivating || activeEffect == null || activeEffect.getDuration() <= 1) {
            if (!((ItemPotionRing) stack.getItem()).isAdvanced) {
                if (canUseBasic) {
                    thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.normalAmplifier, true, false));
                    return true;
                }
            } else {
                thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.advancedAmplifier, true, false));
                return true;
            }
        }

         */
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World playerIn, List<ITextComponent> tooltip, ITooltipFlag advanced) {
        super.appendHoverText(stack, playerIn, tooltip, advanced);
        //tooltip.add(String.format("%d/%d %s", getStoredBlaze(stack), MAX_BLAZE, StringUtil.localize("item." + ActuallyAdditions.MODID + ".item_misc_ring.storage")));
    }
}
