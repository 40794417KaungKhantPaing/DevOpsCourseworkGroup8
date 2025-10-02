package com.napier.gp8;

/**
 * Represents a language spoken in a country
 */
public class CountryLanguage
{
    /**
     * Country code (foreign key)
     */
    public String CountryCode;

    /**
     * Language name
     */
    public String Language;

    /**
     * Is it an official language? ("T" or "F")
     */
    public String IsOfficial;

    /**
     * Percentage of speakers
     */
    public double Percentage;

    /**
     * Country object (optional, can be null if not loaded)
     */
    public Country country;
}
