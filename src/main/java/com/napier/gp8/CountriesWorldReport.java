package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * CountriesWorldReport extends CountriesReportBase and provides
 * methods to retrieve and print countries globally by population.
 */
public class CountriesWorldReport extends CountriesReportBase {

    /**
     * Retrieves all countries in the world ordered by population descending.
     *
     * @param conn Active database connection.
     * @return List of Country objects.
     */
    public ArrayList<Country> getCountries_World_Report(Connection conn) {
        ArrayList<Country> countries = new ArrayList<>();

        //Check if database is connected
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate countries world report.");
            return countries;
        }

        // SQL query to retrieve all countries by population descending
        String query = "SELECT Name AS CountryName, Continent, Region, Population " +
                "FROM country ORDER BY Population DESC";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            countries = buildCountriesFromResultSet(resultSet);

        } catch (SQLException e) {
            System.err.println("Error getting country report due to a database issue.");
            e.printStackTrace();

            return countries;
        }

        return countries;
    }

    /**
     * Retrieves the top N countries in the world ordered by population descending.
     *
     * @param conn Active database connection.
     * @param topNCountries Number of top countries to show
     * @return List of Country objects.
     */
    public ArrayList<Country> getTopNCountries_World_Report(Connection conn, int topNCountries) {
        ArrayList<Country> countries = new ArrayList<>();

        //Check if database is connected
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate countries world report.");
            return countries;
        }

        String query = "SELECT Name AS CountryName, Continent, Region, Population " +
                "FROM country ORDER BY Population DESC LIMIT ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, topNCountries);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                countries = buildCountriesFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting top countries report due to a database issue.");
            e.printStackTrace();
            return countries;
        }

        return countries;
    }

    /**
     * Prints all countries in the world in population descending order.
     *
     * @param countries List of countries
     */
    public void printCountries_World_Report(ArrayList<Country> countries) {
        printCountries(countries, "All Countries in the World Report");
    }

    /**
     * Prints the top N countries in the world by population descending
     * @param countries List of countries to print.
     * @param topNCountries Number of top countries
     */
    public void printTopNCountries_World_Report(ArrayList<Country> countries, int topNCountries) {
        printCountries(countries, "Top " + topNCountries + " Countries in the World Report");
    }

}
