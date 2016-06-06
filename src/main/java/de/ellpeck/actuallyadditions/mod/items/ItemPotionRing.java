/*
 * This file ("ItemPotionRing.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.items;

import de.ellpeck.actuallyadditions.api.misc.IDisplayStandItem;
import de.ellpeck.actuallyadditions.mod.ActuallyAdditions;
import de.ellpeck.actuallyadditions.mod.items.base.ItemBase;
import de.ellpeck.actuallyadditions.mod.items.metalists.ThePotionRings;
import de.ellpeck.actuallyadditions.mod.util.IColorProvidingItem;
import de.ellpeck.actuallyadditions.mod.util.StringUtil;
import de.ellpeck.actuallyadditions.mod.util.Util;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;
import java.util.List;

public class ItemPotionRing extends ItemBase implements IColorProvidingItem, IDisplayStandItem{

    public static final ThePotionRings[] allRings = ThePotionRings.values();

    private final boolean isAdvanced;

    public ItemPotionRing(boolean isAdvanced, String name){
        super(name);
        this.setHasSubtypes(true);
        this.setMaxStackSize(1);
        this.isAdvanced = isAdvanced;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }


    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allRings.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allRings[stack.getItemDamage()].name;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5){
        super.onUpdate(stack, world, player, par4, par5);

        if(!world.isRemote && stack.getItemDamage() < allRings.length){
            if(player instanceof EntityPlayer){
                EntityPlayer thePlayer = (EntityPlayer)player;
                ItemStack equippedStack = thePlayer.getHeldItemMainhand();
                this.effectEntity(thePlayer, stack, equippedStack != null && stack == equippedStack);
            }
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        String standardName = StringUtil.localize(this.getUnlocalizedName()+".name");
        if(stack.getItemDamage() < allRings.length){
            String effect = StringUtil.localize(allRings[stack.getItemDamage()].name);
            return standardName+" "+effect;
        }
        return standardName;
    }


    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allRings.length ? EnumRarity.COMMON : allRings[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allRings.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    protected void registerRendering(){
        for(int i = 0; i < allRings.length; i++){
            ActuallyAdditions.proxy.addRenderRegister(new ItemStack(this, 1, i), this.getRegistryName(), "inventory");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IItemColor getColor(){
        return new IItemColor(){
            @Override
            public int getColorFromItemstack(ItemStack stack, int tintIndex){
                return stack.getItemDamage() >= allRings.length ? 0xFFFFFF : allRings[stack.getItemDamage()].color;
            }
        };
    }

    @Override
    public boolean update(ItemStack stack, TileEntity tile, int elapsedTicks){
        boolean advanced = ((ItemPotionRing)stack.getItem()).isAdvanced;
        int range = advanced ? 96 : 16;
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
                        if(Util.RANDOM.nextInt(100) <= 0){
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

    private void effectEntity(EntityLivingBase thePlayer, ItemStack stack, boolean canUseBasic){
        ThePotionRings effect = ThePotionRings.values()[stack.getItemDamage()];
        Potion potion = Potion.getPotionById(effect.effectID);
        PotionEffect activeEffect = thePlayer.getActivePotionEffect(potion);
        if(!effect.needsWaitBeforeActivating || (activeEffect == null || activeEffect.getDuration() <= 1)){
            if(!((ItemPotionRing)stack.getItem()).isAdvanced){
                if(canUseBasic){
                    thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.normalAmplifier, true, false));
                }
            }
            else{
                thePlayer.addPotionEffect(new PotionEffect(potion, effect.activeTime, effect.advancedAmplifier, true, false));
            }
        }
    }
}
