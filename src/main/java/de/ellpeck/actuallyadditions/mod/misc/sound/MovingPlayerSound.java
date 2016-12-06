/*
 * This file ("MovingPlayerSound.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.misc.sound;

import de.ellpeck.actuallyadditions.mod.items.ItemPortableJukebox;
import de.ellpeck.actuallyadditions.mod.util.ItemUtil;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MovingPlayerSound extends MovingSound{

    private final EntityPlayer player;
    private final int playerInvSlot;

    public MovingPlayerSound(SoundEvent sound, SoundCategory category, EntityPlayer player, int playerInvSlot){
        super(sound, category);
        this.player = player;
        this.playerInvSlot = playerInvSlot;
    }

    @Override
    public void update(){
        if(!this.player.isDead){
            ItemStack stack = this.player.inventory.getStackInSlot(this.playerInvSlot);
            if(StackUtil.isValid(stack) && stack.getItem() instanceof ItemPortableJukebox){
                if(ItemUtil.isEnabled(stack)){
                    this.xPosF = (float)this.player.posX;
                    this.yPosF = (float)this.player.posY;
                    this.zPosF = (float)this.player.posZ;
                    return;
                }
            }
        }

        this.donePlaying = true;
    }
}
