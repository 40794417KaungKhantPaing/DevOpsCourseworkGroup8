package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by continent.
 */
public class PopulationContinentReport {

    private static final Logger LOGGER = Logger.getLogger(PopulationContinentReport.class.getName());

    // ---------------------------------------------------------------------
    // Report 27: Population by Continent
    // ---------------------------------------------------------------------

    /**
     * Retrieves total population grouped by continent, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing continent and population data,
     *         or empty list if error occurs.
     */
    public List<Country> getPopulation_Continent_Report(Connection conn) {
        List<Country> countries = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by continent.");
            return countries;
        }

        String sql = """
                SELECT continent, SUM(population) AS TotalPopulation
                FROM country
                GROUP BY continent
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setContinent(rs.getString("Continent"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by continent.", e);
        }

        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by continent. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Continent Report to the console.
     *
     * @param countries List of Country objects
     */
    public void printPopulation_Continent_Report(List<Country> countries) {
        System.out.println("\n==================== ReportID 27. Population by Continent Report ====================");
        System.out.println("------------------------------------------------------------");
        System.out.printf("%-20s %-20s%n", "Continent", "Total Population");
        System.out.println("------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-20s %,20d%n",
                    country.getContinent(),
                    country.getPopulation());
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("=======================================================================\n");
    }

    // ---------------------------------------------------------------------
    // Report 23: Population in Cities vs Not in Cities by Continent
    // ---------------------------------------------------------------------

    /**
     * Retrieves population of people, people living in cities, and people not living in cities in each continent.
     *
     * @param conn Active database connection
     * @return List of Country objects, using population for total,
     *         gnp for people living in cities, and gnpOld for people not living in cities.
     */
    public List<Country> getPopulation_City_vs_NonCity_ByContinent(Connection conn) {
        List<Country> results = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population by continent (city vs non-city).");
            return results;
        }

        String sql = """
                SELECT 
                    c.Continent,
                    SUM(c.Population) AS TotalPopulation,
                    SUM(ci.Population) AS CityPopulation,
                    (SUM(c.Population) - SUM(ci.Population)) AS NonCityPopulation
                FROM country c
                LEFT JOIN city ci ON ci.CountryCode = c.Code
                GROUP BY c.Continent
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country c = new Country();
                c.setContinent(rs.getString("Continent"));
                c.setPopulation(rs.getLong("TotalPopulation"));

                // Reuse fields: gnp = city population, gnpOld = non-city population
                c.setGnp((double) rs.getLong("CityPopulation"));
                c.setGnpOld((double) rs.getLong("NonCityPopulation"));

                results.add(c);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population by continent (city vs non-city).", e);
        }

        if (results.isEmpty()) {
            LOGGER.warning("No population data found for city vs non-city report by continent.");
        }

        return results;
    }

    /**
     * Prints the population report showing total, city, and non-city populations by continent with percentages.
     *
     * @param results List of Country objects
     */
    public void printPopulation_City_vs_NonCity_ByContinent(List<Country> results) {
        System.out.println("\n==================== ReportID 23. Population in Cities vs Not in Cities by Continent ====================");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %20s %20s %20s %10s %10s%n",
                "Continent", "Total Pop", "City Pop", "Non-City Pop", "City %", "Non-City %");
        System.out.println("--------------------------------------------------------------------------------------------------------");

        for (Country c : results) {
            long total = c.getPopulation();
            long city = c.getGnp() != null ? c.getGnp().longValue() : 0;
            long nonCity = c.getGnpOld() != null ? c.getGnpOld().longValue() : 0;

            double cityPercent = total > 0 ? (city * 100.0 / total) : 0;
            double nonCityPercent = total > 0 ? (nonCity * 100.0 / total) : 0;

            System.out.printf("%-20s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                    c.getContinent(),
                    total,
                    city,
                    nonCity,
                    cityPercent,
                    nonCityPercent);
        }

        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.println("========================================================================================================\n");
    }

}
