package de.ellpeck.actuallyadditions.mod.blocks.render;

import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.client.model.pipeline.VertexTransformer;
import net.minecraftforge.common.model.TRSRTransformation;

public class Transformer extends VertexTransformer {

    protected final Matrix4f transformation;
    protected final Matrix3f normalTransformation;

    public Transformer(TRSRTransformation transformation, VertexFormat format) {
        super(new UnpackedBakedQuad.Builder(format));
        // position transform
        this.transformation = transformation.getMatrix();
        // normal transform
        this.normalTransformation = new Matrix3f();
        this.transformation.getRotationScale(this.normalTransformation);
        this.normalTransformation.invert();
        this.normalTransformation.transpose();
    }

    @Override
    public void put(int element, float... data) {
        VertexFormatElement.EnumUsage usage = parent.getVertexFormat().getElement(element).getUsage();

        // transform normals and position
        if (usage == VertexFormatElement.EnumUsage.POSITION && data.length >= 3) {
            Vector4f vec = new Vector4f(data);
            vec.setW(1.0f);
            transformation.transform(vec);
            data = new float[4];
            vec.get(data);
        } else if (usage == VertexFormatElement.EnumUsage.NORMAL && data.length >= 3) {
            Vector3f vec = new Vector3f(data);
            normalTransformation.transform(vec);
            vec.normalize();
            data = new float[4];
            vec.get(data);
        }
        super.put(element, data);
    }

    public UnpackedBakedQuad build() {
        return ((UnpackedBakedQuad.Builder) parent).build();
    }
}