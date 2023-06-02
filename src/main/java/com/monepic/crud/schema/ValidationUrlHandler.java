package com.monepic.crud.schema;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monepic.crud.schema.annotation.ValidationUrl;

public class ValidationUrlHandler implements AnnotationHandler<ValidationUrl> {

    @Override
    public void handle(ValidationUrl annotation, ObjectNode node) {

        String url = resolveUrl(annotation.controller(), annotation.path(), annotation.parameters());
        node.put("validationUrl",  url);
    }
    public String resolveUrl(Class<?> controller, String path, String...params) {

        String[] pathSegments = path == null ? new String[] {} : path.split("/");

        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(getRequestMapping(controller))
                .pathSegment(pathSegments);

        for(String param : params) {
            builder.queryParam(param);
        }

        return builder.build().toUriString();
    }

    private String getRequestMapping(Class<?> controller) {

        Object value = AnnotationUtils.getValue(AnnotatedElementUtils.findMergedAnnotation(controller, RequestMapping.class));

        if (value instanceof String) {
            return (String) value;
        } else if (value instanceof String[]) {
            String[] toks = (String[]) value;
            return toks.length > 0 ? toks[0] : null;
        } else {
            return null;
        }
    }


}
