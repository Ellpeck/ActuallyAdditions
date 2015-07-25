package ellpeck.actuallyadditions.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ellpeck.actuallyadditions.config.values.ConfigIntValues;
import ellpeck.actuallyadditions.util.INameableItem;
import ellpeck.actuallyadditions.util.ModUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ItemMagnetRing extends ItemEnergy implements INameableItem{

    private static final int RANGE = ConfigIntValues.MAGNET_RING_RANGE.getValue();
    private static final int ENERGY_USED_PER_TICK = ConfigIntValues.MAGNET_RING_ENERGY_USE.getValue();

    public ItemMagnetRing(){
        super(3000000, 5000, 1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
        if(this.getEnergyStored(stack) >= ENERGY_USED_PER_TICK){
            //Get all the Items in the area
            ArrayList<EntityItem> items = (ArrayList<EntityItem>)world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(entity.posX-RANGE, entity.posY-RANGE, entity.posZ-RANGE, entity.posX+RANGE, entity.posY+RANGE, entity.posZ+RANGE));
            if(!items.isEmpty()){
                for(EntityItem item : items){
                    //If the Item is near enough to get picked up
                    //(So it doesn't bounce around until it notices itself..)
                    if(Vec3.createVectorHelper(entity.posX, entity.posY, entity.posZ).distanceTo(Vec3.createVectorHelper(item.posX, item.posY, item.posZ)) <= 1.5){
                        item.onCollideWithPlayer((EntityPlayer)entity);
                    }
                    else{
                        double speed = 0.02;
                        //Move the Item closer to the Player
                        item.motionX += (entity.posX+0.5-item.posX)*speed;
                        item.motionY += (entity.posY+1.0-item.posY)*speed;
                        item.motionZ += (entity.posZ+0.5-item.posZ)*speed;
                    }
                }
            }

            //Use Energy per tick
            if(!((EntityPlayer)entity).capabilities.isCreativeMode){
                this.extractEnergy(stack, ENERGY_USED_PER_TICK, false);
            }
        }
    }

    @Override
    public EnumRarity getRarity(ItemStack stack){
        return EnumRarity.epic;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconReg){
        this.itemIcon = iconReg.registerIcon(ModUtil.MOD_ID_LOWER+":"+this.getName());
    }

    @Override
    public String getName(){
        return "itemSuctionRing";
    }
}
