package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving City Report data.
 */
public class CitiesWorldReport {
    /**
     * Retrieves a list of all cities in the world ordered by largest population to smallest.
     * Columns: Name, Country, District, Population
     *
     * @param conn Active database connection
     * @return List of City objects, or an empty list if an error occurs or no data is found.
     */
    public List<City> getCitiesWorldReport(Connection conn) {

        List<City> cities = new ArrayList<>();

        // 1. Check for null connection and return an empty list upon failure.
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate city report.");
            return cities;
        }

        // 2. Use try-with-resources for automatic Statement closing
        try (Statement stmt = conn.createStatement()) {

            String sql = """
                    SELECT city.Name AS CityName, country.Name AS CountryName,
                           city.District, city.Population
                    FROM city
                    JOIN country ON city.CountryCode = country.Code
                    ORDER BY city.Population DESC;
                    """;

            // 3. Use try-with-resources for automatic ResultSet closing
            try (ResultSet rs = stmt.executeQuery(sql)) {

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
            }

        } catch (SQLException e) {
            // 4. Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving city report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return cities; // Return empty list upon failure
        }

        // 5. Check for Missing Data
        if (cities.isEmpty()) {
            System.out.println("Warning: No city data was found in the database. Report will be empty.");
        }

        return cities;
    }

    /**
     * Prints the City Report to the console.
     *
     * @param cities List of City objects
     */
    public void printCitiesWorldReport(List<City> cities) {
        System.out.println("--------------------------------------------------------------------------------------------" +
                "------------------");
        System.out.println("Cities in World (Ordered by Population Descending)");
        System.out.println("--------------------------------------------------------------------------------------------" +
                "------------------");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Name", "Country", "District", "Population");
        System.out.println("--------------------------------------------------------------------------------------------" +
                "------------------");

        for (City city : cities) {
            System.out.printf("%-35s %-35s %-20s %-15d%n",
                    city.getCityName(),
                    city.getCountry().getCountryName(),
                    city.getDistrict(),
                    city.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------------------------------" +
                "------------------");
    }


}
