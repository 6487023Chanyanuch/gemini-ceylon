package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class PositionPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int positionPairId;

    private double direction;
    private double degree;

    @ManyToOne
    @JoinColumn(name = "observing_program_id")
    @JsonIgnore  // Prevent recursive serialization
    private ObservingProgramModel observingProgramModel;

    public PositionPair() {
    }

    public PositionPair(double direction, double degree) {
        this.direction = direction;
        this.degree = degree;
    }

    public ObservingProgramModel getObservingProgramModel() {
        return observingProgramModel;
    }

    public void setObservingProgramModel(ObservingProgramModel observingProgramModel) {
        this.observingProgramModel = observingProgramModel;
    }

    public double getDirection() {
        return direction;
    }

    public double getDegree() {
        return degree;
    }
}

