package com.monepic.crud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.monepic.crud.entity.MappingItem;
import com.monepic.crud.repo.MappingItemRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitData implements CommandLineRunner {

    final MappingItemRepo repo;

    @Override
    public void run(String... args) {
        log.info("Saving test data");
        MappingItem first = new MappingItem();
        first.setFrom("fish");
        first.setTo("chips");
        repo.save(first);

        MappingItem second = new MappingItem();
        second.setFrom("fish");
        second.setTo("chips");
        repo.save(second);
    }

}