/*
 * This file ("ItemSwordFixedEnchants.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.items.tools.table;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.tools.ItemSwordAA;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemSwordFixedEnchants extends ItemSwordAA implements IToolTableRepairItem{

    private final int maxToolDamage;
    private final EnchantmentCombo[] enchantments;

    private ItemStack repairStack;
    private int repairPerStack;

    private IIcon iconBroken;

    public ItemSwordFixedEnchants(ToolMaterial toolMat, String unlocalizedName, EnumRarity rarity, ItemStack repairStack, int repairPerStack, EnchantmentCombo... enchantments){
        super(toolMat, "", unlocalizedName, rarity);
        this.enchantments = enchantments;
        this.maxToolDamage = this.getMaxDamage();
        this.setMaxDamage(this.maxToolDamage+1);
        this.repairStack = repairStack;
        this.repairPerStack = repairPerStack;
    }

    public boolean isBroken(ItemStack stack){
        return this.isBroken(stack.getItemDamage());
    }

    private boolean isBroken(int damage){
        return damage > this.maxToolDamage;
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass){
        return false;
    }

    @Override
    public boolean isRepairable(){
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack stack1, ItemStack stack2){
        return this.isRepairable();
    }

    @Override
    public int getItemEnchantability(ItemStack stack){
        return 0;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player){
        for(EnchantmentCombo combo : this.enchantments){
            stack.addEnchantment(combo.enchantment, combo.level);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5){
        for(EnchantmentCombo combo : this.enchantments){
            if(!ItemUtil.hasEnchantment(stack, combo.enchantment)){
                stack.addEnchantment(combo.enchantment, combo.level);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        ItemStack stack = new ItemStack(item);
        for(EnchantmentCombo combo : this.enchantments){
            stack.addEnchantment(combo.enchantment, combo.level);
        }
        list.add(stack);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack){
        return super.getItemStackDisplayName(stack)+(this.isBroken(stack) ? " ("+StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".broken.desc")+")" : "");
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta){
        return this.isBroken(stack) ? 0.0F : super.getDigSpeed(stack, block, meta);
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack){
        return !this.isBroken(stack) && super.canHarvestBlock(block, stack);
    }

    @Override
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack useItem, int useRemaining){
        return this.isBroken(stack) ? this.iconBroken : this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int damage){
        return this.isBroken(damage) ? this.iconBroken : this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.iconBroken = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName()+"Broken");
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public ItemStack getRepairStack(){
        return this.repairStack;
    }

    @Override
    public int repairPerStack(){
        return this.repairPerStack;
    }
}
