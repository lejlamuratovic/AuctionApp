ALTER TABLE product
    ADD COLUMN seller_id UUID NOT NULL;

ALTER TABLE product
    ADD FOREIGN KEY (seller_id) REFERENCES users(user_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE;

ALTER TABLE notification
DROP COLUMN read_status;
