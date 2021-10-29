
package library.mangement.system.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import library.mangement.system.model.Book;
import library.mangement.system.model.Member;



public class Database {
    
    private  Connection conn;
    private static Database db = null;
    private static String host;
    private static int port;
    private static String username;
    private static String password;
    
    private Database() throws SQLException{
        connect();
        createDatabase();
        createTables();
    }
    
    public static Database getInstance() throws SQLException{
        if (db==null){
            db= new Database();
            }
        return db;
    }
    
    public void connect() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        loadDatabaseConfig();
        conn = DriverManager.getConnection("jdbc:mysql://" +host+":"+port+"/", username, password);
    }
    
    public void createDatabase(){
        String creatSql = "create database if not exists lmsdb character set utf8 collate utf8_general_ci";
       
        try {
         Statement   stmt = conn.createStatement();
             stmt.execute(creatSql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }  
    public void disconnect() {
        if(conn!=null){
            
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    public void createTables(){
        String createBookTSql = "create table if not exists lmsdb.books (id varchar(255) primary key,title varchar(255),author varchar(255),publisher varchar(255),is_available boolean default true)";
        String createMemberTSql = "create table if not exists lmsdb.members (id varchar(255) primary key,name varchar(255),mobile varchar(255),address varchar(255))";
        String createIssueTSql = "create table if not exists lmsdb.issue (book_id varchar(255),member_id varchar(255),issue_date Date,renew_count int,foreign key (book_id) references books(id),foreign key (member_id) references members(id))";
        
        try {
         Statement   stmt = conn.createStatement();
             stmt.execute(createBookTSql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
         try {
         Statement   stmt = conn.createStatement();
             stmt.execute(createMemberTSql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
          try {
         Statement   stmt = conn.createStatement();
             stmt.execute(createIssueTSql);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }  
    public Connection getConnection(){
      return conn;
    }

    private void loadDatabaseConfig() {
        Preferences prefs = Preferences.userRoot().node("lbdb");
        host = prefs.get("host", "localhost");
        port = prefs.getInt("port", 3306);
        username = prefs.get("user", "root");
        password = prefs.get("password", "");
    }
}
