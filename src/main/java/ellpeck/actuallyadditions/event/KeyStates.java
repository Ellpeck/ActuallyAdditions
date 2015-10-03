/*
 * This file ("KeyStates.java") is part of the Actually Additions Mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://github.com/Ellpeck/ActuallyAdditions/blob/master/README.md
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015 Ellpeck
 */

package ellpeck.actuallyadditions.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ellpeck.actuallyadditions.util.KeyBinds;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyStates{

    public static KeyState mouseButtonState = new KeyState();
    public static KeyState infoButtonState = new KeyState();

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event){
        if(event.phase != TickEvent.Phase.END){
            mouseButtonState.tick(Mouse.isButtonDown(0));
            infoButtonState.tick(Keyboard.isKeyDown(KeyBinds.keybindOpenBooklet.getKeyCode()));
        }
    }

    public static class KeyState{

        private boolean pressed;
        private boolean pressedLastTime;
        private boolean recentlyCheckedPress;

        public void tick(boolean pressed){
            this.pressed = pressed && !this.pressedLastTime;
            this.pressedLastTime = pressed;
            this.recentlyCheckedPress = false;
        }

        public boolean isPressed(){
            boolean isPressed = this.pressed && !this.recentlyCheckedPress;
            this.recentlyCheckedPress = true;
            return isPressed;
        }
    }
}
