package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving the Country Report for a specific continent.
 */
public class CountriesContinentReport {

    /**
     * Retrieves a list of all countries within a specified continent,
     * ordered by population (largest to smallest).
     * Columns: Name, Continent, Region, Population
     *
     * @param conn      Active database connection
     * @param continent Continent name (e.g., "Asia", "Europe")
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public List<Country> getCountries_Continent_Report(Connection conn, String continent) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection or invalid parameter
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate country report.");
            return countries;
        }
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Invalid continent name provided.");
            return countries;
        }

        // 2. Use PreparedStatement for safer parameter handling
        String sql = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Continent = ?
                ORDER BY Population DESC;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, continent.trim());

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    Country country = new Country();
                    country.setCountryName(rs.getString("CountryName"));
                    country.setContinent(rs.getString("Continent"));
                    country.setRegion(rs.getString("Region"));
                    country.setPopulation(rs.getInt("Population"));
                    countries.add(country);
                }
            }

        } catch (SQLException e) {
            // 3. Handle SQL exceptions and print error details
            System.err.println("Error retrieving continent country report:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries; // Return empty list upon failure
        }

        // 4. Warn if no results found
        if (countries.isEmpty()) {
            System.out.println("Warning: No country data found for continent '" + continent + "'.");
        }

        return countries;
    }

    /**
     * Prints the Country Report for a specific continent to the console.
     *
     * @param continent Continent name
     * @param countries List of Country objects
     */
    public void printCountries_Continent_Report(String continent, List<Country> countries) {
        System.out.println("2. All countries in a continent by population Report");
        System.out.println("============================================================================================" +
                "======================");
        System.out.printf("Countries in %s (ordered by population):%n", continent);
        System.out.println("============================================================================================" +
                "======================");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Country Name", "Continent", "Region", "Population");
        System.out.println("--------------------------------------------------------------------------------------------" +
                "----------------------");

        for (Country country : countries) {
            System.out.printf("%-35s %-35s %-20s %-15d%n",
                    country.getCountryName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation());
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }
}
