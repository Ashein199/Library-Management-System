/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.mangement.system.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.mangement.system.database.Database;

/**
 *
 * @author User
 */
public class IssueDAO {
      public void saveIssueInfo(String bookID,String memberID) throws SQLException{
           Connection conn = Database.getInstance().getConnection();
        
        String insertSql = "insert into lmsdb.issue(book_id,member_id,issue_date,renew_count) values (?,?,CURDATE(),?)";
       
        
            PreparedStatement   stmt = conn.prepareStatement(insertSql);
            
            stmt.setString(1,bookID);
            stmt.setString(2,memberID);
           
            stmt.setInt(3,0);
            
             stmt.execute();
          
      }

    public IssueInfo getIssueInfo(String bookID) throws SQLException {
     
          Connection conn = Database.getInstance().getConnection();
        
        IssueInfo issueInfo = null;
        String selectSql = "select * from lmsdb.issue where book_id=?";
        PreparedStatement stmt = conn.prepareStatement(selectSql);
        stmt.setString(1, bookID);
        ResultSet results = stmt.executeQuery();
        while (results.next()){
            
            String memberId = results.getString("member_id");
            String bookId = results.getString("book_id");
            Date issueDate = results.getDate("issue_Date");
            int renewCount = results.getInt("renew_Count");
            issueInfo = new IssueInfo(memberId,bookId,issueDate,renewCount);
        }
        
        return issueInfo;
    }
}
