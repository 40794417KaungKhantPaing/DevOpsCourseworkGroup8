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

    /**
     * Sets the country code.
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the country name.
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * Sets the continent.
     * @param continent
     */
    public void setContinent(String continent) {
        this.continent = continent;
    }

    /**
     * Sets the region.
     * @param region
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Sets the surface area.
     * @param surfaceArea
     */
    public void setSurfaceArea(double surfaceArea) {
        this.surfaceArea = surfaceArea;
    }

    /**
     * Sets the year of independence.
     * @param indepYear
     */
    public void setIndepYear(Integer indepYear) {
        this.indepYear = indepYear;
    }

    /**
     * Sets the population.
     * @param population
     */
    public void setPopulation(long population) {
        this.population = population;
    }

    /**
     * Sets the life expectancy.
     * @param lifeExpectancy
     */
    public void setLifeExpectancy(Double lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    /**
     * Sets the Gross National Product (GNP).
     * @param gnp
     */
    public void setGnp(Double gnp) {
        this.gnp = gnp;
    }

    /**
     * Sets the old Gross National Product (GNP).
     * @param gnpOld
     */
    public void setGnpOld(Double gnpOld) {
        this.gnpOld = gnpOld;
    }

    /**
     * Sets the local/native name of the country.
     * @param localName
     */
    public void setLocalName(String localName) {
        this.localName = localName;
    }

    /**
     * Sets the form of government.
     * @param governmentForm
     */
    public void setGovernmentForm(String governmentForm) {
        this.governmentForm = governmentForm;
    }

    /**
     * Sets the head of state.
     * @param headOfState
     */
    public void setHeadOfState(String headOfState) {
        this.headOfState = headOfState;
    }

    /**
     * Sets the capital city ID.
     * @param capital
     */
    public void setCapital(Integer capital) {
        this.capital = capital;
    }

    /**
     * Sets the secondary country code.
     * @param code2
     */
    public void setCode2(String code2) {
        this.code2 = code2;
    }
}
