package com.example.car.number.generator.repository;

import com.example.car.number.generator.entity.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarNumberRepository extends JpaRepository<CarNumber, Integer> {

    CarNumber findFirstByTypeGettingOrderBySeriesDescNumberDesc(String typeGetting);

    Boolean existsCarNumberBySeriesAndNumberAndRegionAndCountry(String series, int number, int region, String country);

}
