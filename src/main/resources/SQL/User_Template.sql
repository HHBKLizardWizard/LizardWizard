# --------------------------------------------------------------
# Name: User_Template.sql                                      -
# Author: Patrick Schorn                                       -
# Last change: 22.06.2017                                      -
# Description: Creates the database and tables for our program -
# --------------------------------------------------------------

# -- We create here our database --
CREATE DATABASE user_template_db;

# -- and switch to our new database --
use user_template_db;

# -- Now create user table --
CREATE TABLE users
(
  PK_ID int AUTO_INCREMENT,
  Vorname varchar(25),
  Nachname varchar(25),
  Username varchar(25) UNIQUE,
  Password varchar(25),
  Roll int,
  PRIMARY KEY (PK_ID)
);

# -- Now create the template table --
CREATE TABLE templates
(
  PK_ID int AUTO_INCREMENT,
  Templatename varchar(30),
  Option1 boolean,
  Option2 boolean,
  Option3 boolean,
  Option4 boolean,
  Option5 boolean,
  Option6 boolean,
  Option7 boolean,
  Option8 boolean,
  PRIMARY KEY (PK_ID)
);

# -- At least create a table for both UserID and TemplateID --
CREATE TABLE ID_s
(
  FK_UserID int,
  FK_TemplateID int,
  FOREIGN KEY (FK_UserID) REFERENCES users(PK_ID),
  FOREIGN KEY (FK_TemplateID) REFERENCES templates(PK_ID)
);

# -- We are done here --