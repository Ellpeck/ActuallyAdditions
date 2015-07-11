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
import net.minecraft.world.World;

import java.util.List;

public class ItemScubaArmor extends ItemArmorAA{

    private static final int helmetTime = 500;
    private static final int helmetTimeDecreaseInAir = 3;

    public ItemScubaArmor(String name, int type){
        super(name, InitArmorMaterials.armorMaterialScuba, type, null, "armorScuba");
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack armor){
        if(!world.isRemote){
            if(armor.getItem() == InitItems.itemScubaHelm){
                NBTTagCompound compound = armor.getTagCompound();
                if(compound == null) compound = new NBTTagCompound();

                if(player.isInsideOfMaterial(Material.water)){
                    int time = compound.getInteger("waitTime");
                    if(time < helmetTime){
                        compound.setInteger("waitTime", time+1);
                        player.setAir(300);
                    }
                }
                else{
                    this.addAir(compound);
                }
                armor.setTagCompound(compound);
            }
        }
    }

    private void addAir(NBTTagCompound compound){
        int time = compound.getInteger("waitTime");
        if(time > 0){
            compound.setInteger("waitTime", time < helmetTimeDecreaseInAir ? 0 : time-helmetTimeDecreaseInAir);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(!entity.isInsideOfMaterial(Material.water)){
            NBTTagCompound compound = stack.getTagCompound();
            if(compound != null){
                this.addAir(compound);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld){
        super.addInformation(stack, player, list, isHeld);
        if(KeyUtil.isShiftPressed() && stack.getItem() == InitItems.itemScubaHelm){
            NBTTagCompound compound = stack.getTagCompound();
            //TODO Localize
            list.add("Air: "+(compound == null ? helmetTime : helmetTime-compound.getInteger("waitTime"))+"/"+helmetTime);
        }
    }
}
