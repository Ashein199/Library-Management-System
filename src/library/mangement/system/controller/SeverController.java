/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author User
 */
public class SeverController implements Initializable {

    @FXML
    private TextField hostNameField;
    @FXML
    private Spinner<Integer> portSpinner;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private JFXButton saveBtn;
    @FXML
    private JFXButton cancelBtn;

    private Preferences prefs;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        prefs = Preferences.userRoot().node("lbdb");
        String host = prefs.get("host", "localhost");
        int port = prefs.getInt("port", 3306);
        String user = prefs.get("user", "root");
        String password = prefs.get("password", "");
        
        hostNameField.setText(host);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3320, port);
        portSpinner.setValueFactory(valueFactory);
        nameField.setText(user);
        passwordField.setText(password);
    }    

    @FXML
    private void saveServerConfig(ActionEvent event) {
        
        prefs.put("host", hostNameField.getText());
        prefs.putInt("port", portSpinner.getValue());
        prefs.put("user", nameField.getText());
        prefs.put("password", passwordField.getText());
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        
    }
    
}
