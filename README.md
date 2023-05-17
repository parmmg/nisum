# **Nisum Challenge**
Proyecto desarrollado por **Parmenia Maldonado** &copy;

1. Clonar proyecto desde el repositorio.

2. Descargar las dependencias del build.gradle.

3. El proyecto fue desarrollado con Spring Boot 3.1.0-M2, Java 17, bajo el patrón de diseño MVC.

4. La persistencia de datos utiliza JPA, base de datos en memoria H2 y **liquidbase** para la creación de tablas y carga de datos iniciales, dentro del archivo: **src/main/resources/db.changelog.xml** se encuentran el script inicial.

5. El proyecto se despliega en el puerto 9000 de acuerdo a la configuración en el archivo **src/main/resources/application.yml**.

6. El contextPath de la app está configurado con el valor /api.

7. Se indica la respuesta HTTP respectiva. En caso de éxito se envía la data requerida, En caso de error, devuelve la respuesta HTTP respectiva con la data nula y en el mensaje explica el motivo de la excepción.

8. Las configuraciones de las validaciones con expresiones regulares en la base de datos se cargan en el arranque inicial, las que pueden ser modificadas / creadas / listadas. Se exponen los siguientes endpoints: 

**Petición GET:  Obtiene las configuraciones existentes
http://localhost:9000/api/validations/**
  
#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232261293-d9c71bfc-b4a1-47f5-90ca-fba90b1e4bd0.png)

#### 

**Petición POST: Actualiza o crea una validación si no existe
http://localhost:9000/api/validations/**

#### RequestBody:
![image](https://user-images.githubusercontent.com/115352466/232261360-3bb1ab10-1b84-4ca9-92fd-b26d97a66cf7.png)

#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232261370-a25c1b0b-b5be-4317-847f-c5af7e2a10f2.png)

#### Respuesta Error:
![image](https://user-images.githubusercontent.com/115352466/232261474-e15f2390-2ad1-4390-9b3d-24be83b8fa44.png)

9. Los endpoints solicitados para los usuarios, se exponen de la siguiente manera:

**Petición GET: Lista todos los usuarios
http://localhost:9000/api/users/**

#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232262261-47f24181-0290-42dd-ad60-68f11347658f.png)

#### 

**Petición GET: Devuelve un usuario existente por ID, el cual debe ser pasado en el context Path
http://localhost:9000/api/users/{id}**

#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232262310-795b25b9-4914-442d-97d2-a0a73755149f.png)

#### Respuesta Error:
![image](https://user-images.githubusercontent.com/115352466/232261119-d4fd3915-2702-4bfb-87c0-8e972f1cd428.png)

#### 
**Petición POST: Ingresa un nuevo usuario en el context path y los datos en el body.
http://localhost:9000/api/users/{id}**


**Petición PUT: Actualiza el usuario con el id indicado en el context path y los datos en el body. 
http://localhost:9000/api/users/{id}**

#### RequestBody:
![image](https://user-images.githubusercontent.com/115352466/232261146-d106019f-3d74-4676-b67c-b3cb3961d574.png)

#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232261177-5c105e08-6300-4874-a89b-db1c6d703b34.png)

#### Respuesta Error:
![image](https://user-images.githubusercontent.com/115352466/232261519-53d656c1-c169-433a-8047-61734e7516e6.png)


**Petición POST: Login del usuario, devolviendo el presentador del usuario, luego de actualizar los campos de TOKEN, fecha de modificación y último login.
http://localhost:9000/api/login**

#### RequestBody:
![image](https://user-images.githubusercontent.com/115352466/232261552-381619ae-7076-414f-ba5b-a9abcf0a4d9a.png)

#### Respuesta:
![image](https://user-images.githubusercontent.com/115352466/232261561-5f4faeac-382d-4793-b5a0-a37a78b86bad.png)

#### Respuesta Error:
![image](https://user-images.githubusercontent.com/115352466/232261572-4868d82b-7c27-45b5-977b-3e6550b51232.png)


10. Para ingresar a la consola de h2: **http://localhost:9000/api/h2-console** 

Datos para configurar conexión a la base de datos: **Driver Class: org.h2.Driver JDBC URL: jdbc:h2:mem:nisum username: nisum  /  password: nisum**. Estos datos se encuentran en el archivo de configuración **src/main/resources/application.yml**.

![image](https://user-images.githubusercontent.com/115352466/232260977-202347ef-6172-4a78-bc23-328a36321f85.png)

11. El proyecto tiene la swagger para la documentación. Para ingresar a la misma se debe abrir el hipervínculo: **http://localhost:9000/api/swagger-ui/index.html#/**

12. Se realizan pruebas unitarias con JUnit y Mockito, de toda la lógica en los servicios, excepciones y controladores.

13. El diagrama de la solución se encuentra dentro de los recursos, específicamente en la ruta: src\main\resources\Diagrama.png

