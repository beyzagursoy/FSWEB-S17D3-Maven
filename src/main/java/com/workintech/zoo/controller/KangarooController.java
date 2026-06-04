package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.JstlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init(){
        kangaroos = new HashMap<>();
    }

    @GetMapping("/kangaroos")
    public List<Kangaroo> findAll(){
        return kangaroos.values().stream().toList();
    }

    @GetMapping("kangaroos/{id}")
    public Kangaroo findById(@PathVariable Integer id){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping("/kangaroos")
    public Kangaroo create(@RequestBody Kangaroo kangaroo){
        if (kangaroo.getId() <= 0) {
            throw new ZooException("Id must be greater than 0!", HttpStatus.BAD_REQUEST);
        }
        if (kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Kangaroo with id " + kangaroo.getId() + " already exists!", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/kangaroos/{id}")
    public Kangaroo update(@PathVariable Integer id, @RequestBody Kangaroo kangaroo){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo to update with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        kangaroos.put(id, new Kangaroo(id, kangaroo.getName(), kangaroo.getHeight(), kangaroo.getWeight(), kangaroo.getGender(), kangaroo.getIsAggressive()));
        return kangaroos.get(id);
    }

    @DeleteMapping("/kangaroos/{id}")
    public Kangaroo delete(@PathVariable Integer id){
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo to delete with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}
