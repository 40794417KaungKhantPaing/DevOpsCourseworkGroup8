package com.napier.gp8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Handles world city reports (all cities, top N cities) using CitiesReportBase.
 */
public class CitiesWorldReport extends CitiesReportBase {

    /**
     * Retrieve all cities in the world ordered by population descending.
     */
    public ArrayList<City> getCitiesWorldReport(Connection conn) {
        ArrayList<City> cities = new ArrayList<>();
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            cities = buildCitiesFromResultSet(rs);
        } catch (SQLException e) {
            System.err.println("Error retrieving all cities in the world:");
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Retrieve top N populated cities in the world.
     */
    public ArrayList<City> getTopNCitiesWorldReport(Connection conn, int n) {
        ArrayList<City> cities = new ArrayList<>();
        String query = """
                SELECT city.Name AS CityName, country.Name AS CountryName,
                       city.District, city.Population
                FROM city
                JOIN country ON city.CountryCode = country.Code
                ORDER BY city.Population DESC
                LIMIT ?;
                """;

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, n);
            try (ResultSet rs = pstmt.executeQuery()) {
                cities = buildCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving top " + n + " cities in the world:");
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Print all cities in the world report.
     */
    public void printCitiesWorldReport(ArrayList<City> cities) {
        printCities(cities, "All Cities in the World (Ordered by Population Descending)");
    }

    /**
     * Print top N cities in the world report.
     */
    public void printTopNCitiesWorldReport(ArrayList<City> cities, int n) {
        printCities(cities, "Top " + n + " Cities in the World (Ordered by Population Descending)");
    }
}
