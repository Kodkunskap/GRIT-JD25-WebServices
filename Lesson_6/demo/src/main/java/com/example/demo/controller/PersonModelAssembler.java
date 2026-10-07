package com.example.demo.controller;

import com.example.demo.model.Person;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {

    @Override
    public EntityModel<Person> toModel(Person person) {
        EntityModel<Person> model = EntityModel.of(
                person,
                linkTo(
                        methodOn(PersonController.class).get(person.getId())
                ).withSelfRel(),
                linkTo(
                        methodOn(PersonController.class).list()
                ).withRel("collection")
        );
        return model;
    }

    @Override
    public CollectionModel<EntityModel<Person>> toCollectionModel(Iterable<? extends Person> persons) {
        return RepresentationModelAssembler.super.toCollectionModel(persons)
                .add(
                    linkTo(methodOn(PersonController.class).list()).withSelfRel()
                );
    }
}
