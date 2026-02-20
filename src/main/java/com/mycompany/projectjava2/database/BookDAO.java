/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projectjava2.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mycompany.projectjava2.model.Book;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WINDOWS
 */
public class BookDAO {
    //SQL + mapping
    Connection cn = DBConnection.getConnect();
    Statement st;
    ResultSet rs;
    
    public ObservableList<Book> getAllBooks() {
        
        ObservableList<Book> list = FXCollections.observableArrayList();

        //viet cau lenh truy van
        String query = "SELECT * FROM books";
        try {
            //tao statement
            st = cn.createStatement();
            //thuc thi - execute
            rs = st.executeQuery(query);
            //doc du lieu
            while(rs.next())
            {
                list.add(new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getInt("publish_year")
                ));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
