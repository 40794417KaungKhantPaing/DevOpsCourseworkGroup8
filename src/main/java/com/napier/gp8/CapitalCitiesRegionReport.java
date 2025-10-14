package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * CapitalCities_Region generates a report of capital cities within a specific region sorted by population desc.
 * Include get data method and print method.
 * Get data method extracts the capital cities data in a region, ordered by desc from database.
 * Print method displays the report.
 */
public class CapitalCitiesRegionReport {

    /**
     * Retrieves all capital cities in a specific region, ordered by population descending.
     * @param conn Active database connection
     * @param region Name of the region
     * @return ArrayList of capitals, empty if no data or error occurs
     */
    public ArrayList<City> getCapitalCitiesInRegionByPopulation(Connection conn, String region) {

        //arraylist to hold  the retrieved the capital cities data
        ArrayList<City> capitals = new ArrayList<>();

        // Validate the database connection before proceeding
        if (conn == null) {
            System.err.println("Database connection is null. Cannot generate report.");
            return capitals;
        }

        //check the region is not null and not whitespace
        if (region == null || region.trim().isEmpty()) {
            System.err.println("Invalid region input. Please provide a valid region.");
            return capitals;
        }

        //try with resources for automatic closing the statement after use.
        try (Statement stmt = conn.createStatement()) {

            // SQL query to extract data from database, the query join 'country' and 'city' tables, ordered by population
            String strSelect =
                    "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                            "co.Name AS CountryName " +
                            "FROM country co " +
                            "JOIN city c ON co.Capital = c.ID " +
                            "WHERE co.Region = '" + region + "' " +
                            "ORDER BY c.Population DESC";

            //Try-with-resources for ResultSet
            try (ResultSet rs = stmt.executeQuery(strSelect)) {

                while (rs.next()) {

                    //create and populate city object with data
                    City city = new City();
                    city.setId(rs.getInt("ID"));
                    city.setCityName(rs.getString("CapitalName"));
                    city.setCountryCode(rs.getString("CountryCode"));
                    city.setPopulation(rs.getInt("Population"));

                    //create and associate a country object for the city
                    Country country = new Country();
                    country.setCountryName(rs.getString("CountryName"));
                    city.setCountry(country);

                    //Add city to the list
                    capitals.add(city);
                }
            }

        } catch (SQLException e) {
            //provide detailed SQL error information for debugging and logging.
            System.err.println("SQL Error while retrieving capital cities for region: " + region);
            System.err.println("SQL State: " + e.getSQLState()); //SQL state error
            System.err.println("Error Code: " + e.getErrorCode()); // Error code
            e.printStackTrace();
            return capitals;
        }

        // Check if no data was found
        if (capitals.isEmpty()) {
            System.out.println("Warning: No capital city data found for region: " + region);
        }

        //return list of capital cities retrieved from database.
        return capitals;
    }

    /**
     * Prints the capital cities report for a region in a formatted table.
     *
     * @param capitals ArrayList of City objects
     * @param region   Name of the region
     */
    public void printCapitalCitiesInRegionByPopulation(ArrayList<City> capitals, String region) {

        // Validate list if null or empty
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found to display for region: " + region);
            return;
        }

        // Print table header
        System.out.println("\nAll the Capital Cities in Region: " + region + " Report");
        System.out.printf("%-35s %-40s %-15s%n", "Capital", "Country", "Population");
        System.out.println("----------------------------------------------------------------------------------------------------");

        // Iterate each city object and print results
        for (City city : capitals) {
            //Ensure city and country data are valid
            if (city != null && city.getCountry() != null) {
                System.out.printf("%-35s %-40s %,15d%n",
                        city.getCityName(),
                        city.getCountry().getCountryName(),
                        city.getPopulation());
            }
        }

        //print footer line to mark end of report.
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
}
