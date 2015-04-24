package ellpeck.actuallyadditions.tile;

import ellpeck.actuallyadditions.items.ItemUpgrade;
import net.minecraft.item.ItemStack;

public abstract class TileEntityUpgradable extends TileEntityInventoryBase{

    public int speedUpgradeSlot;
    public int burnTimeAmplifier;

    public TileEntityUpgradable(int slots, String name){
        super(slots, name);
    }

    public void speedUp(){
        ItemStack stack = this.slots[speedUpgradeSlot];
        if(stack != null && stack.getItem() instanceof ItemUpgrade && ((ItemUpgrade)stack.getItem()).type == ItemUpgrade.UpgradeType.SPEED){
            int newSpeed = this.getStandardSpeed() - 10*stack.stackSize;
            this.speedUpBurnTimeBySpeed(stack.stackSize);
            if(newSpeed < 5) newSpeed = 5;
            this.setSpeed(newSpeed);
        }
        else{
            this.speedUpBurnTimeBySpeed(0);
            this.setSpeed(this.getStandardSpeed());
        }
    }

    public void speedUpBurnTimeBySpeed(int upgradeAmount){
        this.burnTimeAmplifier = upgradeAmount*2;
    }

    public int getStandardSpeed(){
        return 0;
    }

    public void setSpeed(int newSpeed){

    }
}
