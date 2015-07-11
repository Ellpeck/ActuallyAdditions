package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.material.InitArmorMaterials;
import ellpeck.actuallyadditions.util.KeyUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class ItemScubaArmor extends ItemArmorAA{

    private static final int chestAirTime = 500;
    private static final int chestAirIncreasePerTick = 3;

    public ItemScubaArmor(String name, int type){
        super(name, InitArmorMaterials.armorMaterialScuba, type, null, "armorScuba");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor){
        if(!world.isRemote){
            if(this.hasFullArmor(player)){
                NBTTagCompound compound = armor.getTagCompound();
                if(compound == null) compound = new NBTTagCompound();

                if(armor.getItem() == InitItems.itemScubaChest){
                    if(player.isInsideOfMaterial(Material.water)){
                        int time = compound.getInteger("waitTime");
                        if(time < chestAirTime){
                            compound.setInteger("waitTime", time+1);
                            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 1, 0));
                        }
                    }
                    else{
                        this.addAirToChest(compound);
                    }
                }
                armor.setTagCompound(compound);
            }
        }
    }

    private boolean hasFullArmor(EntityPlayer player){
        return player.inventory.getStackInSlot(36) != null && player.inventory.getStackInSlot(37) != null && player.inventory.getStackInSlot(38) != null && player.inventory.getStackInSlot(39) != null && player.inventory.getStackInSlot(36).getItem() == InitItems.itemScubaBoots && player.inventory.getStackInSlot(37).getItem() == InitItems.itemScubaPants && player.inventory.getStackInSlot(38).getItem() == InitItems.itemScubaChest && player.inventory.getStackInSlot(39).getItem() == InitItems.itemScubaHelm;
    }

    private void addAirToChest(NBTTagCompound compound){
        int time = compound.getInteger("waitTime");
        if(time > 0){
            compound.setInteger("waitTime", time < chestAirIncreasePerTick ? 0 : time-chestAirIncreasePerTick);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!entity.isInsideOfMaterial(Material.water)){
            NBTTagCompound compound = stack.getTagCompound();
            if(compound != null){
                this.addAirToChest(compound);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        super.addInformation(stack, player, list, isHeld);
        if(KeyUtil.isShiftPressed() && stack.getItem() == InitItems.itemScubaChest){
            NBTTagCompound compound = stack.getTagCompound();
            //TODO Localize
            list.add("Air: "+(compound == null ? chestAirTime : chestAirTime-compound.getInteger("waitTime"))+"/"+chestAirTime);
        }
    }
}
