package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private int customerDivisionId;

    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, LocalDate customerCreateDate, LocalDateTime customerCreateTime, String customerCreatedBy, LocalDate customerLastUpdateDate, LocalDateTime customerLastUpdateTime, String customerLastUpdatedBy, int customerDivisionId) {
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
        this.customerDivisionId = customerDivisionId;
    }

    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone)
    {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
    }
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getCustomerCreateDate() {
        return customerCreateDate;
    }

    public void setCustomerCreateDate(LocalDate customerCreateDate) {
        this.customerCreateDate = customerCreateDate;
    }

    public LocalDateTime getCustomerCreateTime() {
        return customerCreateTime;
    }

    public void setCustomerCreateTime(LocalDateTime customerCreateTime) {
        this.customerCreateTime = customerCreateTime;
    }

    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    public void setCustomerCreatedBy(String customerCreatedBy) {
        this.customerCreatedBy = customerCreatedBy;
    }

    public LocalDate getCustomerLastUpdateDate() {
        return customerLastUpdateDate;
    }

    public void setCustomerLastUpdateDate(LocalDate customerLastUpdateDate) {
        this.customerLastUpdateDate = customerLastUpdateDate;
    }

    public LocalDateTime getCustomerLastUpdateTime() {
        return customerLastUpdateTime;
    }

    public void setCustomerLastUpdateTime(LocalDateTime customerLastUpdateTime) {
        this.customerLastUpdateTime = customerLastUpdateTime;
    }

    public String getCustomerLastUpdatedBy() {
        return customerLastUpdatedBy;
    }

    public void setCustomerLastUpdatedBy(String customerLastUpdatedBy) {
        this.customerLastUpdatedBy = customerLastUpdatedBy;
    }

    public int getCustomerDivisionId() {
        return customerDivisionId;
    }

    public void setCustomerDivisionId(int customerDivisionId) {
        this.customerDivisionId = customerDivisionId;
    }



}
