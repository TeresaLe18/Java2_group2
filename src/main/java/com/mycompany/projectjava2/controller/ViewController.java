package com.mycompany.projectjava2.controller;

import com.mycompany.projectjava2.database.BookDAO;
import com.mycompany.projectjava2.file.BookFileHandler;
import com.mycompany.projectjava2.model.Book;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

/**
 * FXML Controller class for Book Management view.
 */
public class ViewController implements Initializable {

    private final BookDAO bookDAO = new BookDAO();
    private Book selectedBook = null; // Currently selected book for editing

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // ===== Column sizing =====
        bookTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        colId.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colTitle.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.37));
        colcat.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.15));
        colAuthor.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.17));
        colPrice.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.10));
        colQty.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.08));
        colPub.prefWidthProperty().bind(bookTable.widthProperty().multiply(0.05));

        // ===== Column data binding =====
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

        // ===== Table row selection listener =====
        bookTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> {
                    if (newVal != null) {
                        selectedBook = newVal;
                        populateForm(newVal);
                    }
                });

        // ===== Load data =====
        refreshTable();
    }

    // ==================== HANDLERS ====================

    @FXML
    private void handleDisplay() {
        refreshTable();
    }

    @FXML
    private void handleAdd(ActionEvent event) {
        String error = validateInput();
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        Book newBook = getBookFromForm();
        if (bookDAO.insertBook(newBook)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book added successfully!");
            refreshTable();
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add book.");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) {
        if (selectedBook == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book from the table to update.");
            return;
        }

        String error = validateInput();
        if (error != null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", error);
            return;
        }

        Book updatedBook = getBookFromForm();
        updatedBook.setBookId(selectedBook.getBookId());

        if (bookDAO.updateBook(updatedBook)) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Book updated successfully!");
            refreshTable();
            clearForm();
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update book.");
        }
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        Book selected = bookTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book from the table to delete.");
            return;
        }

        // Confirmation dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Book");
        confirm.setContentText("Are you sure you want to delete \"" + selected.getTitle() + "\"?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (bookDAO.deleteBook(selected.getBookId())) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Book deleted successfully!");
                refreshTable();
                clearForm();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete book.");
            }
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            refreshTable();
        } else {
            bookTable.setItems(bookDAO.searchBooks(keyword));
        }
    }

    @FXML
    private void handleImport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import CSV File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showOpenDialog(bookTable.getScene().getWindow());
        if (file != null) {
            try {
                List<Book> importedBooks = BookFileHandler.importFromCSV(file);
                int successCount = 0;
                for (Book b : importedBooks) {
                    if (bookDAO.insertBook(b)) {
                        successCount++;
                    }
                }
                showAlert(Alert.AlertType.INFORMATION, "Import Complete",
                        "Successfully imported " + successCount + " / " + importedBooks.size() + " books.");
                refreshTable();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Import Error", "Failed to import CSV:\n" + e.getMessage());
            }
        }
    }

    @FXML
    private void handleExport(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export to CSV");
        fileChooser.setInitialFileName("books_export.csv");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File file = fileChooser.showSaveDialog(bookTable.getScene().getWindow());
        if (file != null) {
            try {
                BookFileHandler.exportToCSV(bookTable.getItems(), file);
                showAlert(Alert.AlertType.INFORMATION, "Export Complete",
                        "Exported " + bookTable.getItems().size() + " books to:\n" + file.getAbsolutePath());
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Export Error", "Failed to export CSV:\n" + e.getMessage());
            }
        }
    }

    // ==================== HELPER METHODS ====================

    /**
     * Populate the input form with the selected book's data.
     */
    private void populateForm(Book book) {
        titleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        catField.setText(book.getCategory());
        priceField.setText(String.valueOf(book.getPrice()));
        qtyField.setText(String.valueOf(book.getQuantity()));
        pbField.setText(String.valueOf(book.getPublishYear()));
    }

    /**
     * Create a Book object from the current form fields.
     */
    private Book getBookFromForm() {
        return new Book(
                titleField.getText().trim(),
                authorField.getText().trim(),
                catField.getText().trim(),
                Double.parseDouble(priceField.getText().trim()),
                Integer.parseInt(qtyField.getText().trim()),
                Integer.parseInt(pbField.getText().trim()));
    }

    /**
     * Validate form inputs.
     * 
     * @return Error message string if invalid, null if valid.
     */
    private String validateInput() {
        if (titleField.getText() == null || titleField.getText().trim().isEmpty()) {
            return "Title is required.";
        }
        if (authorField.getText() == null || authorField.getText().trim().isEmpty()) {
            return "Author is required.";
        }
        if (catField.getText() == null || catField.getText().trim().isEmpty()) {
            return "Category is required.";
        }

        // Price validation
        try {
            double price = Double.parseDouble(priceField.getText().trim());
            if (price < 0) {
                return "Price must be a positive number.";
            }
        } catch (NumberFormatException e) {
            return "Price must be a valid number.";
        }

        // Quantity validation
        try {
            int qty = Integer.parseInt(qtyField.getText().trim());
            if (qty < 0) {
                return "Quantity must be a positive integer.";
            }
        } catch (NumberFormatException e) {
            return "Quantity must be a valid integer.";
        }

        // Publish year validation
        try {
            int year = Integer.parseInt(pbField.getText().trim());
            if (year < 1000 || year > 2100) {
                return "Publish year must be between 1000 and 2100.";
            }
        } catch (NumberFormatException e) {
            return "Publish year must be a valid integer.";
        }

        return null; // All valid
    }

    /**
     * Clear all form fields and reset selection.
     */
    private void clearForm() {
        titleField.clear();
        authorField.clear();
        catField.clear();
        priceField.clear();
        qtyField.clear();
        pbField.clear();
        selectedBook = null;
        bookTable.getSelectionModel().clearSelection();
    }

    /**
     * Refresh the table with all books from DB.
     */
    private void refreshTable() {
        bookTable.setItems(bookDAO.getAllBooks());
    }

    /**
     * Show an alert dialog.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
