package de.jannishornfeck.controller;

import de.jannishornfeck.entity.Test;
import de.jannishornfeck.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping(path = "/tests")
public class TestController {

    private static final String REDIRECT_TESTS = "redirect:/tests";

    @Autowired
    private TestService testService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> createTest(final ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .flatMap(multiValueMap -> {
                    String name = multiValueMap.getFirst("name");

                    Test test = new Test(name);

                    return this.testService.createTest(test);
                })
                .thenReturn(REDIRECT_TESTS);
    }

    @PostMapping(path = "/delete", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> deleteTest(final ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .flatMap(multiValueMap -> {
                    String id = multiValueMap.getFirst("id");

                    return this.testService.deleteTest(id);
                })
                .thenReturn(REDIRECT_TESTS);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<String> updateTest(final ServerWebExchange serverWebExchange) {
        return serverWebExchange.getFormData()
                .flatMap(multiValueMap -> {
                    String id = multiValueMap.getFirst("id");
                    String name = multiValueMap.getFirst("name");

                    return this.testService.updateTest(id, name);
                })
                .thenReturn(REDIRECT_TESTS);
    }

    @GetMapping(path = "/{id}")
    public Mono<String> readTest(@PathVariable final String id, final Model model) {
        return testService.readTest(id)
                .map(test -> model.addAttribute("test", test))
                .thenReturn("test");
    }

    @GetMapping()
    public Mono<String> readTests(final Model model) {
        return testService.readTests()
                .map(tests -> model.addAttribute("tests", tests))
                .thenReturn("tests");
    }

}
