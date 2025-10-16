package com.napier.gp8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Base class for Country reports.
 * Handles ResultSet → Country object mapping and generic print functionality.
 */
public class CountriesReportBase {

    /**
     * Builds a list of Country objects from a ResultSet.
     *
     * @param rs The ResultSet containing country data.
     * @return ArrayList of Country objects.
     */
    public ArrayList<Country> buildCountriesFromResultSet(ResultSet rs) {
        ArrayList<Country> countries = new ArrayList<>();
        if (rs == null) return countries;

        try {
            while (rs.next()) {
                Country country = new Country();
                country.setCountryName(rs.getString("CountryName"));
                country.setContinent(rs.getString("Continent"));
                country.setRegion(rs.getString("Region"));
                country.setPopulation(rs.getInt("Population"));
                countries.add(country);
            }
        } catch (SQLException e) {
            System.err.println("Error reading countries from ResultSet:");
            e.printStackTrace();
        }

        if (countries.isEmpty()) {
            System.out.println("No country data found. Report will be empty.");
        }

        return countries;
    }

    /**
     * Prints a formatted table of countries to the console.
     *
     * @param countries   List of countries to print.
     * @param reportTitle Title of the report.
     */
    public void printCountries(ArrayList<Country> countries, String reportTitle) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No data to display for report: " + reportTitle);
            return;
        }

        System.out.println("\n" + reportTitle);
        System.out.printf("%-35s %-25s %-25s %-15s%n",
                "Country Name", "Continent", "Region", "Population");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            if (country != null) {
                System.out.printf("%-35s %-25s %-25s %,15d%n",
                        country.getCountryName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation());
            }
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------");
    }
}
