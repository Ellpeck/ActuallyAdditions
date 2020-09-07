package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.blocks.BlockCompost;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ActualCompostModel implements IBakedModel {

    public static final Map<Pair<BlockState, Float>, IBakedModel> MODELS = new HashMap<>();

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
//        if (state instanceof BlockState) {
        if (state != null) {
            Pair<BlockState, Float> data = state.get(BlockCompost.COMPOST_PROP);
            if (data == null || data.getRight() <= 0) return CompostModel.compostBase.getQuads(state, side, rand);
            IBakedModel model = MODELS.get(data);
            if (model == null) {
                model = new CompostModel(data.getLeft(), data.getRight());
                MODELS.put(data, model);
            }
            return model.getQuads(state, side, rand);
        }

        return CompostModel.compostBase.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {
        return CompostModel.compostBase.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    // No clue what this one is.
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
        return CompostModel.compostBase.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }
}
