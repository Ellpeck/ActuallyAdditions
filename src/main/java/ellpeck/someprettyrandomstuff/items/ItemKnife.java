package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.config.ConfigValues;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.util.IInformation;
import ellpeck.someprettyrandomstuff.util.IName;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemKnife extends Item implements IName, IInformation{

    public ItemKnife(){
        this.setUnlocalizedName(Util.setUnlocalizedName(this));
        this.setMaxDamage(ConfigValues.itemKnifeMaxDamage);
        this.setCreativeTab(CreativeTab.instance);
        this.setMaxStackSize(1);
        this.setContainerItem(this);
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack){
        ItemStack theStack = stack.copy();
        theStack.setItemDamage(theStack.getItemDamage() + 1);
        theStack.stackSize = 1;
        return theStack;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        list.add(Util.addStandardInformation(this));
    }

    @Override
    public boolean getShareTag(){
        return true;
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
    public String getName(){
        return "itemKnife";
    }

    @Override
    public String[] getInformationString(){
        return new String[]{"This is a test too but it's more cool senn se assa"};
    }
}
