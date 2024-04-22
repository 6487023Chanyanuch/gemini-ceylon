package com.example.demo.controller;

import ch.qos.logback.core.model.Model;
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

    @GetMapping("/sciplans/astronomer/{userId}")
    public ResponseEntity<List<SciencePlan>> getSciencePlanByAstronomerById(@PathVariable Long userId) {
        // Rest of your method implementation
        Optional<List<SciencePlanModel>> sciencePlansOptional = sciencePlanService.getSciencePlanByAstronomerById(userId);
        if (sciencePlansOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<SciencePlanModel> sciencePlans = sciencePlansOptional.get();
        List<SciencePlan> allSciencePlans = sciencePlans.stream().map(sciencePlanModel -> {
            // Fetch sciplan from osc based on science plan ID
            // Assuming ocs.getSciencePlanByNo() fetches the sciplan by its ID
            SciencePlan sciplan = ocs.getSciencePlanByNo(sciencePlanModel.getPlanNum());
            return sciplan;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(allSciencePlans);
    }

    @GetMapping("/astro/{userId}")
    public String showAstroPage ( @PathVariable Long userId, Model model ) {
        // Add logic here to retrieve data for the Astronomer page if needed
        return "astro";
    }

    @GetMapping("/sci/{userId}")
    public String showSciObPage ( @PathVariable Long userId, Model model ) {
        // Add logic here to retrieve data for the Astronomer page if needed
        return "sci";
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
    @PostMapping("/testsciplans")
    public ResponseEntity<SciencePlanModel> testSciencePlan(@RequestBody SciencePlanModel sciencePlan) {
        SciencePlanModel savedSciencePlan = sciencePlanRepository.save((SciencePlan) sciencePlan);
        ocs.testSciencePlan(savedSciencePlan);
        return ResponseEntity.ok(savedSciencePlan);
    }

}