package com.example.demo.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.gemini.app.ocs.model.ObservingProgramConfigs;
import jakarta.persistence.*;

@Entity
public class ObservingProgramModel  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    private int planNo;
    private String opticsPrimary;
    private double fStop;
    private double opticsSecondaryRMS;
    private double scienceFoldMirrorDegree;
    private String scienceFoldMirrorType;
    private int moduleContent;
    private String calibrationUnit;
    private String lightType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tele_position_pairs",
            joinColumns = @JoinColumn(name = "plan_no"),
            inverseJoinColumns = @JoinColumn(name = "position_pair_id"))
    private List<PositionPair> telePositionPairs;

    public void setPlanNo(int planNo) {
        this.planNo = planNo;
    }

    public int getPlanNo() {
        return planNo;
    }


    public void setOpticsPrimary(String opticsPrimary) {
        this.opticsPrimary = opticsPrimary;
    }

    public String getOpticsPrimary() {
        return opticsPrimary;
    }

    public void setfStop(double fStop) {
        this.fStop = fStop;
    }

    public double getfStop() {
        return fStop;
    }

    public void setOpticsSecondaryRMS(double opticsSecondaryRMS) {
        this.opticsSecondaryRMS = opticsSecondaryRMS;
    }

    public double getOpticsSecondaryRMS() {
        return opticsSecondaryRMS;
    }

    public void setScienceFoldMirrorDegree(double scienceFoldMirrorDegree) {
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
    }

    public double getScienceFoldMirrorDegree() {
        return scienceFoldMirrorDegree;
    }

//    public void setScienceFoldMirrorType(String scienceFoldMirrorType) {
//        this.scienceFoldMirrorType = scienceFoldMirrorType;
//    }
//
//    public ObservingProgramConfigs.FoldMirrorType getScienceFoldMirrorType() {
//        return ObservingProgramConfigs.FoldMirrorType.valueOf(scienceFoldMirrorType);
//    }

    public void setModuleContent(int moduleContent) {
        this.moduleContent = moduleContent;
    }

    public int getModuleContent() {
        return moduleContent;
    }

//    public void setCalibrationUnit(String calibrationUnit) {
//        this.calibrationUnit = calibrationUnit;
//    }
//
//    public ObservingProgramConfigs.CalibrationUnit getCalibrationUnit() {
//        return ObservingProgramConfigs.CalibrationUnit.valueOf(calibrationUnit);
//    }

    public void setScienceFoldMirrorType(ObservingProgramConfigs.FoldMirrorType scienceFoldMirrorType) {
        this.scienceFoldMirrorType = scienceFoldMirrorType.name();
    }

    public ObservingProgramConfigs.FoldMirrorType getScienceFoldMirrorType() {
        return ObservingProgramConfigs.FoldMirrorType.valueOf(scienceFoldMirrorType);
    }

    public void setCalibrationUnit(ObservingProgramConfigs.CalibrationUnit calibrationUnit) {
        this.calibrationUnit = calibrationUnit.name();
    }

    public ObservingProgramConfigs.CalibrationUnit getCalibrationUnit() {
        return ObservingProgramConfigs.CalibrationUnit.valueOf(calibrationUnit);
    }


    public void setLightType(String lightType) {
        this.lightType = lightType;
    }

    public ObservingProgramConfigs.LightType getLightType() {
        return ObservingProgramConfigs.LightType.valueOf(lightType);
    }

    public ObservingProgramModel() {
    }

    public ObservingProgramModel(int planNo, String opticsPrimary, double fStop, double opticsSecondaryRMS,
                                 double scienceFoldMirrorDegree, String scienceFoldMirrorType,
                                 int moduleContent, String calibrationUnit, String lightType, List<PositionPair> telePositionPairs) {
        this.planNo = planNo;
        this.opticsPrimary = opticsPrimary;
        this.fStop = fStop;
        this.opticsSecondaryRMS = opticsSecondaryRMS;
        this.scienceFoldMirrorDegree = scienceFoldMirrorDegree;
        this.scienceFoldMirrorType = scienceFoldMirrorType;
        this.moduleContent = moduleContent;
        this.calibrationUnit = calibrationUnit;
        this.lightType = lightType;
//        this.positionPair = positionPair;
        this.telePositionPairs = telePositionPairs;
        //        this.scienceObserver_observingProgram = scienceObserver_observingProgram;
    }

    public PositionPair[] getTelePositionPairs() {
        return telePositionPairs.toArray(new PositionPair[telePositionPairs.size()]);
    }
}