package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopulationCountryReport {

    private static final Logger LOGGER = Logger.getLogger(PopulationCountryReport.class.getName());

    public List<Country> getPopulation_Country_Report(Connection conn) {
        return fetchCountryPopulation(conn);
    }

    public List<Country> getPopulation_City_vs_NonCity_ByCountry(Connection conn) {
        return fetchCountryPopulation(conn);
    }

    private List<Country> fetchCountryPopulation(Connection conn) {
        List<Country> results = new ArrayList<>();
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate country population report.");
            return results;
        }

        String sql = """
                SELECT
                    co.Name AS Country,
                    co.Population AS TotalPopulation,
                    IFNULL(SUM(ci.Population),0) AS CityPopulation,
                    (co.Population - IFNULL(SUM(ci.Population),0)) AS NonCityPopulation
                FROM country co
                LEFT JOIN city ci ON co.Code = ci.CountryCode
                GROUP BY co.Name, co.Population
                ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country c = new Country();
                c.setCountryName(rs.getString("Country"));
                c.setPopulation(rs.getLong("TotalPopulation"));
                c.setGnp(rs.getDouble("CityPopulation"));
                c.setGnpOld(rs.getDouble("NonCityPopulation"));
                results.add(c);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by country.", e);
        }

        if (results.isEmpty()) {
            LOGGER.warning("No population data found for country report.");
        }

        return results;
    }

    public void printPopulation_Country_Report(List<Country> results, String selectedCountry) {
        String groupByColumn = "Country";
        printPopulation(results, "ReportID 29. Population by Country Report",groupByColumn,selectedCountry);
    }

    public void printPopulation_City_vs_NonCity_ByCountry(List<Country> results) {
        printPopulation(results, "ReportID 25. Population by Country (City vs Non-City)", "Country", null);
    }

    private void printPopulation(List<Country> results, String title, String groupByColumn, String selectedValue) {
        System.out.println("\n==================== " + title + " ====================");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-40s %20s %20s %20s %10s %10s%n",
                groupByColumn, "Total Pop", "City Pop", "Non-City Pop", "City %", "Non-City %");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        for (Country c : results) {
            String name = c.getCountryName();
            if (selectedValue != null && !name.equalsIgnoreCase(selectedValue)) continue;
            PopulationUtils.PopValues v = PopulationUtils.calculatePopulationValues(c);
            System.out.printf("%-40s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                    name, v.total(), v.city(), v.nonCity(), v.cityPercent(), v.nonCityPercent());
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("==============================================================================================================================\n");
    }
}
