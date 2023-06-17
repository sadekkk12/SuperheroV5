package com.example.superheltv5.models;

public class SuperPower {
  private int ID;
  private String name;

  public SuperPower() {
  }

  public SuperPower(int ID, String name) {
    this.ID = ID;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

}
