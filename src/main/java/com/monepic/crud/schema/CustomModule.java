package com.monepic.crud.schema;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

import com.github.victools.jsonschema.generator.MemberScope;
import com.github.victools.jsonschema.generator.Module;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigPart;
import com.github.victools.jsonschema.generator.SchemaGeneratorGeneralConfigPart;
import com.github.victools.jsonschema.generator.TypeScope;
import com.github.victools.jsonschema.module.jackson.JsonPropertySorter;
import com.monepic.crud.schema.annotation.JsonSchemaMetadata;
import com.monepic.crud.schema.annotation.ValidationUrl;

public class CustomModule implements Module {

    @SuppressWarnings("rawtypes")
    final Map<Class<? extends Annotation>, AnnotationHandler> annotations = Map.of(
            ValidationUrl.class, new ValidationUrlHandler(),
            JsonSchemaMetadata.class, new JsonSchemaMetadataHandler()
            );

    @Override
    public void applyToConfigBuilder(SchemaGeneratorConfigBuilder builder) {
        apply(builder.forTypesInGeneral());
        apply(builder.forFields());
        apply(builder.forMethods());
    }


    @SuppressWarnings("unchecked")
    private void apply(SchemaGeneratorGeneralConfigPart forTypesInGeneral) {

        /* Disable alphabetic sorting, but respect the JsonPropertyOrder annotation */
        forTypesInGeneral.withPropertySorter(new JsonPropertySorter(false));

        forTypesInGeneral.withTypeAttributeOverride((node, scope, context) -> {
            annotations.forEach((annotation, handler) -> {
                Optional.ofNullable(scope.getType().getErasedType().getAnnotation(annotation))
                .ifPresent(foundAnnotation -> handler.handle(foundAnnotation, node));
            });
        });
    }

    @SuppressWarnings("unchecked")
    private void apply(SchemaGeneratorConfigPart<?> config) {
        config.withInstanceAttributeOverride((node, member, context) -> {
            annotations.forEach((annotation, handler) -> {
                getAnnotation(member, annotation).
                ifPresent(foundAnnotation -> handler.handle(foundAnnotation, node));
            });
        });
    }

    protected <A extends Annotation> Optional<A> getAnnotation(TypeScope scope, Class<A> annotationClass) {
        return Optional.ofNullable(scope.getType().getErasedType().getAnnotation(annotationClass));
    }

    protected <A extends Annotation> Optional<A> getAnnotation(MemberScope<?, ?> member, Class<A> annotationClass) {
        A annotation = member.getContainerItemAnnotation(annotationClass);
        if (annotation == null) { 
            annotation =  member.getAnnotationConsideringFieldAndGetter(annotationClass);
        }
        return Optional.ofNullable(annotation);
    }
}
