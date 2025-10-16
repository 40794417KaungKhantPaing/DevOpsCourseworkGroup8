package com.napier.gp8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles district-based city reports (all cities in a district, top N cities in a district)
 * using CitiesReportBase.
 */
public class CitiesDistrictReport extends CitiesReportBase {

    /**
     * Retrieve all cities in a specific district ordered by population descending.
     */
    public ArrayList<City> getCitiesDistrictReport(Connection conn, String district) {
        ArrayList<City> cities = new ArrayList<>();
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                WHERE city.District = ?
                ORDER BY city.Population DESC;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, district);
            try (ResultSet rs = pstmt.executeQuery()) {
                cities = buildCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving all cities in district: " + district);
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Print all cities in a district report.
     */
    public void printCitiesDistrictReport(ArrayList<City> cities, String district) {
        printCities(cities, "All Cities in District: " + district + " (Ordered by Population Descending)");
    }


}
