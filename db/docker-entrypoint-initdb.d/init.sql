create table if not exists users (
	user_id bigserial primary key,
	first_name varchar(30) not null,
	second_name varchar(30) not null,
	email varchar(250) not null unique,
	password varchar(100) not null
)

CREATE TABLE cars (
    id SERIAL PRIMARY KEY,
    model VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);
