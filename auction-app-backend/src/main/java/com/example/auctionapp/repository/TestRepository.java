package com.example.auctionapp.repository;

import com.example.auctionapp.entity.TestEntity;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestEntity, Integer> {}
