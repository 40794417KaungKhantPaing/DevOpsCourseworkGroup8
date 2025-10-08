package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to generate reports of capital cities in a continent, ordered by population from largest to smallest.
 * It includes get data method and print method
 * Get data method extracts capital cities in a continent, ordered by desc from the database.
 * Print method displays the report.
 */
public class CapitalCities_Continent {

    /**
     * Get capital cities data in a specific continent, sorted by population from largest to smallest.
     * @param conn database connection
     * @param continent Name of the continent
     * @return
     */
    public ArrayList<City> getCapitalCitiesInContinentByPopulation(Connection conn, String continent) {

        ArrayList<City> capitals = new ArrayList<>();

        //Check for null connection and return an empty list upon failure.
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate capital cities continent report.");
            return capitals;
        }

        //check the continent is not null and not whitespace
        if (continent == null || continent.trim().isEmpty()) {
            System.err.println("Invalid continent input. Please provide a valid continent name.");
            return capitals;
        }

        //using try with resources for automatic closing the statement after use.
        try (Statement stmt = conn.createStatement()) {

            // SQL query to get capital cities and their countries in a specific continent , ordered by population desc
            String strSelect =
                    "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                            "co.Name AS CountryName, co.Continent " +
                            "FROM country co " +
                            "JOIN city c ON co.Capital = c.ID " +
                            "WHERE co.Continent = '" + continent + "' " +
                            "ORDER BY c.Population DESC";

            //Use try-with-resources for  ResultSet
            try (ResultSet rs = stmt.executeQuery(strSelect)) {

                while (rs.next()) {

                    // Build City object
                    City city = new City();
                    city.setId(rs.getInt("ID"));
                    city.setCityName(rs.getString("CapitalName"));
                    city.setCountryCode(rs.getString("CountryCode"));
                    city.setPopulation(rs.getInt("Population"));

                    // Build and link country object
                    Country country = new Country();
                    country.setCountryName(rs.getString("CountryName"));
                    city.setCountry(country); //city associate to country

                    //Add city to the list
                    capitals.add(city);
                }
            }

        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities continent report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState()); //SQL state code
            System.err.println("Error Code: " + e.getErrorCode()); //Database-specific error code
            e.printStackTrace(); //print full stack trace

            //return empty list on error
            return capitals;
        }

        //Notify if no data was found
        if (capitals.isEmpty()) {
            System.out.println("Warning: No capital city data found in the database for continent: " + continent);
        }

        //return list of capital cities retrieved from database.
        return capitals;
    }

    /**
     * Prints a list of capital cities in a continent with their country and population report.
     * @param capitals ArrayList of City objects
     * @param continent Name of the continent
     */
    public void printCapitalCitiesInContinentByPopulation(ArrayList<City> capitals, String continent) {

        // Validate array list if null or empty
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found to display for continent: " + continent);
            return;
        }

        // Print header
        System.out.println("\nAll the Capital Cities in " + continent + " Report");
        System.out.printf("%-35s %-60s %-15s%n", "Capital", "Country", "Population");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        //Iterate cities and print results
        for (City city : capitals) {
            if (city != null && city.getCountry() != null) {
                System.out.printf("%-35s %-60s %,15d%n",
                        city.getCityName(),
                        city.getCountry().getCountryName(),
                        city.getPopulation());
            }
        }

        //print footer line to mark end of report
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
    }
}
