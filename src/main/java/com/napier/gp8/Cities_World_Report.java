package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving City Report data.
 */
public class Cities_World_Report {

    /**
     * Retrieves a list of all cities in the world ordered by largest population to smallest.
     * Columns: Name, Country, District, Population
     *
     * @param conn Active database connection
     * @return List of City objects
     */
    public List<City> getCities_World_Report(Connection conn) {
        List<City> cities = new ArrayList<>();

        if (conn == null) {
            System.out.println("Database not connected.");
            return cities;
        }

        try {
            String sql = """
                    SELECT city.Name AS CityName, country.Name AS CountryName,
                           city.District, city.Population
                    FROM city
                    JOIN country ON city.CountryCode = country.Code
                    ORDER BY city.Population DESC;
                    """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                City city = new City();
                Country country = new Country();
                city.setCityName(rs.getString("CityName"));
                // Build Country object and link it
                country.setCountryName(rs.getString("CountryName"));
                city.setCountry(country);
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);

            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error retrieving city report: " + e.getMessage());
        }

        return cities;
    }

    /**
     * Prints the City Report to the console.
     *
     * @param cities List of City objects
     */
    protected void printCities_World_Report(List<City> cities) {
        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Name", "Country", "District", "Population");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (City city : cities) {
            System.out.printf("%-35s %-35s %-20s %-15d%n",
                    city.getCityName(),
                    city.getCountry().getCountryName(),  // <-- get the name
                    city.getDistrict(),
                    city.getPopulation());

        }

        System.out.println("------------------------------------------------------------------------------------");
    }
}
