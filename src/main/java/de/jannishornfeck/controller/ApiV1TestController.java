package de.jannishornfeck.controller;

import de.jannishornfeck.entity.Test;
import de.jannishornfeck.repository.TestRepository;
import de.jannishornfeck.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Controller
@RestController
@RequestMapping(path = "/api/v1/tests")
public class ApiV1TestController extends Logger {

    @Autowired
    private TestRepository testRepository;

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> deleteTest(@PathVariable final String id) {
        return this.testRepository.deleteById(id)
                .doOnSuccess(ignored -> this.logger.info("Test[id=" + id + "] deleted."))
                .thenReturn(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Test>> getTest(@PathVariable final String id) {
        return this.testRepository.findById(id)
                .doOnSuccess(test -> this.logger.info(test + " read."))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Test>>> getTests() {
        return this.testRepository.findAll()
                .doOnComplete(() -> this.logger.info("Tests read."))
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> postTest(@RequestBody final Test test) {
        return this.testRepository.save(test)
                .doOnSuccess(testCreated -> this.logger.info(testCreated + " created."))
                .map(Test::getId)
                .map(id -> ResponseEntity.created(URI.create("/api/v1/test/" + id)).build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Test>> putTest(@PathVariable final String id, @RequestBody final Test test) {
        return this.testRepository.findById(id)
                .flatMap(testToUpdate -> {
                    testToUpdate.setName(test.getName());

                    return this.testRepository.save(testToUpdate);
                })
                .doOnSuccess(testUpdated -> this.logger.info(testUpdated + " updated."))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
