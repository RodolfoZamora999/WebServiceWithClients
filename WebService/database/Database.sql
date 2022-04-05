# Mariadb database
DROP DATABASE IF EXISTS webservice_db;
CREATE DATABASE webservice_db;
USE webservice_db;

CREATE TABLE IF NOT EXISTS role_tb
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user_tb
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255),
    image_profile TEXT,
    id_role INT UNSIGNED,
    FOREIGN KEY (id_role) REFERENCES role_tb(id)
);

CREATE TABLE IF NOT EXISTS contact_tb
(
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(30),
    email VARCHAR(255),
    image_profile TEXT,
    id_user INT UNSIGNED,
    FOREIGN KEY (id_user) REFERENCES user_tb(id)

);

# Insert default roles
INSERT INTO role_tb (name, description) VALUES ('USER', 'Basic permissions'),
                                               ('ADMIN', 'Administrator permissions');