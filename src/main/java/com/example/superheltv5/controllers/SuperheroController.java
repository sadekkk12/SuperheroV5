package com.example.superheltv5.controllers;

import com.example.superheltv5.dto.SuperheroesCreationDto;
import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;
import com.example.superheltv5.services.SuperheroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("superhero")
public class SuperheroController {

  private SuperheroService service;

  public SuperheroController(SuperheroService service) {
    this.service = service;
  }

  @RequestMapping("/all")
  public String getAll(Model model) throws SuperheroException {
    List<Superhero> heroList = service.getAll();
    model.addAttribute("result", heroList);
    return "list";
  }

  @GetMapping("/create")
  public String showForm(Model model) throws SuperheroException {
    Superhero hero = new Superhero();
    model.addAttribute("hero", hero);
    List<String> cities = service.getCities();
    List<String> powers = service.getPowers();
    model.addAttribute("cities", cities);
    model.addAttribute("powers", powers);
    return "createHeroForm";
  }

  @PostMapping("/save")
  public String heroSave(@ModelAttribute ("hero") Superhero hero, Model model) throws SuperheroException {
    //save populated Superhero object in database
    service.save(hero);
    //find hero again in database and show updated list with all heroes
    List<Superhero> heroList = service.getAll();
    model.addAttribute("result", heroList);
    return "list";
  }

  @GetMapping("/create2")
  public String showForm2(Model model) {
    //Virker ikke pga. mgl.by og super powers som forventes i repo
    SuperheroesCreationDto heroesForm = new SuperheroesCreationDto();
    for (int i = 1; i <= 3; i++) {
      heroesForm.addHero(new Superhero());
    }
    model.addAttribute("form", heroesForm);
    return "createHeroForm2";
  }

  @PostMapping("/save2")
  public String saveHeroes(@ModelAttribute SuperheroesCreationDto form, Model model) throws SuperheroException {
    service.saveAll(form.getSuperheroes());
    model.addAttribute("result", service.getAll());
    return "redirect:/superhero/all";
  }

  @ExceptionHandler(SuperheroException.class)
  public String handleError(Model model, Exception exception) {
    model.addAttribute("message",exception.getMessage());
    return "errorPage";
  }
}
