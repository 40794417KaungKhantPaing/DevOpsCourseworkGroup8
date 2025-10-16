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

    private static final Logger LOGGER = Logger.getLogger(PopulationCountryReport.class.getName());

    // ---------------------------------------------------------------------
    // Report 29: Population by Country (specific country)
    // ---------------------------------------------------------------------
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
        }

        return countries;
    }

    public void printPopulation_Country_Report(List<Country> countries, String selectedCountry) {
        System.out.println("\n==================== ReportID 29. Population by Country Report ====================");
        System.out.println("---------------------------------------------------------------------");
        System.out.printf("%-40s %-20s%n", "Country", "Population");
        System.out.println("---------------------------------------------------------------------");

        countries.stream()
                .filter(c -> c.getCountryName().equalsIgnoreCase(selectedCountry))
                .forEach(c -> System.out.printf("%-40s %,20d%n",
                        c.getCountryName(),
                        c.getPopulation()));

        System.out.println("---------------------------------------------------------------------");
        System.out.println("=====================================================================\n");
    }

    // ---------------------------------------------------------------------
    // Report 25: Population by Country (City vs Non-City with percentages)
    // ---------------------------------------------------------------------
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

                // Reuse gnp for city population, gnpOld for non-city population
                country.setGnp(rs.getDouble("CityPopulation"));
                country.setGnpOld(rs.getDouble("NonCityPopulation"));

                countries.add(country);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving city vs non-city population by country.", e);
        }

        return countries;
    }

    public void printPopulation_City_vs_NonCity_ByCountry(List<Country> countries) {
        System.out.println("\n================================ ReportID 25. Population by Country (City vs Non-City) ============================");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-40s %20s %20s %20s %10s %10s%n",
                "Country", "Total Population", "City Population", "Non-City Population", "City %", "Non-City %");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            long total = country.getPopulation();
            long city = country.getGnp() != null ? country.getGnp().longValue() : 0;
            long nonCity = country.getGnpOld() != null ? country.getGnpOld().longValue() : 0;

            double cityPercent = total > 0 ? (city * 100.0 / total) : 0;
            double nonCityPercent = total > 0 ? (nonCity * 100.0 / total) : 0;

            System.out.printf("%-40s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                    country.getCountryName(),
                    total,
                    city,
                    nonCity,
                    cityPercent,
                    nonCityPercent);
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        System.out.println("=======================================================================================================================\n");
    }
}
