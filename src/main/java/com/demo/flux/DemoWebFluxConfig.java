package com.demo.flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@Configuration
public class DemoWebFluxConfig implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routeListing(IDemoHandler handler) {
        return RouterFunctions.route(GET("/demo/user/{userId}"), handler::findUserById)
                .andRoute(GET("/demo/user"), handler::findAllUsers)
                .andRoute(POST("/demo/user"), handler::createUser)
                .andRoute(PUT("/demo/user/{userId}"), handler::updateUser)
                .andRoute(DELETE("/demo/user/{userId}"), handler::deleteUser);
    }

}
