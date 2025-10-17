package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * Handles generating and retrieving the Country Report for a specific continent.
 */
public class CountriesContinentReport extends CountriesReportBase {

    /**
     * Retrieves a list of all countries within a specified continent,
     * ordered by population (largest to smallest).
     * Columns: Name, Continent, Region, Population
     *
     * @param conn      Active database connection
     * @param continent Continent name (e.g., "Asia", "Europe")
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public ArrayList<Country> getCountries_Continent_Report(Connection conn, String continent) {

        ArrayList<Country> countries = new ArrayList<>();

        // 1. Validate connection and parameter
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate continent report.");
            return countries;
        }
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Invalid continent name provided.");
            return countries;
        }

        // 2. Prepare SQL query using PreparedStatement
        String query = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Continent = ?
                ORDER BY Population DESC;
                """;

        // 3. Execute query safely
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, continent.trim());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                countries = buildCountriesFromResultSet(rs); // use base class method
            }
        } catch (SQLException e) {
            // 4. Handle SQL exceptions with detailed messages
            System.err.println("Error retrieving continent country report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            return countries;
        }

        // 5. Warn if no data found
        if (countries.isEmpty()) {
            System.out.println("Warning: No country data found for continent '" + continent + "'.");
        }

        return countries;
    }

    /**
     * Retrieves a list of the top N countries within a specified continent,
     * ordered by population (largest to smallest).
     *
     * @param connection             Active database connection
     * @param continent          Continent name (e.g., "Asia", "Europe")
     * @param numberOfCountries  Number of top countries to limit
     * @return List of Country objects, or an empty list if an error occurs or no data is found.
     */
    public ArrayList<Country> getTopNCountries_Continent_Report(Connection connection, String continent, int numberOfCountries) {

        ArrayList<Country> countries = new ArrayList<>();

        // 1. Validate connection
        if (connection == null) {
            System.err.println("Database not connected. Cannot generate top continent report.");
            return countries;
        }

        // 2. Prepare SQL query with LIMIT clause
        String query = """
                SELECT Name AS CountryName, Continent, Region, Population
                FROM country
                WHERE Continent = ?
                ORDER BY Population DESC
                LIMIT ?;
                """;

        // 3. Execute query safely
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, continent.trim());
            preparedStatement.setInt(2, numberOfCountries);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                countries = buildCountriesFromResultSet(rs); // use base class method
            }
        } catch (SQLException e) {
            // 4. Handle SQL exceptions with detailed messages
            System.err.println("Error retrieving top continent country report due to a database issue:");
            e.printStackTrace();
            return countries;
        }

        // 5. Warn if no data found
        if (countries.isEmpty()) {
            System.out.println("Warning: No top country data found for continent '" + continent + "'.");
        }

        return countries;
    }

    /**
     * Prints the Country Report for a specific continent to the console.
     *
     * @param continent Continent name
     * @param countries List of Country objects
     */
    public void printCountries_Continent_Report(String continent, ArrayList<Country> countries) {
        printCountries(countries, "ReportID 2. All Countries in Continent '" + continent + "' Report");
    }

    /**
     * Prints the Top N Country Report for a specific continent to the console.
     *
     * @param continent         Continent name
     * @param countries         List of Country objects
     * @param numberOfCountries Number of top countries displayed
     */
    public void printTopNCountries_Continent_Report(String continent, ArrayList<Country> countries, int numberOfCountries) {
        printCountries(countries, "ReportID 5. Top " + numberOfCountries + " Countries in Continent '" + continent + "' Report");
    }
}
