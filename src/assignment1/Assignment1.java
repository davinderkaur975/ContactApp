/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import models.Contact;

/**
 *
 * @author Davinder Kaur
 */
public class Assignment1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SQLException {
        // TODO code application logic here
        Contact contact = new Contact("Komal", "Aujla", "115 DunsmoreLane", "705-643-2437", LocalDate.of(1998, Month.DECEMBER, 26),
                new File("./src/Images/komal.jpg")); 
        System.out.println(contact.toString());
       contact.insertIntoDB();
    }
    
}
