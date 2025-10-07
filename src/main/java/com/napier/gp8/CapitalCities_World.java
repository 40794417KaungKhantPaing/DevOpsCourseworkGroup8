package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;

/**
 * Class to generate reports of capital cities in the world, ordered by population from largest to smallest.
 */
public class CapitalCities_World {

    //database connection
    private final Connection conn;

    /**
     * Constructor: accepts an active database connection.
     * @param conn active SQL Connection object
     */
    public CapitalCities_World(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retrieves all capital cities in the world, ordered by largest population to smallest.
     *
     * @return ArrayList of City objects representing capital cities
     */
    public ArrayList<City> getAllCapitalCitiesInWorldByPopulation() {
        ArrayList<City> capitals = new ArrayList<>();

        // Validate database connection
        if (conn == null) {
            System.out.println("Database connection is null.");
            return capitals; // Return empty list
        }

        try {
            // SQL query to get capital cities and their countries, ordered by population
            String strSelect =
                    "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                            "co.Name AS CountryName " +
                            "FROM country co " +
                            "JOIN city c ON co.Capital = c.ID " +
                            "ORDER BY c.Population DESC";

            // Use PreparedStatement for prevent SQL injection
            PreparedStatement stmt = conn.prepareStatement(strSelect);

            // Execute SQL statement
            ResultSet rs = stmt.executeQuery();

            // Extract capital city information
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getInt("ID"));
                city.setCityName(rs.getString("CapitalName"));
                city.setCountryCode(rs.getString("CountryCode"));
                city.setPopulation(rs.getInt("Population"));

                //create and associate a country object for the city
                Country country = new Country();
                country.setCountryName(rs.getString("CountryName"));
                // associate City → Country
                city.setCountry(country);

                capitals.add(city);
            }

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            System.out.println("Failed to get capital cities by population.");
            capitals.clear(); // Clear partial results if error occurs
        }

        return capitals;
    }

    /**
     * Prints a list of capital cities with their country and population.
     *
     * @param capitals ArrayList of City objects
     */
    public void printAllCapitalCitiesInWorldByPopulation(ArrayList<City> capitals) {
        // Validate list
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found to display.");
            return;
        }

        // Print header
        System.out.println("\nAll the Capital Cities in the World by Population:");
        System.out.printf("%-30s %-50s %-15s%n", "Capital", "Country", "Population");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        // Print results, looping through each city object and print details in an aligned columns
        for (City city : capitals) {
            if (city != null && city.getCountry() != null) {
                System.out.printf("%-30s %-50s %,15d%n",
                        city.getCityName(),
                        city.getCountry().getCountryName(),
                        city.getPopulation());
            }
        }
    }
}
