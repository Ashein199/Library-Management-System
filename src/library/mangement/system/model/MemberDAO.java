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
public class MemberDAO {
    
     public void addMember(Member member) throws SQLException{
    
     Connection conn = Database.getInstance().getConnection();
    
     String insertSql = "insert into lmsdb.members (id,name,mobile,address) values (?,?,?,?)";
       
        try {
            PreparedStatement   stmt = conn.prepareStatement(insertSql);
            
            stmt.setString(1,member.getId());
            stmt.setString(2,member.getName());
            stmt.setString(3,member.getMobile());
            stmt.setString(4,member.getAddress());
             stmt.execute();
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
     }
         public Member getMember(String id) throws SQLException{
          Connection conn = Database.getInstance().getConnection();
        
        Member member = null;
        String selectSql = "select * from lmsdb.members where id=?";
        PreparedStatement stmt = conn.prepareStatement(selectSql);
        stmt.setString(1, id);
        ResultSet results = stmt.executeQuery();
        while (results.next()){
            
            String name = results.getString("name");
            String mobile = results.getString("mobile");
            String address = results.getString("address");
            member = new Member(id,name,mobile,address);
        }
        
        return member;
    }
    public List<Member> getMembers() throws SQLException{
        Connection conn = Database.getInstance().getConnection();
        
        List<Member> members = new ArrayList<>();
        String selectSql = "select * from lmsdb.members";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(selectSql);
        while (results.next()){
            String id = results.getString("id");
            String name = results.getString("name");
            String mobile = results.getString("mobile");
            String address = results.getString("address");
            members.add(new Member(id,name,mobile,address));
    }
        return members;
    }
    
    public void updateMember(Member member){
        
    }
    
    public void deleteMember(String id) throws SQLException{
          Connection conn = Database.getInstance().getConnection();
        
      String deleteSql = " delete from lmsdb.members where id=? ";
      PreparedStatement stmt = conn.prepareStatement(deleteSql);
      stmt.setString(1, id);
      stmt.execute();
    }
}
