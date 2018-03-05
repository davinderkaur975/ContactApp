DROP DATABASE contact;
CREATE DATABASE contact;
USE contact;

CREATE TABLE contacts
(
	contactID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    address VARCHAR(255),
    phoneNumber VARCHAR(12),
    birthday DATE,
    imageFile VARCHAR(100)
);


SELECT * FROM contacts;

INSERT INTO contacts (firstName, lastName, address, phoneNumber, birthday, imageFile) VALUES
('Davinder', 'Kaur', '111 Duckworth Street', '705-643-2416', '1998-06-11', 'some file'),
('Jaret', 'Wright', '121 Dunsmore Lane', '705-643-2314', '1983-02-18', 'some file');

SELECT * FROM contacts;


