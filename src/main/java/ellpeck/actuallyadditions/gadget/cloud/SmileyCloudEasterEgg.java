package ellpeck.actuallyadditions.gadget.cloud;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public abstract class SmileyCloudEasterEgg implements ISmileyCloudEasterEgg{

    @Override
    public void renderExtra(float f){

    }

    @Override
    public ResourceLocation getResLoc(){
        return null;
    }

    @Override
    public boolean shouldRenderOriginal(){
        return true;
    }

    @Override
    public boolean hasSpecialRightClick(){
        return false;
    }

    @Override
    public void specialRightClick(World world, int x, int y, int z, Block block, int meta){

    }

    @Override
    public void registerExtraRendering(ModelBaseAA model){

    }

    @Override
    public String displayNameExtra(){
        return null;
    }

    @Override
    public boolean shouldRenderOriginalName(){
        return true;
    }
}
