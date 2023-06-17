package com.example.superheltv5.models;

import java.util.ArrayList;
import java.util.List;

  public class SuperPowerDTO {
    private String name;
    private String selectedValue;
    private List<String> superPowers;

    public SuperPowerDTO(String name, List<String> superPowers) {
      this.name = name;
      this.superPowers = superPowers;
    }
    public SuperPowerDTO() {
      this.name = "";
      this.superPowers = new ArrayList<String>();
    }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSelectedValue() {
    return selectedValue;
  }

  public void setSelectedValue(String selectedValue) {
    this.selectedValue = selectedValue;
  }

  public List<String> getSuperPowers() {
    return superPowers;
  }

  public void setSuperPowers(List<String> superPowers) {
    this.superPowers = superPowers;
  }

  public void addSuperPower(String superpower) {
    superPowers.add(superpower);
  }
}
