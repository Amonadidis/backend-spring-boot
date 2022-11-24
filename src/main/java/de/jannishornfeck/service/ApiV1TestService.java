package de.jannishornfeck.service;

import de.jannishornfeck.entity.Test;
import de.jannishornfeck.util.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApiV1TestService extends Logger {

    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private static final String URL = BASE_URL + "/tests/";
    private static final String URL_ITEM = BASE_URL + "/tests/{id}";

    WebClient webClient = WebClient.builder().baseUrl(BASE_URL).build();

    public Mono<Test> createTest(Test test) {
        return webClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(test), Test.class)
                .retrieve()
                .bodyToMono(Test.class);
    }

    public Mono<Void> deleteTest(String id) {
        return webClient.delete().uri(URL_ITEM, id).retrieve().bodyToMono(Void.class);
    }

    public Flux<Test> getTests() {
        return webClient.get().uri(URL).accept(MediaType.APPLICATION_JSON).retrieve().bodyToFlux(Test.class);
    }

    public Mono<Test> getTest(String id) {
        return webClient.get().uri(URL_ITEM, id).accept(MediaType.APPLICATION_JSON).retrieve().bodyToMono(Test.class);
    }

    public Mono<Test> putTest(String id, Test test) {
        return webClient.put()
                .uri(URL_ITEM, id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(test), Test.class)
                .retrieve()
                .bodyToMono(Test.class);
    }

}
