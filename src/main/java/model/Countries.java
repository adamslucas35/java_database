package model;

/**
 * Countries class.
 */
public class Countries {
    private int countryId;
    private String country;

    /**
     * Constructor.
     * @param country country
     */
    public Countries(String country)
    {
        this.country = country;
    }

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Get country
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set country.
     * @param country country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
