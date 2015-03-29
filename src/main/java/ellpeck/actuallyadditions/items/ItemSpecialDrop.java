package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.IName;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemSpecialDrop extends Item implements IName{

    public static final TheSpecialDrops[] allDrops = TheSpecialDrops.values();
    public IIcon[] textures = new IIcon[allDrops.length];

    public ItemSpecialDrop(){
        this.setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            if(stack.getItemDamage() == TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()){
                world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, 5+new Random().nextInt(6)));
                if(!player.capabilities.isCreativeMode) stack.stackSize--;
            }
        }
        return stack;
    }

    @Override
    public String getName(){
        return "itemSpecial";
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return allDrops[stack.getItemDamage()].rarity;
    }

    @Override
    public int getMetadata(int damage){
        return damage;
    }

    @SuppressWarnings("all")
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list){
        for(int j = 0; j < allDrops.length; j++){
            list.add(new ItemStack(this, 1, j));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack){
        return this.getUnlocalizedName() + allDrops[stack.getItemDamage()].name;
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        if(KeyUtil.isShiftPressed()) list.add(StatCollector.translateToLocal("tooltip." + ModUtil.MOD_ID_LOWER + "." + this.getName() + allDrops[stack.getItemDamage()].getName() + ".desc"));
        else list.add(ItemUtil.shiftForInfo());
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + allDrops[i].getName());
        }
    }
}
