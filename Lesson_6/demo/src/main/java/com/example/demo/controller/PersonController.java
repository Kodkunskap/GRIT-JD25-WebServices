package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/")
public class PersonController {

    private PersonService personService;
    private PersonModelAssembler personModelAssembler;

     public PersonController(PersonService personService,  PersonModelAssembler personModelAssembler) {
         this.personService = personService;
         this.personModelAssembler = personModelAssembler;
     }

    @GetMapping("person/")
/*
    Det går att sätta CORS-regler individuellt för varje endpoint. Men det är ofta
    bättre att göra detta genom en konfiguration (@Configuration) - i vårt exempel
    använder vi WebConfiguration från Spring Boot MVC (WebConfig.java) eller
    SecurityConfiguration (SecurityConfig.java) från Spring Boot Security.

    @CrossOrigin(
            origins = { "http://127.0.0.1:5500", "http://localhost:5050" },
            methods = RequestMethod.GET
    )
 */
    public CollectionModel<EntityModel<Person>> list() {
        return personModelAssembler.toCollectionModel(
                personService.listAll()
        );
    }

    @GetMapping("person/{id}")
    public ResponseEntity<EntityModel<Person>> get(@PathVariable Long id) {
        return new ResponseEntity<>(
                personModelAssembler.toModel(personService.get(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("person/")
    public ResponseEntity<?> post(@Valid @RequestBody Person person) {
        personService.save(person);
        return new ResponseEntity<>(
                personModelAssembler.toModel(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("person/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody Person person,
            @PathVariable Long id
    ) {
        personService.save(person);
        return ResponseEntity.ok(personModelAssembler.toModel(person));
    }

    @DeleteMapping("person/{id}")
    @Validated
    public ResponseEntity<?> delete(@PathVariable @Min(value = 1, message = "Id has to be greater than 0") Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

}
