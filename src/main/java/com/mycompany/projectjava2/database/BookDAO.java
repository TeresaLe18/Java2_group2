package com.mycompany.projectjava2.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.mycompany.projectjava2.model.Book;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for Book CRUD operations.
 */
public class BookDAO {

    Connection cn = DBConnection.getConnect();

    // ==================== READ ====================

    public ObservableList<Book> getAllBooks() {
        ObservableList<Book> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM books";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // ==================== CREATE ====================

    public boolean insertBook(Book book) {
        String query = "INSERT INTO books (title, author, category, price, quantity, publish_year) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, book.getQuantity());
            ps.setInt(6, book.getPublishYear());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // ==================== UPDATE ====================

    public boolean updateBook(Book book) {
        String query = "UPDATE books SET title=?, author=?, category=?, price=?, quantity=?, publish_year=? WHERE book_id=?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setDouble(4, book.getPrice());
            ps.setInt(5, book.getQuantity());
            ps.setInt(6, book.getPublishYear());
            ps.setInt(7, book.getBookId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // ==================== DELETE ====================

    public boolean deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE book_id=?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    // ==================== SEARCH ====================

    public ObservableList<Book> searchBooks(String keyword) {
        ObservableList<Book> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR category LIKE ?";
        try (PreparedStatement ps = cn.prepareStatement(query)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // ==================== HELPER ====================

    private Book mapRow(ResultSet rs) throws SQLException {
        return new Book(
                rs.getInt("book_id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getInt("quantity"),
                rs.getInt("publish_year"));
    }
}
