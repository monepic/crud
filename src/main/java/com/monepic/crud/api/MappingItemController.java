package com.monepic.crud.api;


import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.monepic.crud.entity.MappingItem;
import com.monepic.crud.repo.MappingItemRepo;
import com.monepic.crud.service.SchemaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("mappingItems")
public class MappingItemController {

    final MappingItemRepo repo;
    final SchemaService schema;

    @GetMapping(produces = "application/schema+json")
    public JsonNode schema() {
        return schema.schema(MappingItem.class);
    }

    @GetMapping(produces ="application/json")
    public Iterable<MappingItem> items() {
        return repo.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MappingItem create(@RequestBody MappingItem item) {
        checkNotNull(item);
        item.setId(null);
        return repo.save(item);
    }

    @GetMapping(value = "/{id}")
    public MappingItem read(@PathVariable("id") Long id) {
        return checkFound(id, repo.findById(id));
    }

    @PutMapping(value = "/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody MappingItem item) {
        checkNotNull(item);
        checkFound(id, repo.findById(id));
        item.setId(id);
        repo.save(item);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) {
        repo.deleteById(id);
    }


    private static <T> T checkFound(Long id, Optional<T> item) {
        return item.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private static void checkNotNull(Object obj) {
        if (obj == null) { throw new InvalidRequestException(); }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(Long id) { super(String.format("Resource with id %i not found", id)); }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException() { super("Bad Request"); }
    }
}
