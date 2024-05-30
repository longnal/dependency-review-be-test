CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    status tinyint default 0,
    thumbnail VARCHAR(500) NOT null,
    created_by BIGINT NOT NULL,
    created_at DATE,
    updated_at DATE,
    FOREIGN KEY (created_by) REFERENCES users(id)
)