package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving the Country Report for the world.
 */
public class Countries_World {

    /**
     * Retrieves a list of all countries in the world ordered by population (largest to smallest).
     * Columns: Name, Continent, Region, Population
     *
     * @param conn Active database connection
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public List<Country> getCountries_World_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        // 1. Check for null connection and return an empty list upon failure.
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate country report.");
            return countries;
        }

        // 2. Use try-with-resources for automatic Statement closing
        try (Statement stmt = conn.createStatement()) {

            String sql = """
                    SELECT Name AS CountryName, Continent, Region, Population
                    FROM country
                    ORDER BY Population DESC;
                    """;

            // 3. Execute query and handle results safely
            try (ResultSet rs = stmt.executeQuery(sql)) {

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
            // 4. Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving country report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries; // Return empty list upon failure
        }

        // 5. Warn if no data was retrieved
        if (countries.isEmpty()) {
            System.out.println("Warning: No country data was found in the database. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Country Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printCountries_World_Report(List<Country> countries) {
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Country Name", "Continent", "Region", "Population");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-35s %-35s %-20s %-15d%n",
                    country.getCountryName(),
                    country.getContinent(),
                    country.getRegion(),
                    country.getPopulation());
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}
