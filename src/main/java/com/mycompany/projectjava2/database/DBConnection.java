/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectjava2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WINDOWS
 */
public class DBConnection {
    //tao field Connection su dung de ket noi
    private static Connection conn; //static de ket noi 1 lan thi no se ket noi forever, lam xong thi close
//    public static void main(String[] args) {
//        DBConnection connect = new DBConnection();
//        connect.getConnect();
//    }  //test connect
    //method ket noi
    public static Connection getConnect()
    {
        if(conn == null)
        {                  
            String url = "jdbc:mysql://localhost:3306/bookdb";
            String user = "root";
            String pass = "";        

            try {
                //load driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                //ket noi
                conn = DriverManager.getConnection(url, user, pass);
                System.out.println("Connect successfully");
            } catch (ClassNotFoundException ex) {
                System.out.println("Cannot loading driver");
            } catch (SQLException ex) {
                System.out.println("Cannot connect");
            }
        }
        return conn;
    } 
    
    //method dong ket noi
    public static void closeConnection(){
        if(conn != null)
        {
            try {
                conn.close();
                System.out.println("Close connect successful");
            } catch (SQLException ex) {
                Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
