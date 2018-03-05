/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Davinder Kaur
 */  
public class LaunchContact extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    
     @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ContactTableView.fxml"));
        
        Scene scene = new Scene(root);
       
        stage.setTitle("Create Contact");
        stage.setScene(scene);
        stage.show();
    }
}
