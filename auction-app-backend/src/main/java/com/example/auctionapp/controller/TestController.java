package com.example.auctionapp.controller;

import com.example.auctionapp.entity.TestEntity;
import com.example.auctionapp.repository.TestRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    private final TestRepository testRepository;

    public TestController(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @GetMapping("/")
    public Iterable<TestEntity> findAllTest() {
        return testRepository.findAll();
    }

    @PostMapping("/")
    public TestEntity addTest(@RequestBody TestEntity test) {
        return testRepository.save(test);
    }
}
