package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving City Report data by continent.
 */
public class CitiesContinentReport {

    /**
     * Retrieves a list of all cities in a given continent ordered by largest population to smallest.
     * Columns: Name, Country, District, Population
     *
     * @param conn Active database connection
     * @param continent The continent to filter cities by
     * @return List of City objects, or an empty list if an error occurs or no data is found.
     */
    public List<City> getCities_By_Continent_Report(Connection conn, String continent) {

        List<City> cities = new ArrayList<>();

        // 1. Check for null connection or invalid input
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate city report.");
            return cities;
        }
        if (continent == null || continent.isEmpty()) {
            System.err.println("Invalid continent input. Please specify a valid continent.");
            return cities;
        }

        // 2. SQL query for cities in the given continent ordered by population descending
        String sql = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Continent = ?
                ORDER BY city.Population DESC;
                """;

        // 3. Execute query and populate list
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
            System.err.println("Error retrieving city report for continent: " + continent);
            e.printStackTrace();
        }

        if (cities.isEmpty()) {
            System.out.println("Warning: No city data found for continent '" + continent + "'.");
        }

        return cities;
    }

    /**
     * Prints the City Report for a continent to the console.
     *
     * @param cities List of City objects
     * @param continent The continent name
     */
    public void printCities_By_Continent_Report(List<City> cities, String continent) {
        System.out.println("----------------------------------------------------------------------------------------------" +
                "------------------");
        System.out.printf("Cities in a Continent: %s (Ordered by Population Descending)%n", continent);
        System.out.println("----------------------------------------------------------------------------------------------" +
                "----------------");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Name", "Country", "District", "Population");
        System.out.println("----------------------------------------------------------------------------------------------" +
                "----------------");

        for (City city : cities) {
            System.out.printf("%-35s %-35s %-20s %-15d%n",
                    city.getCityName(),
                    city.getCountry().getCountryName(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------------------------------" +
                "----------------");
    }
}