package com.example.demo.service;

import com.example.demo.model.Astronomer;
import com.example.demo.model.ScienceObserver;
import com.example.demo.model.User;
import com.example.demo.repository.AstronomerRepository;
import com.example.demo.repository.ScienceObserverRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AstronomerRepository astronomerRepository;
    @Autowired
    private ScienceObserverRepository scienceObserverRepository;

    @PostConstruct
    public void init() {
        // เพิ่มข้อมูลลงในฐานข้อมูล
        scienceObserverRepository.save(new ScienceObserver(11L, "pim", "haha", "pim.hah@example.com", "pimCeylon", "+1234567890","6487085"));
        astronomerRepository.save(new Astronomer(12L, "Tong", "Niti", "tong.nit@example.com", "tongCeylon", "+1234567890","6487002"));
        scienceObserverRepository.save(new ScienceObserver(13L, "nai", "nueng", "nai.nue@example.com", "naiCeylon", "+1234567890","6487023"));
        scienceObserverRepository.save(new ScienceObserver(14L, "my", "sang", "my.san@example.com", "myCeylon", "+1234567890","6487077"));
        astronomerRepository.save(new Astronomer(15L, "baiyok", "tula", "bai.tul@example.com", "baiCeylon", "+1234567890","6487058"));
        userRepository.save(new User(16L, "fern", "whaa", "fern.wha@example.com", "fernCeylon", "+1234567890"));
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }
}
