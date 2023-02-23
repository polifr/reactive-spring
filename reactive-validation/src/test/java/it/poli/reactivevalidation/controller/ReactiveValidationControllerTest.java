package it.poli.reactivevalidation.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import it.poli.reactivevalidation.controller.model.PostValidationRequest;

@WebFluxTest(ReactiveValidationController.class)
class ReactiveValidationControllerTest {

  @Autowired private WebTestClient webClient;

  @Test
  void testInjection() {
    assertNotNull(webClient, "wehClient null");
  }

  @ParameterizedTest
  @NullSource
  @ValueSource(strings = {"", " ", "  ", "   "})
  void testPostValidationBadValue(String value) {
    webClient.post()
        .uri("/reactive-validation/process")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(PostValidationRequest.builder().value(value).build())
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .expectStatus()
        .isBadRequest();
  }

  @ParameterizedTest
  @ValueSource(strings = {"A", "1", "XYZ", "1A 2B 3C 4D"})
  void testPostValidationGoodValue(String value) {
    webClient.post()
        .uri("/reactive-validation/process")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(PostValidationRequest.builder().value(value).build())
        .accept(MediaType.TEXT_PLAIN)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(String.class)
        .isEqualTo(value);
  }
}
