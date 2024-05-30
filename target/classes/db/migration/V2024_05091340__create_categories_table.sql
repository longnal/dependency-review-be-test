CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500) NULL,
    status tinyint default 0,
    created_at DATE,
    updated_at DATE
)