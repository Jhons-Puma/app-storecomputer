# App Store Computer - API RESTful

API RESTful para la gestión de una tienda de computadoras, desarrollada con Spring Boot 3 y Java 24.

## Descripción

Este proyecto implementa una API RESTful para la gestión de productos, categorías y marcas de una tienda de computadoras. La aplicación permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre estas entidades, así como búsquedas avanzadas por diferentes criterios.

## Tecnologías Utilizadas

- Java OpenJDK 24
- Spring Boot 3.4.5
- Spring Data JPA
- MySQL
- Lombok
- MapStruct 1.6.3
- SpringDoc OpenAPI (Swagger) 2.8.4
- Maven

## Estructura del Proyecto

El proyecto sigue una arquitectura en capas:

- **model**: Entidades JPA que representan las tablas de la base de datos.
- **repository**: Interfaces de repositorio para acceder a los datos.
- **dto**: Objetos de transferencia de datos implementados con Java Record.
- **mapper**: Mappers para convertir entre entidades y DTOs.
- **service**: Interfaces de servicio y sus implementaciones.
- **controller**: Controladores REST que exponen los endpoints de la API.
- **exception**: Clases para el manejo de excepciones.

## Base de Datos

La aplicación utiliza MySQL con una base de datos llamada `bd_storecomputer` que contiene las siguientes tablas:

- **categorias**: Almacena las categorías de productos.
- **marcas**: Almacena las marcas de productos.
- **productos**: Almacena los productos con referencias a categorías y marcas.

### Relaciones

- Un producto pertenece a una categoría (Many-to-One)
- Un producto pertenece a una marca (Many-to-One)
- Una categoría puede tener muchos productos (One-to-Many)
- Una marca puede tener muchos productos (One-to-Many)

## API Endpoints

La API expone los siguientes endpoints:

### Categorías

- `GET /api/v1/categorias`: Obtener todas las categorías
- `GET /api/v1/categorias/activas`: Obtener categorías activas
- `GET /api/v1/categorias/{id}`: Obtener categoría por ID
- `GET /api/v1/categorias/nombre/{nombre}`: Obtener categoría por nombre
- `POST /api/v1/categorias`: Crear una nueva categoría
- `PUT /api/v1/categorias/{id}`: Actualizar una categoría existente
- `DELETE /api/v1/categorias/{id}`: Eliminar una categoría

### Marcas

- `GET /api/v1/marcas`: Obtener todas las marcas
- `GET /api/v1/marcas/activas`: Obtener marcas activas
- `GET /api/v1/marcas/{id}`: Obtener marca por ID
- `GET /api/v1/marcas/nombre/{nombre}`: Obtener marca por nombre
- `GET /api/v1/marcas/pais/{paisOrigen}`: Obtener marcas por país de origen
- `POST /api/v1/marcas`: Crear una nueva marca
- `PUT /api/v1/marcas/{id}`: Actualizar una marca existente
- `DELETE /api/v1/marcas/{id}`: Eliminar una marca

### Productos

- `GET /api/v1/productos`: Obtener todos los productos
- `GET /api/v1/productos/pagina`: Obtener productos paginados
- `GET /api/v1/productos/activos`: Obtener productos activos
- `GET /api/v1/productos/activos/pagina`: Obtener productos activos paginados
- `GET /api/v1/productos/{id}`: Obtener producto por ID
- `GET /api/v1/productos/codigo/{codigo}`: Obtener producto por código
- `GET /api/v1/productos/categoria/{categoriaId}`: Obtener productos por categoría
- `GET /api/v1/productos/marca/{marcaId}`: Obtener productos por marca
- `GET /api/v1/productos/categoria/{categoriaId}/marca/{marcaId}`: Obtener productos por categoría y marca
- `GET /api/v1/productos/nombre?nombre=value`: Obtener productos por nombre
- `GET /api/v1/productos/precio?min=value&max=value`: Obtener productos por rango de precio
- `GET /api/v1/productos/stock-bajo?stockMinimo=value`: Obtener productos con stock bajo
- `GET /api/v1/productos/disponibles`: Obtener productos disponibles
- `POST /api/v1/productos`: Crear un nuevo producto
- `PUT /api/v1/productos/{id}`: Actualizar un producto existente
- `DELETE /api/v1/productos/{id}`: Eliminar un producto

## Documentación de la API

La documentación de la API está disponible a través de Swagger UI:

- URL: `http://localhost:8080/api/v1/swagger-ui.html`
- API Docs: `http://localhost:8080/api/v1/api-docs`

## Cómo Ejecutar

1. Asegúrate de tener instalado Java 24 y Maven.
2. Configura una base de datos MySQL y actualiza las credenciales en `application.properties` si es necesario.
3. Ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn spring-boot:run
```

4. La aplicación estará disponible en `http://localhost:8080/api/v1`

## Características Adicionales

- **Validación de Datos**: Validación de entrada en los DTOs.
- **Manejo de Excepciones**: Manejo centralizado de excepciones con respuestas HTTP apropiadas.
- **Paginación**: Soporte para paginación en las consultas de productos.
- **Documentación API**: Documentación completa con Swagger/OpenAPI.
- **Logging**: Registro de actividades mediante SLF4J.