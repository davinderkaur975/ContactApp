/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import models.Contact;

/**
 * FXML Controller class
 *
 * @author Davinder Kaur
 */
public class ContactViewController implements Initializable {
    
    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneNumberTextField;
    @FXML private DatePicker birthday;
    @FXML private Label errorMsg;
    @FXML private ImageView imageView;
    
    
    private File imageFile;
    private boolean imageFileChanged;

    /**
     * This method change scene to Table View without adding a new user
     * All data in the form will be lost
     */
    public void cancelButtonPushed(ActionEvent event) throws IOException
    {
        SceneChanger sc = new SceneChanger();
        sc.changeScenes(event, "ContactTableView.fxml", "All Contacts");
    }
    
    /**
     * When this button is pushed it should allow the user to browse a new image file
     * And when user choose the image, it will update the view with that image
     */
    public void chooseImageButtonPushed(ActionEvent event)
    {
        //get the stage to open a new window(or stage in JavaFX)
       Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
       
       
        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        
        
        //create filters
        FileChooser.ExtensionFilter imgFilter = new FileChooser.ExtensionFilter("Image Files","*.jpg","*.png");
        fileChooser.getExtensionFilters().add(imgFilter);
                
        //configure the FileChooser to start in the users' home directory
        String userDirectoryString = System.getProperty("user.home")+"\\Pictures";
        File userDirectory = new File(userDirectoryString);
        
        //check if the userDirectory/pictures exists
        if (!userDirectory.canRead())
            userDirectory = new File(System.getProperty("user.home"));
        
        //configure the FileChooser to use the starting directory
        fileChooser.setInitialDirectory(userDirectory);
  
        
        //Open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);
        
        //check that we got a file
        if (tmpImageFile != null)
        {
            
            imageFile = tmpImageFile;
            //update the image view with the new image
            if(imageFile.isFile()){
            try
            {
                BufferedImage bufferedImage = ImageIO.read(imageFile);
                Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(img);
                imageFileChanged = true;
            }
            catch(IOException e)
            {
                System.err.println(e.getMessage());
            }
        }
        
        }
        
        
        
        
    }
        
        
  
    
    /**
     * This method will read from the scene and try to create a new instance
     */
    public void createContactButtonPushed(ActionEvent event) throws IOException, SQLException
    {
        
        try{
            Contact newContact;
            if(imageFileChanged)
            {
                newContact = new Contact( this.firstNameTextField.getText(), 
                                        this.lastNameTextField.getText(), 
                                        this.addressTextField.getText(),
                                        this.phoneNumberTextField.getText(),
                                        birthday.getValue(), imageFile);  
            }
            else
            {
                newContact = new Contact( this.firstNameTextField.getText(), 
                                        this.lastNameTextField.getText(), 
                                        this.addressTextField.getText(),
                                        this.phoneNumberTextField.getText(),
                                        birthday.getValue());  
            }
            

            //System.out.println(newContact.toString());
            errorMsg.setText("");
            newContact.insertIntoDB();
            SceneChanger sc = new SceneChanger();
            sc.changeScenes(event, "contactTableView.fxml", "All Contacts");
        }
        catch (IllegalArgumentException e)
        {
            errorMsg.setText(e.getMessage());
            
        } catch (SQLException ex)
        {
            Logger.getLogger(ContactViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthday.setValue(LocalDate.now().minusYears(18));
        imageFileChanged = false; //initially the image has not changed, use the default
        
        
        errorMsg.setText(""); //set the error message to be empty to start
        
        //load the default image
        try{
            imageFile = new File("./src/Images/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
            
        }
    }    
    
    
}