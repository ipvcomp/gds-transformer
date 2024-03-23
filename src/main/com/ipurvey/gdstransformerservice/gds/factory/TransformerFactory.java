package com.ipurvey.gdstransformerservice.gds.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class TransformerFactory {
    //TODO: use AMADEUS GDS transformer naming
    private final GDSTransformer gdsTransformer;

    @Autowired
    public TransformerFactory(GDSTransformer gdsTransformer) {
        this.gdsTransformer = gdsTransformer;
    }

    public <T extends Transformer<?>> T  createTransformer(TransformerType type) {
        switch (type) {
            case GDS:
                return (T) gdsTransformer;
            default:
                throw new IllegalArgumentException("Unsupported transformer type: " + type);
        }
    }
}