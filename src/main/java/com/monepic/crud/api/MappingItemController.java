package com.monepic.crud.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.monepic.crud.entity.MappingItem;
import com.monepic.crud.repo.MappingItemRepo;
import com.monepic.crud.service.SchemaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping({"some", "mappingItems"})
public class MappingItemController {

    final MappingItemRepo repo;
    final SchemaService schema;

    @GetMapping(produces ="application/json")
    public Iterable<MappingItem> items() {
        return repo.findAll();
    }

    @GetMapping(produces = "application/schema+json")
    public JsonNode schema() {
        return schema.schema(MappingItem.class);
    }

}
