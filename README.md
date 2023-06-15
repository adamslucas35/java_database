# C195 Software II - Adam S Lucas - WGU
***
## Table of Contents
1. [General Description](#general-description)
   2. [Author](#author)
   3. [IDE](#ide)
   4. [How to use](#how-to-use)
   5. [Additional report](#additional-report)
   6. [mySQL](#mysql)
***
## General Description
***
C195 (Software II) is a scheduling software that pulls from a mySQL database.  "The database contains multiple tables: Appointments, Contacts, Countries, Customers, First Level Divisions, and Users."
Each of these tables has unique information about the appointment, who the appointment is for, and who to contact for the appointment. The user table has information about who can log in and access the scheduler.
Using the application all of these tables work together in order to create customers, schedule appointments and view reports about both the customers and appointments.
## Author
***
Adam S Lucas
#### WGU Number: 010241173

#### Contact:
aluc167@wgu.edu
#### Application Version:
QAM2 — QAM2 TASK 1: JAVA APPLICATION DEVELOPMENT
SOFTWARE II - ADVANCED JAVA CONCEPTS — C195
PRFA — QAM2
#### Date:
2032-06-15
## IDE
***
####Built using:
IntelliJ IDEA 2023.1 (Ultimate Edition)
Build #IU-231.8109.175, built on March 28, 2023
For educational use only.
Java Runtime Version: 17.0.6+10-b829.5 amd64
Windows 11.0
JAVAFX Version: 17.0.7 Windows_x64
#### VM runs with: 
IntelliJ IDEA Community Edition 2021.1.3"
Build #IC-211.7628.21, built on June 30, 2021
Runtime version: 11.0.11+9-b1341.60 amd64
Windows 10 10.0


## How to use
***
In order to use this application, first, extract all from zip folder, you will then have another folder, inside this folder there is another folder with the same name, this is the folder you will want to open in your IDE.

Then to run to application, once inside the IDE on the top bar about 3/4ths to the right there is a play button, also Shift+F10 will run the application, or you can click on the run button next to the Main class near the bottom of the file.
Once you click Run, you may need to modify the Run Configuration. To do this, first make sure that the SQL Connector and JavaFX are connected to the Java Library, then select JAVA 17 in the module configuration. You can do this by clicking FILE, Project Structure, Libraries, then add the Java Libraries, they can both be found on the desktop, add the .jar file from the "my-sql-connector-java-8.0.25" folder, and the lib folder from the "openjx-11.0.2_windows-x64_bin-sdk" folder. (You may get an error in the console when running, this is just a warning saying the project has a newer version of JavaFX - running Java17 - this is not an issue however, and the program will still run)

*If there is no play button, make sure you opened the right folder (the folder inside the extracted folder, not the extracted folder).*

##### Once the application is running, you can now login, then access either the customers page, the appointments page, or the reports page. With the customers or appointments page you can view, create, modify, or delete either customers or appointments. On the reports page you can see specific information about customers or contacts. 

## Additional Report
***
The additional report I created shows two tables, the first table shows duration of all appointments, with the following columns the appointment ID, Start time, End time and Duration of the appointment. The second table has just one column: the average duration of all appointments. This report can help show the length of appointments, so you can decide if appointments should be shorter or longer to help the customer.

## mySQL
***
The mySQL connection is the following version:
My build used the my-sql-connector-java-8.0.33 as this was the available version for download.
The VM uses: my-sql-connector-java-8.0.25 this does not cause any errors as nothing dramatic has been changed from the two.



