package com.example.demo.service;

import com.example.demo.model.Astronomer;
import com.example.demo.model.SciencePlanModel;
import com.example.demo.repository.AstronomerRepository;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.SciencePlanRepository;
import com.example.demo.repository.UserRepository;
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

    @Autowired
    private ScienceObserverRepository scienceObserverRepository;
    @Autowired
    private UserRepository   userRepository;



    public Optional<List<SciencePlanModel>> getSciencePlanByAstronomerById(Long Id) {
        Astronomer astronomer = astronomerRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Astronomer not found with id " + Id));

        List<SciencePlanModel> sciencePlans = astronomer.getSciencePlans().stream()
                .map(sciencePlan -> (SciencePlanModel) sciencePlan)
                .collect(Collectors.toList());

        return Optional.of(Optional.ofNullable(sciencePlans).orElse(Collections.emptyList()));
    }





//    public Optional<List<SciencePlanModel>> getSciencePlanByById(Long id) {
//        Astronomer astronomer = UserRepository.findByID(id)
//                .orElseThrow(() -> new RuntimeException("Astronomer not found with id " + id));
//
//        List<SciencePlanModel> sciencePlans = astronomer.getSciencePlans().stream()
//                .map(sciencePlan -> (SciencePlanModel) sciencePlan)
//                .collect(Collectors.toList());
//
//        return Optional.of(Optional.ofNullable(sciencePlans).orElse(Collections.emptyList()));
//    }


}
