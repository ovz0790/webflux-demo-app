package com.demo.flux;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface IDemoHandler {

    Mono<ServerResponse> createUser(ServerRequest serverRequest);

    Mono<ServerResponse> updateUser(ServerRequest serverRequest);

    Mono<ServerResponse> deleteUser(ServerRequest serverRequest);

    Mono<ServerResponse> findUserById(ServerRequest serverRequest);

    Mono<ServerResponse> findAllUsers(ServerRequest serverRequest);

}
