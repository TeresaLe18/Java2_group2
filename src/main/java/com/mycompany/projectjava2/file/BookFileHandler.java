package com.mycompany.projectjava2.file;

import com.mycompany.projectjava2.model.Book;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles CSV import/export for Book data.
 */
public class BookFileHandler {

    private static final String CSV_HEADER = "Title,Author,Category,Price,Quantity,PublishYear";

    /**
     * Export a list of books to a CSV file.
     */
    public static void exportToCSV(List<Book> books, File file) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            // Write UTF-8 BOM for Excel compatibility
            writer.write('\ufeff');
            writer.write(CSV_HEADER);
            writer.newLine();

            for (Book b : books) {
                writer.write(String.format("%s,%s,%s,%.0f,%d,%d",
                        escapeCsv(b.getTitle()),
                        escapeCsv(b.getAuthor()),
                        escapeCsv(b.getCategory()),
                        b.getPrice(),
                        b.getQuantity(),
                        b.getPublishYear()));
                writer.newLine();
            }
        }
    }

    /**
     * Import books from a CSV file. First line is assumed to be header and skipped.
     */
    public static List<Book> importFromCSV(File file) throws IOException {
        List<Book> books = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line = reader.readLine(); // skip header
            if (line != null && line.startsWith("\ufeff")) {
                // BOM detected, already skipped with header
            }

            int lineNum = 1;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String[] parts = parseCsvLine(line);
                if (parts.length < 6) {
                    throw new IOException(
                            "Invalid CSV format at line " + lineNum + ": expected 6 columns, got " + parts.length);
                }

                try {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String category = parts[2].trim();
                    double price = Double.parseDouble(parts[3].trim());
                    int quantity = Integer.parseInt(parts[4].trim());
                    int publishYear = Integer.parseInt(parts[5].trim());

                    books.add(new Book(title, author, category, price, quantity, publishYear));
                } catch (NumberFormatException e) {
                    throw new IOException("Invalid number format at line " + lineNum + ": " + e.getMessage());
                }
            }
        }
        return books;
    }

    /**
     * Simple CSV line parser that handles quoted fields.
     */
    private static String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        current.append('"');
                        i++; // skip escaped quote
                    } else {
                        inQuotes = false;
                    }
                } else {
                    current.append(c);
                }
            } else {
                if (c == '"') {
                    inQuotes = true;
                } else if (c == ',') {
                    result.add(current.toString());
                    current = new StringBuilder();
                } else {
                    current.append(c);
                }
            }
        }
        result.add(current.toString());
        return result.toArray(new String[0]);
    }

    /**
     * Escape a field for CSV output: wrap in quotes if it contains comma, quote, or
     * newline.
     */
    private static String escapeCsv(String field) {
        if (field == null)
            return "";
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }
}
