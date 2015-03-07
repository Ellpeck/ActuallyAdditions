package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.creative.CreativeTab;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.Util;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoods extends ItemFood implements IName{

    public static final TheFoods[] allFoods = TheFoods.values();
    public IIcon[] textures = new IIcon[allFoods.length];

    public ItemFoods(){
        super(0, 0.0F, false);
        this.setUnlocalizedName(Util.setUnlocalizedName(this));
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTab.instance);
        this.setAlwaysEdible();
        TheFoods.setReturnItems();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return allFoods[stack.getItemDamage()].rarity;
    }

    @Override
    public int func_150905_g(ItemStack stack){
        return allFoods[stack.getItemDamage()].healAmount;
    }

    @Override
    public float func_150906_h(ItemStack stack){
        return allFoods[stack.getItemDamage()].saturation;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return allFoods[stack.getItemDamage()].getsDrunken ? EnumAction.drink : EnumAction.eat;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return allFoods[stack.getItemDamage()].useDuration;
    }

    @Override
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

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allFoods[stack.getItemDamage()].getName();
    }

    @Override
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

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(Util.isShiftPressed()){
            list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + "." + this.getName() + allFoods[stack.getItemDamage()].getName() + ".desc"));
            list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + ".hunger.desc") + ": " + allFoods[stack.getItemDamage()].healAmount);
            list.add(StatCollector.translateToLocal("tooltip." + Util.MOD_ID_LOWER + ".saturation.desc") + ": " + allFoods[stack.getItemDamage()].saturation);
        }
        else list.add(Util.shiftForInfo());
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(Util.MOD_ID_LOWER + ":" + this.getName() + allFoods[i].getName());
        }
    }

    @Override
    public String getName(){
        return "itemFood";
    }
}