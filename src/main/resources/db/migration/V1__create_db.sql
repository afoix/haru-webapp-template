-- Put your create table statements inside here.
-- Remember that the scripts of flyway need to be name with 'V' + a consecutive numbers plus '__'

CREATE TABLE Weather_Records (
    id INT NOT NULL PRIMARY KEY,
    country VARCHAR(200) NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    min_temperature INT NOT NULL,
    max_temperature INT NOT NULL,
    description VARCHAR(200) NOT NULL
)