package com.napier.gp8;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for Country reports.
 * Handles ResultSet → Country object mapping and generic print functionality.
 */
public class CountriesReportBase {

    // Logger instance for logging errors, warnings, and info messages
    private static final Logger logger = Logger.getLogger(CountriesReportBase.class.getName());

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
            logger.log(Level.SEVERE, "Error reading countries from ResultSet", e);
        }

        if (countries.isEmpty()) {
            logger.info("No country data found. Report will be empty.");
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
            logger.info("No data to display for report: " + reportTitle);
            return;
        }

        System.out.println("\n============= " + reportTitle + " =============");
        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.printf("%-35s %-25s %-25s %-15s%n",
                "Country Name", "Continent", "Region", "Population");
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (Country country : countries) {
            if (country != null) {
                System.out.printf("%-35s %-25s %-25s %,15d%n",
                        country.getCountryName(),
                        country.getContinent(),
                        country.getRegion(),
                        country.getPopulation());
            }
        }

        System.out.println("-------------------------------------------------------------------------------------------------------");
        System.out.println("=======================================================================================================\n");
    }
}
