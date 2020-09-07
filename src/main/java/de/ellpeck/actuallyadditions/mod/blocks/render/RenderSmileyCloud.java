package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.ellpeck.actuallyadditions.mod.blocks.InitBlocks;
import de.ellpeck.actuallyadditions.mod.misc.cloud.ISmileyCloudEasterEgg;
import de.ellpeck.actuallyadditions.mod.misc.cloud.SmileyCloudEasterEggs;
import de.ellpeck.actuallyadditions.mod.misc.special.RenderSpecial;
import de.ellpeck.actuallyadditions.mod.misc.special.SpecialRenderInit;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;

import java.util.Locale;

// todo: migrate to client package
public class RenderSmileyCloud extends TileEntityRenderer<TileEntitySmileyCloud> {

    public RenderSmileyCloud(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(TileEntitySmileyCloud theCloud, float partialTicks, MatrixStack matrices, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        matrices.push();
//        matrices.translate((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
        matrices.rotate(new Quaternion(180F, 0.0F, 0.0F, 1.0F));
        matrices.translate(0.0F, -2F, 0.0F);

        if (theCloud.name != null && !theCloud.name.isEmpty()) {
            boolean renderedEaster = false;

            easterEggs: for (ISmileyCloudEasterEgg cloud : SmileyCloudEasterEggs.CLOUD_STUFF) {
                for (String triggerName : cloud.getTriggerNames()) {
                    if (triggerName != null && theCloud.name != null) {
                        if (triggerName.equalsIgnoreCase(theCloud.name)) {
                            matrices.push();

                            BlockState state = theCloud.getWorld().getBlockState(theCloud.getPos());
                            if (state.getBlock() == InitBlocks.blockSmileyCloud) {
                                switch (state.get(HorizontalBlock.HORIZONTAL_FACING)) {
                                    case NORTH:
                                        matrices.rotate(new Quaternion(180, 0, 1, 0));
                                        break;
                                    case EAST:
                                        matrices.rotate(new Quaternion(270, 0, 1, 0));
                                        break;
                                    case WEST:
                                        matrices.rotate(new Quaternion(90, 0, 1, 0));
                                        break;
                                    default:
                                        break;
                                }
                            }

                            cloud.renderExtra(0.0625F);
                            matrices.pop();

                            renderedEaster = true;
                            break easterEggs;
                        }
                    }
                }
            }

            String nameLower = theCloud.name.toLowerCase(Locale.ROOT);
            if (SpecialRenderInit.SPECIAL_LIST.containsKey(nameLower)) {
                RenderSpecial render = SpecialRenderInit.SPECIAL_LIST.get(nameLower);
                if (render != null) {
                    matrices.push();
                    matrices.translate(0F, renderedEaster ? 0.05F : 0.25F, 0F);
                    matrices.rotate(new Quaternion(180F, 1.0F, 0.0F, 1.0F));
                    matrices.scale(0.75F, 0.75F, 0.75F);
                    render.render();
                    matrices.pop();
                }
            }
        }
        matrices.pop();

        Minecraft mc = Minecraft.getInstance();
        if (theCloud.name != null && !theCloud.name.isEmpty() && !mc.gameSettings.hideGUI) {
            BlockPos pos = theCloud.getPos();
            if (mc.player.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) <= 36) {
                AssetUtil.renderNameTag(theCloud.name, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F);
            }
        }
    }
}
