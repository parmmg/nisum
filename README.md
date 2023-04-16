# **Nisum Challenge**
Proyecto desarrollado por **Parmenia Maldonado** &copy;

1. Clonar proyecto desde el repositorio https://github.com/parmmg/nisum.

2. Descargar las dependencias del build.gradle.

3. El proyecto fue desarrollado con Spring Boot 3.1.0-M2, Java 17, bajo el patrón de diseño MVC.

4. La persistencia de datos utiliza JPA, base de datos en memoria H2 y **liquidbase** para la creación de tablas y carga de datos iniciales, dentro del archivo: **/resources/db.changelog.xml** se encuentran el script inicial.

5. El proyecto se despliega en el puerto 9000 de acuerdo a la configuración en el archivo application.yml.

6. El contextPath de la app está configurado con el valor /api.

7. Las configuraciones de las validaciones con expresiones regulares en la base de datos se cargan en el arranque inicial, las que pueden ser modificadas / creadas / listadas, con los siguientes endpoints: 

**Petición GET:  Obtiene las configuraciones existentes
http://localhost:9000/api/listValidations**
  
#### Respuesta:


#### 

**Petición PUT: Actualiza una validación o crea una nueva siempre y cuando la configuración exista y el campo name no exista en la Bdd
http://localhost:9000/api/saveValidation**

#### RequestBody:


#### Respuesta:


8. Los endpoints solicitados para los usuarios, se exponen de la siguiente manera:

**Petición GET: Lista todos los usuarios
http://localhost:9000/api/listUsers**

#### Respuesta:


#### 

**Petición GET: Devuelve un usuario existente por ID, el cual debe ser pasado como parámetro
http://localhost:9000/api/findUserById**

#### Respuesta:


#### 


**Petición PUT: Crea un nuevo usuario y actualiza uno existente cuando envía el ID del mismo. 
http://localhost:9000/api/saveUser**

#### RequestBody:


#### Respuesta:


**Petición POST: Login del usuario, devolviendo el presentador del usuario, luego de actualizar los campos de TOKEN, fecha de modificación y último login.
http://localhost:9000/api/login**

#### RequestBody:


#### Respuesta:


9. Para ingresar a la consola de h2: **http://localhost:9000/api/h2-console** Datos para configurar conexión a la base de datos: Driver Class: org.h2.Driver JDBC URL: jdbc:h2:mem:nisum username: nisum  /  password: nisum

10. El proyecto tiene la swagger para la documentación. Para ingresar a la misma se debe abrir el hipervínculo: **http://localhost:9000/api/swagger-ui/index.html#/**

11. Se realizan pruebas unitarias con JUnit y Mockito, de toda la lógica en los servicios, excepciones y controladores.

12. El diagrama de la solución se encuentra dentro de los recursos, específicamente en la ruta: src\main\resources\Diagrama.png

