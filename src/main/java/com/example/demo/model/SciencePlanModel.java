package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.gemini.app.ocs.model.DataProcRequirement;
import edu.gemini.app.ocs.model.SciencePlan;
import edu.gemini.app.ocs.model.StarSystem;
import jakarta.persistence.*;
import org.h2.table.Plan;

@Entity
public class SciencePlanModel extends SciencePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private int PlanNum;


    @Column(nullable = false)
    private String submitter;

    @Column(nullable = false)
    private String scienceplanName;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = true)
    private int PlanNum;

    @Transient
    private double fundinglnUSD;

    @Transient
    private String objectives;

    @Transient
    private String starSystem;

    @Transient
    private String startDate;

    @Transient
    private String endDate;

    @Transient
    private String telescopeLocation;


    @Transient
    private String fileType;

    @Transient
    private String fileQuality;

    @Transient
    private String colorType;

    @Transient
    private double contrast;

    @Transient
    private double brightness;

    @Transient
    private double saturation;

    @Transient
    private double highlights;

    @Transient
    private double exposure;

    @Transient
    private double shadows;

    @Transient
    private double whites;

    @Transient
    private double blacks;

    @Transient
    private double luminance;

    @Transient
    private double hue;

    @ManyToMany
    @JoinTable(
            name = "astronomer_science_plan",
            joinColumns = @JoinColumn(name = "science_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "astronomer_id"))
    private List<Astronomer> astronomers;
    // No-argument constructor
    public SciencePlanModel() {
    }

    // Constructor with all attributes
    public SciencePlanModel(String scienceplanName, String creator, String submitter, double fundinglnUSD, String objectives, String starSystem, String startDate, String endDate, String telescopeLocation, String fileType, String fileQuality, String colorType, double contrast, double brightness, double saturation, double highlights, double exposure, double shadows, double whites, double blacks, double luminance, double hue, List<Astronomer> astronomerList) {
        this.scienceplanName = scienceplanName;
        this.creator = creator;
        this.submitter = submitter;
        this.fundinglnUSD = fundinglnUSD;
        this.objectives = objectives;
        this.starSystem = starSystem;
        this.startDate = startDate;
        this.endDate = endDate;
        this.telescopeLocation = telescopeLocation;
        this.fileType = fileType;
        this.fileQuality = fileQuality;
        this.colorType = colorType;
        this.contrast = contrast;
        this.brightness = brightness;
        this.saturation = saturation;
        this.highlights = highlights;
        this.exposure = exposure;
        this.shadows = shadows;
        this.whites = whites;
        this.blacks = blacks;
        this.luminance = luminance;
        this.hue = hue;
    }

//    public String getFileQuality() {
//        return fileQuality;
//    }
//
//    public void setFileQuality(String fileQuality) {
//        this.fileQuality = fileQuality != null ? fileQuality : "default"; // Set a default value if fileQuality is null
//    }


    public int getPlanNum() {
        return PlanNum;
    }

    public void setPlanNum(int planNum) {
        this.PlanNum = planNum;
    }

    public double getFundingInUSD() {
        return fundinglnUSD;
    }

    public void setFundingInUSD(double fundinglnUSD) {
        this.fundinglnUSD = fundinglnUSD;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

//    public StarSystem.CONSTELLATIONS getStarSystem() {
//        return StarSystem.CONSTELLATIONS.valueOf(starSystem);
//    }
//
//    public void setStarSystem(String starSystem) {
//        this.starSystem = starSystem;
//    }

//    public StarSystem.CONSTELLATIONS getStarSystem() {
//        try {
//            return StarSystem.CONSTELLATIONS.valueOf(starSystem);
//        } catch (IllegalArgumentException e) {
//            // handle invalid value
//            System.err.println("Invalid star system: " + starSystem);
//            return null; // or return a default value
//        }
//    }

    public StarSystem.CONSTELLATIONS getStarSystem() {
        if (starSystem == null) {
            return null; // หรือกำหนดค่าเริ่มต้นที่ต้องการ
        }
        try {
            return StarSystem.CONSTELLATIONS.valueOf(starSystem);
        } catch (IllegalArgumentException e) {
            // ดำเนินการจัดการข้อผิดพลาดที่เหมาะสม
            System.err.println("Invalid star system: " + starSystem);
            return null; // หรือกำหนดค่าเริ่มต้นที่ต้องการ
        }
    }

//    public StarSystem.CONSTELLATIONS getStarSystem() {
//        if (starSystem == null) {
//            System.err.println("Star system is null");
//            return null;
//        }
//        try {
//            return StarSystem.CONSTELLATIONS.valueOf(starSystem);
//        } catch (IllegalArgumentException e) {
//            // handle invalid value
//            System.err.println("Invalid star system: " + starSystem);
//            return null; // or return a default value
//        }
//    }

//    public void setStarSystem(String starSystem) {
//        this.starSystem = starSystem;
//    }

    public void setStarSystem(String starSystem) {
        if (starSystem == null) {
            throw new IllegalArgumentException("Star system cannot be null");
        }
        try {
            StarSystem.CONSTELLATIONS.valueOf(starSystem);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid star system: " + starSystem);
        }
        this.starSystem = starSystem;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

//    public TELESCOPELOC getTelescopeLocation() {
//        return TELESCOPELOC.valueOf(telescopeLocation);
//    }
//
//    public void setTelescopeLocation(String telescopeLocation) {
//        this.telescopeLocation = telescopeLocation;
//    }

//    public TELESCOPELOC getTelescopeLocation() {
//        try {
//            return TELESCOPELOC.valueOf(telescopeLocation);
//        } catch (IllegalArgumentException e) {
//            // handle invalid value
//            System.err.println("Invalid telescope location: " + telescopeLocation);
//            return null; // or return a default value
//        }
//    }

    public TELESCOPELOC getTelescopeLocation() {
        if (telescopeLocation == null) {
            System.err.println("Telescope location is null");
            return null;
        }
        try {
            return TELESCOPELOC.valueOf(telescopeLocation);
        } catch (IllegalArgumentException e) {
            // handle invalid value
            System.err.println("Invalid telescope location: " + telescopeLocation);
            return null; // or return a default value
        }
    }

    public void setTelescopeLocation(String telescopeLocation) {
        this.telescopeLocation = telescopeLocation;
    }
    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public void setScienceplanName(String scienceplanName) {
        this.scienceplanName = scienceplanName;
    }

    public String getScienceplanName() {
        return scienceplanName;
    }

    public void setFileQuality(String fileQuality) {
        this.fileQuality = fileQuality;
    }

    public String getFileQuality() {
        return fileQuality;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setContrast(double contrast) {
        this.contrast = contrast;
    }

    public double getContrast() {
        return contrast;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getBrightness() {
        return brightness;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setHighlights(double highlights) {
        this.highlights = highlights;
    }

    public double getHighlights() {
        return highlights;
    }

    public void setExposure(double exposure) {
        this.exposure = exposure;
    }

    public double getExposure() {
        return exposure;
    }

    public void setShadows(double shadows) {
        this.shadows = shadows;
    }

    public double getShadows() {
        return shadows;
    }

    public void setWhites(double whites) {
        this.whites = whites;
    }

    public double getWhites() {
        return whites;
    }

    public void setBlacks(double blacks) {
        this.blacks = blacks;
    }

    public double getBlacks() {
        return blacks;
    }

    public void setLuminance(double luminance) {
        this.luminance = luminance;
    }

    public double getLuminance() {
        return luminance;
    }

    public void setHue(double hue) {
        this.hue = hue;
    }

    public double getHue() {
        return hue;
    }


    public void setAstronomers(List<Astronomer> astronomers) {
        this.astronomers = astronomers;
    }

    public List<Astronomer> getAstronomers() {
        return astronomers;
    }

}
