package com.example.demo.controller;

import com.example.demo.model.ObservingProgramModel;
import com.example.demo.model.PositionPair;
import com.example.demo.model.SciencePlanModel;
import com.example.demo.repository.ObservingProgramRepository;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.SciencePlanRepository;
import com.example.demo.service.SciencePlanService;
import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.ObservingProgram;
import edu.gemini.app.ocs.model.SciencePlan;
import edu.gemini.app.ocs.model.TelePositionPair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class ObservingProgramController {
    OCS ocs = new OCS();

    @Autowired
    private SciencePlanRepository sciencePlanRepository;

    @Autowired
    private SciencePlanService sciencePlanService;

    @Autowired
    private ScienceObserverRepository scienceObserverRepository;

    @Autowired
    private ObservingProgramRepository observingProgramRepository;

    @CrossOrigin
    @GetMapping("/observing")
    @ResponseBody
    public List<ObservingProgram> getAllObservingPrograms() {
        ObservingProgram[] observingPrograms = ocs.getObservingPrograms();
        List<ObservingProgram> observingProgramList = new ArrayList<>(observingPrograms.length);
        observingProgramList.addAll(Arrays.asList(observingPrograms));
        return observingProgramList;
    }

    @CrossOrigin
    @GetMapping("/observing/{id}")
    @ResponseBody
    public ObservingProgram getObservingProgramById(@PathVariable Long id) {
        int planNo = id.intValue();
        SciencePlan sp = ocs.getSciencePlanByNo(planNo);
        return ocs.getObservingProgramBySciencePlan(sp);
    }

    @CrossOrigin
    @PostMapping("/addobserving")
    public ResponseEntity<ObservingProgramModel> createObservingProgram(@RequestBody ObservingProgramModel observingProgramModel) {

        List<PositionPair> positionPairs = List.of(observingProgramModel.getTelePositionPairs());
        for (PositionPair positionPair : positionPairs) {
            positionPair.setObservingProgramModel(observingProgramModel);
        }

        ObservingProgramModel savedObservingProgram = observingProgramRepository.saveAndFlush(observingProgramModel);
        int a = savedObservingProgram.getTelePositionPairs().length;
        System.out.println("---------------------------------------------------------------");
        System.out.println(a);
        System.out.println("---------------------------------------------------------------");

        System.out.println("---------------------------------------------------------------");
        System.out.println(savedObservingProgram.getTelePositionPairs());
        System.out.println("---------------------------------------------------------------");

        // Convert PositionPair list to an array of TelePositionPair
        TelePositionPair[] telePositionPairs = new TelePositionPair[positionPairs.size()];
        for (int i = 0; i < positionPairs.size(); i++) {
            PositionPair pp = positionPairs.get(i);
            telePositionPairs[i] = new TelePositionPair(pp.getDirection(), pp.getDegree());
        }
        // Debugging to verify telePositionPairs
        System.out.println("---------------------------------------------------------------");
        System.out.println("Number of Position Pairs: " + positionPairs.size());
        for (TelePositionPair tpp : telePositionPairs) {
            System.out.println("TelePositionPair - Direction: " + tpp.getDirection() + ", Degree: " + tpp.getDegree());
        }
        System.out.println("---------------------------------------------------------------");


        ObservingProgram op = ocs.createObservingProgram(ocs.getSciencePlanByNo(savedObservingProgram.getPlanNo()), savedObservingProgram.getOpticsPrimary()
                ,savedObservingProgram.getfStop(),savedObservingProgram.getOpticsSecondaryRMS(),savedObservingProgram.getScienceFoldMirrorDegree(),
                savedObservingProgram.getScienceFoldMirrorType(),savedObservingProgram.getModuleContent(),savedObservingProgram.getCalibrationUnit(),savedObservingProgram.getLightType(), telePositionPairs);
        System.out.println(op);
        op.validateObservingCondition(op);
        System.out.println(op);
        ocs.saveObservingProgram(op);


        return ResponseEntity.ok(savedObservingProgram);
    }

    @CrossOrigin
    @GetMapping("/allObserving")
    @ResponseBody
    public List<ObservingProgramModel> showAllObservingPrograms() {
        List<ObservingProgramModel> allObservingPrograms = observingProgramRepository.findAll();
        return allObservingPrograms;
    }

}
