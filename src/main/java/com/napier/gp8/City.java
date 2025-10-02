package com.napier.gp8;
/**
 * Represents a city
 */
public class City
{
    /**
     * City ID
     */
    public int ID;

    /**
     * City name
     */
    public String Name;

    /**
     * Country code (foreign key)
     */
    public String CountryCode;

    /**
     * District of the city
     */
    public String District;

    /**
     * Population of the city
     */
    public int Population;

    /**
     * Country object (optional, can be null if not loaded)
     */
    public Country country;
}
