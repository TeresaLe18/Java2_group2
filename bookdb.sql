-- =============================================
-- Book Management System - Database Setup
-- =============================================

CREATE DATABASE IF NOT EXISTS bookdb;
USE bookdb;

CREATE TABLE IF NOT EXISTS books (
    book_id       INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(255) NOT NULL,
    author        VARCHAR(255) NOT NULL,
    category      VARCHAR(100),
    price         DOUBLE DEFAULT 0,
    quantity      INT DEFAULT 0,
    publish_year  INT
);

-- Sample data
INSERT INTO books (title, author, category, price, quantity, publish_year) VALUES
('Clean Code', 'Robert C. Martin', 'Programming', 450000, 10, 2008),
('Design Patterns', 'Gang of Four', 'Programming', 520000, 5, 1994),
('The Pragmatic Programmer', 'David Thomas', 'Programming', 380000, 8, 1999),
('Head First Java', 'Kathy Sierra', 'Programming', 350000, 15, 2005),
('Effective Java', 'Joshua Bloch', 'Programming', 490000, 7, 2018),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Algorithms', 650000, 4, 2009),
('Database System Concepts', 'Abraham Silberschatz', 'Database', 580000, 6, 2019),
('Artificial Intelligence', 'Stuart Russell', 'AI', 720000, 3, 2020);
