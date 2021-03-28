DROP DATABASE IF EXISTS car_number_db;
CREATE DATABASE car_number_db;
USE car_number_db;

CREATE TABLE car_number (
  id int NOT NULL AUTO_INCREMENT,
  series varchar(5) NOT NULL,
  number int(3) ZEROFILL NOT NULL,
  region int NOT NULL,
  country varchar(5) NOT NULL,
  type_getting varchar(10) NOT NULL,
  PRIMARY KEY (id),
  KEY series (series),
  KEY number (number),
  KEY region (region),
  KEY country (country),
  KEY type_getting (type_getting),
  UNIQUE KEY series_nummber_region_country (series, number, region, country)
) ENGINE=INNODB DEFAULT CHARSET=utf8;