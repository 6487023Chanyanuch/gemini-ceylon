package com.example.demo.service;

import com.example.demo.model.Astronomer;
import com.example.demo.model.SciencePlanModel;
import com.example.demo.repository.AstronomerRepository;
import com.example.demo.repository.SciencePlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SciencePlanService {

    @Autowired
    private SciencePlanRepository sciencePlanRepository;

    @Autowired
    private AstronomerRepository astronomerRepository;

    public Optional<List<SciencePlanModel>> getSciencePlanByAstronomerById(Long astronomerId) {
        Astronomer astronomer = astronomerRepository.findById(astronomerId)
                .orElseThrow(() -> new RuntimeException("Astronomer not found with id " + astronomerId));

        List<SciencePlanModel> sciencePlans = astronomer.getSciencePlans().stream()
                .map(sciencePlan -> (SciencePlanModel) sciencePlan)
                .collect(Collectors.toList());

        return Optional.of(Optional.ofNullable(sciencePlans).orElse(Collections.emptyList()));
    }
}