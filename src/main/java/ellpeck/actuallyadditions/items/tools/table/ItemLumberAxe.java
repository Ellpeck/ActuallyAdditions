/*
 * This file ("ItemLumberAxe.java") is part of the Actually Additions Mod for Minecraft.
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
import ellpeck.actuallyadditions.items.tools.ItemAxeAA;
import ellpeck.actuallyadditions.util.ModUtil;
import ellpeck.actuallyadditions.util.StringUtil;
import ellpeck.actuallyadditions.util.WorldPos;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemLumberAxe extends ItemAxeAA implements IToolTableRepairItem{

    private final int maxToolDamage;

    private ItemStack repairStack;
    private int repairPerStack;

    private IIcon iconBroken;

    public ItemLumberAxe(ToolMaterial toolMat, String unlocalizedName, EnumRarity rarity, ItemStack repairStack, int repairPerStack){
        super(toolMat, "", unlocalizedName, rarity);
        this.maxToolDamage = this.getMaxDamage();
        this.setMaxDamage(this.maxToolDamage+1);
        this.repairStack = repairStack;
        this.repairPerStack = repairPerStack;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player){

        return false;
    }

    private ArrayList<WorldPos> getTreeBlocksToBreak(World world, int startX, int startY, int startZ){
        ArrayList<WorldPos> positions = new ArrayList<WorldPos>();
        int range = 3;
        for(int x = -range; x < range+1; x++){
            for(int z = -range; z < range+1; z++){
                for(int y = -range; y < range+1; y++){
                    int theX = startX+x;
                    int theY = startY+y;
                    int theZ = startZ+z;


                }
            }
        }
        return positions;
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
