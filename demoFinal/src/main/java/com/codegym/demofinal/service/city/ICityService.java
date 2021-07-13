package com.codegym.demofinal.service.city;

import com.codegym.demofinal.model.City;
import com.codegym.demofinal.service.IGeneralService;

import java.util.Optional;

public interface ICityService extends IGeneralService<City> {
    Iterable<City> findAllByNameContaining(String name);
}
