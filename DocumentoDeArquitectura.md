# # Home Run Software
## Indice
## Arquitectura

### Descripción de la Arquitectura utilizada
La arquitectura por capas es una de las formas más simples de los patrones arquitectonicos de software. En esta arquitectura los componentes son organizados en capas horizontales. Estas capas son independientes y se comunican unicamente con las capas adjacentes. Una arquitectura por capas general puede constar de n-capas sin embargo la implementación más comun consta de 4 capas que son: Capa de Presentación,Capa de Logica de negocio (Bussiness Layer), Capa de Persistencia y Capa de Almacenamiento. Las ventajas de usar esta arquitectura es que es sencilla de implementar y probar. Las desventajas es que la aplicación se vuelve dificil de modificar y escalar.
### Definición teórica de Microservicios
Es un acercamineto para desarrollar una aplicacion como un conjunto de pequeños servicios, cada uno corriendo sus propios procesos y comunicando con mecanismos ligeros. Cada microservicio debe ser despeglable y escalable de manera independiente y cada servicio tiene limites definidos permitiendo que los diferentes servicios sean escritos en diferentes lenguajes de programación.

### Diagrama de arquitectura con descripción
![alt text](diagramaArquitectura.png "Title")
### Diagrama de secuencia
### Diagrama Entidad Relación
### Descripción Entidades

