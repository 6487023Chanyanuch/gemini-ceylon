package com.example.demo.controller;

import com.example.demo.model.Astronomer;
import com.example.demo.model.ObservingProgramModel;
import com.example.demo.model.SciencePlanModel;
//import com.example.demo.repository.OCSRepository;
import com.example.demo.repository.ObservingProgramRepository;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.SciencePlanRepository;

import com.example.demo.service.SciencePlanService;
import edu.gemini.app.ocs.OCS;
import edu.gemini.app.ocs.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

//
//    @CrossOrigin
//    @GetMapping("/sciplans/{id}")
//    @ResponseBody
//    public SciencePlan getSciencePlanById(@PathVariable Long id) {
//        return ocs.getSciencePlanByNo(Math.toIntExact(id));
//    }

    @CrossOrigin
    @PostMapping("/testsciplans")
    public ResponseEntity<SciencePlanModel> testSciencePlan(@RequestBody SciencePlanModel sciencePlan) {
        SciencePlanModel savedSciencePlan = sciencePlanRepository.save((SciencePlan) sciencePlan);
        ocs.testSciencePlan(savedSciencePlan);
        return ResponseEntity.ok(savedSciencePlan);
    }

//    @PostMapping("/cree")
//    public ResponseEntity<ObservingProgramModel> createObservingProgram(@RequestBody ObservingProgramModel observingProgram) {
//        ObservingProgramModel newObservingProgram = observingProgramRepository.save(observingProgram);
//        return ResponseEntity.ok(newObservingProgram);
//    }

//    @CrossOrigin
//    @PostMapping("/cree")
//    public ResponseEntity<ObservingProgramModel> createObserving(@RequestBody ObservingProgramModel observationProgram) {
//
//        ObservingProgramModel obsm = observingProgramRepository.save(observationProgram);
//        TelePositionPair[] telePositionPairs = new TelePositionPair[5];
//        TelePositionPair telePositionPair1 = new TelePositionPair(35.0, 40.0);
//        telePositionPairs[0] = telePositionPair1;
//        TelePositionPair telePositionPair2 = new TelePositionPair(50.0, 60.0);
//        telePositionPairs[1] = telePositionPair2;
//        TelePositionPair telePositionPair3 = new TelePositionPair(80.0, 15.0);
//        telePositionPairs[2] = telePositionPair3;
//        TelePositionPair telePositionPair4 = new TelePositionPair(90.0, 10.0);
//        telePositionPairs[3] = telePositionPair4;
//        TelePositionPair telePositionPair5 = new TelePositionPair(20.0, 20.0);
//        telePositionPairs[4] = telePositionPair5;
//
//        ObservingProgram op = ocs.createObservingProgram(ocs.getSciencePlanByNo(obsm.getPlanNo()), obsm.getOpticsPrimary()
//                ,obsm.getfStop(),obsm.getOpticsSecondaryRMS(),obsm.getScienceFoldMirrorDegree(),
//                obsm.getScienceFoldMirrorType(),obsm.getModuleContent(),obsm.getCalibrationUnit(),obsm.getLightType(),telePositionPairs);
//      System.out.println(op);
//      op.validateObservingCondition(op);
//      System.out.println(op);
//      ocs.saveObservingProgram(op);
//
//        return ResponseEntity.ok(obsm);
//    }

