package de.jannishornfeck.service;

import de.jannishornfeck.entity.Test;
import de.jannishornfeck.repository.TestRepository;
import de.jannishornfeck.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class TestService extends Logger {

    @Autowired
    private TestRepository testRepository;

    public Mono<Test> createTest(Test test) {
        return this.testRepository.save(test)
                .doOnSuccess(createdTest -> this.logger.info(createdTest + " created."));
    }

    public Mono<Void> deleteTest(String id) {
        return this.testRepository.deleteById(id)
                .doOnSuccess(ignored -> this.logger.info("Test[id=\"" + id + "\"] deleted."));
    }

    public Mono<Test> updateTest(String id, String name) {
        return this.testRepository.findById(id)
                .flatMap(test -> {
                    test.setName(name);

                    return this.testRepository.save(test);
                })
                .doOnSuccess(testUpdated -> this.logger.info(testUpdated + " updated."));
    }

    public Mono<Test> readTest(String id) {
        return this.testRepository.findById(id)
                .doOnSuccess(test -> this.logger.info(test + " read."));
    }

    public Mono<List<Test>> readTests() {
        return this.testRepository.findAll()
                .doOnComplete(() -> this.logger.info("Tests read."))
                .collectList();
    }

}
