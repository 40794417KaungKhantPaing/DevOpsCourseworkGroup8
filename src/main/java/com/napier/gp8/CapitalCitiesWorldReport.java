package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to generate reports of capital cities in the world, ordered by population from largest to smallest.
 * It provides methods to get capital city data from the database and
 * print the results in format
 */
public class CapitalCitiesWorldReport {

    /**
     * Retrieves all capital cities in the world, ordered by largest population to smallest.
     * @param conn active SQL Connection object
     * @return ArrayList of City objects representing capital cities
     */
    public ArrayList<City> getAllCapitalCitiesInWorldByPopulation(Connection conn) {

        ArrayList<City> capitals = new ArrayList<>();

        // Check for null connection and return an empty list upon failure.
        if (conn == null) {
            System.err.println("Database not connected. Cannot generate capital cities report.");
            return capitals;
        }

        //Use try-with-resources for automatic Statement closing
        try (Statement stmt = conn.createStatement()) {

            // SQL query to get capital cities and their countries, ordered by population
            String strSelect =
                    "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                            "co.Name AS CountryName " +
                            "FROM country co " +
                            "JOIN city c ON co.Capital = c.ID " +
                            "ORDER BY c.Population DESC";

            //Use try-with-resources for automatic ResultSet closing
            try (ResultSet rs = stmt.executeQuery(strSelect)) {

                while (rs.next()) {

                    //create city and country object
                    City city = new City();
                    Country country = new Country();

                    //set city object with data
                    city.setId(rs.getInt("ID"));
                    city.setCityName(rs.getString("CapitalName"));
                    city.setCountryCode(rs.getString("CountryCode"));
                    city.setPopulation(rs.getInt("Population"));

                    //associate country object for the city
                    country.setCountryName(rs.getString("CountryName"));
                    city.setCountry(country);

                    //Add city to the list.
                    capitals.add(city);
                }
            }

        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities report due to a database issue:");
            System.err.println("SQL State: " + e.getSQLState()); //SQL state error
            System.err.println("Error Code: " + e.getErrorCode()); //Error code
            e.printStackTrace();
            return capitals; //return safely with an empty list.
        }

        //Check if no data was found
        if (capitals.isEmpty()) {
            System.out.println("Warning: No capital city data found in the database. Report will be empty.");
        }

        return capitals;
    }

    /**
     * Prints a list of capital cities with their country and population.
     * @param capitals ArrayList of City objects
     */
    public void printAllCapitalCitiesInWorldByPopulation(ArrayList<City> capitals) {

        // Validate list if null or empty
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found to display.");
            return;
        }

        // Print header
        System.out.println("\nAll the Capital Cities in the World by Population:");
        System.out.printf("%-35s %-40s %-15s%n", "Capital", "Country", "Population");
        System.out.println("-----------------------------------------------------------------------------------------------");

        //Iterate cities and print data
        for (City city : capitals) {
            if (city != null && city.getCountry() != null) {
                System.out.printf("%-35s %-40s %,15d%n",
                        city.getCityName(),
                        city.getCountry().getCountryName(),
                        city.getPopulation());
            }
        }

        //print footer line to mark end of report.
        System.out.println("-----------------------------------------------------------------------------------------------");
    }
}
