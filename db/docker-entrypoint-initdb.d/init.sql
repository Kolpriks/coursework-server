CREATE TABLE cars (
    id SERIAL PRIMARY KEY, 
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image BYTEA,
    description TEXT,
    consumption DECIMAL(5, 2) NOT NULL, 
    seats INT NOT NULL,
    co2 DECIMAL(5, 2) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE favorite (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    cars_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (cars_id) REFERENCES cars(id) ON DELETE CASCADE
);

CREATE TABLE assignment (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    car_id  INT NOT NULL,
    CONSTRAINT fk_assignment_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_assignment_car
        FOREIGN KEY (car_id)  REFERENCES cars(id) ON DELETE CASCADE,
    CONSTRAINT uq_assignment_user_car UNIQUE (user_id, car_id)
);

ALTER TABLE assignment
ADD COLUMN manager_id INT NOT NULL;

ALTER TABLE assignment
ADD CONSTRAINT fk_assignment_manager
    FOREIGN KEY (manager_id)
    REFERENCES users(id)
    ON DELETE RESTRICT;
