package com.example.demo.repository;


import edu.gemini.app.ocs.model.ObservingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ObservingProgramModel;

public interface ObservingProgramRepository extends JpaRepository<ObservingProgramModel, Integer> {
}
