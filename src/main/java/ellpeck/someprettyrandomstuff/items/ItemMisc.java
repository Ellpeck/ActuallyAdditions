package ellpeck.someprettyrandomstuff.items;

import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.metalists.TheMiscItems;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMisc extends Item{

    public static final TheMiscItems[] allMiscItems = TheMiscItems.values();

    public ItemMisc(){
        this.setUnlocalizedName("itemMisc");
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTab.instance);
    }

    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allMiscItems.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allMiscItems[stack.getItemDamage()].name;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName(stack).substring(5) + ".desc"));
        else list.add(Util.shiftForInfo());
    }
}
