/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import library.mangement.system.database.Database;

/**
 *
 * @author User
 */
public class BookDAO {
    
    public void addBook(Book book) throws SQLException{
        
        Connection conn = Database.getInstance().getConnection();
        
        String insertSql = "insert into lmsdb.books (id,title,author,publisher) values (?,?,?,?)";
       
        try {
            PreparedStatement   stmt = conn.prepareStatement(insertSql);
            
            stmt.setString(1,book.getId());
            stmt.setString(2,book.getTitle());
            stmt.setString(3,book.getAuthor());
            stmt.setString(4,book.getPublisher());
             stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }
    
    public Book getBook(String id) throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        
        Book book = null;
        String selectSql = "select * from lmsdb.books where id=?";
        PreparedStatement stmt = conn.prepareStatement(selectSql);
        stmt.setString(1, id);
        ResultSet results = stmt.executeQuery();
        while (results.next()){
            
            String title = results.getString("title");
            String author = results.getString("author");
            String publisher = results.getString("publisher");
            boolean available = results.getBoolean("is_available");
            book = new Book(id,title,author,publisher,available);
        }
        
        return book;
    }
    public List<Book> getBooks() throws SQLException{
        
        Connection conn = Database.getInstance().getConnection();
        
        List<Book> books = new ArrayList<>();
        String selectSql = "select * from lmsdb.books";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(selectSql);
        while (results.next()){
            String id = results.getString("id");
            String title = results.getString("title");
            String author = results.getString("author");
            String publisher = results.getString("publisher");
           
            books.add(new Book(id,title,author,publisher));
        }
        
        return books;
    }
    
    public void updateBook(String bookID,boolean isAvailable) throws SQLException{
          Connection conn = Database.getInstance().getConnection();
        
        String updateSql = "update lmsdb.books set is_available=? where id=?";
       
        
            PreparedStatement   stmt = conn.prepareStatement(updateSql);
            
            stmt.setBoolean(1,isAvailable);
            stmt.setString(2,bookID);
            
             stmt.execute();
        
        
    }
    
    public void deleteBook(String id) throws SQLException{
        
       Connection conn = Database.getInstance().getConnection();
        
      String deleteSql = " delete from lmsdb.books where id=? ";
      PreparedStatement stmt = conn.prepareStatement(deleteSql);
      stmt.setString(1, id);
      stmt.execute();
      
    }
}
