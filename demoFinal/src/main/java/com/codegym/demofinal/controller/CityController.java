package com.codegym.demofinal.controller;

import com.codegym.demofinal.model.City;
import com.codegym.demofinal.model.Country;
import com.codegym.demofinal.service.city.ICityService;
import com.codegym.demofinal.service.country.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private ICountryService countryService;

    @Autowired
    private ICityService cityService;

    @ModelAttribute("countries")
    public Iterable<Country> categories(){
        return countryService.findAll();
    }

    @GetMapping("/list")
    public ModelAndView showAll(){
        Iterable<City> cities = cityService.findAll();
        ModelAndView mav = new ModelAndView("list");
        mav.addObject("cities", cities);
        return mav;
    }


    @GetMapping("/view/{id}")
    public ModelAndView showEach(@PathVariable("id") Long id){
        Optional<City> cityOptional = cityService.findById(id);
        if (cityOptional.isPresent()){
            ModelAndView mav = new ModelAndView("view", "city", cityOptional.get());
            return mav;
        } else {
            return new ModelAndView("error404");
        }
    }


    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView mav = new ModelAndView("create", "city", new City());
        return mav;
    }

    @PostMapping("/create")
    public ModelAndView addNewCity(@ModelAttribute City city, BindingResult result){
        city.validate(city, result);
        if (result.hasFieldErrors()){
            return new ModelAndView("create", "city", city);
        }
        cityService.save(city);
        ModelAndView mav = new ModelAndView("create", "city", new City());
        mav.addObject("message", city.getName()+" was added");
        return mav;
    }


    @GetMapping("edit/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id){
        Optional<City> cityOptional = cityService.findById(id);
        if (cityOptional.isPresent()){
            ModelAndView mav = new ModelAndView("edit", "city", cityOptional.get());
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("error404");
            return mav;
        }
    }

    @PostMapping("/edit")
    public ModelAndView editCity(@ModelAttribute City city, BindingResult result){
        city.validate(city, result);
        if (result.hasFieldErrors()){
            return new ModelAndView("edit", "city", city);
        }
        cityService.save(city);
        ModelAndView mav = new ModelAndView("edit", "city", city);
        mav.addObject("message", "Change in "+city.getName()+"'s detail was saved");
        return mav;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id){
        Optional<City> cityOptional = cityService.findById(id);
        if (cityOptional.isPresent()){
            ModelAndView mav = new ModelAndView("delete", "city", cityOptional.get());
            return mav;
        } else {
            return new ModelAndView("error404");
        }
    }

    @PostMapping("/delete")
    public String deleteCity(@ModelAttribute City city, RedirectAttributes redirectAttributes){
        Optional<City> cityOptional = cityService.findById(city.getId());
        if (cityOptional.isPresent()){
            cityService.remove(city.getId());
            redirectAttributes.addFlashAttribute("message", city.getName()+" was deleted");
            return "redirect:/cities/list";
        } else {
            return "error404";
        }
    }
}
