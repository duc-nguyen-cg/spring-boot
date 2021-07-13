package com.codegym.demofinal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private Country country;
    
    private double area;

    private int population;

    private double GDP;

    private String description;

    public City(String name, Country country, double area, int population, double GDP, String description) {
        this.name = name;
        this.country = country;
        this.area = area;
        this.population = population;
        this.GDP = GDP;
        this.description = description;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        City city = (City) target;
        Double area = city.getArea();
        Integer population = city.getPopulation();
        Double gdp = city.getGDP();
        if (area <= 0){
            errors.rejectValue("area", "area.min", "Area must be positive");
        }
        if (population <= 0){
            errors.rejectValue("population", "population.min", "Population must be positive");
        }
        if (gdp <= 0){
            errors.rejectValue("GDP", "gdp.min", "GDP must be positive");
        }
    }
}
