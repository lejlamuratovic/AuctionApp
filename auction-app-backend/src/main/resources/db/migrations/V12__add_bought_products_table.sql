CREATE TABLE bought_products (
    bought_product_id UUID PRIMARY KEY,
    buyer_id UUID NOT NULL,
    product_id UUID NOT NULL,
    payment_info_id UUID NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES users(user_id)
       ON UPDATE CASCADE
       ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (payment_info_id) REFERENCES payment_info(payment_info_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
