package ellpeck.actuallyadditions.gadget.cloud;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import net.minecraft.client.model.ModelRenderer;

import java.util.ArrayList;

public class SmileyCloudEasterEggs{

    public static final ArrayList<ISmileyCloudEasterEgg> cloudStuff = new ArrayList<ISmileyCloudEasterEgg>();

    static{
        //Glenthor
        register(new SmileyCloudEasterEgg(){

            public ModelRenderer s9;

            @Override
            public String getTriggerName(){
                return "Glenthor";
            }

            @Override
            public void renderExtra(float f){
                s9.render(f);
            }

            @Override
            public boolean shouldRenderOriginal(){
                return false;
            }

            @Override
            public void registerExtraRendering(ModelBaseAA model){
                s9 = new ModelRenderer(model, 0, 31);
                s9.addBox(0F, 0F, 0F, 1, 6, 6);
                s9.setRotationPoint(7F, 16F, -2F);
                s9.setTextureSize(64, 64);
                s9.mirror = true;
            }
        });
    }

    private static void register(ISmileyCloudEasterEgg egg){
        cloudStuff.add(egg);
    }
}
