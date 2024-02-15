package com.ipurvey.gdstransformerservice.gds.factory;

public class TransformerFactory {
    public static <T extends Transformer<?>> T  createTransformer(TransformerType type) {
        switch (type) {
            case GDS:
                return (T) new GDSTransformer();
            default:
                throw new IllegalArgumentException("Unsupported transformer type: " + type);
        }
    }
}