//    @CrossOrigin
//    @PostMapping("/cree")
//    public ResponseEntity<ObservingProgramModel> createObserving(@RequestBody ObservingProgramModel observationProgram) {
//
//        ObservingProgramModel obsm = observingProgramRepository.save((ObservingProgram) observationProgram);
//
//        // ส่วนที่เพิ่มเติม เรียกใช้งานโมเดลที่ได้รับจาก JSON และประมวลผลต่อไป
//        SciencePlan sciencePlin = ocs.getSciencePlanByNo(Math.toIntExact(4));
//
//        // เรียกใช้งาน ObservingProgramConfigs เพื่อเตรียมข้อมูลที่จำเป็น
//        ObservingProgramConfigs.FoldMirrorType foldMirrorTypes = observationProgram.getScienceFoldMirrorType();
//        ObservingProgramConfigs.CalibrationUnit calibrationUnit = observationProgram.getCalibrationUnit();
//        ObservingProgramConfigs.LightType lightType = observationProgram.getLightType();
//        TelePositionPair[] telePositionPairs = observationProgram.getTelePositionPair();
//
//        // สร้าง ObservingProgram จากข้อมูลที่ได้รับมา
//        ObservingProgram op = ocs.createObservingProgram(sciencePlin, obsm.getOpticsPrimary(),
//                obsm.getfStop(), obsm.getOpticsSecondaryRMS(), obsm.getScienceFoldMirrorDegree(),
//                foldMirrorTypes, obsm.getModuleContent(), calibrationUnit, lightType, telePositionPairs);
//
//        // ประมวลผลต่อไปตามที่ต้องการ
//        System.out.println(op);
//        op.validateObservingCondition(op);
//        System.out.println(op);
//        ocs.saveObservingProgram(op);
//
//        return ResponseEntity.ok(obsm);
//    }
//
//    @CrossOrigin
//    @PostMapping("/cree")
//    public ResponseEntity<ObservingProgramModel> createObserving(@RequestBody ObservingProgramModel observationProgram) {
//
//        ObservingProgramModel obsm = observingProgramRepository.save((ObservingProgramModel) observationProgram);
//        // Sample parameters
////        SciencePlan sciencePlin = ocs.getSciencePlanByNo(obsm.getPlanNo());
//
//        ObservingProgramConfigs.FoldMirrorType foldMirrorTypes = ObservingProgramConfigs.FoldMirrorType.REFLECTIVE_CONVERGING_BEAM;
//        ObservingProgramConfigs.CalibrationUnit[] calibrationunits = ObservingProgramConfigs.getCalibrationUnit();
//        ObservingProgramConfigs.LightType[] lightTypes = ObservingProgramConfigs.getLightType();
//        TelePositionPair[] telePositionPairs = new TelePositionPair[5];
//        TelePositionPair telePositionPair1 = new TelePositionPair(35.0, 40.0);
//        telePositionPairs[0] = telePositionPair1;
//        TelePositionPair telePositionPair2 = new TelePositionPair(50.0, 60.0);
//        telePositionPairs[1] = telePositionPair2;
//        TelePositionPair telePositionPair3 = new TelePositionPair(80.0, 15.0);
//        telePositionPairs[2] = telePositionPair3;
//        TelePositionPair telePositionPair4 = new TelePositionPair(90.0, 10.0);
//        telePositionPairs[3] = telePositionPair4;
//        TelePositionPair telePositionPair5 = new TelePositionPair(20.0, 20.0);
//        telePositionPairs[4] = telePositionPair5;
//
//        ObservingProgram op = ocs.createObservingProgram(ocs.getSciencePlanByNo(obsm.getPlanNo()), obsm.getOpticsPrimary()
//                ,obsm.getfStop(),obsm.getOpticsSecondaryRMS(),obsm.getScienceFoldMirrorDegree(),
//                foldMirrorTypes,obsm.getModuleContent(),calibrationunits[1],lightTypes[1],telePositionPairs);
//        System.out.println(op);
//        op.validateObservingCondition(op);
//        System.out.println(op);
//        ocs.saveObservingProgram(op);
//
//        return ResponseEntity.ok(obsm);
//    }

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

//    @PostMapping("/addobserving")
//    public ResponseEntity<ObservingProgramModel> createObservingProgram(@RequestBody ObservingProgramModel observingProgramModel) {
//        ObservingProgramModel savedObservingProgram = observingProgramRepository.save(observingProgramModel);
//        ocs.saveObservingProgram(savedObservingProgram);
//        return ResponseEntity.ok(savedObservingProgram);
//    }

//    @CrossOrigin
//    @PostMapping("/cree")
//    public ResponseEntity<ObservingProgramModel> createObserving(@RequestBody ObservingProgramModel observationProgram) {
//
//        ObservingProgramModel obsm = observingProgramRepository.save(observationProgram);
//
//        // ส่วนที่เพิ่มเติม เรียกใช้งานโมเดลที่ได้รับจาก JSON และประมวลผลต่อไป
//        SciencePlan sciencePlin = ocs.getSciencePlanByNo(Math.toIntExact(4));
//
//        // เรียกใช้งาน ObservingProgramConfigs เพื่อเตรียมข้อมูลที่จำเป็น
//        ObservingProgramConfigs.FoldMirrorType foldMirrorTypes = observationProgram.getScienceFoldMirrorType();
//        ObservingProgramConfigs.CalibrationUnit calibrationUnit = observationProgram.getCalibrationUnit();
//        ObservingProgramConfigs.LightType lightType = observationProgram.getLightType();
////        TelePositionPair[] telePositionPairs = observationProgram.getTelePositionPair();
//
//        TelePositionPair[] telePositionPairsArray = convertToTelePositionPairArray(observationProgram.getTelePositionPairs());
//
//        // สร้าง ObservingProgram จากข้อมูลที่ได้รับมา
//        ObservingProgram op = ocs.createObservingProgram(sciencePlin,
//                obsm.getOpticsPrimary(),
//                obsm.getfStop(),
//                obsm.getOpticsSecondaryRMS(),
//                obsm.getScienceFoldMirrorDegree(),
//                foldMirrorTypes,
//                obsm.getModuleContent(),
//                calibrationUnit,
//                lightType,
//                telePositionPairsArray); // ใช้ telePositionPairsArray แทน telePositionPair
//
//        // ประมวลผลต่อไปตามที่ต้องการ
//        System.out.println(op);
//        op.validateObservingCondition(op);
//        System.out.println(op);
//        ocs.saveObservingProgram(op);
//
//        return ResponseEntity.ok(obsm);
//    }
//
//    private TelePositionPair[] convertToTelePositionPairArray(List<ObservingProgramModel.PositionPair> positionPairs) {
//        TelePositionPair[] telePositionPairsArray = new TelePositionPair[positionPairs.size()];
//        for (int i = 0; i < positionPairs.size(); i++) {
//            ObservingProgramModel.PositionPair positionPair = positionPairs.get(i);
//            TelePositionPair telePositionPair = new TelePositionPair(positionPair.getDirection(), positionPair.getDegree());
//            telePositionPairsArray[i] = telePositionPair;
//        }
//        return telePositionPairsArray;
//    }




}