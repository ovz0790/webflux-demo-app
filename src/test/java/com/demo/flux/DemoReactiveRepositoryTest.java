package com.demo.flux;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DemoReactiveRepositoryTest {

    @Autowired
    private DemoReactiveRepository demoReactiveRepository;

    @Test
    public void shoudExecuteBasicGrudOperations() {
        val newUser = new DemoUserDto().setUserName("Some Name");

        val createdUser = demoReactiveRepository.save(newUser);
        StepVerifier.create(createdUser).assertNext(user -> assertNotNull(user.getUserId())).expectComplete().verify();

        Mono<DemoUserDto> foundById = demoReactiveRepository.findById(createdUser.block().getUserId());
        StepVerifier.create(foundById).assertNext(user -> assertNotNull(user)).expectComplete().verify();

        val all = demoReactiveRepository.findAll().collectList();
        StepVerifier.create(all).recordWith(ArrayList::new).expectNextCount(1).expectComplete().verify();

    }
}
