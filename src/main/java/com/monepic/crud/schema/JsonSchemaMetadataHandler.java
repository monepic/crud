package com.monepic.crud.schema;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monepic.crud.schema.annotation.JsonSchemaMetadata;

public class JsonSchemaMetadataHandler implements AnnotationHandler<JsonSchemaMetadata> {

    @Override
    public void handle(JsonSchemaMetadata annotation, ObjectNode node) {
        node.put(annotation.key(), annotation.value());
    }

}
