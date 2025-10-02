package com.napier.gp8;

/**
 * Represents a country in the world database
 * Variables are declared as private for Encapsulation.
 * Getter methods
 */
public class Country
{

     //Country code (primary key)
    private String code;

    //Country name
    private String countryName;

    //Continent
    private String continent;

    //Region
    private String region;

    //Surface area
    private double surfaceArea;

    //Year of independence
    private Integer indepYear;

    //Population of the country
    private long population;

    //Life expectancy
    private Double lifeExpectancy;

    //Gross National Product
    private Double gnp;

    //Old GNP
    private Double gnpOld;

    //local name
    private String localName;

    //government form
    private String governmentForm;

    //Head of state
    private String headOfState;

    //Capital city ID
    private Integer capital;

    //Secondary country code
    private String code2;

    /**
     * Gets the country code.
     * @return ISO country code
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the country name.
     * @return official country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Gets the continent.
     * @return continent name
     */
    public String getContinent() {
        return continent;
    }

    /**
     * Gets the region of the country.
     * @return region
     */
    public String getRegion() {
        return region;
    }

    /**
     * Gets the surface area.
     * @return surface area in square kilometers
     */
    public double getSurfaceArea() {
        return surfaceArea;
    }

    /**
     * Gets the year of independence.
     * @return year of independence
     */
    public Integer getIndepYear() {
        return indepYear;
    }

    /**
     * Gets the population of the country.
     * @return population
     */
    public long getPopulation() {
        return population;
    }

    /**
     * Gets the life expectancy.
     * @return life expectancy
     */
    public Double getLifeExpectancy() {
        return lifeExpectancy;
    }

    /**
     * Gets the Gross National Product (GNP).
     * @return GNP
     */
    public Double getGnp() {
        return gnp;
    }

    /**
     * Gets the old Gross National Product (GNP).
     * @return previous GNP
     */
    public Double getGnpOld() {
        return gnpOld;
    }

    /**
     * Gets the local/native name of the country.
     * @return local name
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Gets the form of government.
     * @return government form
     */
    public String getGovernmentForm() {
        return governmentForm;
    }

    /**
     * Gets the head of state.
     * @return head of state
     */
    public String getHeadOfState() {
        return headOfState;
    }

    /**
     * Gets the capital city ID.
     * @return capital city ID
     */
    public Integer getCapital() {
        return capital;
    }

    /**
     * Gets the secondary country code.
     * @return secondary country code
     */
    public String getCode2() {
        return code2;
    }

}
