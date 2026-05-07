# Backend - Sistema de Control de Acceso (AccessControl)

Este es el núcleo del sistema de control de acceso, desarrollado con **Spring Boot 3.5.0** y **Java 17**. El sistema está diseñado siguiendo los principios de la Programación Orientada a Objetos (POO) y una arquitectura limpia por capas para garantizar la escalabilidad y mantenibilidad.

## 🚀 Tecnologías Principales
- **Framework:** Spring Boot 3.5.0
- **Base de Datos:** MongoDB (Atlas)
- **Seguridad:** Spring Security (CORS configurado para entornos locales y producción)
- **Correo:** SendGrid API
- **QR:** ZXing (Zebra Crossing) para generación y decodificación de códigos QR.
- **Documentación:** README integral y comentarios de código siguiendo estándares Java.

## 🏗️ Estructura y Arquitectura del Sistema
El proyecto se organiza en capas lógicas para asegurar una separación clara de responsabilidades, facilitando el mantenimiento y el cumplimiento de principios SOLID:

### 1. Capa de Presentación (Web Layer)
Es el punto de entrada a la aplicación.
-   **`controller`**: Define los endpoints REST. Son "Thin Controllers", delegando toda la lógica pesada a los servicios.
-   **`dto` (Data Transfer Objects)**: Objetos que definen la estructura de datos que viaja entre el cliente y el servidor, protegiendo el modelo interno.
-   **`mapper`**: Clases encargadas de transformar los DTOs en Modelos y viceversa.
-   **`exception`**: Manejo centralizado de errores mediante `@ControllerAdvice`, asegurando respuestas uniformes ante fallos.

### 2. Capa de Negocio (Service Layer)
Contiene las reglas de negocio y la lógica principal.
-   **`service`**: Define los contratos (interfaces) de las funcionalidades.
-   **`service.impl`**: Implementaciones concretas de los servicios.
-   **`util`**: Clases auxiliares y de validación que soportan la lógica de negocio.

### 3. Capa de Datos (Persistence Layer)
Maneja la comunicación con la base de datos.
-   **`repository`**: Interfaces que utilizan Spring Data MongoDB para realizar operaciones CRUD y consultas personalizadas.

### 4. Capa de Dominio (Model Layer)
Define el "corazón" de la aplicación.
-   **`model`**: Contiene la jerarquía de clases de `Usuario`. Define cómo se guardan los datos en MongoDB y aplica lógica de dominio (como el polimorfismo en la generación de contenido QR).

### 5. Capa de Configuración (Infrastructure Layer)
-   **`config`**: Configuraciones transversales del sistema, como la seguridad (Spring Security), CORS y configuraciones de beans de terceros.

## 🛠️ Fundamentos de POO Implementados
El sistema demuestra el uso avanzado de POO:

-   **Herencia y Polimorfismo:** Existe una clase abstracta `Usuario` de la cual heredan `Residente`, `Empleado`, `Administrador` y `Visitante`. Cada subclase define sus propios permisos y puede sobrescribir el contenido del código QR.
-   **Encapsulamiento:** Todos los modelos y DTOs utilizan campos privados con getters y setters explícitos para proteger el estado de los objetos.
-   **Inversión de Dependencias:** Los controladores dependen de interfaces de servicio (`AuthService`, `UsuarioService`), lo que facilita las pruebas unitarias y el desacoplamiento.
-   **Patrón DTO & Mapper:** Se utilizan objetos de transferencia de datos (DTOs) para las entradas/salidas de la API, evitando exponer la estructura de la base de datos directamente.

## 🔑 Funcionalidades Clave

### 1. Registro con Verificación de Doble Paso
Cuando un usuario se registra, el sistema:
1. Valida los datos y guarda temporalmente al usuario.
2. Genera un enlace de verificación único enviado por correo (SendGrid).
3. Tras la verificación, el usuario se activa en la base de datos y se le envía su código QR de acceso personalizado.

### 2. Validación de Acceso QR
El sistema permite validar un acceso de dos formas:
-   **JSON:** Enviando el token del QR directamente.
-   **Archivo:** Subiendo una imagen del código QR (el backend procesa la imagen, decodifica el token y valida el acceso).

## ⚙️ Configuración y Ejecución

### Variables de Entorno Requeridas
El sistema utiliza las siguientes propiedades (configurables en `application.properties` o variables de entorno):
- `PORT`: Puerto del servidor (por defecto 8080).
- `SPRING_DATA_MONGODB_URI`: Cadena de conexión a MongoDB Atlas.
- `SENDGRID_API_KEY`: Llave de la API de SendGrid para el envío de correos.
- `APP_BASE_URL`: URL base para los enlaces de verificación.

### Comandos de Ejecución
```bash
# Limpiar y compilar el proyecto
mvn clean compile

# Ejecutar las pruebas
mvn test

# Iniciar la aplicación
mvn spring-boot:run
```

## 📂 Estructura de Paquetes
```text
com.access.access_control
├── config      # Configuraciones de seguridad y CORS
├── controller  # Endpoints de la API REST
├── dto         # Objetos de transferencia de datos
├── exception   # Manejo global de excepciones
├── mapper      # Conversores entre Entidades y DTOs
├── model       # Modelos de dominio y jerarquía de usuarios
├── repository  # Interfaces de acceso a datos (MongoDB)
├── service     # Interfaces y lógica de negocio
└── util        # Clases de utilidad y validación
```

---
Desarrollado para el proyecto final de POO - 6to Semestre.
