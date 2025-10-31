package com.napier.gp8;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopulationRegionReport {

    private static final Logger LOGGER = Logger.getLogger(PopulationRegionReport.class.getName());

    public List<Country> getPopulation_Region_Report(Connection conn) {
        return fetchRegionPopulation(conn);
    }

    public List<Country> getPopulation_Region_Details_Report(Connection conn) {
        return fetchRegionPopulation(conn);
    }

    private List<Country> fetchRegionPopulation(Connection conn) {
        List<Country> results = new ArrayList<>();
        if (conn == null) {
            LOGGER.warning("Database not connected. Cannot generate region population report.");
            return results;
        }

        String sql = """
                    SELECT
                        c.Region AS Region,
                        SUM(c.Population) AS TotalPopulation,
                        COALESCE(SUM(city_pop.CityPopulation), 0) AS CityPopulation,
                        SUM(c.Population) - COALESCE(SUM(city_pop.CityPopulation), 0) AS NonCityPopulation
                    FROM country c
                    LEFT JOIN (
                        SELECT CountryCode, SUM(Population) AS CityPopulation
                        FROM city
                        GROUP BY CountryCode
                    ) city_pop ON c.Code = city_pop.CountryCode
                    GROUP BY c.Region
                    ORDER BY TotalPopulation DESC;
                """;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country c = new Country();
                c.setRegion(rs.getString("Region"));
                c.setPopulation(rs.getLong("TotalPopulation"));
                c.setGnp(rs.getDouble("CityPopulation"));
                c.setGnpOld(rs.getDouble("NonCityPopulation"));
                results.add(c);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error retrieving population report by region.", e);
        }

        if (results.isEmpty()) {
            LOGGER.warning("No population data found for region report.");
        }

        return results;
    }

    public void printPopulation_Region_Report(List<Country> results, String selectedRegion) {
        String groupbyColumn = "Region";
        printPopulation(results, "ReportID 28. Population by Region Report",groupbyColumn,selectedRegion);
    }

    public void printPopulation_Region_Details_Report(List<Country> results) {
        printPopulation(results, "Report ID 24. Population by Region (Urban vs Non-Urban)", "Region", null);
    }

    private void printPopulation(List<Country> results, String title, String groupByColumn, String selectedValue) {
        System.out.println("\n==================== " + title + " ====================");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        System.out.printf("%-30s %20s %20s %20s %10s %10s%n",
                groupByColumn, "Total Pop", "City Pop", "Non-City Pop", "City %", "Non-City %");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");

        if (results == null || results.isEmpty()) {
            System.out.println("No data available to display.");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            System.out.println("=========================================================================================================================\n");
            return;
        }

        for (Country c : results) {
            String name = c.getRegion();
            if (selectedValue != null && !name.equalsIgnoreCase(selectedValue)) continue;
            PopulationUtils.PopValues v = PopulationUtils.calculatePopulationValues(c);
            System.out.printf("%-30s %,20d %,20d %,20d %9.2f%% %9.2f%%%n",
                    name, v.total(), v.city(), v.nonCity(), v.cityPercent(), v.nonCityPercent());
        }

        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("=========================================================================================================================\n");
    }
}
