package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.material.InitArmorMaterials;
import ellpeck.actuallyadditions.util.ItemUtil;
import ellpeck.actuallyadditions.util.KeyUtil;
import net.minecraft.block.material.Material;
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

                int time = compound.getInteger("waitTime");
                if(player.isInsideOfMaterial(Material.water)){
                    if(time < helmetTime){
                        compound.setInteger("waitTime", time+1);
                        player.setAir(300);
                    }
                }
                else{
                    if(time > 0){
                        compound.setInteger("waitTime", time < helmetTimeDecreaseInAir ? 0 : time-helmetTimeDecreaseInAir);
                    }
                }
                armor.setTagCompound(compound);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean isHeld) {
        ItemUtil.addInformation(this, list, 1, "");
        if(KeyUtil.isShiftPressed() && stack.getItem() == InitItems.itemScubaHelm){
            NBTTagCompound compound = stack.getTagCompound();
            //TODO Localize
            list.add("Air: "+(compound == null ? helmetTime : helmetTime-compound.getInteger("waitTime"))+"/"+helmetTime);
        }
    }
}
