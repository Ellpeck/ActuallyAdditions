package de.ellpeck.actuallyadditions.mod.blocks.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

public class RenderTypes extends RenderType {
    public RenderTypes(String NameIn, VertexFormat FormatIn, int GLMode, int BufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable ClearTask) {
        super(NameIn, FormatIn, GLMode, BufferSize, useDelegate, needsSorting, setupTask, ClearTask);
    }

/*    public static final RenderType LASER = create("actuallyadditions:laser", DefaultVertexFormats.POSITION_COLOR,
        GL11.GL_QUADS, 256, false, true,
        RenderType.State.builder()
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setTextureState(NO_TEXTURE)
            .setOutputState(RenderType.OUTLINE_TARGET)
            .createCompositeState(true));*/
    public static final RenderType LASER = create("actuallyadditions:laser", DefaultVertexFormats.POSITION_COLOR_LIGHTMAP,
    GL11.GL_QUADS, 256, false, true,
    RenderType.State.builder()
        .setTransparencyState(ADDITIVE_TRANSPARENCY)
        .setTextureState(NO_TEXTURE)
        .setOutputState(RenderType.TRANSLUCENT_TARGET)
        .setLightmapState(RenderState.LIGHTMAP)
        .setAlphaState(RenderState.DEFAULT_ALPHA)
        .setCullState(RenderState.NO_CULL)
        .createCompositeState(true));
}
