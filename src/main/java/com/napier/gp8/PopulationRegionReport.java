package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by region.
 */
public class PopulationRegionReport {

    private static final Logger LOGGER = Logger.getLogger(PopulationRegionReport.class.getName());

    /**
     * Retrieves total population grouped by region.
     *
     * @param conn Active database connection
     * @return List of Country objects
     */
    public List<Country> getPopulation_Region_Report(Connection conn) {
        List<Country> countries = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by region.");
            return countries;
        }

        String sql = """
                SELECT Region, SUM(Population) AS TotalPopulation
                FROM country
                GROUP BY Region
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setRegion(rs.getString("Region"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by region.", e);
        }

        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by region.");
        }

        return countries;
    }

    /**
     * NEW REPORT:
     * Retrieves total population, people living in cities, and people not living in cities in each region.
     * Reuses Country class fields:
     * - region → region name
     * - population → total population
     * - gnp → people living in cities
     * - gnpOld → people not living in cities
     *
     * @param conn Active database connection
     * @return List of Country objects containing region population data
     */
    public List<Country> getPopulation_Region_Details_Report(Connection conn) {
        List<Country> regions = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate detailed population report by region.");
            return regions;
        }

        String sql = """
                SELECT
                    c.Region AS Region,
                    SUM(c.Population) AS TotalPopulation,
                    SUM(ci.Population) AS CityPopulation,
                    (SUM(c.Population) - SUM(ci.Population)) AS NonCityPopulation
                FROM country c
                LEFT JOIN city ci ON c.Code = ci.CountryCode
                GROUP BY c.Region
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country region = new Country();
                region.setRegion(rs.getString("Region"));
                region.setPopulation(rs.getLong("TotalPopulation"));
                region.setGnp(rs.getDouble("CityPopulation"));       // reuse gnp for city population
                region.setGnpOld(rs.getDouble("NonCityPopulation")); // reuse gnpOld for non-city population
                regions.add(region);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving detailed region population report.", e);
        }

        if (regions.isEmpty()) {
            LOGGER.warning("No data found for detailed region population report.");
        }

        return regions;
    }

    /**
     * Prints simple region population report.
     */
    protected void printPopulation_Region_Report(List<Country> countries) {
        System.out.println("\n==================== ReportID 28. Population by Region Report ====================");
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("%-30s %-20s%n", "Region", "Total Population");
        System.out.println("--------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-30s %,20d%n", country.getRegion(), country.getPopulation());
        }

        System.out.println("--------------------------------------------------------------------");
        System.out.println("====================================================================\n");
    }

    /**
     * Prints detailed population report: total, city, and non-city populations.
     * Uses:
     * - region → Region
     * - population → Total
     * - gnp → City pop
     * - gnpOld → Non-city pop
     */
    protected void printPopulation_Region_Details_Report(List<Country> regions) {
        System.out.println("\n==================== Report ID 24. Population by Region (Urban vs Non-Urban) ====================");
        System.out.println("----------------------------------------------------------------------------------");
        System.out.printf("%-30s %-20s %-20s %-20s%n",
                "Region", "Total Population", "City Population", "Non-City Population");
        System.out.println("----------------------------------------------------------------------------------");

        for (Country region : regions) {
            System.out.printf("%-30s %,20d %,20.0f %,20.0f%n",
                    region.getRegion(),
                    region.getPopulation(),
                    region.getGnp(),      // city pop
                    region.getGnpOld());  // non-city pop
        }

        System.out.println("----------------------------------------------------------------------------------");
        System.out.println("==================================================================================\n");
    }
}
