ALTER TABLE users
ADD COLUMN date_of_birth DATE,
ADD COLUMN profile_picture VARCHAR(255),
ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT true;

ALTER TABLE payment_info
ADD COLUMN state VARCHAR(255);
