package ellpeck.actuallyadditions.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemSwordAA extends ItemSword implements INameableItem{

    private String name;
    private String oredictName;
    private EnumRarity rarity;
    private String repairItem;

    public ItemSwordAA(ToolMaterial toolMat, String repairItem, String unlocalizedName, EnumRarity rarity){
        super(toolMat);
        this.name = unlocalizedName;
        this.rarity = rarity;
        this.repairItem = repairItem;
        this.oredictName = name;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        if(KeyUtil.isShiftPressed()){
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+".desc"));
            list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".durability.desc") + ": " + (this.getMaxDamage()-this.getDamage(stack)) + "/" + this.getMaxDamage());
        }
        else list.add(ItemUtil.shiftForInfo());
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        int[] idsStack = OreDictionary.getOreIDs(stack);
        for(int id : idsStack){
            if(OreDictionary.getOreName(id).equals(repairItem)) return true;
        }
        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public String getName(){
        return name;
    }

    private String getOredictName(){
        return oredictName;
    }
}
