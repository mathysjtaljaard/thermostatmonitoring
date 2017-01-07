create database thermostatTrackingdb;
use thermostatTrackingdb;
create TABLE trackingData (
  tracking_id int(11) NOT NULL AUTO_INCREMENT,
  create_time datetime NOT NULL,
  fanOn boolean NOT NULL,
  heatOn boolean NOT NULL,
  auxOn boolean NOT NULL,
  acOn boolean NOT NULL,
  temp double NOT NULL,
  PRIMARY KEY (`tracking_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
