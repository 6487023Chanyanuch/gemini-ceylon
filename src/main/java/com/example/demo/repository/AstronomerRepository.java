package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Astronomer;

public interface AstronomerRepository extends JpaRepository<Astronomer, Long> {
}
