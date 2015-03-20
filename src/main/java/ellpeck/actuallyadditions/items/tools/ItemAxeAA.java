package ellpeck.actuallyadditions.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemAxeAA extends ItemAxe implements IName{

    private String name;
    private EnumRarity rarity;
    private ItemStack repairItem;

    public ItemAxeAA(ToolMaterial toolMat, ItemStack repairItem, String unlocalizedName, EnumRarity rarity){
        super(toolMat);
        this.name = unlocalizedName;
        this.rarity = rarity;
        this.repairItem = repairItem;
        this.setUnlocalizedName(Util.setUnlocalizedName(this));
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
        if(Util.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + ".durability.desc") + ": " + (this.getMaxDamage()-this.getDamage(stack)) + "/" + this.getMaxDamage());
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        return stack.getItem() == repairItem.getItem();
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return this.rarity;
    }

    @Override
    public String getName(){
        return name;
    }
}
