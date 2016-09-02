CREATE TABLE salary
(
    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    salary DOUBLE NOT NULL,
    bonus DOUBLE NOT NULL,
    increment DOUBLE NOT NULL
);
CREATE UNIQUE INDEX salary_id_uindex ON salary (id);

INSERT INTO salary (value, bonus, increment) VALUES (1000.00, 500.00, 100.00);
INSERT INTO salary (value, bonus, increment) VALUES (2000.00, 450.50, 234.10);
INSERT INTO salary (value, bonus, increment) VALUES (3000.00, 650.45, 200.80);
INSERT INTO salary (value, bonus, increment) VALUES (4000.00, 789.32, 500.67);