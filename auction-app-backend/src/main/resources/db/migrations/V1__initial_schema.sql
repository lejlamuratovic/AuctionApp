CREATE TABLE category (
      category_id SERIAL PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      parent_category_id INTEGER REFERENCES category(category_id)
);

CREATE TABLE product (
     product_id SERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     image_url VARCHAR(255) NOT NULL,
     description TEXT NOT NULL,
     start_price DECIMAL(10, 2) NOT NULL,
     start_date TIMESTAMP NOT NULL,
     end_date TIMESTAMP NOT NULL,
     category_id INTEGER REFERENCES category(category_id) NOT NULL,
     status VARCHAR(50) NOT NULL
);
