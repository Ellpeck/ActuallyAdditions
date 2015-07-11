package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.metalists.TheJams;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemJams extends ItemFood implements INameableItem{

    public static final TheJams[] allJams = TheJams.values();
    public IIcon overlayIcon;

    public ItemJams(){
        super(0, 0.0F, false);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? EnumRarity.common : allJams[stack.getItemDamage()].rarity;
    }

    @Override
    public int func_150905_g(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].healAmount;
    }

    @Override
    public float func_150906_h(ItemStack stack){
        return stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].saturation;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for (int j = 0; j < allJams.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + (stack.getItemDamage() >= allJams.length ? " ERROR!" : allJams[stack.getItemDamage()].getName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass){
        return pass > 0 ? (stack.getItemDamage() >= allJams.length ? 0 : allJams[stack.getItemDamage()].color) : super.getColorFromItemStack(stack, pass);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player){
        ItemStack stackToReturn = super.onEaten(stack, world, player);

        if(!world.isRemote && stack.getItemDamage() < allJams.length){
            PotionEffect firstEffectToGet = new PotionEffect(allJams[stack.getItemDamage()].firstEffectToGet, 200);
            player.addPotionEffect(firstEffectToGet);

            PotionEffect secondEffectToGet = new PotionEffect(allJams[stack.getItemDamage()].secondEffectToGet, 600);
            player.addPotionEffect(secondEffectToGet);

            ItemStack returnItem = new ItemStack(Items.glass_bottle);
            if(!player.inventory.addItemStackToInventory(returnItem.copy())){
                EntityItem entityItem = new EntityItem(player.worldObj, player.posX, player.posY, player.posZ, returnItem.copy());
                entityItem.delayBeforeCanPickup = 0;
                player.worldObj.spawnEntityInWorld(entityItem);
            }
        }
        return stackToReturn;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(stack.getItemDamage() < allJams.length){
            if(KeyUtil.isShiftPressed()){
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+".desc.1"));
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+allJams[stack.getItemDamage()].getName()+".desc"));
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+".desc.2"));
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+"."+this.getName()+".desc.3"));
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".hunger.desc")+": "+allJams[stack.getItemDamage()].healAmount);
                list.add(StatCollector.translateToLocal("tooltip."+ModUtil.MOD_ID_LOWER+".saturation.desc")+": "+allJams[stack.getItemDamage()].saturation);
            }
            else list.add(ItemUtil.shiftForInfo());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int damage, int pass){
        return pass > 0 ? this.overlayIcon : super.getIconFromDamageForRenderPass(damage, pass);
    }

    @Override
    public boolean requiresMultipleRenderPasses(){
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName());
        this.overlayIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + "Overlay");
    }

    @Override
    public String getName(){
        return "itemJam";
    }
}