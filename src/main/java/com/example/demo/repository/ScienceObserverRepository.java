package com.example.demo.repository;

import com.example.demo.model.SciencePlanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.ScienceObserver;

public interface ScienceObserverRepository extends JpaRepository<ScienceObserver, Long> {
}