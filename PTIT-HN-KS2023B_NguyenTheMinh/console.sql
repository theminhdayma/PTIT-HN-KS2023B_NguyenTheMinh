DROP DATABASE IF EXISTS hackathonJavaWeb;
CREATE DATABASE hackathonJavaWeb;
USE hackathonJavaWeb;

CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    status bit default 1
);

INSERT INTO category(category_name, description) VALUES
('Điện thoại', 'Các sản phẩm điện thoại di động'),
('Máy tính bảng', 'Các sản phẩm máy tính bảng'),
('Laptop', 'Các sản phẩm laptop và máy tính xách tay'),
('Phụ kiện', 'Các phụ kiện điện tử như tai nghe, sạc, ốp lưng'),
('Đồng hồ thông minh', 'Các sản phẩm đồng hồ thông minh và thiết bị đeo tay thông minh');

CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL CHECK ( price > 0 ),
    image_url VARCHAR(255),
    status bit default 1,
    created_at DATETIME,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

INSERT INTO product (product_name, description, price, image_url, created_at, category_id) VALUES
('iPhone 14', 'Điện thoại thông minh mới nhất của Apple', 999.99, 'https://example.com/iphone14.jpg', NOW(), 1),
('Samsung Galaxy S23', 'Điện thoại thông minh cao cấp của Samsung', 899.99, 'https://example.com/galaxys23.jpg', NOW(), 1),
('iPad Pro', 'Máy tính bảng cao cấp của Apple', 1099.99, 'https://example.com/ipadpro.jpg', NOW(), 2),
('MacBook Pro', 'Laptop mạnh mẽ cho công việc và giải trí', 1999.99, 'https://example.com/macbookpro.jpg', NOW(), 3),
('Tai nghe Bluetooth', 'Tai nghe không dây chất lượng cao', 199.99, 'https://example.com/bluetoothheadphones.jpg', NOW(), 4),
('Apple Watch Series 8', 'Đồng hồ thông minh mới nhất của Apple', 399.99, 'https://example.com/applewatch8.jpg', NOW(), 5),
('Samsung Galaxy Tab S8', 'Máy tính bảng cao cấp của Samsung', 899.99, 'https://example.com/galaxytabs8.jpg', NOW(), 2),
('Dell XPS 13', 'Laptop mỏng nhẹ với hiệu năng cao', 1499.99, 'https://example.com/dellxps13.jpg', NOW(), 3),
('Sạc không dây', 'Sạc không dây tiện lợi cho điện thoại', 49.99, 'https://example.com/wirelesscharger.jpg', NOW(), 4),
('Fitbit Versa 3', 'Đồng hồ thông minh theo dõi sức khỏe', 229.99, 'https://example.com/fitbitversa3.jpg', NOW(), 5);


DELIMITER //

-- Category

DROP PROCEDURE IF EXISTS get_all_categories;
CREATE PROCEDURE get_all_categories()
BEGIN
    SELECT * FROM category WHERE status = 1;
END //

DROP PROCEDURE IF EXISTS get_category_by_id;
CREATE PROCEDURE get_category_by_id(IN cat_id INT)
BEGIN
    SELECT * FROM category WHERE category_id = cat_id AND status = 1;
END //

DROP PROCEDURE IF EXISTS check_name_category_exists;
CREATE PROCEDURE check_name_category_exists(IN cat_name VARCHAR(255))
BEGIN
    SELECT COUNT(*) AS count FROM category WHERE category_name = cat_name AND status = 1;
END //

DROP PROCEDURE IF EXISTS add_category;
CREATE PROCEDURE add_category(IN cat_name VARCHAR(255), IN cat_desc TEXT)
BEGIN
    INSERT INTO category (category_name, description) VALUES (cat_name, cat_desc);
END //

DROP PROCEDURE IF EXISTS update_category;
CREATE PROCEDURE update_category(IN cat_id INT, IN cat_name VARCHAR(255), IN cat_desc TEXT)
BEGIN
    UPDATE category
    SET category_name = cat_name, description = cat_desc
    WHERE category_id = cat_id AND status = 1;
END //

DROP PROCEDURE IF EXISTS delete_category;
CREATE PROCEDURE delete_category(IN cat_id INT)
BEGIN
    DECLARE count_products INT;
    SELECT COUNT(*) INTO count_products FROM product WHERE category_id = cat_id AND status = 1;
    IF count_products > 0 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Không thể xóa danh mục này vì đang tồn tại sản phẩm';
    ELSE
        DELETE FROM category
        WHERE category_id = cat_id AND status = 1;
    END IF;
END //

DROP PROCEDURE IF EXISTS search_category_by_name;
CREATE PROCEDURE search_category_by_name(IN cat_name VARCHAR(255))
BEGIN
    SELECT * FROM category
    WHERE category_name LIKE CONCAT('%', cat_name, '%') AND status = 1;
END //

-- Product

DROP PROCEDURE IF EXISTS get_all_products;
CREATE PROCEDURE get_all_products()
BEGIN
    SELECT * FROM product WHERE status = 1;
END //

DROP PROCEDURE IF EXISTS get_product_by_id;
CREATE PROCEDURE get_product_by_id(IN prod_id INT)
BEGIN
    SELECT * FROM product WHERE product_id = prod_id AND status = 1;
END //

DROP PROCEDURE IF EXISTS check_name_product_exists;
CREATE PROCEDURE check_name_product_exists(IN prod_name VARCHAR(255))
BEGIN
    SELECT COUNT(*) AS count FROM product WHERE product_name = prod_name AND status = 1;
END //

DROP PROCEDURE IF EXISTS add_product;
CREATE PROCEDURE add_product(IN prod_name VARCHAR(255), IN prod_desc TEXT, IN prod_price DECIMAL(10, 2), IN prod_image_url VARCHAR(255), IN cat_id INT)
BEGIN
    INSERT INTO product (product_name, description, price, image_url, created_at, category_id)
    VALUES (prod_name, prod_desc, prod_price, prod_image_url, NOW(), cat_id);
END //

DROP PROCEDURE IF EXISTS update_product;
CREATE PROCEDURE update_product(IN prod_id INT, IN prod_name VARCHAR(255), IN prod_desc TEXT, IN prod_price DECIMAL(10, 2), IN prod_image_url VARCHAR(255), IN cat_id INT)
BEGIN
    UPDATE product
    SET product_name = prod_name, description = prod_desc, price = prod_price, image_url = prod_image_url, category_id = cat_id
    WHERE product_id = prod_id AND status = 1;
END //

DROP PROCEDURE IF EXISTS delete_product;
CREATE PROCEDURE delete_product(IN prod_id INT)
BEGIN
    DELETE FROM product
    WHERE product_id = prod_id AND status = 1;
END //

DROP PROCEDURE IF EXISTS search_product_by_name;
CREATE PROCEDURE search_product_by_name(IN prod_name VARCHAR(255))
BEGIN
    SELECT * FROM product
    WHERE product_name LIKE CONCAT('%', prod_name, '%') AND status = 1;
END //

DELIMITER ;