package ellpeck.actuallyadditions.gadget.cloud;

import ellpeck.actuallyadditions.blocks.render.ModelBaseAA;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface ISmileyCloudEasterEgg{

    /**
     * Extra rendering function
     */
    void renderExtra(float f);

    /**
     * Registers extra rendering
     */
    void registerExtraRendering(ModelBaseAA model);

    /**
     * If the Original cloud should be rendered
     */
    boolean shouldRenderOriginal();

    boolean hasSpecialRightClick();

    /**
     * If something special happens on right-click of the cloud
     */
    void specialRightClick(World world, int x, int y, int z, Block block, int meta);

    /**
     * Something in addition to the default name in the name tag
     */
    String displayNameExtra();

    /**
     * If the original name should be rendered
     */
    boolean shouldRenderOriginalName();

    /**
     * The name the cloud has to have for this effect to occur
     */
    String getTriggerName();

    ResourceLocation getResLoc();
}
