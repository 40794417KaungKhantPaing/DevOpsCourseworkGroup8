package com.napier.gp8;

import java.sql.*;
import java.util.*;

/**
 * Generates city population reports for all continents,
 * showing each continent’s cities ordered by population descending.
 */
public class Cities_Continent_Report {

    /**
     * Retrieves a map of all continents and their cities,
     * each ordered by population descending.
     *
     * @param conn Active database connection
     * @return Map where key = Continent name, value = List of City objects
     */
    public Map<String, List<City>> getCitiesContinentsReport(Connection conn) {
        Map<String, List<City>> continentCitiesMap = new LinkedHashMap<>();

        // Validate connection
        if (conn == null) {
            System.err.println("Database not connected. Cannot retrieve report.");
            return continentCitiesMap;
        }

        // Query to get all distinct continents
        List<String> continents = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT Continent FROM country ORDER BY Continent ASC;")) {

            while (rs.next()) {
                continents.add(rs.getString("Continent"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving continent list.");
            e.printStackTrace();
            return continentCitiesMap;
        }

        // Query for cities by continent
        String sql = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC;
                """;

        for (String continent : continents) {
            List<City> cities = new ArrayList<>();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, continent);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        City city = new City();
                        Country country = new Country();
                        city.setCityName(rs.getString("CityName"));
                        country.setCountryName(rs.getString("CountryName"));
                        city.setCountry(country);
                        city.setDistrict(rs.getString("District"));
                        city.setPopulation(rs.getInt("Population"));
                        cities.add(city);
                    }
                }

            } catch (SQLException e) {
                System.err.println("Error retrieving data for continent: " + continent);
                e.printStackTrace();
            }

            continentCitiesMap.put(continent, cities);
        }

        return continentCitiesMap;
    }

    /**
     * Prints the city report for all continents.
     *
     * @param continentCitiesMap Map where key = Continent name, value = List of City objects
     */
    public void printCitiesContinentsReport(Map<String, List<City>> continentCitiesMap) {

        if (continentCitiesMap == null || continentCitiesMap.isEmpty()) {
            System.out.println("No data available to display.");
            return;
        }

        for (Map.Entry<String, List<City>> entry : continentCitiesMap.entrySet()) {
            String continent = entry.getKey();
            List<City> cities = entry.getValue();

            System.out.println();
            System.out.println("========================================================================================" +
                    "====================");
            System.out.printf("Cities in %-20s %n", continent);
            System.out.println("========================================================================================" +
                    "====================");
            System.out.printf("%-35s %-35s %-20s %-15s%n", "City", "Country", "District", "Population");
            System.out.println("----------------------------------------------------------------------------------------" +
                    "--------------------");

            if (cities.isEmpty()) {
                System.out.println("No city data found for this continent.");
            } else {
                for (City city : cities) {
                    System.out.printf("%-35s %-35s %-20s %-15d%n",
                            city.getCityName(),
                            city.getCountry().getCountryName(),
                            city.getDistrict(),
                            city.getPopulation());
                }
            }

            System.out.println("----------------------------------------------------------------------------------------" +
                    "--------------------");
        }
    }
}
