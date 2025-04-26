-- Create tables if they don't exist (this is optional as JPA will create them)
-- But we include it for clarity and to ensure proper order

-- Categorias table
CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
);

-- Marcas table
CREATE TABLE IF NOT EXISTS marcas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    pais_origen VARCHAR(100),
    sitio_web VARCHAR(255),
    activo BOOLEAN DEFAULT TRUE
);

-- Productos table
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion VARCHAR(500),
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    imagen_url VARCHAR(255),
    fecha_creacion DATETIME,
    activo BOOLEAN DEFAULT TRUE,
    categoria_id BIGINT NOT NULL,
    marca_id BIGINT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id),
    FOREIGN KEY (marca_id) REFERENCES marcas(id)
);

-- Insert sample data for Categorias
INSERT INTO categorias (nombre, descripcion, activo) VALUES
('Laptops', 'Computadoras portátiles para uso personal y profesional', true),
('Desktops', 'Computadoras de escritorio para uso en oficina y gaming', true),
('Monitores', 'Pantallas para computadoras de diferentes tamaños y resoluciones', true),
('Periféricos', 'Dispositivos externos como teclados, mouse, etc.', true),
('Componentes', 'Partes internas de computadoras como CPU, RAM, etc.', true),
('Almacenamiento', 'Dispositivos para almacenar datos como discos duros, SSD, etc.', true),
('Networking', 'Equipos para redes como routers, switches, etc.', true),
('Software', 'Programas y aplicaciones para computadoras', true),
('Accesorios', 'Complementos para computadoras como fundas, mochilas, etc.', true),
('Gaming', 'Productos especializados para videojuegos', true);

-- Insert sample data for Marcas
INSERT INTO marcas (nombre, pais_origen, sitio_web, activo) VALUES
('HP', 'Estados Unidos', 'https://www.hp.com', true),
('Dell', 'Estados Unidos', 'https://www.dell.com', true),
('Lenovo', 'China', 'https://www.lenovo.com', true),
('Asus', 'Taiwán', 'https://www.asus.com', true),
('Acer', 'Taiwán', 'https://www.acer.com', true),
('Apple', 'Estados Unidos', 'https://www.apple.com', true),
('Samsung', 'Corea del Sur', 'https://www.samsung.com', true),
('MSI', 'Taiwán', 'https://www.msi.com', true),
('Gigabyte', 'Taiwán', 'https://www.gigabyte.com', true),
('Logitech', 'Suiza', 'https://www.logitech.com', true);

-- Insert sample data for Productos
INSERT INTO productos (nombre, descripcion, precio, stock, codigo, imagen_url, fecha_creacion, activo, categoria_id, marca_id) VALUES
('HP Pavilion 15', 'Laptop con procesador Intel Core i5, 8GB RAM, 512GB SSD', 899.99, 25, 'HP-PAV-15', 'https://example.com/images/hp-pavilion.jpg', NOW(), true, 1, 1),
('Dell XPS 13', 'Laptop ultradelgada con procesador Intel Core i7, 16GB RAM, 1TB SSD', 1299.99, 15, 'DELL-XPS-13', 'https://example.com/images/dell-xps.jpg', NOW(), true, 1, 2),
('Lenovo ThinkPad X1', 'Laptop empresarial con procesador Intel Core i7, 16GB RAM, 512GB SSD', 1499.99, 10, 'LEN-TP-X1', 'https://example.com/images/lenovo-thinkpad.jpg', NOW(), true, 1, 3),
('Asus ROG Strix', 'Desktop gaming con procesador AMD Ryzen 7, 32GB RAM, 1TB SSD, RTX 3080', 1999.99, 8, 'ASUS-ROG-STX', 'https://example.com/images/asus-rog.jpg', NOW(), true, 2, 4),
('Acer Predator', 'Monitor gaming 27" 144Hz 1ms', 349.99, 20, 'ACER-PRED-27', 'https://example.com/images/acer-predator.jpg', NOW(), true, 3, 5),
('Apple MacBook Pro', 'Laptop con chip M1 Pro, 16GB RAM, 512GB SSD', 1999.99, 12, 'APP-MBP-M1', 'https://example.com/images/apple-macbook.jpg', NOW(), true, 1, 6),
('Samsung Odyssey G7', 'Monitor curvo gaming 32" 240Hz', 699.99, 15, 'SAM-ODY-G7', 'https://example.com/images/samsung-odyssey.jpg', NOW(), true, 3, 7),
('MSI GeForce RTX 3070', 'Tarjeta gráfica para gaming de alto rendimiento', 599.99, 7, 'MSI-RTX-3070', 'https://example.com/images/msi-rtx.jpg', NOW(), true, 5, 8),
('Gigabyte B550 AORUS', 'Placa base para procesadores AMD Ryzen', 179.99, 18, 'GB-B550-AOR', 'https://example.com/images/gigabyte-aorus.jpg', NOW(), true, 5, 9),
('Logitech MX Master 3', 'Mouse inalámbrico de alta precisión', 99.99, 30, 'LOG-MX-M3', 'https://example.com/images/logitech-mx.jpg', NOW(), true, 4, 10);