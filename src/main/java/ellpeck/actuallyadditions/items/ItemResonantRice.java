package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemResonantRice extends Item implements INameableItem{

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            stack.stackSize--;
            world.createExplosion(null, player.posX, player.posY, player.posZ, 0.5F, true);
        }
        return stack;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        ItemUtil.addInformation(this, list, 1, "");
        if(KeyUtil.isShiftPressed() && OreDictionary.getOres("nuggetEnderium", false).isEmpty()) list.add(StringUtil.RED + StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + ".itemResonantRice.uncraftable.desc"));
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
        return "itemResonantRice";
    }

    private String getOredictName(){
        return this.getName();
    }
}
