package com.monepic.crud.schema;

import java.lang.annotation.Annotation;

import com.fasterxml.jackson.databind.node.ObjectNode;

interface AnnotationHandler<A extends Annotation> {

    void handle(A annotation, ObjectNode node);
}