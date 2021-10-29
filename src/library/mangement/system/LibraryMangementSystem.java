/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.mangement.system.database.Database;
import library.mangement.system.util.MessageBox;

/**
 *
 * @author User
 */
public class LibraryMangementSystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        try{
          Database db = Database.getInstance();  
        }
        catch(SQLException e){
            MessageBox.showAndWaitErrorMessage("Db Error", "Cannot connect to database");
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/library/mangement/system/view/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
