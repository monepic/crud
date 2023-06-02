package com.monepic.crud.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationModule;
import com.github.victools.jsonschema.module.jakarta.validation.JakartaValidationOption;
import com.monepic.crud.schema.CustomModule;

@Component
public class SchemaServiceImpl extends AbstractSchemaService {

    private static final JacksonModule JACKSON = new JacksonModule(
            JacksonOption.RESPECT_JSONPROPERTY_ORDER
            );

    private static final JakartaValidationModule JAKARTA = new JakartaValidationModule(
            JakartaValidationOption.INCLUDE_PATTERN_EXPRESSIONS
            );
    
    private final SchemaGenerator generator;

    public SchemaServiceImpl() {

        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12, BEAN)
                .with(JACKSON)
                .with(JAKARTA)
                .with(new CustomModule());
        configBuilder.forMethods().withIgnoreCheck(ms -> ! isGetter(ms));
        generator = new SchemaGenerator(configBuilder.build());
    }

    @Override
    public JsonNode schema(Class<?> clazz) {
        return generator.generateSchema(clazz);
    }

}
