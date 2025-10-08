package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving population reports by country.
 */
public class Population_Country_Report {

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
            System.err.println("Database not connected. Cannot generate population report by country.");
            return countries;
        }

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
            // 3. Handle SQL errors gracefully
            System.err.println("Error retrieving population report by country:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 4. Check if data was retrieved
        if (countries.isEmpty()) {
            System.out.println("Warning: No country population data found. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Country Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Country_Report(List<Country> countries) {
        System.out.println("\n==================== Population by Country Report ====================");
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
