package com.napier.gp8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles country-based city reports (all cities in a country, top N cities in a country)
 * using CitiesReportBase.
 */
public class CitiesCountryReport extends CitiesReportBase {

    /**
     * Retrieve all cities in a specific country ordered by population descending.
     */
    public ArrayList<City> getCitiesCountryReport(Connection conn, String countryName) {
        ArrayList<City> cities = new ArrayList<>();
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE country.Name = ?
                ORDER BY city.Population DESC;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, countryName);
            try (ResultSet rs = pstmt.executeQuery()) {
                cities = buildCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all cities in country: " + countryName);
            e.printStackTrace();
        }

        return cities;
    }


    /**
     * Print all cities in a country report.
     */
    public void printCitiesCountryReport(ArrayList<City> cities, String countryName) {
        printCities(cities, "All Cities in Country: " + countryName + " (Ordered by Population Descending)");
    }


}
