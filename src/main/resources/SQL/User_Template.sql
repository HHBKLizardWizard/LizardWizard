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
  username varchar(25) UNIQUE,
  firstname varchar(25),
  lastname varchar(25),
  password varchar(25),
  rights varchar(6),
  PRIMARY KEY (pk_id)
);

# -- Now create the template table --
CREATE TABLE templates
(
  pk_id int AUTO_INCREMENT,
  scenario boolean,
  competences boolean,
  materials boolean,
  technics boolean,
  results boolean,
  contents boolean,
  notes boolean,
  achievments boolean,
  PRIMARY KEY (PK_ID)
);

# -- At least create a table for both UserID and TemplateID --
CREATE TABLE user_templates
(
  fk_userid int,
  fk_templateid int,
  templatename varchar(30),
  FOREIGN KEY (fk_userid) REFERENCES users(pk_id),
  FOREIGN KEY (fk_templateid) REFERENCES templates(pk_id)
);

# -- We are done here --