package com.monepic.crud.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface SchemaService {

    JsonNode schema(Class<?> clazz);

}
