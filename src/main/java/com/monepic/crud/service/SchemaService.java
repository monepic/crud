package com.monepic.crud.service;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.MethodScope;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.github.victools.jsonschema.generator.SchemaVersion;

@Component
public class SchemaService {

    private final SchemaGenerator generator;


    public static final OptionPreset BEAN = new OptionPreset(
            Option.SCHEMA_VERSION_INDICATOR,
            Option.ADDITIONAL_FIXED_TYPES,
            //            Option.FLATTENED_ENUMS,
            //            Option.FLATTENED_OPTIONALS,
            //            Option.FLATTENED_SUPPLIERS,
            Option.NONPUBLIC_NONSTATIC_FIELDS_WITH_GETTERS,
            Option.NONSTATIC_NONVOID_NONGETTER_METHODS,
            Option.FIELDS_DERIVED_FROM_ARGUMENTFREE_METHODS,
            Option.ALLOF_CLEANUP_AT_THE_END
            );

    private static boolean isGetter(MethodScope ms) {
        String methodName = ms.getDeclaredName();
        return (methodName.startsWith("get") && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3)))
                || (methodName.startsWith("is") && methodName.length() > 2 && Character.isUpperCase(methodName.charAt(2)));
    }

    public SchemaService() {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2019_09, BEAN);
        configBuilder.forMethods().withIgnoreCheck(ms -> ! isGetter(ms));
        SchemaGeneratorConfig config = configBuilder.build();
        generator = new SchemaGenerator(config);
    }

    public JsonNode schema(Class<?> clazz) {
        return generator.generateSchema(clazz);
    }

}
