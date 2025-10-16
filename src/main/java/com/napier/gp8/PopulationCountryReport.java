package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by country.
 */
public class PopulationCountryReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationCountryReport.class.getName());

    /**
     * Retrieves total population for all countries, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing country name and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_Country_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by country.");
            return countries;
        }

        // SQL query to retrieve all countries sorted by population
        String sql = """
                SELECT Name, Population
                FROM country
                ORDER BY Population DESC;
                """;

        // 2. Use try-with-resources for automatic closing
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setCountryName(rs.getString("Name"));
                country.setPopulation(rs.getLong("Population"));
                countries.add(country);
            }

        } catch (SQLException e) {
            // 3. Log SQL errors instead of printing stack traces
            LOGGER.log(Level.SEVERE, "Error retrieving population report by country.", e);
            return countries;
        }

        // 4. Log a warning if no data was retrieved
        if (countries.isEmpty()) {
            LOGGER.warning("No country population data found. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Country Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Country_Report(List<Country> countries) {
        System.out.println("\n==================== ReportID 29. Population by Country Report ====================");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-40s %-20s%n", "Country", "Population");
        System.out.println("---------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-40s %,20d%n",
                    country.getCountryName(),
                    country.getPopulation());
        }

        System.out.println("---------------------------------------------------------------------");
        System.out.println("=====================================================================\n");
    }
}
