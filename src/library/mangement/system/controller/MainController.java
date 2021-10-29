/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.mangement.system.model.Book;
import library.mangement.system.model.BookDAO;
import library.mangement.system.model.IssueDAO;
import library.mangement.system.model.IssueInfo;
import library.mangement.system.model.Member;
import library.mangement.system.model.MemberDAO;

/**
 *
 * @author User
 */
public class MainController implements Initializable {

    @FXML
    private JFXButton newBookBtn;
    @FXML
    private MenuItem closeItem;
    @FXML
    private JFXButton newMemberBtn;

    @FXML
    private JFXTextField bookIDField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    private Text publisherText;
    @FXML
    private JFXButton booksBtn;
    @FXML
    private JFXButton membersBtn;
    
    
    private BookDAO bookDAO;
    @FXML
    private JFXTextField memberIDField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
 private MemberDAO memberDAO;
 private IssueDAO issueDAO;
    @FXML
    private JFXButton issueBtn;
    @FXML
    private Text availableText;
    
    private boolean isBookInfoSeatched = false;
    private boolean isMemberInfoSeatched = false;
    @FXML
    private JFXTextField issueBookIDField;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    private Text dateText;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submissionBtn;
    @FXML
    private Text issueDateText;
    @FXML
    private Text renewCountText;
    @FXML
    private MenuItem serverItem;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         bookDAO = new BookDAO();
         memberDAO = new MemberDAO();
         issueDAO = new IssueDAO();

    }

    @FXML
    private void loadNewBookWindow(ActionEvent event) throws IOException {
        loadWindow("New Book", "/library/mangement/system/view/newbook.fxml");
    }

    @FXML
    private void loadNewMemberWindow(ActionEvent event) throws IOException {
        loadWindow("New Member", "/library/mangement/system/view/newmember.fxml");
    }

    @FXML
    private void loadBookListsWindow(ActionEvent event) throws IOException {
        loadWindow("Book Lists", "/library/mangement/system/view/booksList.fxml");
    }

    @FXML
    private void loadMemberListsWindow(ActionEvent event) throws IOException {
        loadWindow("Member Lists", "/library/mangement/system/view/membersList.fxml");
    }

    private void loadWindow(String Title, String url) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(newBookBtn.getScene().getWindow());
        stage.setScene(scene);
        stage.setTitle(Title);
        stage.show();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.setTitle("Confirm");
        alert.setContentText("Are u sure?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.YES) {
            Stage stage = (Stage) newBookBtn.getScene().getWindow();

            stage.close();
        }
    }

    @FXML
    private void searchBookInfo(ActionEvent event) {
        
        clearText();
        String bookID = bookIDField.getText();
        
        if(bookID.isEmpty()){
            return ;
        }
        
        try {
          Book  book = bookDAO.getBook(bookID);
          if (book!=null){
            titleText.setText(book.getTitle());
            authorText.setText(book.getAuthor());
            boolean available = book.isAvailable();
            String availableStr = available?"Available":"Not Available";
            availableText.setText(availableStr);
            isBookInfoSeatched = true;

        }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private void clearText() {
      
          titleText.setText("-");  
          authorText.setText("-");  
          availableText.setText("-");
    }

    @FXML
    private void searchMemberInfo(ActionEvent event) {
        removeText();
        String memberID = memberIDField.getText();
        
        if(memberID.isEmpty()){
            return ;
        }
        
        try {
          Member  member = memberDAO.getMember(memberID);
          if (member!=null){
            nameText.setText(member.getName());
            mobileText.setText(member.getMobile());
            addressText.setText(member.getAddress());
            isMemberInfoSeatched = true;
          }
        }
         catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
     private void removeText() {
      
          nameText.setText("-");  
          mobileText.setText("-");  
          addressText.setText("-");
     }

    @FXML
    private void issueBook(ActionEvent event) {
        String bookID = bookIDField.getText();
        String memberID = memberIDField.getText();
        
        if(bookID.isEmpty() || memberID.isEmpty()){
            System.out.println("Plz search book and member info");
            return;
        }
        
        if(!isBookInfoSeatched && !isMemberInfoSeatched){
            System.out.println("plz search");
            return;
        }
        
        isBookInfoSeatched = false;
        isMemberInfoSeatched = false;
        
        if(availableText.getText().equals("Not Available")){
            System.out.println("This book is already issued");
            return;
        }
        
        try {
            issueDAO.saveIssueInfo(bookID,memberID);
            bookDAO.updateBook(bookID,false);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void searchIssueInfo(ActionEvent event) {
        
        String bookID = issueBookIDField.getText();
        clearIssueInfo();
        
        try {
            IssueInfo issueInfo = issueDAO.getIssueInfo(bookID);
            if (issueInfo!=null){
           
            Book book = bookDAO.getBook(issueInfo.getBookID());
            
            bTitleText.setText(book.getTitle());
            bAuthorText.setText(book.getAuthor());
            bPublisherText.setText(book.getPublisher());
            
            Member member = memberDAO.getMember(issueInfo.getMemberID());
            
            mNameText.setText(member.getName());
            mMobileText.setText(member.getMobile());
            mAddressText.setText(member.getAddress());
            
            issueDateText.setText("Issue Date"+issueInfo.getIssueDate());
            renewCountText.setText("Renew Count"+issueInfo.getRenewCount());
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private void clearIssueInfo() {
         
      
          bTitleText.setText("-");  
          bAuthorText.setText("-");  
          bPublisherText.setText("-");
          mNameText.setText("-");
          mMobileText.setText("-");
          mAddressText.setText("-");
          issueDateText.setText("-");
          renewCountText.setText("-");
    }

    @FXML
    private void loadDatabaseServerWindow(ActionEvent event) throws IOException {
        
        
            loadWindow("Database Server", "/library/mangement/system/view/server.fxml");
       
     
        
    }
    }


   
    

