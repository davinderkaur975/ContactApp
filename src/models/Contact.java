/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author Davinder Kaur
 */
public class Contact {
    private String firstName, lastName, address, phoneNumber;
    private LocalDate birthday;
    private File imageFile; 
    private int contactID;

    public Contact(String firstName, String lastName, String address, String phoneNumber, LocalDate birthday) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        setBirthday(birthday);
        setImageFile(new File("./src/Images/defaultImage.png"));
    }

    public Contact(String firstName, String lastName, String address, String phoneNumber, LocalDate birthday, File imageFile) throws IOException {
        this(firstName, lastName, address, phoneNumber, birthday);
        setImageFile(imageFile);
        copyImageFile();
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        if(contactID >= 0)
            this.contactID = contactID;
        else
            throw new IllegalArgumentException("Contact ID must be greater than equal to 0");
    }
    

    public LocalDate getBirthday() {
        return birthday;
    }

    
    /**
     * This will validate that the contact is between the ages of 10 and 100
     * @param birthday 
     */
    public void setBirthday(LocalDate birthday) {
        int age = Period.between(birthday, LocalDate.now()).getYears();
        
        if(age >= 10 && age <= 100){
           this.birthday = birthday; 
        }
        else{
            throw new IllegalArgumentException("Persons must be 10-100 years of age");
        }
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
    
    /**
     * This method copy the image to the Images folder
     * @throws IOException 
     */
    public void copyImageFile() throws IOException
    {
        //create a new path to copy image to local directory
        Path sourcePath = imageFile.toPath();
        
        Path targetPath = Paths.get("./src/Images/"+ imageFile.getName());
        
        //Copy file to new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        
        //update the image file to point to new File
        imageFile = new File(targetPath.toString());
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    
    /**
     * area code     city     house
     * XXX          -XXX     -XXXX 
     * @param phoneNumber 
     */
    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber.matches("[2-9]\\d{2}[-.]?\\d{3}[-.]\\d{4}")){
            this.phoneNumber = phoneNumber;   
        }
        else{
            throw new IllegalArgumentException("Phone number should be in the correct format i.e. NXX-XXX-XXXX");   
        }
        this.phoneNumber = phoneNumber;
    }
    
  
    /**
     * This method will return a string with the persons first name, last name, address
     */
    public String toString()
    {
        return String.format("%s %s lives %s and is %d years old", firstName, lastName, address,Period.between(birthday, LocalDate.now()).getYears());
    }
  
    public void insertIntoDB() throws SQLException
    {
         Connection conn=null;
        PreparedStatement ps = null;
        
        try
        {
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + "contact", "root", "");
            
            //2. Create a String to hold the SQL statement.  ? will be our
            //place holders
            String sql = "INSERT INTO contacts (firstName, lastName, address, phoneNumber, birthday, imageFile) VALUES (?,?,?,?,?,?)";
            
            //3.  Prepare the query
            ps = conn.prepareStatement(sql);
            
            //convert the birthday into a sql date
            Date db = Date.valueOf(birthday);
            //4. Bind the parameters
            ps.setString(1, this.firstName);
            ps.setString(2, this.lastName);
            ps.setString(3, this.address);
            ps.setString(4, this.phoneNumber);
            ps.setDate(5, db);
            ps.setString(6, this.imageFile.getName());
            
            //5. execute the sql statement
            ps.execute();
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
        finally
        {
            if (conn != null)
                conn.close();
            if (ps != null)
                ps.close();
        }
    }
}

