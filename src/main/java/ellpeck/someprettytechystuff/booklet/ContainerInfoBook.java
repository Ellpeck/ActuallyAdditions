package ellpeck.someprettytechystuff.booklet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerInfoBook extends Container {

    @SuppressWarnings("unused")
    public ContainerInfoBook(EntityPlayer player){

    }

    public boolean canInteractWith(EntityPlayer player){
        return true;
    }
}
