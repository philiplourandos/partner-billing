-- Partner billing tables
CREATE TABLE DB_SERVERS(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    jdbc_url varchar(200) NOT NULL,
    jdbc_username varchar(50) NOT NULL,
    jdbc_password varchar(50) NOT NULL,
    site_id INT NOT NULL
) ENGINE=InnoDB;
