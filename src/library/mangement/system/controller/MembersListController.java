/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.mangement.system.model.Member;
import library.mangement.system.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author User
 */
public class MembersListController implements Initializable {

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private TableColumn<Member, String> idColumn;

    private MemberDAO memberDAO;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;
    @FXML
    private MenuItem deleteItem;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
       initColumns();
       loadTableData();
    }    

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mobileColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
    }

    private void loadTableData() {
        try {
            memberTable.getItems().addAll(memberDAO.getMembers());
        } catch (SQLException ex) {
            Logger.getLogger(MembersListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteMember(ActionEvent event) {
        
          Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        
            if (selectedMember == null){
                System.out.println("select member");
                return;
            }
            try {
            memberTable.getItems().remove(selectedMember);
            
            memberDAO.deleteMember(selectedMember.getId());
        } 
            catch (SQLException ex) {
            Logger.getLogger(BooksListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    
    }
    
}
