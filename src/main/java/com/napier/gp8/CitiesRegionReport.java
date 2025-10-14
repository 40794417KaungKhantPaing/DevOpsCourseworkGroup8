package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving City Report data.
 */
public class CitiesRegionReport {
    /**
     * Retrieves a list of all cities in a given region ordered by largest population to smallest.
     * Columns: Name, Country, District, Population
     *
     * @param conn   Active database connection
     * @param region The region name to filter by
     * @return List of City objects, or an empty list if an error occurs or no data is found.
     */
    public List<City> getCitiesRegionReport(Connection conn, String region) {

        List<City> cities = new ArrayList<>();

        // 1. Check for null connection and invalid region
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate city report.");
            return cities;
        }
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Region name cannot be null or empty.");
            return cities;
        }

        // 2. Use try-with-resources for automatic Statement and ResultSet closing
        String sql = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Region = ?
                ORDER BY city.Population DESC;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, region);

            try (ResultSet rs = pstmt.executeQuery()) {
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
            System.err.println("Error retrieving city report for region: " + region);
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return cities;
        }

        if (cities.isEmpty()) {
            System.out.println("Warning: No city data found for region '" + region + "'. Report will be empty.");
        }

        return cities;
    }

    /**
     * Prints the City Report to the console.
     *
     * @param cities List of City objects
     * @param region The region name
     */
    public void printCitiesRegionReport(List<City> cities, String region) {
        System.out.println("--------------------------------------------------------------------------------------------" +
                "------------------");
        System.out.println("Cities in Region: " + region + " (Ordered by Population Descending)");
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
