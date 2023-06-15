package model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Appointments class.
 */
public class Appointments
{
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private LocalDate createDate;
    private LocalDateTime createTime;
    private String createdBy;

    private LocalDate updateDate;
    private LocalDateTime updateTime;
    private String updatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor
     * @param appointmentId appointmentId
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startDate startDate
     * @param startTime startTime
     * @param endDate endDate
     * @param endTime endTime
     * @param customerId customerID
     * @param userId userID
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDate startDate, LocalTime    startTime, LocalDate endDate, LocalTime endTime, int customerId, int userId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    /**
     * Get appointment ID.
     * @return appointmentID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Get title.
     * @return title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title.
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get Description.
     * @return description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set description.
     * @param description description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *  Get location.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set location.
     * @param location location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get type.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set type.
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get start date.
     * @return startdate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Get Start Time.
     * @return time
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Get end date.
     * @return enddate
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Get end time.
     * @return endtime
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Get customer Id.
     * @return customerid
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Get user id.
     * @return user id
     */
    public int getUserId() {
        return userId;
    }

}
