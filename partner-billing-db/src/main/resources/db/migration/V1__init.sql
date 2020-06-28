CREATE TABLE db_servers(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, jdbc_url varchar(200) NOT NULL, jdbc_username varchar(50) NOT NULL, jdbc_password varchar(50) NOT NULL, site_id INT NOT NULL);
CREATE TABLE cdr (calldate datetime DEFAULT '2000-00-00 00:00:00' NOT NULL, clid varchar(80) NOT NULL, src varchar(80) NOT NULL, dst varchar(80) NOT NULL, dcontext varchar(80) NOT NULL, channel varchar(80) NOT NULL, dstchannel varchar(80) NOT NULL, lastapp varchar(80) NOT NULL, lastdata varchar(80) NOT NULL, duration int DEFAULT '0' NOT NULL, billsec int DEFAULT '0' NOT NULL, disposition varchar(45) NOT NULL, amaflags int DEFAULT '0' NOT NULL, accountcode varchar(20) NOT NULL, uniqueid varchar(32) NOT NULL, userfield varchar(255) NOT NULL, cost varchar(10), site_id int, id int(10) NOT NULL AUTO_INCREMENT, PRIMARY KEY (id));
CREATE TABLE cdr_query (id int NOT NULL AUTO_INCREMENT, site_id int, query text, active bit, created datetime, partner_id varchar(25), PRIMARY KEY (id));
CREATE TABLE discipline_group (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(25), site_id int, group_id int, PRIMARY KEY (id));
CREATE TABLE partner (id int NOT NULL AUTO_INCREMENT, site_id int, name varchar(25), description varchar(50), accountcode int, group_id int, discipline_id int, PRIMARY KEY (id));
CREATE TABLE partner_group (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(50), site_id int, PRIMARY KEY (id));
CREATE TABLE site (id int NOT NULL AUTO_INCREMENT, name varchar(25), description varchar(50), outputFile varchar(100) NOT NULL, PRIMARY KEY (id));
CREATE TABLE aspivia (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, Extension varchar(15), Pbx_Date_Time datetime, Duration int, Account_Code int(2), Digits varchar(20), Cost decimal(10,2), Destination varchar(30), Carrier varchar(30), Attribute int(2), Site_id int NOT NULL);
ALTER TABLE aspivia ADD CONSTRAINT fk_site_id FOREIGN KEY (Site_id) REFERENCES site(id);
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

INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (1, 'Access Health', 'Access Health', 1, 4);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (2, 'Access Health', 'Access Health', 2, 15);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (3, 'Ambulatory PMB', 'Ambulatory PMB', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (4, 'Ambulatory PMB', 'Ambulatory PMB', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (5, 'Contributions', 'Contributions', 1, 6);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (6, 'Contributions', 'Contributions', 2, 17);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (7, 'Europ Assist', 'Europ Assist', 1, 8);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (8, 'Europ Assist', 'Europ Assist', 2, 19);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (9, 'Health Line', 'Health Line', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (10, 'Health Line', 'Health Line', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (11, 'PreAuth', 'PreAuth', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (12, 'PreAuth', 'PreAuth', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (13, 'Pinnacle', 'Pinnacle', 2, 23);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (14, 'Pinnacle', 'Pinnacle', 1, 12);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (15, 'OptiClear', 'OptiClear', 2, 22);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (16, 'OptiClear', 'OptiClear', 1, 11);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (17, 'Oncology', 'Oncology', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (18, 'Oncology', 'Oncology', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (19, 'Meds Management', 'Meds Management', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (20, 'Meds Management', 'Meds Management', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (21, 'Medipost', 'Medipost', 2, 20);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (22, 'Medipost', 'Medipost', 1, 9);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (23, 'Primecure Dental', 'Primecure Dental', 1, 13);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (24, 'Primecure Dental', 'Primecure Dental', 2, 24);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (25, 'Primecure HIV', 'Primecure HIV', 1, 13);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (26, 'Primecure HIV', 'Primecure HIV', 2, 24);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (27, 'Sapphire/Beryl', 'Sapphire/Beryl', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (28, 'Sapphire/Beryl', 'Sapphire/Beryl', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (29, 'Universal Chronic', 'Universal Chronic', 1, 14);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (30, 'Universal Chronic', 'Universal Chronic', 2, 25);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (31, 'Universal HIV', 'Universal HIV', 1, 14);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (32, 'Universal HIV', 'Universal HIV', 2, 25);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (33, 'Teledirect', 'Teledirect', 1, 26);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (34, 'Teledirect', 'Teledirect', 2, 27);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (35, 'GP Specialist', 'GP Specialist', 1, 5);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (36, 'GP Specialist', 'GP Specialist', 2, 16);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (37, 'MyCare', 'MyCare', 1, 29);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (38, 'MyCare', 'MyCare', 2, 30);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (39, 'EOH Health Program', 'EOH Health Program', 1, 31);
INSERT INTO discipline_group (id, name, description, site_id, group_id) VALUES (40, 'EOH Health Program', 'EOH Health Program', 2, 32);
