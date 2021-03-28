package com.example.car.number.generator.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "car_number")
public class CarNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    private String series;
    @Column
    private Integer number;
    @Column
    private Integer region;
    @Column
    private String country;
    @Column
    private String typeGetting;
}
