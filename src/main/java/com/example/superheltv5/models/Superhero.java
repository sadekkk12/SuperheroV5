package com.example.superheltv5.models;

import java.util.ArrayList;
import java.util.List;

public class Superhero {
  private int heroId;
  private String heroName;
  private String realName;
  private int creationYear;
  private City city;
  private List<String> superPowers;
  //private SuperPowerDTO superpowerDTO; //TODO prøv med power objekter

  public Superhero() {
    this.city = null;
    superPowers = new ArrayList<>();
  }

  public Superhero(int heroId, String heroName, String realName, int creationYear, List<String> superPowers) {
    this.heroId = heroId;
    this.heroName = heroName;
    this.realName = realName;
    this.creationYear = creationYear;
    this.superPowers = superPowers;
  }

  public int getHeroId() {
    return heroId;
  }

  public String getHeroName() {
    return heroName;
  }

  public String getRealName() {
    return realName;
  }

  public int getCreationYear() {
    return creationYear;
  }

  public void setHeroId(int heroId) {
    this.heroId = heroId;
  }

  public void setHeroName(String heroName) {
    this.heroName = heroName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public void setCreationYear(int creationYear) {
    this.creationYear = creationYear;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public City getCity() {
    return city;
  }

  public List<String> getSuperPowers() {
    return superPowers;
  }

  public void setSuperPowers(List<String> superPowers) {
    this.superPowers = superPowers;
  }
}

