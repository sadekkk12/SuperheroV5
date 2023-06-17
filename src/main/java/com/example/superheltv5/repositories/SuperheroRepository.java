package com.example.superheltv5.repositories;

import com.example.superheltv5.models.City;
import com.example.superheltv5.models.SuperPower;
import com.example.superheltv5.models.Superhero;
import com.example.superheltv5.services.SuperheroException;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("real_database")
public class SuperheroRepository implements ISuperheroRepository {

  @Override
  public List<Superhero> getAll() throws SuperheroException {
    List<Superhero> superheroList = new ArrayList<>();

    try {
      // All information and work related to database connection is in ConnectionManager
      Connection conn = ConnectionManager.getConnection();
      String SQL = "SELECT * from superhero";
      Statement stmt = conn.createStatement();
      ResultSet resultSet = stmt.executeQuery(SQL);

      while (resultSet.next()) {
        // table columns retrieved by position
        int id = resultSet.getInt(1);
        String heroName = resultSet.getString(2);
        String realName = resultSet.getString(3);
        int creationYear = resultSet.getInt(4);
        superheroList.add(new Superhero(id, heroName, realName, creationYear, null));
      }
    } catch (SQLException e) {
      throw new SuperheroException("Something wrong with query");
    }
    return superheroList;
  }

  public void save(Superhero hero) throws SuperheroException {
    try {
      Connection conn = ConnectionManager.getConnection();
      // vi gemmer alt eller intet som "unit of work/business transaction"
      conn.setAutoCommit(false);

      //  Find ID for den valgte by
      // TODO - evt. have liste af byer liggende i memory
      City heroCity = hero.getCity();
      String SQLCityID = "SELECT CITY_ID FROM CITY WHERE NAME = ?";
      PreparedStatement pstmt = conn.prepareStatement(SQLCityID);
      pstmt.setString(1, hero.getCity().getCityName());
      ResultSet rs = pstmt.executeQuery(); //søg efter City
      if (rs.next()) {
        heroCity.setCityID(rs.getInt(1));
      }

      // Indsæt ny superhelt
      String SQL = "INSERT INTO superhero (HERO_NAME, REAL_NAME, CREATION_YEAR, CITY_ID) " +
          "VALUES (?, ?, ?, ?)";
      // Vi har brug for ny Super hero ID, så vi kan oprette super powers bagefter
      pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
      pstmt.setString(1, hero.getHeroName());
      pstmt.setString(2, hero.getRealName());
      pstmt.setInt(3, hero.getCreationYear());
      pstmt.setInt(4, hero.getCity().getCityID());

      pstmt.executeUpdate(); // insert superhero
      rs = pstmt.getGeneratedKeys(); // få fat i det nye ID
      if (rs.next()) {
        hero.setHeroId(rs.getInt(1));
      }

      //Indsæt super powers på den nye superhelt
      int heroID = hero.getHeroId();
      String SQL2 = "INSERT INTO superhero_powers (HERO_ID, POWER_ID) " +
          "VALUES (?, ?)";
      PreparedStatement pstmt2 = conn.prepareStatement(SQL2);
      pstmt2.setInt(1, heroID);
      //Find superpower ID i DB
      // TODO - evt. have liste af powers liggende i memory
      for (String powerName : hero.getSuperPowers()) {
        pstmt2.setInt(2, getPower(powerName).getID());
        pstmt2.executeUpdate(); // insert superhero power
      }
      conn.commit();
    } catch (SQLException e) {
      throw new SuperheroException(e.getMessage());
    }
  }

  @Override
  public void saveall(List<Superhero> superheroes) throws SuperheroException {
    for (Superhero hero : superheroes) {
      save(hero);
    }
  }

  @Override
  public List<String> getCities() throws SuperheroException {
    List<String> cities = new ArrayList<>();
    try {
      Connection con = ConnectionManager.getConnection();
      String query = "SELECT name FROM CITY";
      PreparedStatement ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        cities.add(rs.getString(1));
      }
    } catch (SQLException e) {
      throw new SuperheroException(e.getMessage());
    }
    return cities;
  }

  public List<String> getPowers() throws SuperheroException {
    List<String> powers = new ArrayList<>();
    try {
      Connection con = ConnectionManager.getConnection();
      String query = "SELECT name FROM SUPERPOWER";
      PreparedStatement ps = con.prepareStatement(query);
      ResultSet rs = ps.executeQuery();
      while (rs.next()) {
        powers.add(rs.getString(1));
      }
    } catch (SQLException e) {
      throw new SuperheroException(e.getMessage());
    }
    return powers;
  }

  private SuperPower getPower(String powerName) throws SuperheroException {
    try {
      Connection con = ConnectionManager.getConnection();
      String SQL = "SELECT * FROM SUPERPOWER WHERE NAME = ?";
      PreparedStatement ps = con.prepareStatement(SQL);
      ps.setString(1, powerName);
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new SuperPower(rs.getInt(1), rs.getString(2));
      }
    } catch (SQLException e) {
      throw new SuperheroException(e.getMessage());
    }
    return null;
  }


}
