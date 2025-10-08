package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles generating and retrieving the Country Report for a specific region.
 */
public class Countries_Region {

    /**
     * Retrieves a list of all countries within a specified region,
     * ordered by population (largest to smallest).
     * Columns: Name, Continent, Region, Population
     *
     * @param conn   Active database connection
     * @param region Region name (e.g., "Southern and Central Asia", "Western Europe")
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public List<Country> getCountries_Region_Report(Connection conn, String region) {

        List<Country> countries = new ArrayList<>();

        // 1. Validate connection and parameter
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate region report.");
            return countries;
        }
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Invalid region name provided.");
            return countries;
        }

        // 2. Prepare SQL statement
        String sql = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Region = ?
                ORDER BY Population DESC;
                """;

        // 3. Execute query using PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, region.trim());

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
            // 4. Handle SQL exceptions
            System.err.println("Error retrieving region country report:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 5. Warn if empty result
        if (countries.isEmpty()) {
            System.out.println("Warning: No country data found for region '" + region + "'.");
        }

        return countries;
    }

    /**
     * Prints the Country Report for a specific region to the console.
     *
     * @param region    Region name
     * @param countries List of Country objects
     */
    public void printCountries_Region_Report(String region, List<Country> countries) {
        System.out.println("3. All countries in a region by population Report");
        System.out.println("==================================================================================================================");
        System.out.printf("Countries in region: %s (ordered by population)%n", region);
        System.out.println("==================================================================================================================");
        System.out.printf("%-35s %-35s %-20s %-15s%n", "Country Name", "Continent", "Region", "Population");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

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