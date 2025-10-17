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
    // Report 27: Population by Continent (updated with City & Non-City columns)
    // ---------------------------------------------------------------------

    /**
     * Retrieves population by continent including city and non-city breakdown.
     *
     * @param conn Active database connection
     * @return List of Country objects (reuse gnp for CityPop, gnpOld for NonCityPop)
     */
    public List<Country> getPopulation_Continent_Report(Connection conn) {
        List<Country> results = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by continent.");
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

                // Reuse existing fields — no new setters
                c.setGnp((double) rs.getLong("CityPopulation"));
                c.setGnpOld((double) rs.getLong("NonCityPopulation"));

                results.add(c);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by continent.", e);
        }

        if (results.isEmpty()) {
            LOGGER.warning("No population data found by continent. Report will be empty.");
        }

        return results;
    }

    /**
     * Prints the Population by Continent Report to the console.
     *
     * @param results List of Country objects
     */
    public void printPopulation_Continent_Report(List<Country> results) {
        System.out.println("\n==================== ReportID 27. Population by Continent Report ====================");
        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %20s %20s %20s %10s %12s%n",
                "Continent", "Total Pop", "City Pop", "Non-City Pop", "City %", "Non-City %");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Country c : results) {
            long total = c.getPopulation();
            long city = c.getGnp() != null ? c.getGnp().longValue() : 0;
            long nonCity = c.getGnpOld() != null ? c.getGnpOld().longValue() : 0;

            double cityPercent = total > 0 ? (city * 100.0 / total) : 0;
            double nonCityPercent = total > 0 ? (nonCity * 100.0 / total) : 0;

            System.out.printf("%-20s %,20d %,20d %,20d %9.2f%% %11.2f%%%n",
                    c.getContinent(),
                    total,
                    city,
                    nonCity,
                    cityPercent,
                    nonCityPercent);
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------");
        System.out.println("=============================================================================================================\n");
    }

    // ---------------------------------------------------------------------
    // Report 23 remains unchanged
    // ---------------------------------------------------------------------

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
