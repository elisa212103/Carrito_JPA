INSERT INTO carrito (id_usuario, correo_usuario)
VALUES (1, 'elisa.lapastora@gmail.com');

INSERT INTO carrito (id_usuario, correo_usuario)
VALUES (2, 'pepe@gmail.com');

-- carrito_id debe de existir dentro de bd carrito
INSERT INTO articulo (descripcion, unidades, precio_unitario, carrito_id)
VALUES ('Libro Java', 2, 15.5, 1);

INSERT INTO articulo (descripcion, unidades, precio_unitario, carrito_id)
VALUES ('Teclado', 1, 45.0, 1);

INSERT INTO articulo (descripcion, unidades, precio_unitario, carrito_id)
VALUES ('Ratón', 3, 12.0, 2);