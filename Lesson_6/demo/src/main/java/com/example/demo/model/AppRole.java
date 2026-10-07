package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "approles")
public class AppRole {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Spara gärna med prefix "ROLE_" i databasen (t.ex. ROLE_ADMIN)
    @Column(unique = true, nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}