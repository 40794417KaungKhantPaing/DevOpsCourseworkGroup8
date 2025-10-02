package com.napier.gp8;

public class CountryLanguage
{
    // Country code (foreign key to Country)
    private String countryCode;

    // Language name spoken in the country
    private String language;

    // Indicates whether the language is official ("T" for true, "F" for false)
    private String isOfficial;

    // Percentage of the population that speaks the language
    private double percentage;

    // Associated Country object (optional, may be null if not loaded)
    private Country country;

    /**
     * Gets the country code.
     * @return country code
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Gets the language name.
     * @return language name
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Checks if the language is official.
     * @return "T" if official, "F" otherwise
     */
    public String getIsOfficial() {
        return isOfficial;
    }

    /**
     * Gets the percentage of speakers.
     * @return percentage of population that speaks the language
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * Gets the associated Country object.
     * @return Country object, or null if not loaded
     */
    public Country getCountry() {
        return country;
    }
}
