CREATE TABLE db_servers(
    id INT AUTO_INCREMENT PRIMARY KEY,
    jdbc_url varchar(200),
    jdbc_username varchar(50),
    jdbc_password varchar(50)
);