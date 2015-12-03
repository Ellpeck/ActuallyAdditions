/*
 * This file ("ItemSpecialDrop.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.base.ItemBase;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemSpecialDrop extends ItemBase{

    public static final int SOLID_XP_AMOUNT = 8;

    public static final TheSpecialDrops[] allDrops = TheSpecialDrops.values();
    @SideOnly(Side.CLIENT)
    public IIcon[] textures;

    public ItemSpecialDrop(String name){
        super(name);
        this.setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1){
        return par1 >= textures.length ? null : textures[par1];
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            if(stack.getItemDamage() == TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()){
                if(!player.isSneaking()){
                    world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT));
                    if(!player.capabilities.isCreativeMode){
                        stack.stackSize--;
                    }
                }
                else{
                    world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT*stack.stackSize));
                    if(!player.capabilities.isCreativeMode){
                        stack.stackSize = 0;
                    }
                }
            }
        }
        return stack;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return stack.getItemDamage() >= allDrops.length ? StringUtil.BUGGED_ITEM_NAME : this.getUnlocalizedName()+allDrops[stack.getItemDamage()].name;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allDrops.length ? EnumRarity.common : allDrops[stack.getItemDamage()].rarity;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allDrops.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.textures = new IIcon[allDrops.length];
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getBaseName()+allDrops[i].name);
        }
    }
}
