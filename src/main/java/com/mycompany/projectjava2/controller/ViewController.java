/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.projectjava2.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author WINDOWS
 */
public class ViewController implements Initializable {

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<?> bookTable;
    @FXML
    private TableColumn<?, ?> colId;
    @FXML
    private TableColumn<?, ?> colTitle;
    @FXML
    private TableColumn<?, ?> colAuthor;
    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TextField catField;
    @FXML
    private TextField qtyField;
    @FXML
    private TextField pbField;
    @FXML
    private TableColumn<?, ?> colcat;
    @FXML
    private TableColumn<?, ?> colQty;
    @FXML
    private TableColumn<?, ?> colPub;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colTitle.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.32));
        colcat.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.15));
        colAuthor.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.17));
        colPrice.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.10));
        colQty.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colPub.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.10));
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
