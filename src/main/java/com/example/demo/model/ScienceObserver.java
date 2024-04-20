package com.example.demo.model;

import jakarta.persistence.*;
import java.util.List;


@Entity
public class ScienceObserver extends User{

    private String sciObserverID;

//    @OneToMany(mappedBy = "scienceObserver_observingProgram")
//    private List<ObservingProgramModel> observingPrograms;

    public ScienceObserver() {}

    public ScienceObserver(Long id, String firstname, String lastname, String email, String password, String phone, String sciObserverID) {
        super(id, firstname, lastname, email, password, phone);
        this.sciObserverID = sciObserverID;
    }

    public String getSciObserverID() {
        return sciObserverID;
    }
    public void setSciObserverID(String sciObserverID) {
        this.sciObserverID = sciObserverID;
    }

    @Override
    public String toString() {
        return "ScienceObserver{" +
                "sciObserverID=" + sciObserverID +
                '}';
    }
}


