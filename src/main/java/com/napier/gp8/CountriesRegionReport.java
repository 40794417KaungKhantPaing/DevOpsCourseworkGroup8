package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * Handles generating and retrieving the Country Report for a specific region.
 */
public class CountriesRegionReport extends CountriesReportBase {

    /**
     * Retrieves a list of all countries within a specified region,
     * ordered by population (largest to smallest).
     * Columns: Name, Continent, Region, Population
     *
     * @param conn   Active database connection
     * @param region Region name (e.g., "Southern and Central Asia", "Western Europe")
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public ArrayList<Country> getCountries_Region_Report(Connection conn, String region) {

        ArrayList<Country> countries = new ArrayList<>();

        // 1. Validate connection and region parameter
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate region report.");
            return countries;
        }
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Invalid region name provided.");
            return countries;
        }

        // 2. Prepare SQL query
        String query = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Region = ?
                ORDER BY Population DESC;
                """;

        // 3. Execute query using PreparedStatement
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, region.trim());

            try (ResultSet rs = pstmt.executeQuery()) {
                // Use base class method to build list
                countries = buildCountriesFromResultSet(rs);
            }

        } catch (SQLException e) {
            // 4. Handle SQL exceptions with detailed messages
            System.err.println("Error retrieving region country report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 5. Warn if no data found
        if (countries.isEmpty()) {
            System.out.println("Warning: No country data found for region '" + region + "'.");
        }

        return countries;
    }

    /**
     * Retrieves a list of the top N countries within a specified region,
     * ordered by population (largest to smallest).
     *
     * @param connection               Active database connection
     * @param regionName          Region name (e.g., "Southern and Central Asia")
     * @param numberOfCountries  Number of top countries to limit
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public ArrayList<Country> getTopNCountries_Region_Report(Connection connection, String regionName, int numberOfCountries) {

        ArrayList<Country> countries = new ArrayList<>();

        // 1. Validate connection and parameters
        if (connection == null) {
            System.err.println("Database not connected. Cannot generate top N countries in region report.");
            return countries;
        }
        if (regionName == null || regionName.trim().isEmpty()) {
            System.err.println("Invalid region name provided.");
            return countries;
        }

        // 2. Prepare SQL query with LIMIT
        String query = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Region = ?
                ORDER BY Population DESC
                LIMIT ?;
                """;

        // 3. Execute query safely
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, regionName.trim());
            pstmt.setInt(2, numberOfCountries);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Use base class method to build list
                countries = buildCountriesFromResultSet(rs);
            }

        } catch (SQLException e) {
            // 4. Handle SQL exceptions with detailed messages
            System.err.println("Error retrieving top countries region report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 5. Warn if empty
        if (countries.isEmpty()) {
            System.out.println("Warning: No top country data found for region '" + regionName + "'.");
        }

        return countries;
    }

    /**
     * Prints the Country Report for a specific region to the console.
     *
     * @param regionName    Region name
     * @param countries List of Country objects
     */
    public void printCountries_Region_Report(String regionName, ArrayList<Country> countries) {
        printCountries(countries, "ReportID 3. All Countries in Region '" + regionName + "' Report");
    }

    /**
     * Prints the Top N Country Report for a specific region to the console.
     *
     * @param regionName             Region name
     * @param countries          List of Country objects
     * @param topNCountries  Number of top countries displayed
     */
    public void printTopNCountries_Region_Report(String regionName, ArrayList<Country> countries, int topNCountries) {
        printCountries(countries, "ReportID 6. Top " + topNCountries + " Countries in the Region '" + regionName + "' Report");
    }
}
