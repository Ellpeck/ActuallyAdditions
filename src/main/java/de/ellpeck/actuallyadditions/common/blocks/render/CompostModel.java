package de.ellpeck.actuallyadditions.blocks.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Random;

// todo: come back and fix this render as It's broken atm :cry:
public class CompostModel implements IBakedModel {
    public static IBakedModel compostBase;
    private final IBakedModel display;
    private final ImmutableList<BakedQuad> general;
    private final ImmutableMap<Direction, ImmutableList<BakedQuad>> faces;

    public CompostModel(BlockState flowerState, float height) {
        this.display = Minecraft.getInstance().getBlockRendererDispatcher().getModelForState(flowerState);

//        TRSRTransformation transform = TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, -.218F, 0), null, new Vector3f(0.75F, height / 1.81F, 0.75F), null));

        ImmutableList.Builder<BakedQuad> builder;
        EnumMap<Direction, ImmutableList<BakedQuad>> faces = new EnumMap<>(Direction.class);

        for (Direction face : Direction.values()) {
            builder = ImmutableList.builder();
            if (!this.display.isBuiltInRenderer()) {
//                for (BakedQuad quad : this.display.getQuads(flowerState, face, 0)) {
//                    Transformer transformer = new Transformer(transform, quad.getFormat());
//                    quad.pipe(transformer);
//                    builder.add(transformer.build());
//                }
                builder.addAll(compostBase.getQuads(null, face, new Random()));
            }
            faces.put(face, builder.build());
        }

        if (!this.display.isBuiltInRenderer()) {
            builder = ImmutableList.builder();
//            for (BakedQuad quad : this.display.getQuads(flowerState, null, 0)) {
//                Transformer transformer = new Matrix4f(transform, quad.getFormat());
//                quad.pipe(transformer);
//                builder.add(transformer.build());
//            }
            builder.addAll(compostBase.getQuads(null, null, new Random()));
            this.general = builder.build();
        } else this.general = ImmutableList.of();

        this.faces = Maps.immutableEnumMap(faces);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        if (side == null) return this.general;
        return this.faces.get(side);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return compostBase.isAmbientOcclusion() && this.display.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    // No idea what this does.. Something? I'm sure
    @Override
    public boolean func_230044_c_() {
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
        return ItemOverrideList.EMPTY;
    }

}
