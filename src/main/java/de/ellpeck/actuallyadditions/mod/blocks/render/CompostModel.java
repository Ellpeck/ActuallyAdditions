package de.ellpeck.actuallyadditions.mod.blocks.render;

import java.util.EnumMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.vecmath.Vector3f;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.TRSRTransformation;

public class CompostModel implements IBakedModel {
    public static IBakedModel compostBase;
    private final IBakedModel display;
    private final ImmutableList<BakedQuad> general;
    private final ImmutableMap<EnumFacing, ImmutableList<BakedQuad>> faces;

    public CompostModel(IBlockState flowerState, float height) {
        this.display = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(flowerState);
        
        TRSRTransformation transform = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, -.218F, 0), null, new Vector3f(0.75F, height / 1.81F, 0.75F), null));

        ImmutableList.Builder<BakedQuad> builder;
        EnumMap<EnumFacing, ImmutableList<BakedQuad>> faces = new EnumMap<>(EnumFacing.class);

        for (EnumFacing face : EnumFacing.values()) {
            builder = ImmutableList.builder();
            if (!display.isBuiltInRenderer()) {
                for (BakedQuad quad : display.getQuads(flowerState, face, 0)) {
                    Transformer transformer = new Transformer(transform, quad.getFormat());
                    quad.pipe(transformer);
                    builder.add(transformer.build());
                }
                builder.addAll(compostBase.getQuads(null, face, 0));
            }
            faces.put(face, builder.build());
        }

        if (!display.isBuiltInRenderer()) {
            builder = ImmutableList.builder();
            for (BakedQuad quad : display.getQuads(flowerState, null, 0)) {
                Transformer transformer = new Transformer(transform, quad.getFormat());
                quad.pipe(transformer);
                builder.add(transformer.build());
            }
            builder.addAll(compostBase.getQuads(null, null, 0));
            this.general = builder.build();
        } else general = ImmutableList.of();

        this.faces = Maps.immutableEnumMap(faces);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        if (side == null) return general;
        return faces.get(side);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return compostBase.isAmbientOcclusion() && display.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return compostBase.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

}
