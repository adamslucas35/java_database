package model;

public class Countries {
    private int countryId;
    private String country;

    public Countries(String country)
    {
        this.country = country;
    }

    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
