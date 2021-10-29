
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import library.mangement.system.database.Database;
import library.mangement.system.model.Book;
import library.mangement.system.model.BookDAO;
import library.mangement.system.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewbookController implements Initializable {

    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXTextField publisherField;
    @FXML
    private JFXButton saveBtn;
private BookDAO bookDAO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      bookDAO = new BookDAO();
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
       Stage stage = (Stage) cancelBtn.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void saveBook(ActionEvent event) {
        String id = idField.getText();
        String title = titleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();
        
        if(id.isEmpty()|| title.isEmpty()|| author.isEmpty()|| publisher.isEmpty()){
            System.out.println("Fill all fields");
        }
        try {      
            bookDAO.addBook(new Book(id,title,author,publisher))  ;
            MessageBox.showMessage("Success", "Book was successfully added");
            Stage stage = (Stage)saveBtn.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(NewbookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
