package com.example.demo.repository;

import com.example.demo.model.SciencePlanModel;
import edu.gemini.app.ocs.model.SciencePlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SciencePlanRepository extends JpaRepository<SciencePlanModel, Long> {
//    <S extends SciencePlanModel> S save(SciencePlanModel entity);

    SciencePlanModel save(SciencePlan scip);

    List<SciencePlan> findByAstronomersId(Long astronomerId);
}