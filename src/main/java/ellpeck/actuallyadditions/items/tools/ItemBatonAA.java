package ellpeck.actuallyadditions.items.tools;

import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.util.IActAddItemOrBlock;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @author canitzp
 */

public class ItemBatonAA extends Item implements IActAddItemOrBlock{

    private String name;
    private String repairItem;
    private ToolMaterial material;

    public ItemBatonAA(ToolMaterial material, String repairItem, String name){
        this.material = material;
        this.name = name;
        this.repairItem = repairItem;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase livingBase, EntityLivingBase attacker)
    {
        stack.damageItem(1, attacker);
        knockback(livingBase, (EntityPlayer) attacker);
        return true;
    }

    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_)
    {
        if ((double)p_150894_3_.getBlockHardness(p_150894_2_, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D)
        {
            p_150894_1_.damageItem(2, p_150894_7_);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack p_77661_1_)
    {
        return EnumAction.block;
    }

    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_)
    {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }

    public int getItemEnchantability()
    {
        return this.material.getEnchantability();
    }

    @Override
    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
        int[] idsStack = OreDictionary.getOreIDs(stack);
        for(int id : idsStack){
            if(OreDictionary.getOreName(id).equals(repairItem)){
                return true;
            }
        }
        return false;
    }

    public Multimap getItemAttributeModifiers()
    {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 2F + material.getDamageVsEntity(), 0));
        return multimap;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    /**
        Method from http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/1415944-adding-knockback-to-items-mobs
     */
    private void knockback(Entity entity, EntityPlayer player){
        double d = entity.posX - player.posX + material.getEfficiencyOnProperMaterial();
        double d1;
        for(d1 = entity.posZ - player.posZ; d * d + d1 * d1 < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
        {
            d = (Math.random() - Math.random()) * 0.01D;
        }

        entity.isAirBorne = true;
        float f = MathHelper.sqrt_double(d * d + d1 * d1);
        float f1 = 0.4F;
        entity.motionX /= 2D;
        entity.motionY /= 2D;
        entity.motionZ /= 2D;
        entity.motionX += (d / (double)f) * (double)f1 * 2;
        entity.motionY += 0.40000000596046448D;
        entity.motionZ += (d1 / (double)f) * (double)f1 * 2;
        if(entity.motionY > 0.40000000596046448D)
        {
            entity.motionY = 0.40000000596046448D;
        }
    }

}
