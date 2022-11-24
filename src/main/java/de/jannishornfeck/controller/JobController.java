package de.jannishornfeck.controller;

import de.jannishornfeck.entity.Test;
import de.jannishornfeck.service.ApiV1TestService;
import de.jannishornfeck.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

@Controller
@RestController
@RequestMapping(path = "/jobs")
public class JobController extends Logger {

    @Autowired
    ApiV1TestService testService;

    @GetMapping(path = "/create-all-tests")
    public ResponseEntity<Void> createAllTests(@RequestParam(defaultValue = "10") final int amount) {
        CompletableFuture.runAsync(() -> IntStream.range(1, amount + 1)
                .forEach(i -> {
                    Test test = new Test("Test " + i);

                    testService.createTest(test).subscribe();
                })
        );

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/delete-all-tests")
    public ResponseEntity<Void> deleteAllTests() {
        CompletableFuture.runAsync(() -> testService.getTests()
                .flatMap(test -> testService.deleteTest(test.getId()))
                .subscribe()
        );

        return ResponseEntity.ok().build();
    }

}