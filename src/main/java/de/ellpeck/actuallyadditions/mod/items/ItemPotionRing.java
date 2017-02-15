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
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.ThePotionRings;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.ModUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

public class ItemPotionRing extends ItemBase implements IColorProvidingItem, IDisplayStandItem{

    public static final ThePotionRings[] ALL_RINGS = ThePotionRings.values();

    public static final int MAX_BLAZE = 800;
    private final boolean isAdvanced;

    public ItemPotionRing(boolean isAdvanced, String name){
        super(name);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.isAdvanced = isAdvanced;
    }

    public static int getStoredBlaze(ItemStack stack){
        if(!StackUtil.isValid(stack) || !stack.hasTagCompound()){
            return 0;
        }
        else{
            return stack.getTagCompound().getInteger("Blaze");
        }
    }

    public static void setStoredBlaze(ItemStack stack, int amount){
        if(StackUtil.isValid(stack)){
            if(!stack.hasTagCompound()){
                stack.setTagCompound(new NBTTagCompound());
            }
            stack.getTagCompound().setInteger("Blaze", amount);
        }
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        double diff = MAX_BLAZE-getStoredBlaze(stack);
        return diff/MAX_BLAZE;
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack){
        int curr = getStoredBlaze(stack);
        return MathHelper.hsvToRGB(Math.max(0.0F, (float)curr/MAX_BLAZE)/3.0F, 1.0F, 1.0F);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= ALL_RINGS.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+ALL_RINGS[stack.getItemDamage()].name;
    }

    @Override
    public boolean showDurabilityBar(ItemStack itemStack){
        return true;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5){
        super.onUpdate(stack, world, player, par4, par5);

        if(!world.isRemote && stack.getItemDamage() < ALL_RINGS.length){
            if(player instanceof EntityPlayer){
                EntityPlayer thePlayer = (EntityPlayer)player;

                int storedBlaze = getStoredBlaze(stack);
                if(storedBlaze > 0){
                    ItemStack equippedStack = thePlayer.getHeldItemMainhand();
                    ItemStack offhandStack = thePlayer.getHeldItemOffhand();

                    if(this.effectEntity(thePlayer, stack, (StackUtil.isValid(equippedStack) && stack == equippedStack) || (StackUtil.isValid(offhandStack) && stack == offhandStack))){
                        if(world.getTotalWorldTime()%10 == 0){
                            setStoredBlaze(stack, storedBlaze-1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged){
        return slotChanged || !ItemStack.areItemsEqual(oldStack, newStack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        String standardName = StringUtil.localize(this.getUnlocalizedName()+".name");
        if(stack.getItemDamage() < ALL_RINGS.length){
            String effect = StringUtil.localize(ALL_RINGS[stack.getItemDamage()].name);
            return standardName+" "+effect;
        }
        return standardName;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= ALL_RINGS.length ? EnumRarity.COMMON : ALL_RINGS[stack.getItemDamage()].rarity;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, NonNullList list){
        for(int j = 0; j < ALL_RINGS.length; j++){
            list.add(new ItemStack(this, 1, j));

            ItemStack full = new ItemStack(this, 1, j);
            setStoredBlaze(full, MAX_BLAZE);
            list.add(full);
        }
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < ALL_RINGS.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), "inventory");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IItemColor getItemColor(){
        return new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex){
                return stack.getItemDamage() >= ALL_RINGS.length ? 0xFFFFFF : ALL_RINGS[stack.getItemDamage()].color;
            }
        };
    }

    @Override
    public boolean update(ItemStack stack, TileEntity tile, int elapsedTicks){
        boolean advanced = ((ItemPotionRing)stack.getItem()).isAdvanced;
        int range = advanced ? 48 : 16;
        List<EntityLivingBase> entities = tile.getWorld().getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(tile.getPos().getX()-range, tile.getPos().getY()-range, tile.getPos().getZ()-range, tile.getPos().getX()+range, tile.getPos().getY()+range, tile.getPos().getZ()+range));
        if(entities != null && !entities.isEmpty()){
            if(advanced){
                //Give all entities the effect
                for(EntityLivingBase entity : entities){
                    this.effectEntity(entity, stack, true);
                }
                return true;
            }
            else{
                Potion potion = Potion.getPotionById(ThePotionRings.values()[stack.getItemDamage()].effectID);
                for(EntityLivingBase entity : entities){
                    if(entity.isPotionActive(potion)){
                        //Sometimes make the effect switch to someone else
                        if(tile.getWorld().rand.nextInt(100) <= 0){
                            entity.removePotionEffect(potion);
                            break;
                        }
                        else{
                            //Continue giving the entity that already has the potion effect the effect
                            //Otherwise, it will randomly switch around to other entities
                            this.effectEntity(entity, stack, true);
                            return true;
                        }
                    }
                }

                //Give the effect to someone new if no one had it or it randomly switched
                Collections.shuffle(entities);
                this.effectEntity(entities.get(0), stack, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getUsePerTick(ItemStack stack, TileEntity tile, int elapsedTicks){
        return 325;
    }

    private boolean effectEntity(EntityLivingBase thePlayer, ItemStack stack, boolean canUseBasic){
        ThePotionRings effect = ThePotionRings.values()[stack.getItemDamage()];
        Potion potion = Potion.getPotionById(effect.effectID);
        PotionEffect activeEffect = thePlayer.getActivePotionEffect(potion);
        if(!effect.needsWaitBeforeActivating || (activeEffect == null || activeEffect.getDuration() <= 1)){
            if(!((ItemPotionRing)stack.getItem()).isAdvanced){
                if(canUseBasic){
                    thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.normalAmplifier, true, false));
                    return true;
                }
            }
            else{
                thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.advancedAmplifier, true, false));
                return true;
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced){
        super.addInformation(stack, playerIn, tooltip, advanced);

        tooltip.add(String.format("%d/%d %s",this.getStoredBlaze(stack) , MAX_BLAZE, StringUtil.localize("item."+ModUtil.MOD_ID+".item_misc_ring.storage")));
    }
}
