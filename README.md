# API Carritos – Spring Boot

Proyecto REST sencillo en **Spring Boot** que gestiona carritos de la compra y sus artículos usando una estructura en memoria (`Map<Long, Carrito>`).  
No utiliza base de datos: los datos se pierden al reiniciar la aplicación.

## Estructura general

- **Base URL:** `/api/carritos`
- **Formato:** JSON
- **Controlador principal:** `ControladorRest`
- **Entidades:** `Carrito`, `Articulo`

---

##  Endpoints disponibles

### Crear un carrito
**POST** `/api/carritos`

Crea un nuevo carrito.

- **Path variable:** `id`
- **Body:** objeto `Carrito`
- **Respuesta:** `201 CREATED`
- **Errores:**
  - `400 BAD REQUEST` → el carrito ya existe

---

### Añadir un artículo a un carrito
**POST** `/api/carritos/{id}/articulos`

Añade un artículo a un carrito existente.

- **Path variable:** `id` (ID del carrito)
- **Body:** objeto `Articulo`
- **Respuesta:** carrito actualizado
- **Errores:**
  - `404 NOT FOUND` → carrito no encontrado

---

### Obtener un carrito
**GET** `/api/carritos/{id}`

Devuelve un carrito por su ID.

- **Path variable:** `id`
- **Respuesta:** objeto `Carrito`
- **Errores:**
  - `404 NOT FOUND` → carrito no encontrado

---

### Actualizar un carrito
**PUT** `/api/carritos/{id}`

Reemplaza completamente un carrito existente.

- **Path variable:** `id`
- **Body:** objeto `Carrito`
- **Respuesta:** carrito actualizado
- **Errores:**
  - `404 NOT FOUND` → carrito no encontrado

---

### Borrar un carrito
**DELETE** `/api/carritos/{id}`

Elimina un carrito.

- **Path variable:** `id`
- **Respuesta:** `204 NO CONTENT`
- **Errores:**
  - `404 NOT FOUND` → carrito no encontrado

---



---
