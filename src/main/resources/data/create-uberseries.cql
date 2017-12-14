
DROP KEYSPACE IF EXISTS uber_pickup_data;
CREATE KEYSPACE uber_pickup_data WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
//CREATE KEYSPACE uber_pickup_data WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };

use uber_pickup_data;


CREATE TABLE raw_uber_hourly_data (
   dt timestamp,      // date timestamp
   lat double,       // latitude
   lon double,       // longtitue
   base text,        // base
   PRIMARY KEY (dt, lat, lon, base)
)
WITH CLUSTERING ORDER BY (lat DESC, lon DESC, base DESC);

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

