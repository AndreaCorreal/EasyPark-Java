-- =====================================================
-- Proyecto Easy Park - Sistema de gestión de parqueaderos
-- Script de creación de la base de datos (MySQL 8)
-- Aprendiz: Andrea Correal - Ficha 3186667
-- =====================================================

DROP DATABASE IF EXISTS easy_park;
CREATE DATABASE easy_park CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE easy_park;

-- -----------------------------------------------------
-- Tablas sin llaves foráneas
-- -----------------------------------------------------

CREATE TABLE usuario (
  id_usuario     INT NOT NULL AUTO_INCREMENT,
  nombre         VARCHAR(30)  NOT NULL,
  apellido       VARCHAR(50)  NOT NULL,
  cedula         VARCHAR(15)  NOT NULL,
  celular        VARCHAR(15)  DEFAULT NULL,
  correo         VARCHAR(100) NOT NULL,
  password_hash  VARCHAR(255) NOT NULL,
  rol            VARCHAR(20)  NOT NULL,
  PRIMARY KEY (id_usuario),
  UNIQUE KEY uq_usuario_cedula (cedula),
  UNIQUE KEY uq_usuario_correo (correo)
);

CREATE TABLE vehiculo (
  placa                  VARCHAR(10)  NOT NULL,
  tipo_vehiculo          VARCHAR(50)  NOT NULL,
  propietario            VARCHAR(100) NOT NULL,
  documento_propietario  VARCHAR(20)  NOT NULL,
  marca                  VARCHAR(50)  DEFAULT NULL,
  color                  VARCHAR(30)  DEFAULT NULL,
  PRIMARY KEY (placa)
);

CREATE TABLE espacio (
  id_espacio      INT NOT NULL AUTO_INCREMENT,
  numero_espacio  VARCHAR(10) NOT NULL,
  estado          VARCHAR(20) NOT NULL DEFAULT 'Disponible',
  tipo            VARCHAR(20) NOT NULL,
  PRIMARY KEY (id_espacio),
  UNIQUE KEY uq_numero_espacio (numero_espacio)
);

CREATE TABLE tarifa (
  id_tarifa       INT NOT NULL AUTO_INCREMENT,
  valor_hora      DECIMAL(10,2) NOT NULL,
  valor_fraccion  DECIMAL(10,2) NOT NULL,
  tipo_tarifa     VARCHAR(30)   NOT NULL,
  PRIMARY KEY (id_tarifa)
);

-- -----------------------------------------------------
-- Tablas con llaves foráneas
-- -----------------------------------------------------

CREATE TABLE entrada (
  id_entrada          INT NOT NULL AUTO_INCREMENT,
  fecha_hora_entrada  DATETIME    NOT NULL,
  placa               VARCHAR(10) NOT NULL,
  id_espacio          INT NOT NULL,
  id_usuario          INT NOT NULL,
  PRIMARY KEY (id_entrada),
  CONSTRAINT fk_entrada_vehiculo FOREIGN KEY (placa)      REFERENCES vehiculo (placa),
  CONSTRAINT fk_entrada_espacio  FOREIGN KEY (id_espacio) REFERENCES espacio (id_espacio),
  CONSTRAINT fk_entrada_usuario  FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

-- Una entrada tiene como máximo una salida (1:1)
CREATE TABLE salida (
  id_salida          INT NOT NULL AUTO_INCREMENT,
  fecha_hora_salida  DATETIME NOT NULL,
  id_entrada         INT NOT NULL,
  id_usuario         INT NOT NULL,
  PRIMARY KEY (id_salida),
  UNIQUE KEY uq_salida_entrada (id_entrada),
  CONSTRAINT fk_salida_entrada FOREIGN KEY (id_entrada) REFERENCES entrada (id_entrada),
  CONSTRAINT fk_salida_usuario FOREIGN KEY (id_usuario) REFERENCES usuario (id_usuario)
);

-- Una salida genera un solo pago (1:1)
CREATE TABLE pago (
  id_pago          INT NOT NULL AUTO_INCREMENT,
  valor_total      DECIMAL(10,2) NOT NULL,
  fecha_hora_pago  DATETIME      NOT NULL,
  metodo_pago      VARCHAR(50)   NOT NULL,
  id_salida        INT NOT NULL,
  id_tarifa        INT NOT NULL,
  PRIMARY KEY (id_pago),
  UNIQUE KEY uq_pago_salida (id_salida),
  CONSTRAINT fk_pago_salida FOREIGN KEY (id_salida) REFERENCES salida (id_salida),
  CONSTRAINT fk_pago_tarifa FOREIGN KEY (id_tarifa) REFERENCES tarifa (id_tarifa)
);

-- -----------------------------------------------------
-- Datos de prueba
-- -----------------------------------------------------

-- Contraseña de los tres usuarios de prueba: Easy2026*
-- (se guarda encriptada con SHA-256)
INSERT INTO usuario (nombre, apellido, cedula, celular, correo, password_hash, rol) VALUES
('Laura',  'Gomez', '1000000001', '3000000001', 'laura@easypark.com',  'a72c2840aecd5ec75f27861f39ab392bd71cb4cef81e78d8ab7201e7cc121a1d', 'Administrador'),
('Carlos', 'Perez', '1000000002', '3000000002', 'carlos@easypark.com', 'a72c2840aecd5ec75f27861f39ab392bd71cb4cef81e78d8ab7201e7cc121a1d', 'Operador'),
('Diana',  'Lopez', '1000000003', '3000000003', 'diana@easypark.com',  'a72c2840aecd5ec75f27861f39ab392bd71cb4cef81e78d8ab7201e7cc121a1d', 'Operador');

INSERT INTO vehiculo VALUES
('ABC123', 'Automovil',   'Luis Torres', '2000000001', 'Renault',   'Blanco'),
('DEF456', 'Automovil',   'Ana Ruiz',    '2000000002', 'Chevrolet', 'Gris'),
('GHI78A', 'Motocicleta', 'Pedro Diaz',  '2000000003', 'Yamaha',    'Negro');

INSERT INTO espacio (numero_espacio, estado, tipo) VALUES
('A01', 'Disponible', 'Automovil'),
('A02', 'Disponible', 'Automovil'),
('A03', 'Disponible', 'Automovil'),
('M01', 'Disponible', 'Motocicleta'),
('M02', 'Disponible', 'Motocicleta');

INSERT INTO tarifa (valor_hora, valor_fraccion, tipo_tarifa) VALUES
(4000.00, 2000.00, 'Automovil'),
(2000.00, 1000.00, 'Motocicleta'),
(5000.00, 2500.00, 'Camioneta');

INSERT INTO entrada (fecha_hora_entrada, placa, id_espacio, id_usuario) VALUES
('2026-09-27 08:00:00', 'ABC123', 1, 2),
('2026-09-27 09:00:00', 'DEF456', 2, 2),
('2026-09-27 10:00:00', 'GHI78A', 4, 3);

INSERT INTO salida (fecha_hora_salida, id_entrada, id_usuario) VALUES
('2026-09-27 10:00:00', 1, 2),
('2026-09-27 12:00:00', 2, 3),
('2026-09-27 11:00:00', 3, 3);

INSERT INTO pago (valor_total, fecha_hora_pago, metodo_pago, id_salida, id_tarifa) VALUES
(8000.00,  '2026-09-27 10:00:00', 'Efectivo',      1, 1),
(12000.00, '2026-09-27 12:00:00', 'Transferencia', 2, 1),
(2000.00,  '2026-09-27 11:00:00', 'Efectivo',      3, 2);
