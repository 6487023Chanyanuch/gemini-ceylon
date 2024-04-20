package com.example.demo.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.List;

@Entity
public class Astronomer extends User {

    private String astronomerID;

    @ManyToMany(mappedBy = "astronomers")
    private List<SciencePlanModel> sciencePlans;

    public Astronomer() {}

    public Astronomer(Long id, String firstname, String lastname, String email, String password, String phone, String astronomerID) {
        super(id, firstname, lastname, email, password, phone);
        this.astronomerID = astronomerID;
    }

    public String getAstronomerID() {return astronomerID;}
    public void setAstronomerID(String astronomerID) {
        this.astronomerID = astronomerID;
    }

    @Override
    public String toString() {
        return "Astronomer{" +
                super.toString() +
                ", astronomerID=" +
                astronomerID + "}";
    }

    public List<SciencePlanModel> getSciencePlans() {
        return sciencePlans;
    }

    public void setSciencePlans(List<SciencePlanModel> sciencePlans) {
        this.sciencePlans = sciencePlans;
    }
}
