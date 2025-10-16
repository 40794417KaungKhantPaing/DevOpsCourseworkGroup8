package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles generating and retrieving population reports by country.
 */
public class PopulationCountryReport {

    // Logger instance for this class
    private static final Logger LOGGER = Logger.getLogger(PopulationCountryReport.class.getName());

    /**
     * Retrieves total population for all countries, ordered from largest to smallest.
     *
     * @param conn Active database connection
     * @return List of Country objects containing country name and population data,
     *         or an empty list if an error occurs or no data is found.
     */
    public List<Country> getPopulation_Country_Report(Connection conn) {

        List<Country> countries = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by country.");
            return countries;
        }

        String sql = """
                SELECT Name, Population
                FROM country
                ORDER BY Population DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setCountryName(rs.getString("Name"));
                country.setPopulation(rs.getLong("Population"));
                countries.add(country);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by country.", e);
            return countries;
        }

        if (countries.isEmpty()) {
            LOGGER.warning("No country population data found. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population by Country Report to the console.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_Country_Report(List<Country> countries) {
        System.out.println("\n==================== ReportID 29. Population by Country Report ====================");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-40s %-20s%n", "Country", "Population");
        System.out.println("---------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-40s %,20d%n",
                    country.getCountryName(),
                    country.getPopulation());
        }

        System.out.println("---------------------------------------------------------------------");
        System.out.println("=====================================================================\n");
    }

    // -------------------------------------------------------------------------
    // NEW REPORT: Population, City Population, and Non-City Population by Country
    // -------------------------------------------------------------------------

    /**
     * Retrieves total population, city population, and non-city population per country.
     *
     * @param conn Active database connection
     * @return List of Country objects with country name, total population,
     *         and derived city/non-city population.
     */
    public List<Country> getPopulation_City_vs_NonCity_ByCountry(Connection conn) {

        List<Country> countries = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate city vs non-city population report by country.");
            return countries;
        }

        String sql = """
                SELECT
                    co.Name AS Country,
                    co.Population AS TotalPopulation,
                    IFNULL(SUM(ci.Population), 0) AS CityPopulation,
                    (co.Population - IFNULL(SUM(ci.Population), 0)) AS NonCityPopulation
                FROM country co
                LEFT JOIN city ci ON co.Code = ci.CountryCode
                GROUP BY co.Name, co.Population
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setCountryName(rs.getString("Country"));
                country.setPopulation(rs.getLong("TotalPopulation"));

                // We can temporarily reuse the GNP fields to store the two derived values
                // but better is to just print them directly.
                // So no need to modify Country class — we’ll just print directly later.

                // To keep data if needed:
                country.setSurfaceArea(rs.getDouble("CityPopulation")); // store temporarily
                country.setLifeExpectancy(rs.getDouble("NonCityPopulation")); // store temporarily

                countries.add(country);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving city vs non-city population by country.", e);
            return countries;
        }

        if (countries.isEmpty()) {
            LOGGER.warning("No population data found by country (city vs non-city). Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints the Population, City Population, and Non-City Population per Country.
     *
     * @param countries List of Country objects
     */
    protected void printPopulation_City_vs_NonCity_ByCountry(List<Country> countries) {
        System.out.println("\n==================== ReportID 25. Population by Country (City vs Non-City) ====================");
        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.printf("%-40s %-20s %-20s %-20s%n", "Country", "Total Population", "City Population", "Non-City Population");
        System.out.println("-----------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            System.out.printf("%-40s %,20d %,20.0f %,20.0f%n",
                    country.getCountryName(),
                    country.getPopulation(),
                    country.getSurfaceArea(),        // temporarily holds city population
                    country.getLifeExpectancy());    // temporarily holds non-city population
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------");
        System.out.println("===========================================================================================================\n");
    }
}
