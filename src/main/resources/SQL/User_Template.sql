# --------------------------------------------------------------
# Name: User_Template.sql                                      -
# Author: Patrick Schorn                                       -
# Last change: 26.06.2017                                      -
# Description: Creates the database and tables for our program -
# --------------------------------------------------------------

# -- We create here our database --
CREATE DATABASE user_template_db;

# -- and switch to our new database --
use user_template_db;

# -- Now create user table --
CREATE TABLE users
(
  pk_id int AUTO_INCREMENT,
  username varchar(25) UNIQUE NOT NULL,
  firstname varchar(25) NOT NULL,
  lastname varchar(25) NOT NULL,
  password varchar(50) NOT NULL,
  rights varchar(6) NOT NULL,
  PRIMARY KEY (pk_id)
);

# -- Now create the template table --
CREATE TABLE templates
(
  pk_id int AUTO_INCREMENT,
  scenario boolean NOT NULL,
  competences boolean NOT NULL,
  materials boolean NOT NULL,
  technics boolean NOT NULL,
  results boolean NOT NULL,
  contents boolean NOT NULL,
  notes boolean NOT NULL,
  achievements boolean NOT NULL,
  PRIMARY KEY (PK_ID)
);

# -- At least create a table for both UserID and TemplateID --
CREATE TABLE user_templates
(
  fk_userid int,
  fk_templateid int,
  templatename varchar(30) NOT NULL,
  FOREIGN KEY (fk_userid) REFERENCES users(pk_id)
  ON DELETE CASCADE,
  FOREIGN KEY (fk_templateid) REFERENCES templates(pk_id)
  ON DELETE CASCADE
);

INSERT INTO users (username, firstname, lastname, password, rights) VALUES ("root", "root", "root", "root", "admin");
