
DROP KEYSPACE IF EXISTS uber_pickup_data;
CREATE KEYSPACE uber_pickup_data WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
//CREATE KEYSPACE uber_pickup_data WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };

use uber_pickup_data;


CREATE TABLE sensors (
   customerid int,
   sensorid int,
   changedate timestamp,
   value text,
   PRIMARY KEY (customerid, changedate)
) WITH CLUSTERING ORDER BY (changedate DESC);

CREATE TABLE raw_uber_hourly_data (
   lat double,
   lon double,
   base text,
   dt timestamp,
PRIMARY KEY (base, dt)
) WITH CLUSTERING ORDER BY (dt DESC);

//CREATE TABLE raw_uber_hourly_data (
//   dt timestamp,
//   lat double,
//   lon double,
//   base text,
//   PRIMARY KEY (dt)
//)
//WITH CLUSTERING ORDER BY (dt DESC);

CREATE TABLE daily_aggregate_kmean_prediction (
   day int,
   hour int,
   prediction int,
   PRIMARY KEY (day, hour)
) WITH CLUSTERING ORDER BY (hour DESC);


CREATE TABLE base_company_name (
   base text,
   name text,
   PRIMARY KEY (base, name)
) WITH CLUSTERING ORDER BY (name DESC);

