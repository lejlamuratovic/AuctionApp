CREATE TABLE category (
      category_id SERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL
);

CREATE TABLE product (
     product_id SERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     image_url VARCHAR(255),
     description TEXT,
     start_price DECIMAL(10, 2),
     start_date TIMESTAMP,
     end_date TIMESTAMP,
     category_id INTEGER REFERENCES category(category_id),
     status VARCHAR(50)
);
