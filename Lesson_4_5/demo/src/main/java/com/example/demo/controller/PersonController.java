package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Person> list() {
        return personService.listAll();
    }

    @GetMapping("person/{id}")
    public Person get(@PathVariable Long id) {
        return personService.get(id);
    }

    @PostMapping("person/")
    public void post(@RequestBody Person person) {
        personService.save(person);
    }

    @PutMapping("person/{id}")
    public ResponseEntity<?> update(
            @RequestBody Person person,
            @PathVariable Long id
    ) {

        //return ResponseEntity.ok(new Person());
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @DeleteMapping("person/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        personService.delete(id);
        return ResponseEntity.ok().build();
    }

}
