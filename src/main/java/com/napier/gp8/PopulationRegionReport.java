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

    // ---------------------------------------------------------------------
    // Report 28: Population by Region (Updated with City & Non-City details)
    // ---------------------------------------------------------------------

    public List<Country> getPopulation_Region_Report(Connection conn) {
        List<Country> regions = new ArrayList<>();

        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate population report by region.");
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
                region.setGnp(rs.getDouble("CityPopulation"));     // reuse GNP for CityPopulation
                region.setGnpOld(rs.getDouble("NonCityPopulation"));// reuse GNP_OLD for NonCityPopulation
                regions.add(region);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by region.", e);
        }

        if (regions.isEmpty()) {
            LOGGER.warning("No population data found by region.");
        }

        return regions;
    }

    public void printPopulation_Region_Report(List<Country> regions, String selectedRegion) {
        System.out.println("\n==================== ReportID 28. Population by Region Report ====================");
        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %20s %20s %20s %10s %10s%n",
                "Region", "Total Population", "City Population", "Non-City Population", "City %", "Non-City %");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        regions.stream()
                .filter(c -> c.getRegion().equalsIgnoreCase(selectedRegion))
                .forEach(region -> {
                    long total = region.getPopulation();
                    long city = region.getGnp() != null ? region.getGnp().longValue() : 0;
                    long nonCity = region.getGnpOld() != null ? region.getGnpOld().longValue() : 0;

                    double cityPercent = total > 0 ? (city * 100.0 / total) : 0;
                    double nonCityPercent = total > 0 ? (nonCity * 100.0 / total) : 0;

                    System.out.printf("%-30s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                            region.getRegion(),
                            total,
                            city,
                            nonCity,
                            cityPercent,
                            nonCityPercent);
                });

        System.out.println("--------------------------------------------------------------------------------------------------------------");
        System.out.println("==============================================================================================================\n");
    }

    // ---------------------------------------------------------------------
    // Report 24: Population by Region (Urban vs Non-Urban)
    // ---------------------------------------------------------------------

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
                region.setGnp(rs.getDouble("CityPopulation"));
                region.setGnpOld(rs.getDouble("NonCityPopulation"));
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

    public void printPopulation_Region_Details_Report(List<Country> regions) {
        System.out.println("\n========================== Report ID 24. Population by Region (Urban vs Non-Urban) ==========================");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-30s %20s %20s %20s %10s %10s%n",
                "Region", "Total Population", "City Population", "Non-City Population", "City %", "Non-City %");
        System.out.println("--------------------------------------------------------------------------------------------------------------");

        for (Country region : regions) {
            long total = region.getPopulation();
            long city = region.getGnp() != null ? region.getGnp().longValue() : 0;
            long nonCity = region.getGnpOld() != null ? region.getGnpOld().longValue() : 0;

            double cityPercent = total > 0 ? (city * 100.0 / total) : 0;
            double nonCityPercent = total > 0 ? (nonCity * 100.0 / total) : 0;

            System.out.printf("%-30s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                    region.getRegion(),
                    total,
                    city,
                    nonCity,
                    cityPercent,
                    nonCityPercent);
        }

        System.out.println("------------------------------------------------------------------------------------------------------------");
        System.out.println("============================================================================================================\n");
    }
}
