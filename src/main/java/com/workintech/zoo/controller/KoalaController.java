package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init(){
        koalas = new HashMap<>();
    }

    @GetMapping("/koalas")
    public List<Koala> findAll(){
        return koalas.values().stream().toList();
    }

    @GetMapping("/koalas/{id}")
    public Koala findById(@PathVariable Integer id){
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping("/koalas")
    public Koala create(@RequestBody Koala koala){
        if (koala.getId() <= 0) {
            throw new ZooException("Id must be greater than 0!", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala with id " + koala.getId() + " already exists!", HttpStatus.BAD_REQUEST);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/koalas/{id}")
    public Koala update(@PathVariable Integer id, @RequestBody Koala koala){
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala to update with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        koalas.put(id, new Koala(id, koala.getName(), koala.getWeight(), koala.getSleepHour(), koala.getGender()));
        return koalas.get(id);
    }

    @DeleteMapping("/koalas/{id}")
    public Koala delete(@PathVariable Integer id){
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala to delete with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}
