package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by region.
 */
public class PopulationRegionReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationRegionReport.class.getName());

    /**
     * Retrieves total population grouped by region, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing region and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_Region_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by region.");
            return countries;
        }

        // SQL query for total population by region
        String sql = """
                SELECT Region, SUM(Population) AS TotalPopulation
                FROM country
                GROUP BY Region
                ORDER BY TotalPopulation DESC;
                """;

        // 2. Use try-with-resources for automatic Statement and ResultSet closing
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setRegion(rs.getString("Region"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

        } catch (SQLException e) {
            // 3. Log SQL errors instead of printing stack traces
            LOGGER.log(Level.SEVERE, "Error retrieving population report by region.", e);
            return countries;
        }

        // 4. Warn if no data found
        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by region. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Region Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Region_Report(List<Country> countries) {
        System.out.println("\n==================== Population by Region Report ====================");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-30s %-20s%n", "Region", "Total Population");
        System.out.println("--------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-30s %,20d%n",
                    country.getRegion(),
                    country.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.println("====================================================================\n");
    }
}
