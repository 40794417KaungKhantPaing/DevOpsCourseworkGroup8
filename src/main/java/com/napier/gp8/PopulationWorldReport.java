package com.napier.gp8;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving the total population of the world.
 */
public class PopulationWorldReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationWorldReport.class.getName());

    /**
     * Retrieves the total world population from the database.
     *
     * @param conn Active database connection
     * @return total world population (long), or 0 if an error occurs or no data found
     */
    public long getPopulation_World_Report(Connection conn) {

        long totalPopulation = 0;

        // 1. Check for null connection
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot retrieve world population report.");
            return 0;
        }

        // SQL query to calculate total world population
        String sql = """
                SELECT SUM(Population) AS WorldPopulation
                FROM country;
                """;

        // 2. Execute query
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                totalPopulation = rs.getLong("WorldPopulation");
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving total world population.", e);
            return 0;
        }

        // 3. Log if no population data found
        if (totalPopulation == 0) {
            LOGGER.warning("No population data found in the database. World population report may be empty.");
        }

        return totalPopulation;
    }

    /**
     * Prints the total world population to the console in a formatted layout.
     *
     * @param totalPopulation Total population of the world
     */
    protected void printPopulation_World_Report(long totalPopulation) {
        System.out.println("\n==================== ReportID 26. Population of the World Report ====================");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-40s %,20d%n", "Total World Population", totalPopulation);
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("=======================================================================\n");
    }
}
