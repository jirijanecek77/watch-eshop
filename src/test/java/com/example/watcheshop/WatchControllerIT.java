package com.example.watcheshop;

import com.example.watcheshop.api.v1.dto.WatchInputDTO;
import com.example.watcheshop.model.Watch;
import com.example.watcheshop.repository.WatchRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.Base64;
import java.util.UUID;

import static com.example.watcheshop.api.v1.controller.ApiPath.API_WATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(
        classes = {WatchEshopApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class WatchControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private WatchRepository watchRepository;


    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @AfterEach
    public void tearDown() {
        watchRepository.deleteAll();
    }

    @Test
    void testCreateWatch() {
        WatchInputDTO dto = prepareInputDTO();

        webTestClient.post()
                .uri(API_WATCH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.title").isEqualTo("Prim")
                .jsonPath("$.price").isEqualTo(250000)
                .jsonPath("$.description").isEqualTo("A watch with a water fountain picture");

        Iterable<Watch> watches = watchRepository.findAll();
        assertThat(watches).hasSize(1);
        assertThat(watches.iterator().next()).isEqualToIgnoringGivenFields(
                getExpectedWatch(), "id"
        );
    }

    @Test
    void testCreateWatchWithConflict() {
        watchRepository.save(getExpectedWatch());
        WatchInputDTO dto = prepareInputDTO();

        webTestClient.post()
                .uri(API_WATCH)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .bodyValue(dto)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);

        Iterable<Watch> watches = watchRepository.findAll();
        assertThat(watches).hasSize(1);
    }

    private WatchInputDTO prepareInputDTO() {
        WatchInputDTO inputDTO = new WatchInputDTO();
        inputDTO.title = "Prim";
        inputDTO.price = "250000";
        inputDTO.description = "A watch with a water fountain picture";
        inputDTO.fountain = "R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=";

        return inputDTO;
    }

    private Watch getExpectedWatch() {
        return new Watch(UUID.randomUUID(),
                "Prim",
                250000,
                "A watch with a water fountain picture",
                Base64.getDecoder().decode("R0lGODlhAQABAIAAAAUEBAAAACwAAAAAAQABAAACAkQBADs=")
        );
    }
}