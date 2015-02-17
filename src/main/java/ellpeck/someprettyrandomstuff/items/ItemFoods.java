package ellpeck.someprettyrandomstuff.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.someprettyrandomstuff.creative.CreativeTab;
import ellpeck.someprettyrandomstuff.items.metalists.TheFoods;
import ellpeck.someprettyrandomstuff.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoods extends ItemFood{

    public static final TheFoods[] allFoods = TheFoods.values();
    public IIcon[] textures = new IIcon[allFoods.length];

    public ItemFoods(){
        super(0, 0.0F, false);
        this.setUnlocalizedName("itemFood");
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTab.instance);
        this.setAlwaysEdible();
        TheFoods.setReturnItems();
    }

    public int func_150905_g(ItemStack stack){
        return allFoods[stack.getItemDamage()].healAmount;
    }

    public float func_150906_h(ItemStack stack){
        return allFoods[stack.getItemDamage()].saturation;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return allFoods[stack.getItemDamage()].getsDrunken ? EnumAction.drink : EnumAction.eat;
    }

    public int getMaxItemUseDuration(ItemStack stack){
        return allFoods[stack.getItemDamage()].useDuration;
    }

    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allFoods.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allFoods[stack.getItemDamage()].name;
    }

    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack stackToReturn = super.onEaten(stack, world, player);
        ItemStack returnItem = allFoods[stack.getItemDamage()].returnItem;
        if (returnItem != null){
            if(!player.inventory.addItemStackToInventory(returnItem.copy())){
                if(!world.isRemote){
                    EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem.copy());
                    entityItem.delayBeforeCanPickup = 0;
                    player.worldObj.spawnEntityInWorld(entityItem);
                }
            }
        }
        return stackToReturn;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + this.getUnlocalizedName(stack).substring(5) + ".desc"));
            list.add(StatCollector.translateToLocal("tooltip.hunger.desc") + ": " + allFoods[stack.getItemDamage()].healAmount);
            list.add(StatCollector.translateToLocal("tooltip.saturation.desc") + ": " + allFoods[stack.getItemDamage()].saturation);
        }
        else list.add(Util.shiftForInfo());
    }

    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + Util.getSubbedUnlocalized(this) + allFoods[i].getName());
        }
    }
}