## Documentación de la API
### Endpoints
* [Listar propiedades](#listarPropiedades) : `GET /api/propiedades/`
* [Listar propiedades paginadas](#listarPaginadas) : `GET /api/propiedades/{offsetValue}/{cantidad}`
* [Listar propiedad especifica](#listarEspecifica) : `GET /api/propiedades/{id}`
* [Crear propiedad](#crearPropiedad) : `POST /api/propiedades/`
* [Crear propiedades desde csv](#crearCSV) : `POST /api/propiedades/`
* [Actualizar propiedad](#actualizarPropiedad) : `PUT /api/propiedades/{id}`
* [Eliminar propiedad](#eliminarPropiedad) : `DELETE /api/propiedades/{id}`

#### <a name="listarPropiedades"></a> Listar propiedades
* URI :
  * `GET /api/propiedades/`
* Descripción:
  * Se utiliza para obtener el listado de propiedades registradas en el sistema
  junto con su información correspondiente.
* Respuesta (200OK):
```json    
  {
    "1": {
      "costo" : 1000000.00,
      "ubicacion" : "Oriente",
      "direccion" : {
        "idDireccion" : 1,
        "calle" : "48",
        "numero" : "478",
        "cruzamientos" : "53 y 56",
        "colonia" : "México Oriente"
      },
      "metros" : 56,
      "banos" : 2.5,
      "habitaciones" : 3,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36",
      "idusuario" : 3
    },
    "2": {
      "costo" : 3500140.00,
      "ubicacion" : "Norte",
      "direccion" : {
        "idDireccion" : 2,
        "calle" : "48",
        "numero" : "478",
        "cruzamientos" : "53 y 56",
        "colonia" : "Francisco de Montejo"
      },
      "metros" : 100,
      "banos" : 4,
      "habitaciones" : 4,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36",
      "idusuario" : 3
    }
  }         
```

#### <a name="listarEspecifica"></a> Listar propiedad especifica
* URI :
  * `GET /api/propiedades/{id}`
* Descripción:
  * Se utiliza para obtener la información de una propiedad especifica registrada
  en el sistema.
* Respuesta (200OK):    
```json    
  {
    "1": {
      "costo" : 1000000.00,
      "ubicacion" : "Oriente",
      "direccion" : {
        "idDireccion" : 1,
        "calle" : "48",
        "numero" : "478",
        "cruzamientos" : "53 y 56",
        "colonia" : "México Oriente"
      },
      "metros" : 56,
      "banos" : 2.5,
      "habitaciones" : 3,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36",
      "idusuario" : 3
    }
  }
```       
* Ejemplo de request: `GET /api/propiedades/1`
  * Respuesta (200OK):
```json
    {
      "1": {
        "costo" : 1000000.00,
        "ubicacion" : "Oriente",
        "direccion" : {
          "idDireccion" : 1,
          "calle" : "48",
          "numero" : "478",
          "cruzamientos" : "53 y 56",
          "colonia" : "México Oriente"
        },
        "metros" : 56,
        "banos" : 2.5,
        "habitaciones" : 3,
        "fechaPublicacion" : "2009-03-02 23:13:36",
        "fechaCreacion" : "2009-03-02 23:13:36",
        "idusuario" : 3
      }
    }     
```    

#### <a name="listarPaginadas"></a> Listar propiedades paginadas
* URI :
  * `GET /api/propiedades/{offsetValue}/{cantidad}`
* Descripción:
  * Se utiliza para solicitar una busqueda con la capacidad de paginar las listarPropiedades
  en conjunto con la información de estas registradas en el sistema.
* Ejemplo de request: `GET /api/propiedades/5/20`
  * Descripción: La respuesta son un total de 20 propiedades comenzando desde la propiedad con el id 5, por lo que se devolverá el listado de las propiedades de la 5 a la 25.
  * Respuesta (200OK):
```json    
    {
      "5": {
        "costo" : 1000000.00,
        "ubicacion" : "Oriente",
        "direccion" : {
          "idDireccion" : 1,
          "calle" : "48",
          "numero" : "478",
          "cruzamientos" : "53 y 56",
          "colonia" : "México Oriente"
        },
        "metros" : 56,
        "banos" : 2.5,
        "habitaciones" : 3,
        "fechaPublicacion" : "2009-03-02 23:13:36",
        "fechaCreacion" : "2009-03-02 23:13:36",
        "idusuario" : 3
      },

      ...      

      "25": {
        "costo" : 3500140.00,
        "ubicacion" : "Norte",
        "direccion" : {
          "idDireccion" : 2,
          "calle" : "48",
          "numero" : "478",
          "cruzamientos" : "53 y 56",
          "colonia" : "Francisco de Montejo"
        },
        "metros" : 100,
        "banos" : 4,
        "habitaciones" : 4,
        "fechaPublicacion" : "2009-03-02 23:13:36",
        "fechaCreacion" : "2009-03-02 23:13:36",
        "idusuario" : 3
      }
    }         
```  

#### <a name="crearPropiedad"></a> Crear propiedades
* URI :
  * `POST /api/propiedades`
* Descripción:
  * Se utiliza para realizar el registro de una propiedad dentro del sistema.
* Campos requeridos:
```
  {
    "usuario" : String,
    "password" : String,
    "costo" : Float con dos puntos decimales,
    "ubicacion" : String,
    "direccion" : {
      "calle" : String,
      "numero" : String,
      "cruzamientos" : String,
      "colonia" : String
    },
    "metros" : Integer,
    "banos" : float,
    "habitaciones" : Integer,
    "fechaPublicacion" : String (Y-m-d H:i:s),
    "fechaCreacion" : String (Y-m-d H:i:s),
  }
```
* Respuesta (200OK):    
```json
  {
    "3": {
      "costo" : 1000000.00,
      "ubicacion" : "Oriente",
      "direccion" : {
        "idDireccion" : 5,
        "calle" : "48",
        "numero" : "478",
        "cruzamientos" : "53 y 56",
        "colonia" : "México Oriente"
      },
      "metros" : 56,
      "banos" : 2.5,
      "habitaciones" : 3,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36",
      "idusuario" : 3
    }
  }     
```  
* Ejemplo de request: `POST /api/propiedades`
  * Request:
```json
    {
      "usuario" : "a16016263@alumnos.uady.mx",
      "password" : "123123",
      "costo" : 1000000.00,
      "ubicacion" : "Oriente",
      "direccion" : {
        "calle" : "48",
        "numero" : "478",
        "cruzamientos" : "53 y 56",
        "colonia" : "México Oriente"
      },
      "metros" : 56,
      "banos" : 2.5,
      "habitaciones" : 3,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36"
    }
```
  * Respuesta (200OK):
```json
    {
      "1": {
        "costo" : 1000000.00,
        "ubicacion" : "Oriente",
        "direccion" : {
          "idDireccion" : 4,
          "calle" : "48",
          "numero" : "478",
          "cruzamientos" : "53 y 56",
          "colonia" : "México Oriente"
        },
        "metros" : 56,
        "banos" : 2.5,
        "habitaciones" : 3,
        "fechaPublicacion" : "2009-03-02 23:13:36",
        "fechaCreacion" : "2009-03-02 23:13:36",
        "idusuario" : 3
      }
    }     
```

#### <a name="crearCSV"></a> Crear propiedades desde CSV

#### <a name="actualizarPropiedad"></a> Actualizar propiedad

#### <a name="eliminarPropiedad"></a> Eliminar propiedad


## Criterios de calidad la API
Criterios de calidad clasificados por su enfoque de calidad

### Fiabilidad
-Toda funcionalidad del sistema y transacción de negocio va a responder al usuario en menos de 5 segundos.
-Los datos modificados en la base de datos van a ser actualizados en menos de 2 segundos.

### Eficiencia
-Al momento de realizar un proceso, este no debe de sobrepasar el 20% del uso del CPU.
-La carga en memoria no debe superar a los 20 Kb, 30 Kb.

### Seguridad
-El sistema va a estar protegido contra intrusiones externas o usuarios malintencionados.

### Usabilidad
-El tiempo de aprendizaje del sistema por un usuario va a ser menor a 2 horas.
-La tasa de errores cometidos por el usuario va a ser menor del 1% de las transacciones  totales ejecutadas en el sistema.
-El sistema va a proporcionar mensajes de error que sean informativos y orientados a usuario final.

### Dependabilidad
-El tiempo para iniciar o reiniciar el sistema va a ser menor a 2 minutos.
-El promedio de duración de fallas va a ser menor a 2 minutos.

### Compatibilidad/Portabilidad
-Las pruebas de software van a ser gestionadas con una herramienta de gestión de software testing.
-El programa va a estar escrito y preparado de manera comprensible para cualquier desarrollador nuevo que quiera comprender el programa.
