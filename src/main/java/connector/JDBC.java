package connector;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class JDBC
{
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String databaseUrl = protocol + vendor + location + databaseName;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "sqlUser";
    private static final String password = "Passw0rd!";
    public static Connection connection;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");



    public static void openConnection()
    {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(databaseUrl, username, password);
            System.out.println("Connection opened successful!");
            Statement statement = connection.createStatement();
            statement.execute("SET time_zone = '+00:00'");
        }
        catch(Exception e)
        {
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public static LocalDateTime convertUTCtoLocal(LocalDateTime utcDateTime)
    {
        return utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDateTime convertLocaltoUTC(LocalDateTime localDateTime)
    {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneOffset.UTC);
        return utcDateTime.toLocalDateTime();
    }

    public static ZonedDateTime convertLocaltoEastern(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return zonedDateTime.withZoneSameInstant(ZoneId.of("US/Eastern"));
    }

    public static Connection getConnection()
    {
        return connection;
    }



}
