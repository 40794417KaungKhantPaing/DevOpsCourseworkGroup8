package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by district.
 */
public class PopulationDistrictReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationDistrictReport.class.getName());

    /**
     * Retrieves total population grouped by district, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing district and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_District_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by district.");
            return countries;
        }

        // SQL query to calculate population per district (from city table)
        String sql = """
                SELECT District, SUM(Population) AS TotalPopulation
                FROM city
                GROUP BY District
                ORDER BY TotalPopulation DESC;
                """;

        // 2. Use try-with-resources for automatic Statement and ResultSet closing
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                // Using 'countryName' field to store district name (to match existing Country class)
                country.setCountryName(rs.getString("District"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

        } catch (SQLException e) {
            // 3. Log SQL errors instead of printing stack traces
            LOGGER.log(Level.SEVERE, "Error retrieving population report by district.", e);
            return countries;
        }

        // 4. Warn if no data found
        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by district. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by District Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_District_Report(List<Country> countries) {
        System.out.println("\n==================== ReportID 30. Population by District Report ====================");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-30s %-20s%n", "District", "Total Population");
        System.out.println("---------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-30s %,20d%n",
                    country.getCountryName(),  // Reusing countryName to represent district name
                    country.getPopulation());
        }

        System.out.println("---------------------------------------------------------------------");
        System.out.println("=====================================================================\n");
    }
}
