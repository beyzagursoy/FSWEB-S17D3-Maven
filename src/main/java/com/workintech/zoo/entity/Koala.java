package com.workintech.zoo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Koala {
    private Integer id;
    private String name;
    private double sleepHour;
    private double weight;
    private String gender;
}
