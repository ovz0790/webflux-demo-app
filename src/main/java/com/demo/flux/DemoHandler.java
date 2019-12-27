package com.demo.flux;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Service
@Slf4j
@RequiredArgsConstructor
public class DemoHandler implements IDemoHandler {

    private static final Mono<ServerResponse> NOT_FOUND_RESPONSE = ServerResponse.notFound().build();

    private final DemoReactiveRepository demoReactiveRepository;

    @Override
    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {

        log.debug("creating new user with name");

        final Mono<DemoUserDto> created = serverRequest.bodyToMono(DemoUserDto.class)
                .flatMap(demoReactiveRepository::save);
        return ok().contentType(APPLICATION_JSON)
                .body(created, DemoUserDto.class)
                .onErrorContinue((error, response) -> log.error("ERROR: ", error));
    }

    @Override
    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        final String userId = serverRequest.pathVariable("userId");
        log.debug("Updating user {}", userId);
        final Mono<DemoUserDto> updated = serverRequest.bodyToMono(DemoUserDto.class)
                .flatMap(demoReactiveRepository::save);
        return ok().contentType(APPLICATION_JSON)
                .body(updated, DemoUserDto.class)
                .onErrorContinue((error, response) -> log.error("ERROR: ", error));
    }

    @Override
    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        final String userId = serverRequest.pathVariable("userId");
        log.debug("Deleting user {}", userId);
        final Mono<Void> deleted = demoReactiveRepository.deleteById(userId);
        return ok()
                .body(deleted, Void.class)
                .onErrorContinue((error, response) -> log.error("ERROR: ", error));

    }

    @Override
    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        final String userId = serverRequest.pathVariable("userId");
        log.debug("Searching user {}", userId);
        final Mono<DemoUserDto> found = demoReactiveRepository.findById(userId);
        return ok().contentType(APPLICATION_JSON)
                .body(found, DemoUserDto.class)
                .switchIfEmpty(NOT_FOUND_RESPONSE)
                .onErrorContinue((error, response) -> log.error("ERROR: ", error));
    }

    @Override
    public Mono<ServerResponse> findAllUsers(ServerRequest serverRequest) {
        log.debug("Searching all users");
        val all = demoReactiveRepository.findAll();
        return ok().contentType(APPLICATION_JSON)
                .body(all, DemoUserDto.class)
                .switchIfEmpty(NOT_FOUND_RESPONSE);
    }

}
