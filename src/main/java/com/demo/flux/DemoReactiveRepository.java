package com.demo.flux;

import lombok.val;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class DemoReactiveRepository implements ReactiveCrudRepository<DemoUserDto, String> {

    private final Map<String, DemoUserDto> storage = new ConcurrentHashMap<>();

    @Override
    public Mono<DemoUserDto> save(DemoUserDto user) {
        val newId = UUID.randomUUID().toString();
        storage.put(newId, user.setUserId(newId));
        return Mono.justOrEmpty(storage.get(newId));
    }

    @Override
    public <S extends DemoUserDto> Flux<S> saveAll(Iterable<S> entities) {
        return notSupportedFluxOperation();
    }

    @Override
    public <S extends DemoUserDto> Flux<S> saveAll(Publisher<S> entityStream) {
        return notSupportedFluxOperation();
    }

    @Override
    public Mono<DemoUserDto> findById(String id) {
        return Mono.justOrEmpty(storage.get(id));
    }

    @Override
    public Mono<DemoUserDto> findById(Publisher<String> id) {
        return notSupportedMonoOperation();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return Mono.justOrEmpty(storage.containsKey(id));
    }

    @Override
    public Mono<Boolean> existsById(Publisher<String> id) {
        return notSupportedBooleanOperation();
    }

    @Override
    public Flux<DemoUserDto> findAll() {
        return Flux.fromStream(storage.values().stream());
    }

    @Override
    public Flux<DemoUserDto> findAllById(Iterable<String> strings) {
        return notSupportedFluxOperation();
    }

    @Override
    public Flux<DemoUserDto> findAllById(Publisher<String> idStream) {
        return notSupportedFluxOperation();
    }

    @Override
    public Mono<Long> count() {
        final long count = storage.size();
        return Mono.just(count);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        storage.remove(id);
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteById(Publisher<String> id) {
        return notSupportedMonoVoidOperation();
    }

    @Override
    public Mono<Void> delete(DemoUserDto entity) {
        storage.remove(entity.getUserId());
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends DemoUserDto> entities) {
        return notSupportedMonoVoidOperation();
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends DemoUserDto> entityStream) {
        return notSupportedMonoVoidOperation();
    }

    @Override
    public Mono<Void> deleteAll() {
        storage.clear();
        return Mono.empty();
    }

    private Mono<DemoUserDto> notSupportedMonoOperation() {
        throw new NotSupportedOperationException();
    }

    private Mono<Boolean> notSupportedBooleanOperation() {
        throw new NotSupportedOperationException();
    }

    private  <S extends DemoUserDto> Flux<S> notSupportedFluxOperation() {
        throw new NotSupportedOperationException();
    }

    private Mono<Void> notSupportedMonoVoidOperation() {
        throw new NotSupportedOperationException();
    }

}
