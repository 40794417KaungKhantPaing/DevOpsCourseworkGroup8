package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving population reports by region.
 */
public class Population_Region_Report {

    /**
     * Retrieves total population grouped by region, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing region and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_Region_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection and return an empty list upon failure.
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate population report by region.");
            return countries;
        }

        String sql = """
                SELECT Region, SUM(Population) AS TotalPopulation
                FROM country
                GROUP BY Region
                ORDER BY TotalPopulation DESC;
                """;

        // 2. Use try-with-resources for automatic Statement closing
        try (Statement stmt = conn.createStatement()) {

            // 3. Use try-with-resources for automatic ResultSet closing
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    Country country = new Country();
                    country.setRegion(rs.getString("Region"));
                    country.setPopulation(rs.getLong("TotalPopulation"));
                    countries.add(country);
                }
            }

        } catch (SQLException e) {
            // 4. Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving population report by region:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 5. Check for Missing Data
        if (countries.isEmpty()) {
            System.out.println("Warning: No population data found by region. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Region Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Region_Report(List<Country> countries) {
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-30s %-20s%n", "Region", "Total Population");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-30s %,20d%n",
                    country.getRegion(),
                    country.getPopulation());
        }

        System.out.println("------------------------------------------------------------");
    }
}
