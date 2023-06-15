package model;

/**
 * Contacts class.
 */
public class Contacts
{

    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructor
     * @param contactId contactID
     * @param contactName contactName
     * @param email email
     */
    public Contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Constructor.
     * @param contactName contactName.
     */
    public Contacts(String contactName)
    {
        this.contactName = contactName;
    }


    /**
     * Get contact name.
     * @return name
     */
    public String getContactName() {
        return contactName;
    }

}
