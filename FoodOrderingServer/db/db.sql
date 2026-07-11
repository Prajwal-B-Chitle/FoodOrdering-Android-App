DROP DATABASE IF EXISTS onlinefoodordering_db;
CREATE DATABASE onlinefoodordering_db;
USE onlinefoodordering_db;

CREATE TABLE user(
    uid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    email VARCHAR(50),
    password VARCHAR(100),
    mobile CHAR(10)
);

CREATE TABLE food(
    fid INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(20),
    price DECIMAL(7,2),
    description VARCHAR(300),
    image VARCHAR(100)
);

CREATE TABLE orders (
    oid INT PRIMARY KEY AUTO_INCREMENT,
    uid INT,
    odate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deldate TIMESTAMP,
    total_amount DECIMAL(9,2),
    FOREIGN KEY (uid) REFERENCES user(uid) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orderdetails(
    oid INT,
    fid INT,
    quantity INT,
    FOREIGN KEY (oid) REFERENCES orders(oid) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (fid) REFERENCES food(fid) ON DELETE CASCADE ON UPDATE CASCADE
);