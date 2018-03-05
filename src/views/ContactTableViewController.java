/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Contact;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Davinder Kaur
 */
public class ContactTableViewController implements Initializable {

    @FXML private TableView<Contact> contactTable;
    @FXML private TableColumn<Contact, Integer> contactIDColumn;
    @FXML private TableColumn<Contact, String> firstNameColumn;
    @FXML private TableColumn<Contact, String> lastNameColumn;
    @FXML private TableColumn<Contact, LocalDate> birthdayColumn;
    @FXML private TableColumn<Contact, String> addressColumn;
    @FXML private TableColumn<Contact, String> phoneNumberColumn;
    
    
     /**
     * This method will switch to the new contact scene when button is pushed
     */
    public void newContactButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "ContactView.fxml", "Create New Contact");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // configure the table columns
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<Contact, Integer>("contactID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("lastName"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<Contact, LocalDate>("birthday"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("address"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<Contact, String>("phoneNumber"));
        
        try{
            loadContacts();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }  
    
    /**
     * This method will load the contacts from the database and load them into the TableView object
     */
    public void loadContacts() throws SQLException
    {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
             //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + "contact", "root", "");
            
            //2. create a statement object
            statement = conn.createStatement();
            
            //3. createthe SQL query
            resultSet = statement.executeQuery("SELECT * FROM contacts");
            
            //4. create contact objects from each record
            while(resultSet.next())
            {
                Contact newContact = new Contact(resultSet.getString("firstName"),
                                                  resultSet.getString("lastName"),
                                                  resultSet.getString("address"),
                                                  resultSet.getString("phoneNumber"),
                                                  resultSet.getDate("birthday").toLocalDate());
                newContact.setContactID(resultSet.getInt("ContactID"));
                newContact.setImageFile(new File(resultSet.getString("imageFile")));
                contacts.add(newContact);
            }
            contactTable.getItems().addAll(contacts);
        }
        
        catch(Exception e){
            System.err.println(e.getMessage());

        }
        finally{
            if(conn != null)
                conn.close();
            if(statement != null)
                statement.close();
            if(resultSet != null)
                resultSet.close();
        }
    }
    
    
}

