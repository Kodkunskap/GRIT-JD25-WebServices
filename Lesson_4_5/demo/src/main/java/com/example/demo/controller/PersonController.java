package com.example.demo.controller;

import com.example.demo.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class PersonController {

    @GetMapping("person/")
    public List<Person> list() {
        return List.of(new Person());
    }

    @GetMapping("person/{id}")
    public Person get(@PathVariable Long id) {
        return new Person();
    }

    @PostMapping("person/")
    public void post(@RequestBody Person person) {

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
    public void delete(@PathVariable Long id) {

    }

}
