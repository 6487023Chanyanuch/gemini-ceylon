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
    @GetMapping("/sciplans/astronomer/{Id}")
    public ResponseEntity<List<Map<String, Object>>> getSciencePlanByAstronomerById(@PathVariable Long Id) {
        Optional<List<SciencePlanModel>> sciencePlansOptional = sciencePlanService.getSciencePlanByAstronomerById(Id);
        if (sciencePlansOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SciencePlanModel> sciencePlans = sciencePlansOptional.get();

        List<Map<String, Object>> allSciencePlans = sciencePlans.stream().map(sciencePlan -> {
            Map<String, Object> planDetails = new HashMap<>();
            planDetails.put("id", sciencePlan.getPlanNum());
            planDetails.put("creator", sciencePlan.getCreator());
            // Fetch sciplan from osc based on science plan ID
            // Assuming osc.getSciencePlanByNo() fetches the sciplan by its ID
            SciencePlan sciplan = ocs.getSciencePlanByNo(sciencePlan.getPlanNum());
            // Add sciplan details to the map
            if (sciplan != null) {
                planDetails.put("sciplanDetails", sciplan);
            }
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
    @GetMapping ("/testsciplans/{id}")
    public ResponseEntity<String> testSciencePlan(@PathVariable Long id) {
        if (id != null) {
            try {
//                Long planNo = Long.parseLong(planNoStr);
                SciencePlan sp = ocs.getSciencePlanByNo(Math.toIntExact(id));
                String testResult = ocs.testSciencePlan(sp);
//                String status = SciencePlan.STATUS.valueOf(String sp);
//                System.out.println(testResult);
                return ResponseEntity.ok(testResult);
            } catch (NumberFormatException e) {
                return ResponseEntity.badRequest().body("Invalid plan ID");
            }
        } else {
            return ResponseEntity.badRequest().body("Plan ID is required");
        }
    }

}