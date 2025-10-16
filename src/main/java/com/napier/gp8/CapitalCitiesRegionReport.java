package com.napier.gp8;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * CapitalCitiesRegionReport extends CapitalCitiesReportBase and provides
 * methods to retrieve and print capital cities in a specific region by population.
 */
public class CapitalCitiesRegionReport extends CapitalCitiesReportBase {

    /**
     * Get all capital cities in a specific region, ordered by population descending
     *
     * @param conn Database connection object
     * @param region Region name to filter countries
     * @return ArrayList of City objects
     */
    public ArrayList<City> getAllCapitalCitiesInRegionByPopulation(Connection conn, String region) {
        // ArrayList to hold the retrieved capital cities data
        ArrayList<City> capitals = new ArrayList<>();

        //Validate Connection
        if (conn == null) {
            System.err.println("Database not connected. Cannot display All Capital Cities in the Region Report");
            return capitals;
        }

        // SQL query to get capitals in the specified region, ordered by population
        String query = "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                "co.Name AS CountryName FROM country co " +
                "JOIN city c ON co.Capital = c.ID " +
                "WHERE co.Region = ? ORDER BY c.Population DESC";

        try (PreparedStatement queryStmt = conn.prepareStatement(query)) {

            // Bind the region parameter safely to prevent SQL injection
            queryStmt.setString(1, region);

            try (ResultSet rs = queryStmt.executeQuery()) {

                // Call helper method from base class to build the list of capitals
                capitals = buildCapitalCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities report due to a database issue:");
            System.err.println("Error Code: " + e.getErrorCode()); //Error code
            e.printStackTrace();
            return capitals; //return safely with an empty list.
        }

        return capitals;
    }

    /**
     * Get top N capital cities in a specific region by population
     *
     * @param conn Database connection object
     * @param region Region name to filter countries
     * @param numberOfCapitalCities Number of top capitals to retrieve
     * @return ArrayList of City objects
     */
    public ArrayList<City> getTopNCapitalCitiesInRegionByPopulation(Connection conn, String region, int numberOfCapitalCities) {
        ArrayList<City> capitals = new ArrayList<>();

        //Validate Connection
        if (conn == null) {
            System.err.println("Database not connected. Cannot display Top N Capital Cities in the Region Report");
            return capitals;
        }

        String query = "SELECT c.ID, c.Name AS CapitalName, c.CountryCode, c.Population, " +
                "co.Name AS CountryName FROM country co " +
                "JOIN city c ON co.Capital = c.ID " +
                "WHERE co.Region = ? ORDER BY c.Population DESC LIMIT ?";

        try (PreparedStatement preparedStmt = conn.prepareStatement(query)) {
            //safe binding
            preparedStmt.setInt(2, numberOfCapitalCities);
            preparedStmt.setString(1, region);
            try (ResultSet rs = preparedStmt.executeQuery()) {
                capitals = buildCapitalCitiesFromResultSet(rs);
            }
        } catch (SQLException e) {
            //Catch SQL exceptions, print detailed error, and return the (empty) list
            System.err.println("Error retrieving capital cities report due to a database issue:");
            System.err.println("Error Code: " + e.getErrorCode()); //Error code
            e.printStackTrace();
            return capitals; //return safely with an empty list.
        }

        return capitals;
    }

    /**
     * Print all capital cities in a specific region
     *
     * @param capitals List of City objects to print
     * @param region Region name for the report title
     */
    public void printAllCapitalCitiesInRegionByPopulation(ArrayList<City> capitals, String region) {

        //call general print method from base class
        printCapitalCities(capitals, "All Capital Cities in Region: " + region + " Report");
    }

    /**
     * Print top N capital cities in a specific region
     *
     * @param capitals List of City objects to print
     * @param region Region name for the report title
     * @param numberOfCapitalCities Number of top capitals displayed
     */
    public void printTopNCapitalCitiesInRegionByPopulation(ArrayList<City> capitals, String region, int numberOfCapitalCities) {
        printCapitalCities(capitals, "Top " + numberOfCapitalCities + " Capital Cities in Region: " + region + " Report");
    }

}
