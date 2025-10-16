package com.napier.gp8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Base class for city reports (world, continent, region).
 * Handles ResultSet → City mapping and printing.
 */
public class CitiesReportBase {

    /**
     * Build a list of City objects from a ResultSet.
     * Expects ResultSet columns: CityName, CountryName, District, Population
     */
    public ArrayList<City> buildCitiesFromResultSet(ResultSet rs) {
        ArrayList<City> cities = new ArrayList<>();
        if (rs == null) return cities;

        try {
            while (rs.next()) {
                City city = new City();
                Country country = new Country();

                city.setCityName(rs.getString("CityName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));

                country.setCountryName(rs.getString("CountryName"));
                city.setCountry(country);

                cities.add(city);
            }
        } catch (SQLException e) {
            System.err.println("Error reading cities from ResultSet:");
            e.printStackTrace();
        }

        if (cities.isEmpty()) {
            System.out.println("No city data found. Report will be empty.");
        }

        return cities;
    }

    /**
     * Print a list of cities with a report title.
     */
    public void printCities(ArrayList<City> cities, String reportTitle) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No data to display for report: " + reportTitle);
            return;
        }

        System.out.println("\n" + reportTitle);
        System.out.printf("%-35s %-35s %-20s %-15s%n", "City", "Country", "District", "Population");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (City city : cities) {
            if (city != null && city.getCountry() != null) {
                System.out.printf("%-35s %-35s %-20s %,15d%n",
                        city.getCityName(),
                        city.getCountry().getCountryName(),
                        city.getDistrict(),
                        city.getPopulation());
            }
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
    }
}
