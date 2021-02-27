// TODO: [port] removed, not required?
//package de.ellpeck.actuallyadditions.mod.blocks.render;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.minecraft.client.renderer.model.IBakedModel;
//import org.apache.commons.lang3.tuple.Pair;
//
//import de.ellpeck.actuallyadditions.mod.blocks.BlockCompost;
//
//import net.minecraft.client.renderer.block.model.BakedQuad;
//import net.minecraft.client.renderer.block.model.IBakedModel;
//import net.minecraft.client.renderer.block.model.ItemOverrideList;
//import net.minecraft.client.renderer.texture.TextureAtlasSprite;
//import net.minecraft.util.Direction;
//import net.minecraftforge.common.property.IExtendedBlockState;
//
//public class ActualCompostModel implements IBakedModel {
//
//    public static final Map<Pair<BlockState, Float>, IBakedModel> MODELS = new HashMap<>();
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public List<BakedQuad> getQuads(BlockState state, Direction side, long rand) {
//        if (state instanceof IExtendedBlockState) {
//            Pair<BlockState, Float> data = ((IExtendedBlockState) state).getValue(BlockCompost.COMPOST_PROP);
//            if (data == null || data.getRight() <= 0) return CompostModel.compostBase.getQuads(state, side, rand);
//            IBakedModel model = MODELS.get(data);
//            if (model == null) {
//                model = new CompostModel(data.getLeft(), data.getRight());
//                MODELS.put(data, model);
//            }
//            return model.getQuads(state, side, rand);
//        }
//        return CompostModel.compostBase.getQuads(state, side, rand);
//    }
//
//    @Override
//    public boolean isAmbientOcclusion() {
//        return CompostModel.compostBase.isAmbientOcclusion();
//    }
//
//    @Override
//    public boolean isGui3d() {
//        return false;
//    }
//
//    @Override
//    public boolean isBuiltInRenderer() {
//        return false;
//    }
//
//    @Override
//    public TextureAtlasSprite getParticleTexture() {
//        return CompostModel.compostBase.getParticleTexture();
//    }
//
//    @Override
//    public ItemOverrideList getOverrides() {
//        return ItemOverrideList.NONE;
//    }
//
//}
