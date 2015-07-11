package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemArmorAA extends ItemArmor implements INameableItem{

    private ItemStack repairItem;
    private String name;
    private String[] textures;

    public ItemArmorAA(String name, ArmorMaterial material, int type, ItemStack repairItem, String textureBase){
        super(material, 0, type);
        this.repairItem = repairItem;
        this.name = name;
        String texture = ModUtil.MOD_ID_LOWER+":textures/armor/"+textureBase;
        textures = new String[]{texture+"1.png", texture+"2.png"};
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.rare;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        ItemUtil.addInformation(this, list, 1, "");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        return this.repairItem != null && stack.getItem() == this.repairItem.getItem();
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
        return this.textures[slot == 2 ? 1 : 0];
    }
}
