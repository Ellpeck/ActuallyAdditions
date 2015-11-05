/*
 * This file ("ItemWaterRemovalRing.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemWaterRemovalRing extends ItemEnergy{

    public ItemWaterRemovalRing(){
        super(1000000, 5000);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!(entity instanceof EntityPlayer) || world.isRemote){
            return;
        }

        EntityPlayer player = (EntityPlayer)entity;
        ItemStack equipped = player.getCurrentEquippedItem();

        if(equipped != null && equipped == stack && this.getEnergyStored(stack) >= ConfigIntValues.WATER_RING_ENERGY_USE.getValue()){

            //Setting everything to air
            for(int x = -ConfigIntValues.WATER_RING_RANGE.getValue(); x < ConfigIntValues.WATER_RING_RANGE.getValue()+1; x++){
                for(int z = -ConfigIntValues.WATER_RING_RANGE.getValue(); z < ConfigIntValues.WATER_RING_RANGE.getValue()+1; z++){
                    for(int y = -ConfigIntValues.WATER_RING_RANGE.getValue(); y < ConfigIntValues.WATER_RING_RANGE.getValue()+1; y++){
                        int theX = MathHelper.floor_double(player.posX+x);
                        int theY = MathHelper.floor_double(player.posY+y);
                        int theZ = MathHelper.floor_double(player.posZ+z);
                        if(this.getEnergyStored(stack) >= ConfigIntValues.WATER_RING_ENERGY_USE.getValue()){
                            //Remove Water
                            if(world.getBlock(theX, theY, theZ) == Blocks.water || world.getBlock(theX, theY, theZ) == Blocks.flowing_water){
                                world.setBlockToAir(theX, theY, theZ);

                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, ConfigIntValues.WATER_RING_ENERGY_USE.getValue(), false);
                                }
                            }
                            //Remove Lava
                            else if(world.getBlock(theX, theY, theZ) == Blocks.lava || world.getBlock(theX, theY, theZ) == Blocks.flowing_lava){
                                world.setBlockToAir(theX, theY, theZ);

                                if(!player.capabilities.isCreativeMode){
                                    this.extractEnergy(stack, ConfigIntValues.WATER_RING_ENERGY_USE.getValue()*2, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public String getName(){
        return "itemWaterRemovalRing";
    }
}
