package com.example.superheltv5.repositories;

import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("stub_database")
public class SuperheroRepositoryStub implements ISuperheroRepository {

  private List<Superhero> stubDatabase = new ArrayList<>();

  public SuperheroRepositoryStub() {
   Superhero hero1 = new Superhero(1, "Tarzan", "Lord Greystoke", 1912, null);
    stubDatabase.add(hero1);
    Superhero hero2 = new Superhero(2, "Spider-man", "Peter Parker", 1962, null);
    stubDatabase.add(hero2);
  }

  @Override
  public List<Superhero> getAll() {
    return stubDatabase;
  }

  @Override
  public void save(Superhero hero) throws SuperheroException {
    stubDatabase.add(hero);
  }

  @Override
  public void saveall(List<Superhero> superheroes) {
    for (Superhero hero: superheroes) {
      stubDatabase.add(hero);
    }
  }

  @Override
  public List<String> getCities() throws SuperheroException {
    return null;
  }

  @Override
  public List<String> getPowers() throws SuperheroException {
    return null;
  }
}
