package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PersonController {

    private PersonService personService;

     public PersonController(PersonService personService) {
         this.personService = personService;
     }

    @GetMapping("person/")
//    @CrossOrigin(
//            origins = { "http://127.0.0.1:5500", "http://localhost:5050" },
//            methods = RequestMethod.GET
//    )
    public List<Person> list() {
        return personService.listAll();
    }

    @GetMapping("person/{id}")
    public Person get(@PathVariable Long id) {
        return personService.get(id);
    }

    @PostMapping("person/")
    public ResponseEntity<?> post(@Valid @RequestBody Person person) {
        personService.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @PutMapping("person/{id}")
    public ResponseEntity<?> update(
            @Valid @RequestBody Person person,
            @PathVariable Long id
    ) {
        personService.save(person);
        return ResponseEntity.ok(new Person());
    }

    @DeleteMapping("person/{id}")
    @Validated
    public ResponseEntity<?> delete(@PathVariable @Min(value = 1, message = "Id has to be greater than 0") Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

}
