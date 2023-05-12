package connector;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String databaseUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;



    public static void openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(databaseUrl, username, password);
            System.out.println("Connection opened successful!");
        }
        catch(Exception e)
        {
            System.out.println("!!!Error: " + e);
        }
    }

    public static void closeConnection()
    {
        try {
            Class.forName(driver);
            connection.close();
            System.out.println("Connection closed successfully!");
        } catch (Exception e)
        {
            System.out.println("!!!Error: " + e);
        }
    }



}
