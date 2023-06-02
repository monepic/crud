package com.monepic.crud.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.victools.jsonschema.generator.MethodScope;
import com.github.victools.jsonschema.generator.Option;
import com.github.victools.jsonschema.generator.OptionPreset;

public abstract class AbstractSchemaService implements SchemaService {

    protected static final List<Option> DEFAULT_OPTIONS = List.of(
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

    protected static final OptionPreset BEAN = toOptionPreset(DEFAULT_OPTIONS);

    @SafeVarargs
    protected static OptionPreset toOptionPreset(Collection<Option>... options) {
        Set<Option> combined = new HashSet<>();
        for (Collection<Option> opts : options) {
            combined.addAll(opts);
        }
        return new OptionPreset(combined.toArray(new Option[combined.size()]));
    }

    protected static boolean isGetter(MethodScope ms) {
        String methodName = ms.getDeclaredName();
        return (methodName.startsWith("get") && methodName.length() > 3 && Character.isUpperCase(methodName.charAt(3)))
                || (methodName.startsWith("is") && methodName.length() > 2 && Character.isUpperCase(methodName.charAt(2)));
    }

}
