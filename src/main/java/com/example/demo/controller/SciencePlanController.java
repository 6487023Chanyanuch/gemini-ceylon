package com.example.demo.controller;

import com.example.demo.model.Astronomer;
import com.example.demo.model.ObservingProgramModel;
import com.example.demo.model.PositionPair;
import com.example.demo.model.SciencePlanModel;
//import com.example.demo.repository.OCSRepository;
import com.example.demo.repository.ObservingProgramRepository;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.SciencePlanRepository;

import com.example.demo.service.SciencePlanService;
import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class SciencePlanController {

    OCS ocs = new OCS();

    @Autowired
    private SciencePlanRepository sciencePlanRepository;

    @Autowired
    private SciencePlanService sciencePlanService;

//    @Autowired
//    private OCSRepository ocsRepository;

    @Autowired
    private ScienceObserverRepository scienceObserverRepository;

    @Autowired
    private ObservingProgramRepository observingProgramRepository;

    @CrossOrigin
    @GetMapping("/sciplans")
    @ResponseBody
    public List<SciencePlan> getAllSciencePlans() {
        return ocs.getAllSciencePlans();
    }

    @CrossOrigin
    @GetMapping("/sciplans/{id}")
    @ResponseBody
    public SciencePlan getSciencePlanById(@PathVariable Long id) {
        return ocs.getSciencePlanByNo(Math.toIntExact(id));
    }

    @CrossOrigin
    @GetMapping("/sciplans/astronomer/{astronomerId}")
    public ResponseEntity<List<Map<String, Object>>> getSciencePlanByAstronomerById(@PathVariable Long astronomerId) {
        Optional<List<SciencePlanModel>> sciencePlansOptional = sciencePlanService.getSciencePlanByAstronomerById(astronomerId);
        if (sciencePlansOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SciencePlanModel> sciencePlans = sciencePlansOptional.get();

        List<Map<String, Object>> allSciencePlans = sciencePlans.stream().map(sciencePlan -> {
            Map<String, Object> planDetails = new HashMap<>();
            planDetails.put("id", sciencePlan.getPlanNum());
            planDetails.put("creator", sciencePlan.getCreator());;
            // Add more fields if needed
            return planDetails;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(allSciencePlans);
    }

    @CrossOrigin
    @PostMapping("/addsciplans")
    public ResponseEntity<SciencePlanModel> createSciencePlan(@RequestBody SciencePlanModel sciencePlann) {

        // Check telescopeLocation
        String telescopeLocation = String.valueOf(sciencePlann.getTelescopeLocation());
        if (telescopeLocation == null || !isValidTelescopeLocation(telescopeLocation)) {
            throw new IllegalArgumentException("Invalid telescope location: " + telescopeLocation);
        }

        // Assuming that sciencePlan.getAstronomers() returns a list of Astronomer objects
        List<Astronomer> astronomers = sciencePlann.getAstronomers();
        // You may want to validate or process the astronomers list here if needed

        // Set the association between SciencePlanModel and Astronomers
        sciencePlann.setAstronomers(astronomers);


        DataProcRequirement dpr1 =
                new DataProcRequirement(sciencePlann.getFileType(), sciencePlann.getFileQuality(), sciencePlann.getColorType(),
                        sciencePlann.getContrast(), sciencePlann.getBrightness(), sciencePlann.getSaturation(), sciencePlann.getHighlights()
                        , sciencePlann.getExposure(), sciencePlann.getShadows(), sciencePlann.getWhites(),
                        sciencePlann.getBlacks(), sciencePlann.getLuminance(), sciencePlann.getHue());

        sciencePlann.setDataProcRequirements(dpr1);

        String sc = ocs.createSciencePlan(sciencePlann);
        String numberOnly= sc.replaceAll("[^0-9]", "");
        System.out.println(sc);
        int planId = Integer.parseInt(numberOnly);
        System.out.println(planId);
        System.out.println("55555555555555555555555555555555555555555555555555555");
        sciencePlann.setPlanNum(planId);
//        System.out.println(sciencePlann.getPlanNum());
        SciencePlanModel savedSciencePlan = sciencePlanRepository.save((SciencePlan) sciencePlann);

        return ResponseEntity.ok(savedSciencePlan);
    }


    private boolean isValidStarSystem(String starSystem) {
        try {
            StarSystem.CONSTELLATIONS constellation = StarSystem.CONSTELLATIONS.valueOf(starSystem);
            return constellation != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isValidTelescopeLocation(String telescopeLocation) {
        try {
            SciencePlan.TELESCOPELOC location = SciencePlan.TELESCOPELOC.valueOf(telescopeLocation);
            return location != null;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    @CrossOrigin
    @PostMapping("/testsciplans")
    public ResponseEntity<SciencePlanModel> testSciencePlan(@RequestBody SciencePlanModel sciencePlan) {
        SciencePlanModel savedSciencePlan = sciencePlanRepository.save((SciencePlan) sciencePlan);
        ocs.testSciencePlan(savedSciencePlan);
        return ResponseEntity.ok(savedSciencePlan);
    }


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

}