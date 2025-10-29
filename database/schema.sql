-- schema.sql
CREATE TABLE client (
                        client_id SERIAL PRIMARY KEY,    -- DB-generated unique id
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        age INT NOT NULL
);

CREATE TABLE train_connection (
                                  route_id VARCHAR(20) PRIMARY KEY,
                                  departure_city VARCHAR(100) NOT NULL,
                                  arrival_city VARCHAR(100) NOT NULL,
                                  departure_time VARCHAR(20) NOT NULL,
                                  arrival_time VARCHAR(20) NOT NULL,
                                  train_type VARCHAR(50) NOT NULL,
                                  days_of_operation VARCHAR(50),
                                  first_class_ticket_rate VARCHAR(20),
                                  second_class_ticket_rate VARCHAR(20)
);

CREATE TABLE reservation (
                             reservation_id SERIAL PRIMARY KEY,
                             client_id INT NOT NULL REFERENCES client(client_id),
                             route_ids TEXT[] NOT NULL        -- store multi-leg route IDs
);

CREATE TABLE ticket (
                        ticket_id SERIAL PRIMARY KEY,
                        reservation_id INT NOT NULL REFERENCES reservation(reservation_id) ON DELETE CASCADE,
                        travel_class VARCHAR(20) NOT NULL,   -- "First Class" or "Second Class"
                        total_price NUMERIC(10,2) NOT NULL
);

CREATE TABLE trip (
                      trip_id SERIAL PRIMARY KEY,          -- alphanumeric
                      reservation_ids INT[]           -- list of reservation IDs
);

CREATE TABLE reservation_connection (
                                        reservation_id INT NOT NULL REFERENCES reservation(reservation_id),
                                        route_id TEXT NOT NULL,  -- matches TrainConnection.routeID
                                        PRIMARY KEY(reservation_id, route_id)
);
