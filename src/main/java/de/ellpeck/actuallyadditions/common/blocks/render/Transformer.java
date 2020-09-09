package de.ellpeck.actuallyadditions.common.blocks.render;


//public class Transformer extends VertexTransformer {
//
//    protected final Matrix4f transformation;
//    protected final Matrix3f normalTransformation;
//
//    public Transformer(TRSRTransformation transformation, VertexFormat format) {
//        super(new UnpackedBakedQuad.Builder(format));
//        // position transform
//        this.transformation = transformation.getMatrix();
//        // normal transform
//        this.normalTransformation = new Matrix3f();
//        this.transformation.getRotationScale(this.normalTransformation);
//        this.normalTransformation.invert();
//        this.normalTransformation.transpose();
//    }
//
//    @Override
//    public void put(int element, float... data) {
//        VertexFormatElement.EnumUsage usage = this.parent.getVertexFormat().getElement(element).getUsage();
//
//        // transform normals and position
//        if (usage == VertexFormatElement.EnumUsage.POSITION && data.length >= 3) {
//            Vector4f vec = new Vector4f(data);
//            vec.setW(1.0f);
//            this.transformation.transform(vec);
//            data = new float[4];
//            vec.get(data);
//        } else if (usage == VertexFormatElement.EnumUsage.NORMAL && data.length >= 3) {
//            Vector3f vec = new Vector3f(data);
//            this.normalTransformation.transform(vec);
//            vec.normalize();
//            data = new float[4];
//            vec.get(data);
//        }
//        super.put(element, data);
//    }
//
//    public UnpackedBakedQuad build() {
//        return ((UnpackedBakedQuad.Builder) this.parent).build();
//    }
//}