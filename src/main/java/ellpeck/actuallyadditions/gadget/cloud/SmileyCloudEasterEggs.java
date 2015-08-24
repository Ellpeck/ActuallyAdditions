package ellpeck.actuallyadditions.gadget.cloud;

import ellpeck.actuallyadditions.util.AssetUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class SmileyCloudEasterEggs{

    public static final ArrayList<ISmileyCloudEasterEgg> cloudStuff = new ArrayList<ISmileyCloudEasterEgg>();

    static{
        //Glenthor
        register(new SmileyCloudEasterEgg(){
            @Override
            public String[] getTriggerNames(){
                return new String[]{"glenthor"};
            }
            @Override
            public void renderExtra(float f){
                GL11.glTranslatef(f*8F, f*24F, f*-4F);
                GL11.glRotatef(180F, 0F, 0F, 1F);
                GL11.glRotatef(85F, 0F, 1F, 0F);
                GL11.glRotatef(70F, 0F, 0F, 1F);
                GL11.glScalef(0.75F, 0.75F, 0.75F);
                AssetUtil.renderItem(new ItemStack(Items.dye, 1, 2), 0);
            }
        });
    }

    private static void register(ISmileyCloudEasterEgg egg){
        cloudStuff.add(egg);
    }
}
