CREATE SCHEMA cdrjhb;
CREATE SCHEMA cdrcpt;
CREATE SCHEMA cdrdurb;
CREATE TABLE db_servers(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, jdbc_url varchar(200) NOT NULL, jdbc_username varchar(50) NOT NULL, jdbc_password varchar(50) NOT NULL, site_id INT NOT NULL);
CREATE TABLE cdr (calldate datetime DEFAULT '2000-00-00 00:00:00' NOT NULL, clid varchar(80) NOT NULL, src varchar(80) NOT NULL, dst varchar(80) NOT NULL, dcontext varchar(80) NOT NULL, channel varchar(80) NOT NULL, dstchannel varchar(80) NOT NULL, lastapp varchar(80) NOT NULL, lastdata varchar(80) NOT NULL, duration int DEFAULT '0' NOT NULL, billsec int DEFAULT '0' NOT NULL, disposition varchar(45) NOT NULL, amaflags int DEFAULT '0' NOT NULL, accountcode varchar(20) NOT NULL, uniqueid varchar(32) NOT NULL, userfield varchar(255) NOT NULL, cost varchar(10), site_id int, id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE cdr_query (id int NOT NULL AUTO_INCREMENT, site_id int, query text, active bit, created datetime, partner_id varchar(25), PRIMARY KEY (id));
CREATE TABLE discipline_group (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(25), site_id int, group_id int, PRIMARY KEY (id));
CREATE TABLE partner (id int NOT NULL AUTO_INCREMENT, site_id int, name varchar(25), description varchar(50), accountcode int, group_id int, discipline_id int, PRIMARY KEY (id));
CREATE TABLE partner_group (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(50), site_id int, PRIMARY KEY (id));
CREATE TABLE site (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(50), outputFile varchar(100) NOT NULL, PRIMARY KEY (id));
CREATE TABLE cdrjhb.cdr (calldate datetime DEFAULT '0000-00-00 00:00:00' NOT NULL, clid varchar(80) NOT NULL, src varchar(80) NOT NULL, dst varchar(80) NOT NULL, dcontext varchar(80) NOT NULL, channel varchar(80) NOT NULL, dstchannel varchar(80) NOT NULL, lastapp varchar(80) NOT NULL, lastdata varchar(80) NOT NULL, duration int DEFAULT '0' NOT NULL, billsec int DEFAULT '0' NOT NULL, disposition varchar(45) NOT NULL, amaflags int DEFAULT '0' NOT NULL, accountcode varchar(20) NOT NULL, uniqueid varchar(32) NOT NULL, userfield varchar(255) NOT NULL, cost varchar(10), site_id int, id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE cdrcpt.cdr (calldate datetime DEFAULT '0000-00-00 00:00:00' NOT NULL, clid varchar(80) NOT NULL, src varchar(80) NOT NULL, dst varchar(80) NOT NULL, dcontext varchar(80) NOT NULL, channel varchar(80) NOT NULL, dstchannel varchar(80) NOT NULL, lastapp varchar(80) NOT NULL, lastdata varchar(80) NOT NULL, duration int DEFAULT '0' NOT NULL, billsec int DEFAULT '0' NOT NULL, disposition varchar(45) NOT NULL, amaflags int DEFAULT '0' NOT NULL, accountcode varchar(20) NOT NULL, uniqueid varchar(32) NOT NULL, userfield varchar(255) NOT NULL, cost varchar(10), site_id int, id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE cdrdurb.cdr (calldate datetime DEFAULT '0000-00-00 00:00:00' NOT NULL, clid varchar(80) NOT NULL, src varchar(80) NOT NULL, dst varchar(80) NOT NULL, dcontext varchar(80) NOT NULL, channel varchar(80) NOT NULL, dstchannel varchar(80) NOT NULL, lastapp varchar(80) NOT NULL, lastdata varchar(80) NOT NULL, duration int DEFAULT '0' NOT NULL, billsec int DEFAULT '0' NOT NULL, disposition varchar(45) NOT NULL, amaflags int DEFAULT '0' NOT NULL, accountcode varchar(20) NOT NULL, uniqueid varchar(32) NOT NULL, userfield varchar(255) NOT NULL, cost varchar(10), site_id int, id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE aspivia (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Extension varchar(15), Pbx_Date_Time datetime, Duration int, Account_Code int(2), Digits varchar(20), Cost decimal(10,2), Destination varchar(30), Carrier varchar(30), Attribute int(2), Site_id int NOT NULL);
ALTER TABLE aspivia ADD CONSTRAINT fk_site_id FOREIGN KEY (Site_id) REFERENCES site(id);
INSERT INTO db_servers(jdbc_url, jdbc_username, jdbc_password, site_id) VALUES('jdbc:h2:mem:test;SCHEMA=cdrjhb;DB_CLOSE_DELAY=-1', 'sa', '', 1);
INSERT INTO db_servers(jdbc_url, jdbc_username, jdbc_password, site_id) VALUES('jdbc:h2:mem:test;SCHEMA=cdrcpt;DB_CLOSE_DELAY=-1', 'sa', '', 2);
INSERT INTO db_servers(jdbc_url, jdbc_username, jdbc_password, site_id) VALUES('jdbc:h2:mem:test;SCHEMA=cdrdurb;DB_CLOSE_DELAY=-1', 'sa', '', 1);
INSERT INTO site (id, name, description, outputFile) VALUES (1, 'CPT', 'Cape Town', 'test-classes/Di_Mhg-CTAccounts_12345_1234');
INSERT INTO site (id, name, description, outputFile) VALUES (2, 'JHB', 'Braamfontein', 'test-classes/Di_Mhg-JBAccounts_12345_1234');
INSERT INTO partner_group (id, name, description, site_id) VALUES (4, 'ACCESS HEALTH', 'Access Health', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (5, 'MHRS', 'Mhrs', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (6, 'MEDSCHEME', 'Medscheme', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (7, 'NETCARE', 'Netcare', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (8, 'EUROP ASSIST', 'Europ Assitance', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (9, 'MEDIPOST', 'Medipost', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (10, 'MH', 'Metropolitan Health', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (11, 'OPTICLEAR', 'Opticlear', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (12, 'PINNACLE', 'Pinnacle', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (13, 'PRIMECURE', 'Primecure', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (14, 'UNIVERSAL', 'Universal', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (15, 'ACCESS HEALTH', 'Access Health', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (16, 'MHRS', 'Mhrs', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (17, 'MEDSCHEME', 'Medscheme', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (18, 'NETCARE', 'Netcare', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (19, 'EUROP ASSIST', 'Europ Assitance', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (20, 'MEDIPOST', 'Medipost', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (21, 'MH', 'Metropolitan Health', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (22, 'OPTICLEAR', 'Opticlear', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (23, 'PINNACLE', 'Pinnacle', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (24, 'PRIMECURE', 'Primecure', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (25, 'UNIVERSAL', 'Universal', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (26, 'TELEDIRECT', 'Teledirect', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (27, 'TELEDIRECT', 'Teledirect', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (29, 'MyCare', 'MyCare', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (30, 'MyCare', 'MyCare', 2);
INSERT INTO partner_group (id, name, description, site_id) VALUES (31, 'EOH', 'EOH', 1);
INSERT INTO partner_group (id, name, description, site_id) VALUES (32, 'EOH', 'EOH', 2);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (1, 1, 'AIDS', 'AIDS', 1, 5, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (2, 2, 'AIDS', 'AIDS', 1, 16, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (3, 1, 'CT Claims', 'CT Claims', 2, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (4, 2, 'CT Claims', 'CT Claims', 2, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (5, 1, 'Care Programs', 'Care Programs', 3, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (6, 2, 'Care Programs', 'Care Programs', 3, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (7, 1, 'Emergency', 'Emergency', 4, 7, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (8, 2, 'Emergency', 'Emergency', 4, 18, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (9, 1, 'Meds Management', 'Meds Management', 5, 5, 20);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (10, 2, 'Meds Management', 'Meds Management', 5, 16, 19);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (11, 1, 'Medipost', 'Medipost', 6, 9, 22);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (12, 2, 'Medipost', 'Medipost', 6, 20, 21);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (13, 1, 'Health Line', 'Health Line', 7, 5, 9);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (14, 2, 'Health Line', 'Health Line', 7, 16, 10);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (15, 1, 'Ambulatory PMB', 'Ambulatory PMB', 8, 5, 3);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (16, 2, 'Ambulatory PMB', 'Ambulatory PMB', 8, 16, 4);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (17, 1, 'Oncology', 'Oncology', 9, 5, 18);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (18, 2, 'Oncology', 'Oncology', 9, 16, 17);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (19, 1, 'Sapphire/Beryl', 'Sapphire/Beryl', 10, 5, 27);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (20, 2, 'Sapphire/Beryl', 'Sapphire/Beryl', 10, 16, 28);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (21, 1, 'Pinnacle', 'Pinnacle', 11, 12, 14);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (22, 2, 'Pinnacle', 'Pinnacle', 11, 23, 13);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (23, 1, 'PrimeCure', 'PrimeCure', 12, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (24, 2, 'PrimeCure', 'PrimeCure', 12, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (25, 1, 'Friends of GEMS', 'Friends of GEMS', 13, 10, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (26, 2, 'Friends of GEMS', 'Friends of GEMS', 13, 21, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (27, 1, 'PreAuth', 'PreAuth', 14, 5, 12);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (28, 2, 'PreAuth', 'PreAuth', 14, 16, 11);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (29, 1, 'Maternity', 'Maternity', 15, 10, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (30, 2, 'Maternity', 'Maternity', 15, 21, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (31, 1, 'REO Networks', 'REO Networks', 16, 10, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (32, 2, 'REO Networks', 'REO Networks', 16, 21, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (33, 1, 'Medcor/Agility', 'Medcor/Agility', 17, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (34, 2, 'Medcor/Agility', 'Medcor/Agility', 17, null, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (35, 1, 'Sapphire/Beryl(IP)', 'Sapphire/Beryl(IP)', 18, 5, 27);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (36, 2, 'Sapphire/Beryl(IP)', 'Sapphire/Beryl(IP)', 18, 16, 28);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (37, 1, 'Meds Management (IP)', 'Meds Management (IP)', 19, 5, 20);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (38, 2, 'Meds Management (IP)', 'Meds Management (IP)', 19, 16, 19);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (39, 1, 'PreAuth(IP)', 'PreAuth(IP)', 20, 5, 12);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (40, 2, 'PreAuth(IP)', 'PreAuth(IP)', 20, 16, 11);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (41, 1, 'Medipost (IP)', 'Medipost (IP)', 21, 9, 22);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (42, 1, 'Health Line (IP)', 'Health Line (IP)', 22, 5, 9);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (43, 2, 'Health Line (IP)', 'Health Line (IP)', 21, 16, 10);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (44, 1, 'Oncology (IP)', 'Oncology (IP)', 23, 5, 18);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (45, 2, 'Oncology (IP)', 'Oncology (IP)', 22, 16, 17);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (46, 1, 'Pinnacle (IP)', 'Pinnacle (IP)', 24, 12, 14);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (47, 2, 'Pinnacle (IP)', 'Pinnacle (IP)', 23, 23, 13);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (48, 1, 'REO Networks (IP)', 'REO Networks (IP)', 25, 10, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (49, 2, 'REO Networks (IP)', 'REO Networks (IP)', 24, 21, null);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (50, 1, 'Europ Assist', 'Europ Assist', 26, 8, 7);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (51, 2, 'Europ Assist', 'Europ Assist', 25, 19, 8);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (52, 1, 'Contributions (IP)', 'Contributions (IP)', 27, 6, 5);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (53, 2, 'Contributions (IP)', 'Contributions (IP)', 27, 17, 6);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (54, 1, 'Access Health', 'Access Health', 28, 4, 1);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (55, 2, 'Access Health', 'Access Health', 28, 15, 2);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (56, 1, 'Primecure Dental', 'Primecure Dental', 29, 13, 23);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (57, 2, 'Primecure Dental', 'Primecure Dental', 29, 24, 24);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (58, 1, 'PrimeCure HIV', 'PrimeCure HIV', 30, 13, 25);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (59, 2, 'PrimeCure HIV', 'PrimeCure HIV', 30, 24, 26);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (60, 1, 'OptiClear', 'OptiClear', 31, 11, 16);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (61, 2, 'OptiClear', 'OptiClear', 31, 22, 15);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (62, 1, 'Universal Chronic', 'Universal Chronic', 32, 14, 29);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (63, 2, 'Universal Chronic', 'Universal Chronic', 32, 25, 30);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (64, 1, 'Universal HIV', 'Universal HIV', 33, 14, 31);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (65, 2, 'Universal HIV', 'Universal HIV', 33, 25, 32);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (66, 1, 'Contributions', 'Contributions', 34, 6, 5);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (67, 2, 'Contributions', 'Contributions', 34, 17, 6);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (68, 1, 'Ambulatory PMB (IP)', 'Ambulatory PMB (IP)', 35, 5, 3);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (69, 2, 'Ambulatory PMB (IP)', 'Ambulatory PMB (IP)', 35, 16, 4);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (70, 1, 'Medipost (COM)', 'Medipost (COM)', 39, 9, 22);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (71, 2, 'Medipost (COM)', 'Medipost (COM)', 39, 20, 21);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (72, 1, 'Universal Chronic (COM)', 'Universal Chronic (COM)', 37, 14, 29);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (73, 2, 'Universal Chronic (COM)', 'Universal Chronic (COM)', 37, 25, 30);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (74, 1, 'Universal HIV (COM)', 'Universal HIV (COM)', 38, 14, 31);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (75, 2, 'Universal HIV (COM)', 'Universal HIV (COM)', 38, 25, 32);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (76, 1, 'OptiClear (COM)', 'OptiClear (COM)', 36, 11, 16);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (77, 2, 'OptiClear (COM)', 'OptiClear (COM)', 36, 22, 15);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (78, 1, 'Teledirect', 'Teledirect', 40, 26, 33);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (79, 2, 'Teledirect', 'Teledirect', 40, 27, 34);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (80, 1, 'Primecure Dental (COM)', 'Primecure Dental (COM)', 41, 13, 23);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (81, 2, 'Primecure Dental (COM)', 'Primecure Dental (COM)', 41, 24, 24);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (82, 1, 'Primecure HIV (COM)', 'Primecure HIV (COM)', 42, 13, 25);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (83, 2, 'Primecure HIV (COM)', 'Primecure HIV (COM)', 42, 24, 26);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (84, 1, 'Ambulatory PMB (COM)', 'Ambulatory PMB (COM)', 43, 5, 3);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (85, 2, 'Ambulatory PMB (COM)', 'Ambulatory PMB (COM)', 43, 16, 4);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (86, 1, 'Health Line (COM)', 'Health Line (COM)', 44, 5, 9);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (87, 2, 'Health Line (COM)', 'Health Line (COM)', 44, 16, 10);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (88, 1, 'Meds Management (COM)', 'Meds Management (COM)', 45, 5, 20);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (89, 2, 'Meds Management (COM)', 'Meds Management (COM)', 45, 16, 19);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (90, 1, 'Oncology (COM)', 'Oncology (COM)', 46, 5, 18);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (91, 2, 'Oncology (COM)', 'Oncology (COM)', 46, 16, 17);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (92, 1, 'PreAuth(COM)', 'PreAuth(COM)', 47, 5, 12);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (93, 2, 'PreAuth(COM)', 'PreAuth(COM)', 47, 16, 11);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (94, 1, 'Sapphire/Beryl(COM)', 'Sapphire/Beryl(COM)', 48, 5, 27);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (95, 2, 'Sapphire/Beryl(COM)', 'Sapphire/Beryl(COM)', 48, 16, 28);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (96, 1, 'Contributions (COM)', 'Contributions (COM)', 49, 6, 5);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (97, 2, 'Contributions (COM)', 'Contributions (COM)', 49, 17, 6);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (98, 1, 'MyCare', 'MyCare', 50, 29, 37);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (99, 2, 'MyCare', 'MyCare', 50, 30, 38);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (100, 1, 'GP Specialist', 'GP Specialist', 53, 5, 35);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (101, 1, 'GP Specialist (IP)', 'GP Specialist (IP)', 51, 5, 35);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (102, 1, 'GP Specialist (COM)', 'GP Specialist (COM)', 52, 5, 35);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (103, 2, 'GP Specialist', 'GP Specialist', 53, 16, 36);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (104, 2, 'GP Specialist (IP)', 'GP Specialist (IP)', 51, 16, 36);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (105, 2, 'GP Specialist (COM)', 'GP Specialist (COM)', 52, 16, 36);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (106, 1, 'EOH Health Program', 'EOH Health Program', 54, 31, 39);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (107, 1, 'EOH Health Program (COM)', 'EOH Health Program (COM)', 55, 31, 39);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (108, 1, 'EOH Health Program (IP)', 'EOH Health Program (IP)', 56, 31, 39);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (109, 2, 'EOH Health Program', 'EOH Health Program', 54, 32, 40);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (110, 2, 'EOH Health Program (COM)', 'EOH Health Program (COM)', 55, 32, 40);
INSERT INTO partner (id, site_id, name, description, accountcode, group_id, discipline_id) VALUES (111, 2, 'EOH Health Program (IP)', 'EOH Health Program (IP)', 56, 32, 40);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 13:23:49', '0848000887', '0848000887', 'europassist', 'outbound-europassist', 'Zap/40-1', 'IAX2/jhblvgw05_is_voip_out-29085', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 77, 33, 'ANSWERED', 3, '25', '', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049969);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 13:44:33', '0785211425', '0785211425', 'europassist', 'outbound-europassist', 'Zap/71-1', 'IAX2/jhblvgw05_is_voip_out-26572', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 246, 211, 'ANSWERED', 3, '25', '1444218273.303031', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049970);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 13:45:32', '0794209962', '0794209962', 'europassist', 'outbound-europassist', 'Zap/9-1', 'IAX2/jhblvgw05_is_voip_out-17332', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 73, 22, 'ANSWERED', 3, '25', '1444218332.303047', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049971);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 13:48:03', '0713855125', '0713855125', 'europassist', 'outbound-europassist', 'Zap/31-1', 'IAX2/jhblvgw05_is_voip_out-22591', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 35, 8, 'ANSWERED', 3, '25', '1444218483.303090', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049972);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:03:59', '0110426734', '0110426734', 'europassist', 'outbound-europassist', 'Zap/44-1', 'IAX2/jhblvgw05_is_voip_out-29237', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 99, 53, 'ANSWERED', 3, '25', '1444219439.303353', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049973);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:13:10', '0718535061', '0718535061', 'europassist', 'outbound-europassist', 'Zap/15-1', 'IAX2/jhblvgw05_is_voip_out-17071', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 63, 29, 'ANSWERED', 3, '25', '1444219990.303498', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049974);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:16:49', '0797009666', '0797009666', 'europassist', 'outbound-europassist', 'Zap/54-1', 'IAX2/jhblvgw05_is_voip_out-20454', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 391, 361, 'ANSWERED', 3, '25', '1444220209.303562', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049975);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:24:28', '0721908190', '0721908190', 'europassist', 'outbound-europassist', 'Zap/53-1', 'IAX2/jhblvgw05_is_voip_out-23948', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 137, 83, 'ANSWERED', 3, '25', '1444220668.303729', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049976);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:28:27', '0792078302', '0792078302', 'europassist', 'outbound-europassist', 'Zap/14-1', 'IAX2/jhblvgw05_is_voip_out-26330', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 87, 37, 'ANSWERED', 3, '25', '1444220907.303807', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049977);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:34:06', '0822044308', '0822044308', 'europassist', 'outbound-europassist', 'Zap/28-1', 'IAX2/jhblvgw05_is_voip_out-18490', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 93, 65, 'ANSWERED', 3, '25', '1444221246.303915', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049978);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:49:56', '0127610500', '0127610500', 'europassist', 'outbound-europassist', 'Zap/17-1', 'IAX2/jhblvgw05_is_voip_out-17101', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 47, 3, 'ANSWERED', 3, '25', '1444222196.304248', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049979);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 14:51:43', '0436055240', '0436055240', 'europassist', 'outbound-europassist', 'Zap/30-1', 'IAX2/jhblvgw05_is_voip_out-31972', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 43, 10, 'ANSWERED', 3, '25', '1444222303.304294', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049980);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:17:51', '0338166200', '0338166200', 'europassist', 'outbound-europassist', 'Zap/3-1', 'IAX2/jhblvgw05_is_voip_out-30025', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 228, 204, 'ANSWERED', 3, '25', '1444223871.304790', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049981);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:19:24', '0333944742', '0333944742', 'europassist', 'outbound-europassist', 'Zap/64-1', 'IAX2/jhblvgw05_is_voip_out-29991', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 28, 1, 'ANSWERED', 3, '25', '1444223964.304820', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049982);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:32:10', '0842444259', '0842444259', 'europassist', 'outbound-europassist', 'Zap/53-1', 'IAX2/jhblvgw05_is_voip_out-24045', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 51, 5, 'ANSWERED', 3, '25', '1444224730.305062', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049983);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:38:18', '0319096876', '0319096876', 'europassist', 'outbound-europassist', 'Zap/57-1', 'IAX2/jhblvgw05_is_voip_out-19382', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 277, 21, 'ANSWERED', 3, '25', '1444225098.305180', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049984);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:47:34', '0176485125', '0176485125', 'europassist', 'outbound-europassist', 'Zap/9-1', 'IAX2/jhblvgw05_is_voip_out-26011', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 33, 1, 'ANSWERED', 3, '25', '1444225654.305345', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049985);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:48:49', '0849354439', '0849354439', 'europassist', 'outbound-europassist', 'Zap/68-1', 'IAX2/jhblvgw05_is_voip_out-31840', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 152, 101, 'ANSWERED', 3, '25', '1444225729.305368', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049986);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:50:26', '0607241847', '0607241847', 'europassist', 'outbound-europassist', 'Zap/44-1', 'IAX2/jhblvgw05_is_voip_out-22877', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 92, 43, 'ANSWERED', 3, '25', '1444225826.305388', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049987);
INSERT INTO cdrjhb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 15:59:34', '0735456813', '0735456813', 'europassist', 'outbound-europassist', 'Zap/82-1', 'IAX2/jhblvgw05_is_voip_out-28069', 'Dial', 'IAX2/jhblvgw05_is_voip_out/0119918007', 65, 34, 'ANSWERED', 3, '25', '1444226374.305533', 'IVR_EUROPASSIST_ENG_MHVOIS', null, 2, 12049988);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:11:52', '0137681113', '0137681113', 'benefit_networks', 'outbound-benefit_networks', 'Zap/24-1', 'IAX2/medscheme-15860', 'Dial', 'IAX2/medscheme/0214661977', 399, 203, 'ANSWERED', 3, '18', '1444198312.295369', 'IVR_BENEFIT_NETWORKS_ENG', null, 2, 12050778);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:12:53', '0436434395', '0436434395', 'benefit_networks', 'outbound-benefit_networks', 'Zap/42-1', 'IAX2/medscheme-11600', 'Dial', 'IAX2/medscheme/0214661977', 254, 146, 'ANSWERED', 3, '18', '1444198373.295399', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050779);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:18:40', '0873509884', '0873509884', 'benefit_networks', 'outbound-benefit_networks', 'Zap/30-1', 'IAX2/medscheme-2764', 'Dial', 'IAX2/medscheme/0214661977', 102, 61, 'ANSWERED', 3, '18', '1444198720.295512', 'IVR_BENEFIT_NETWORKS_ENG', null, 2, 12050780);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:34:23', '0152914242', '0152914242', 'benefit_networks', 'outbound-benefit_networks', 'Zap/85-1', 'IAX2/medscheme-12789', 'Dial', 'IAX2/medscheme/0214661977', 38, 3, 'ANSWERED', 3, '18', '1444199663.295831', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050781);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:35:36', '0152914242', '0152914242', 'benefit_networks', 'outbound-benefit_networks', 'Zap/77-1', 'IAX2/medscheme-2161', 'Dial', 'IAX2/medscheme/0214661977', 142, 106, 'ANSWERED', 3, '18', '1444199736.295863', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050782);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:44:06', '0114915171', '0114915171', 'benefit_networks', 'outbound-benefit_networks', 'Zap/41-1', 'IAX2/medscheme-13069', 'Dial', 'IAX2/medscheme/0214661977', 560, 452, 'ANSWERED', 3, '18', '1444200246.296066', 'IVR_BENEFIT_NETWORKS_ENG', null, 2, 12050783);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:47:15', '0182931640', '0182931640', 'benefit_networks', 'outbound-benefit_networks', 'Zap/30-1', 'IAX2/medscheme-3336', 'Dial', 'IAX2/medscheme/0214661977', 189, 157, 'ANSWERED', 3, '18', '1444200435.296150', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050784);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:47:38', '0517540263', '0517540263', 'benefit_networks', 'outbound-benefit_networks', 'Zap/2-1', 'IAX2/medscheme-3836', 'Dial', 'IAX2/medscheme/0214661977', 153, 115, 'ANSWERED', 3, '18', '1444200458.296167', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050785);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:50:58', '0100071562', '0100071562', 'benefit_networks', 'outbound-benefit_networks', 'Zap/57-1', 'IAX2/medscheme-8548', 'Dial', 'IAX2/medscheme/0214661977', 178, 146, 'ANSWERED', 3, '18', '1444200658.296233', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050786);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:54:03', '0459331029', '0459331029', 'benefit_networks', 'outbound-benefit_networks', 'Zap/17-1', 'IAX2/medscheme-13253', 'Dial', 'IAX2/medscheme/0214661977', 224, 178, 'ANSWERED', 3, '18', '1444200843.296319', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050787);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:57:04', '0517530701', '0517530701', 'benefit_networks', 'outbound-benefit_networks', 'Zap/45-1', 'IAX2/medscheme-4821', 'Dial', 'IAX2/medscheme/0214661977', 164, 131, 'ANSWERED', 3, '18', '1444201024.296388', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050788);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:59:48', '0333980056', '0333980056', 'benefit_networks', 'outbound-benefit_networks', 'Zap/86-1', 'IAX2/medscheme-14726', 'Dial', 'IAX2/medscheme/0214661977', 243, 201, 'ANSWERED', 3, '18', '1444201188.296454', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050789);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:04:21', '0474914455', '0474914455', 'benefit_networks', 'outbound-benefit_networks', 'Zap/15-1', 'IAX2/medscheme-15695', 'Dial', 'IAX2/medscheme/0214661977', 184, 139, 'ANSWERED', 3, '18', '1444201461.296566', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050790);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:05:36', '0152915031', '0152915031', 'benefit_networks', 'outbound-benefit_networks', 'Zap/59-1', 'IAX2/medscheme-13016', 'Dial', 'IAX2/medscheme/0214661977', 149, 112, 'ANSWERED', 3, '18', '1444201536.296588', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050791);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:05:56', '0152971162', '0152971162', 'benefit_networks', 'outbound-benefit_networks', 'Zap/44-1', 'IAX2/medscheme-1341', 'Dial', 'IAX2/medscheme/0214661977', 131, 92, 'ANSWERED', 3, '18', '1444201556.296599', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050792);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:06:28', '0187712602', '0187712602', 'benefit_networks', 'outbound-benefit_networks', 'Zap/30-1', 'IAX2/medscheme-1765', 'Dial', 'IAX2/medscheme/0214661977', 596, 544, 'ANSWERED', 3, '18', '1444201588.296616', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050793);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:10:44', '0178263206', '0178263206', 'benefit_networks', 'outbound-benefit_networks', 'Zap/71-1', 'IAX2/medscheme-15892', 'Dial', 'IAX2/medscheme/0214661977', 186, 126, 'ANSWERED', 3, '18', '1444201844.296733', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050794);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:12:58', '0156325188', '0156325188', 'benefit_networks', 'outbound-benefit_networks', 'Zap/84-1', 'IAX2/medscheme-10366', 'Dial', 'IAX2/medscheme/0214661977', 163, 106, 'ANSWERED', 3, '18', '1444201978.296795', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050795);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:15:41', '0117137702', '0117137702', 'benefit_networks', 'outbound-benefit_networks', 'Zap/15-1', 'IAX2/medscheme-7381', 'Dial', 'IAX2/medscheme/0214661977', 283, 227, 'ANSWERED', 3, '18', '1444202141.296856', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050796);
INSERT INTO cdrcpt.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:30:53', '0358313429', '0358313429', 'benefit_networks', 'outbound-benefit_networks', 'Zap/3-1', 'IAX2/medscheme-4432', 'Dial', 'IAX2/medscheme/0214661977', 169, 133, 'ANSWERED', 3, '18', '1444203053.297242', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050797);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:35:26', '"0125293000" <0125293000>', '0125293000', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002fe76', 'IAX2/medscheme-10387', 'Dial', 'IAX2/medscheme/0214661977', 209, 156, 'ANSWERED', 3, '18', '1444199726.565284', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.49', 2, 12050908);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:37:39', '"0123160200" <0123160200>', '0123160200', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT15-0002fe8f', 'IAX2/medscheme-9436', 'Dial', 'IAX2/medscheme/0214663457', 272, 270, 'ANSWERED', 3, '18', '1444199859.565360', 'AGENT_BENEFIT_NETWORKS_NDE', '0.86', 2, 12050909);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:42:41', '0318220450', '0318220450', 'benefit_networks', 'outbound-benefit_networks', 'Zap/87-1', 'IAX2/medscheme-6302', 'Dial', 'IAX2/medscheme/0214661977', 2225, 2124, 'ANSWERED', 3, '18', '1444200161.565604', 'IVR_BENEFIT_NETWORKS_ENG', null, 2, 12050910);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:44:00', '0213912661', '0213912661', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002ff06', 'IAX2/medscheme-6530', 'Dial', 'IAX2/medscheme/0214661977', 187, 136, 'ANSWERED', 3, '18', '1444200240.565682', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.43', 2, 12050911);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:44:29', '"0329460927" <0329460927>', '0329460927', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT12-0002ff0e', 'IAX2/medscheme-1322', 'Dial', 'IAX2/medscheme/0214661977', 292, 290, 'ANSWERED', 3, '18', '1444200269.565708', 'AGENT_BENEFIT_NETWORKS_ENG', '0.92', 2, 12050912);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:44:39', '"0123418244" <0123418244>', '0123418244', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002ff10', 'IAX2/medscheme-3465', 'Dial', 'IAX2/medscheme/0214661977', 414, 383, 'ANSWERED', 3, '18', '1444200279.565714', 'IVR_BENEFIT_NETWORKS_SB_SP', '1.21', 2, 12050913);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:45:58', '0215726171', '0215726171', 'benefit_networks', 'outbound-benefit_networks', 'Zap/28-1', 'IAX2/medscheme-2637', 'Dial', 'IAX2/medscheme/0214661977', 174, 121, 'ANSWERED', 3, '18', '1444200358.565749', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050914);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:46:31', '"0514329158" <0514329158>', '0514329158', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002ff25', 'IAX2/medscheme-11688', 'Dial', 'IAX2/medscheme/0214661977', 172, 131, 'ANSWERED', 3, '18', '1444200391.565777', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.41', 2, 12050915);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:51:52', '"0358380010" <0358380010>', '0358380010', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT12-0002ff7d', 'IAX2/medscheme-5302', 'Dial', 'IAX2/medscheme/0214663497', 190, 188, 'ANSWERED', 3, '18', '1444200712.566031', 'AGENT_BENEFIT_NETWORKS_REO_SP', '0.6', 2, 12050916);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:53:17', '"0178194191" <0178194191>', '0178194191', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT13-0002ff93', 'IAX2/medscheme-15675', 'Dial', 'IAX2/medscheme/0214661977', 196, 194, 'ANSWERED', 3, '18', '1444200797.566098', 'AGENT_BENEFIT_NETWORKS_ENG', '0.61', 2, 12050917);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:53:39', '0317073773', '0317073773', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002ff95', 'IAX2/medscheme-13408', 'Dial', 'IAX2/medscheme/0214661977', 296, 213, 'ANSWERED', 3, '18', '1444200819.566113', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.67', 2, 12050918);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:55:47', '"0587130648" <0587130648>', '0587130648', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT13-0002ffb8', 'IAX2/medscheme-5056', 'Dial', 'IAX2/medscheme/0214661977', 256, 253, 'ANSWERED', 3, '18', '1444200947.566223', 'AGENT_BENEFIT_NETWORKS_ENG', '0.8', 2, 12050919);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:57:32', '0153554808', '0153554808', 'benefit_networks', 'outbound-benefit_networks', 'Zap/11-1', 'IAX2/medscheme-4626', 'Dial', 'IAX2/medscheme/0214661977', 226, 180, 'ANSWERED', 3, '18', '1444201052.566292', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050920);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 08:59:24', '0219870880', '0219870880', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-0002fff7', 'IAX2/medscheme-8712', 'Dial', 'IAX2/medscheme/0214661977', 300, 265, 'ANSWERED', 3, '18', '1444201164.566387', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.84', 2, 12050921);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:03:42', '"0137955477" <0137955477>', '0137955477', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT13-0003003a', 'IAX2/medscheme-14875', 'Dial', 'IAX2/medscheme/0214661977', 354, 352, 'ANSWERED', 3, '18', '1444201422.566561', 'AGENT_BENEFIT_NETWORKS_ENG', '1.11', 2, 12050922);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:04:19', '0152674115', '0152674115', 'benefit_networks', 'outbound-benefit_networks', 'Zap/52-1', 'IAX2/medscheme-5716', 'Dial', 'IAX2/medscheme/0214661977', 326, 297, 'ANSWERED', 3, '18', '1444201459.566581', 'IVR_BENEFIT_NETWORKS_SB_SP', null, 2, 12050923);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:08:01', '"0233489060" <0233489060>', '0233489060', 'benefit_networks', 'outbound-benefit_networks', 'SIP/JHBLEXT15-00030077', 'IAX2/medscheme-8411', 'Dial', 'IAX2/medscheme/0214661977', 432, 430, 'ANSWERED', 3, '18', '1444201681.566739', 'AGENT_BENEFIT_NETWORKS_ENG', '1.36', 2, 12050924);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:12:45', '0219340977', '0219340977', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-000300e4', 'IAX2/medscheme-5026', 'Dial', 'IAX2/medscheme/0214661977', 235, 194, 'ANSWERED', 3, '18', '1444201965.566982', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.61', 2, 12050925);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:13:00', '0312014652', '0312014652', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-000300e8', 'IAX2/medscheme-5679', 'Dial', 'IAX2/medscheme/0214661977', 735, 643, 'ANSWERED', 3, '18', '1444201980.566995', 'IVR_BENEFIT_NETWORKS_SB_SP', '2.04', 2, 12050926);
INSERT INTO cdrdurb.cdr (calldate, clid, src, dst, dcontext, channel, dstchannel, lastapp, lastdata, duration, billsec, disposition, amaflags, accountcode, uniqueid, userfield, cost, site_id, id) VALUES ('2015-10-07 09:18:29', '0213724514', '0213724514', 'benefit_networks', 'outbound-benefit_networks', 'SIP/VOIS-00030154', 'IAX2/medscheme-6826', 'Dial', 'IAX2/medscheme/0214661977', 154, 105, 'ANSWERED', 3, '18', '1444202309.567239', 'IVR_BENEFIT_NETWORKS_SB_SP', '0.33', 2, 12050927);

-- Autogenerated: do not edit this file

CREATE TABLE BATCH_JOB_INSTANCE  (
	JOB_INSTANCE_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT ,
	JOB_NAME VARCHAR(100) NOT NULL,
	JOB_KEY VARCHAR(32) NOT NULL,
	constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
) ;

CREATE TABLE BATCH_JOB_EXECUTION  (
	JOB_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT  ,
	JOB_INSTANCE_ID BIGINT NOT NULL,
	CREATE_TIME TIMESTAMP NOT NULL,
	START_TIME TIMESTAMP DEFAULT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
	constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
	references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
	JOB_EXECUTION_ID BIGINT NOT NULL ,
	TYPE_CD VARCHAR(6) NOT NULL ,
	KEY_NAME VARCHAR(100) NOT NULL ,
	STRING_VAL VARCHAR(250) ,
	DATE_VAL TIMESTAMP DEFAULT NULL ,
	LONG_VAL BIGINT ,
	DOUBLE_VAL DOUBLE PRECISION ,
	IDENTIFYING CHAR(1) NOT NULL ,
	constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION  (
	STEP_EXECUTION_ID BIGINT IDENTITY NOT NULL PRIMARY KEY ,
	VERSION BIGINT NOT NULL,
	STEP_NAME VARCHAR(100) NOT NULL,
	JOB_EXECUTION_ID BIGINT NOT NULL,
	START_TIME TIMESTAMP NOT NULL ,
	END_TIME TIMESTAMP DEFAULT NULL ,
	STATUS VARCHAR(10) ,
	COMMIT_COUNT BIGINT ,
	READ_COUNT BIGINT ,
	FILTER_COUNT BIGINT ,
	WRITE_COUNT BIGINT ,
	READ_SKIP_COUNT BIGINT ,
	WRITE_SKIP_COUNT BIGINT ,
	PROCESS_SKIP_COUNT BIGINT ,
	ROLLBACK_COUNT BIGINT ,
	EXIT_CODE VARCHAR(2500) ,
	EXIT_MESSAGE VARCHAR(2500) ,
	LAST_UPDATED TIMESTAMP,
	constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
	STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
	references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
) ;

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
	JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
	SHORT_CONTEXT VARCHAR(2500) NOT NULL,
	SERIALIZED_CONTEXT LONGVARCHAR ,
	constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
	references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
) ;

CREATE SEQUENCE BATCH_STEP_EXECUTION_SEQ;
CREATE SEQUENCE BATCH_JOB_EXECUTION_SEQ;
CREATE SEQUENCE BATCH_JOB_SEQ;
