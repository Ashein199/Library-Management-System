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
import library.mangement.system.model.Member;
import library.mangement.system.model.MemberDAO;
import library.mangement.system.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author User
 */
public class NewmemberController implements Initializable {

    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField mobileField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXButton saveBtn;

    private MemberDAO memberDAO;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       memberDAO = new MemberDAO();
    }    

    @FXML
    private void closeWindow(ActionEvent event) {
         Stage stage = (Stage) cancelBtn.getScene().getWindow();

        stage.close();
    }

    @FXML
    private void saveMember(ActionEvent event) {
        String id = idField.getText();
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        
        if(id.isEmpty()|| name.isEmpty()|| mobile.isEmpty()|| address.isEmpty()){
            System.out.println("Fill all fields");
        }
        try {
            memberDAO.addMember(new Member(id,name,mobile,address));
            MessageBox.showMessage("Success", "Member was successfully added");
            Stage stage = (Stage)saveBtn.getScene().getWindow();
            stage.close();
            System.out.println("Successed");
        } catch (SQLException ex) {
            System.out.println("Failed");
            Logger.getLogger(NewmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
