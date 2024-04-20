package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.gemini.app.ocs.model.ObservingProgram;
import edu.gemini.app.ocs.model.ObservingProgramConfigs;
import edu.gemini.app.ocs.model.TelePositionPair;
import jakarta.persistence.*;

@Entity
public class ObservingProgramModel extends ObservingProgram {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int planNo;
    private String opticsPrimary;
    private double fStop;
    private double opticsSecondaryRMS;
    private double scienceFoldMirrorDegree;
    private String scienceFoldMirrorType;
    private int moduleContent;
    private String calibrationUnit;
    private String lightType;


    @Override
    public void setPlanNo(int planNo) {
        this.planNo = planNo;
    }

    @Override
    public int getPlanNo() {
        return planNo;
    }

    @Override
    public void setOpticsPrimary(String opticsPrimary) {
        this.opticsPrimary = opticsPrimary;
    }

    @Override
    public String getOpticsPrimary() {
        return opticsPrimary;
    }

    @Override
    public void setfStop(double fStop) {
        this.fStop = fStop;
    }

    @Override
    public double getfStop() {
        return fStop;
    }

    @Override
    public void setOpticsSecondaryRMS(double opticsSecondaryRMS) {
        this.opticsSecondaryRMS = opticsSecondaryRMS;
    }

    @Override
    public double getOpticsSecondaryRMS() {
        return opticsSecondaryRMS;
    }

    @Override
    public void setScienceFoldMirrorDegree(double scienceFoldMirrorDegree) {
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
    }

    @Override
    public double getScienceFoldMirrorDegree() {
        return scienceFoldMirrorDegree;
    }


    public void setScienceFoldMirrorType(String scienceFoldMirrorType) {
        this.scienceFoldMirrorType = scienceFoldMirrorType;
    }

    @Override
    public ObservingProgramConfigs.FoldMirrorType getScienceFoldMirrorType() {
        return ObservingProgramConfigs.FoldMirrorType.valueOf(scienceFoldMirrorType);
    }

    @Override
    public void setModuleContent(int moduleContent) {
        this.moduleContent = moduleContent;
    }

    @Override
    public int getModuleContent() {
        return moduleContent;
    }

    public void setCalibrationUnit(String calibrationUnit) {
        this.calibrationUnit = calibrationUnit;
    }

    @Override
    public ObservingProgramConfigs.CalibrationUnit getCalibrationUnit() {
        return ObservingProgramConfigs.CalibrationUnit.valueOf(calibrationUnit);
    }

    public void setLightType(String lightType) {
        this.lightType = lightType;
    }

    @Override
    public ObservingProgramConfigs.LightType getLightType() {
        return ObservingProgramConfigs.LightType.valueOf(lightType);
    }

//    public Map<Object, Object> getTelePositionPairs() {
//        Map<Object, Object> telePositionPairsMap = new HashMap<>();
//        for (int i = 0; i < telePositionPairs.size(); i++) {
//            PositionPair positionPair = telePositionPairs.get(i);
//            telePositionPairsMap.put("PositionPair" + i, positionPair);
//        }
//        return telePositionPairsMap;
//    }

//    public TelePositionPair[] getTelePositionPairs() {
//        return telePositionPairs;
//    }
//
//    public void setTelePositionPairs(TelePositionPair[] telePositionPairs) {
//        this.telePositionPairs = telePositionPairs;
//    }

    @Embeddable
    public static class PositionPair {
        private double direction;
        private double degree;

        public PositionPair() {
        }

        @JsonCreator
        public PositionPair(@JsonProperty("direction") double direction, @JsonProperty("degree") double degree) {
            this.direction = direction;
            this.degree = degree;
        }

        public double getDegree() {
            return degree;
        }

        public void setDegree(double degree) {
            this.degree = degree;
        }

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }
    }

    @ElementCollection
    @JsonDeserialize(contentAs = TelePositionPair.class)
    private List<PositionPair> telePositionPairs;

    public List<PositionPair> getTelePositionPairs() {
        return telePositionPairs;
    }

    public void setTelePositionPairs(List<PositionPair> telePositionPairs) {
        this.telePositionPairs = telePositionPairs;
    }


//    @ManyToOne
//    @JoinColumn(name = "science_observer_id")
//    private ScienceObserver scienceObserver_observingProgram;
//
//    public ScienceObserver getScienceObserver() {
//        return scienceObserver_observingProgram;
//    }
//
//    public void setScienceObserver(ScienceObserver scienceObserver) {
//        this.scienceObserver_observingProgram = scienceObserver;
//    }

    public ObservingProgramModel() {
    }

    public ObservingProgramModel(int planNo, String opticsPrimary, double fStop, double opticsSecondaryRMS,
                                 double scienceFoldMirrorDegree, String scienceFoldMirrorType,
                                 int moduleContent, String calibrationUnit, String lightType,
                                 List<PositionPair> telePositionPairs) {
        this.planNo = planNo;
        this.opticsPrimary = opticsPrimary;
        this.fStop = fStop;
        this.opticsSecondaryRMS = opticsSecondaryRMS;
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
        this.scienceFoldMirrorType = scienceFoldMirrorType;
        this.moduleContent = moduleContent;
        this.calibrationUnit = calibrationUnit;
        this.lightType = lightType;
        this.telePositionPairs = telePositionPairs;
//        this.scienceObserver_observingProgram = scienceObserver_observingProgram;
    }
}