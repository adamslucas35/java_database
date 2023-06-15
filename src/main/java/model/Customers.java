package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Customers class.
 */
public class Customers
{
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private LocalDate customerCreateDate;
    private LocalDateTime customerCreateTime;
    private String customerCreatedBy;
    private LocalDate customerLastUpdateDate;
    private LocalDateTime customerLastUpdateTime;
    private String customerLastUpdatedBy;
    private String customerDivision;
    private String customerCountry;

    /**
     * Constructor.
     * @param customerId ID
     * @param customerName name
     * @param customerAddress address
     * @param customerPostalCode postal
     * @param customerPhone phone
     * @param customerCreateDate date
     * @param customerCreateTime time
     * @param customerCreatedBy by
     * @param customerLastUpdateDate updatedate
     * @param customerLastUpdateTime updatetime
     * @param customerLastUpdatedBy updateby
     * @param customerDivision division
     * @param customerCountry country
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, LocalDate customerCreateDate, LocalDateTime customerCreateTime, String customerCreatedBy, LocalDate customerLastUpdateDate, LocalDateTime customerLastUpdateTime, String customerLastUpdatedBy, String customerDivision, String customerCountry) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerCreateDate = customerCreateDate;
        this.customerCreateTime = customerCreateTime;
        this.customerCreatedBy = customerCreatedBy;
        this.customerLastUpdateDate = customerLastUpdateDate;
        this.customerLastUpdateTime = customerLastUpdateTime;
        this.customerLastUpdatedBy = customerLastUpdatedBy;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
    }

    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, String customerDivision, String customerCountry)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivision = customerDivision;
        this.customerCountry = customerCountry;
    }

    /**
     * Get customer id
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * get customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * set customer name
     * @param customerName customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Get customer address.
     * @return address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Get postal code
     * @return postal
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Get phone
     * @return phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Get division
     * @return customer division
     */
    public String getCustomerDivision() {
        return customerDivision;
    }

}
