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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.mangement.system.database.Database;
import library.mangement.system.model.Book;
import library.mangement.system.model.BookDAO;



public class BooksListController implements Initializable {

    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, String> idColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;

    private BookDAO bookDAO;
    @FXML
    private MenuItem deleteItem;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
       initColumns();
       loadTableData();
    }    

    private void initColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
    }

    private void loadTableData() {
        try {
            bookTable.getItems().addAll(bookDAO.getBooks());
        } catch (SQLException ex) {
            Logger.getLogger(BooksListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteBook(ActionEvent event) {
        
        
            Book selectedBook = bookTable.getSelectionModel().getSelectedItem();
            
            if (selectedBook == null){
                System.out.println("select book");
                return;
            }
            try {
            bookTable.getItems().remove(selectedBook);
            
            bookDAO.deleteBook(selectedBook.getId());
        } catch (SQLException ex) {
            Logger.getLogger(BooksListController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}