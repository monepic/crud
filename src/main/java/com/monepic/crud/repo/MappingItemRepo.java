package com.monepic.crud.repo;

import org.springframework.data.repository.CrudRepository;

import com.monepic.crud.entity.MappingItem;

public interface MappingItemRepo extends CrudRepository<MappingItem, Long> {

}
