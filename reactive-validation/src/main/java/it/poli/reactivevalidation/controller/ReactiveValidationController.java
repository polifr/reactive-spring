package it.poli.reactivevalidation.controller;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.poli.reactivevalidation.controller.model.PostValidationRequest;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping({"/reactive-validation"})
public class ReactiveValidationController {

  @PostMapping({"/process"})
  public Mono<ResponseEntity<String>> postValidation(@Valid @RequestBody Mono<PostValidationRequest> request) {
    return request.map(res -> ResponseEntity.ok(res.getValue()));
  }
}
