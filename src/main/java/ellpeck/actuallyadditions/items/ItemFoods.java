package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.metalists.TheFoods;
import ellpeck.actuallyadditions.util.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemFoods extends ItemFood implements INameableItem{

    public static final TheFoods[] allFoods = TheFoods.values();
    public IIcon[] textures = new IIcon[allFoods.length];

    public ItemFoods(){
        super(0, 0.0F, false);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        TheFoods.setReturnItems();
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? EnumRarity.common : allFoods[stack.getItemDamage()].rarity;
    }

    @Override
    public int func_150905_g(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].healAmount;
    }

    @Override
    public float func_150906_h(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].saturation;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? EnumAction.eat : (allFoods[stack.getItemDamage()].getsDrunken ? EnumAction.drink : EnumAction.eat);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack){
        return stack.getItemDamage() >= allFoods.length ? 0 : allFoods[stack.getItemDamage()].useDuration;
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
        return this.getUnlocalizedName() + (stack.getItemDamage() >= allFoods.length ? " ERROR!" : allFoods[stack.getItemDamage()].getName());
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack stackToReturn = super.onEaten(stack, world, player);
        ItemStack returnItem = stack.getItemDamage() >= allFoods.length ? null : allFoods[stack.getItemDamage()].returnItem;
        if(returnItem != null){
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
        if(stack.getItemDamage() < allFoods.length){
            if(KeyUtil.isShiftPressed()){
                list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+allFoods[stack.getItemDamage()].getName()+".desc"));
                list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".hunger.desc")+": "+allFoods[stack.getItemDamage()].healAmount);
                list.add(StringUtil.localize("tooltip."+ModUtil.MOD_ID_LOWER+".saturation.desc")+": "+allFoods[stack.getItemDamage()].saturation);
            }
            else list.add(ItemUtil.shiftForInfo());
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return par1 >= textures.length ? null : textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + allFoods[i].getName());
        }
    }

    @Override
    public String getName(){
        return "itemFood";
    }
}