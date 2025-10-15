package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by continent.
 */
public class PopulationContinentReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationContinentReport.class.getName());

    /**
     * Retrieves total population grouped by continent, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing continent and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_Continent_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection and return an empty list upon failure.
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by continent.");
            return countries;
        }

        // SQL query for population grouped by continent
        String sql = """
                SELECT continent, SUM(population) AS TotalPopulation
                FROM country
                GROUP BY continent
                ORDER BY TotalPopulation DESC;
                """;

        // 2. Use try-with-resources for automatic Statement and ResultSet closing
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setContinent(rs.getString("Continent"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

        } catch (SQLException e) {
            // Log the error properly instead of printing stack trace
            LOGGER.log(Level.SEVERE, "Error retrieving population report by continent.", e);
            return countries;
        }

        // 3. Check for Missing Data
        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by continent. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Continent Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Continent_Report(List<Country> countries) {
        System.out.println("\n==================== Population by Continent Report ====================");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-20s%n", "Continent", "Total Population");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-20s %,20d%n",
                    country.getContinent(),
                    country.getPopulation());
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("=======================================================================\n");
    }
}
