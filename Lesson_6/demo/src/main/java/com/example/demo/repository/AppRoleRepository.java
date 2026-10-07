package com.example.demo.repository;

import com.example.demo.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, Long> {
    public AppRole findByName(String name);
}
