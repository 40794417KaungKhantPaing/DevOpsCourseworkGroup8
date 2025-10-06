package com.napier.gp8;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate population reports by continent.
 */
public class population_continent {

    private Connection conn;

    // Constructor: pass the DB connection
    public population_continent(Connection conn) {
        this.conn = conn;
    }

    /**
     * Fetches and displays total population by continent.
     */
    public void display() {
        String sql = """
                SELECT continent, SUM(population) AS TotalPopulation
                FROM country
                GROUP BY continent
                ORDER BY TotalPopulation DESC;
                """;

        List<Country> countries = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Country country = new Country();
                country.setContinent(rs.getString("Continent"));
                country.setPopulation(rs.getLong("TotalPopulation"));
                countries.add(country);
            }

            // Print report
            System.out.println("\nPopulation by Continent:");
            System.out.printf("%-15s %-20s%n", "Continent", "Total Population");
            System.out.println("-------------------------------------");

            for (Country country : countries) {
                System.out.printf("%-15s %,20d%n",
                        country.getContinent(),
                        country.getPopulation());
            }

        } catch (SQLException e) {
            System.err.println("Query failed in PopulationContinent!");
            e.printStackTrace();
        }
    }
}
