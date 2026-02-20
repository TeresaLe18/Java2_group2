/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectjava2.controller;
import com.mycompany.projectjava2.database.BookDAO;
import com.mycompany.projectjava2.model.Book;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author WINDOWS
 */
public class ViewController implements Initializable {
    BookDAO book = new BookDAO();

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField searchField;
    @FXML
    private TextField catField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField pbField;
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, Double> colPrice;
    @FXML
    private TableColumn<Book, String> colcat;
    @FXML
    private TableColumn<Book, Integer> colQty;
    @FXML
    private TableColumn<Book, Integer> colPub;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colTitle.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.37));
        colcat.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.15));
        colAuthor.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.17));
        colPrice.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.10));
        colQty.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colPub.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.05));
        
        colId.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        colId.setStyle("-fx-alignment: CENTER;");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colcat.setCellValueFactory(new PropertyValueFactory<>("category"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setStyle("-fx-alignment: CENTER-RIGHT;");
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setStyle("-fx-alignment: CENTER;");
        colPub.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
        colPub.setStyle("-fx-alignment: CENTER;");
        handleDisplay();
    }
    
    @FXML
    private void handleDisplay() {      
        bookTable.setItems(book.getAllBooks());
    }

    @FXML
    private void handleAdd(ActionEvent event) {
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
    }

    @FXML
    private void handleSearch(ActionEvent event) {
    }

    @FXML
    private void handleImport(ActionEvent event) {
    }

    @FXML
    private void handleExport(ActionEvent event) {
    }

    @FXML
    private void handleDelete(ActionEvent event) {
    }
    
    

}
