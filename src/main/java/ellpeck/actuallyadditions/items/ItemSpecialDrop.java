package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.items.metalists.TheSpecialDrops;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemSpecialDrop extends Item implements INameableItem{

    public static final int SOLID_XP_AMOUNT = 8;

    public static final TheSpecialDrops[] allDrops = TheSpecialDrops.values();
    public IIcon[] textures = new IIcon[allDrops.length];

    public ItemSpecialDrop(){
        this.setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
        if(!world.isRemote){
            if(stack.getItemDamage() == TheSpecialDrops.SOLIDIFIED_EXPERIENCE.ordinal()){
                if(!player.isSneaking()){
                    world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT));
                    if(!player.capabilities.isCreativeMode) stack.stackSize--;
                }
                else{
                    world.spawnEntityInWorld(new EntityXPOrb(world, player.posX+0.5, player.posY+0.5, player.posZ+0.5, SOLID_XP_AMOUNT*stack.stackSize));
                    if(!player.capabilities.isCreativeMode) stack.stackSize = 0;
                }
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
        return stack.getItemDamage() >= allDrops.length ? EnumRarity.common : allDrops[stack.getItemDamage()].rarity;
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
        return this.getUnlocalizedName() + (stack.getItemDamage() >= allDrops.length ? " ERROR!" : allDrops[stack.getItemDamage()].getName());
    }

    @Override
    public IIcon getIconFromDamage(int par1){
        return par1 >= textures.length ? null : textures[par1];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        for(int i = 0; i < textures.length; i++){
            textures[i] = iconReg.registerIcon(ModUtil.MOD_ID_LOWER + ":" + this.getName() + allDrops[i].getName());
        }
    }
}
