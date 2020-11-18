# # Home Run Software
## Indice
1. [Introducción](#intro)
2. [Arquitectura](#arqui)
3. [Documentación de la API](#api)
4. [Criterios de Calidad](#calidad)
<a name="intro"></a>
## Introducción

### Propósito
Describir la arquitectura que será utilizada para el desarrollo del proyecto "Home Run Software" a traves de la conceptualización de cada uno de sus componentes. 
### Alcance
El presente documento será de utilidad para los desarrolladores de "Home Run Software". Se espera que al revisarlo se entiendan todas las componentes del proyecto, asi cómo la relación que existe entre ellas. Para ello, será necesario describir rigurosamente todos los procesos y entidades del sistema a través de diagramas UML. Teniendo claro el diseño del software, se podrá continuar con la fase de construcción del software.
### Documentos de referencia

<a name="arqui"></a>
## Arquitectura

### Descripción de la Arquitectura utilizada
La arquitectura por capas es una de las formas más simples de los patrones arquitectonicos de software. En esta arquitectura los componentes son organizados en capas horizontales. Estas capas son independientes y se comunican unicamente con las capas adjacentes. Una arquitectura por capas general puede constar de n-capas sin embargo la implementación más comun consta de 4 capas que son: Capa de Presentación,Capa de Logica de negocio (Bussiness Layer), Capa de Persistencia y Capa de Almacenamiento. Las ventajas de usar esta arquitectura es que es sencilla de implementar y probar. Las desventajas es que la aplicación se vuelve dificil de modificar y escalar.
### Definición teórica de Microservicios
Es un acercamineto para desarrollar una aplicacion como un conjunto de pequeños servicios, cada uno corriendo sus propios procesos y comunicando con mecanismos ligeros. Cada microservicio debe ser despeglable y escalable de manera independiente y cada servicio tiene limites definidos permitiendo que los diferentes servicios sean escritos en diferentes lenguajes de programación.

### Diagrama de arquitectura con descripción
![alt text](diagramaArquitectura.jpg "Title")
En este diagrama se pueden observar los componentes principales del sistema. El componente llamado "App" representa la api a traves de la  cual se ingresará la información de las propiedades. La "App" luego almacena la información en la base de datos. Al almacenarse información en la base de datos la información es tomada por la "App de ML" que usa un algoritmo de regresión lineal para poder predecir valores de hogares por zonas. Los resultados de este algoritmo es luego almacenado en la base de datos
### Diagrama de secuencia
![alt text](buscarFiltrado.jpg "Title")
![alt text](createCasa.jpg "Title")
![alt text](cargaMasiva.jpg "Title")
![alt text](deleteCasa.png "Title")
![alt text](getCasa.jpg "Title")
![alt text](getCasas.jpg "Title")
![alt text](app2.jpg "Title")

### Diagrama Entidad Relación
### Descripción Entidades

<a name="api"></a>
## Documentación de la API
### Endpoints
* [Listar propiedades](#listarPropiedades) : `GET /api/propiedades/`
* [Listar propiedades paginadas](#listarPaginadas) : `GET /api/propiedades/{offsetValue}/{cantidad}`
* [Listar propiedad especifica](#listarEspecifica) : `GET /api/propiedades/{id}`
* [Crear propiedad](#crearPropiedad) : `POST /api/propiedades/`
* [Crear propiedades desde csv](#crearCSV) : `POST /api/propiedades/csv`
* [Actualizar propiedad](#actualizarPropiedad) : `PUT /api/propiedades/{id}`
* [Eliminar propiedad](#eliminarPropiedad) : `DELETE /api/propiedades/{id}`

#### <a name="listarPropiedades"></a> Listar propiedades
* URI :
  * `GET /api/propiedades/`
* Descripción:
  * Se utiliza para obtener el listado de las propiedades registradas en el sistema
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
* URI :
  * `POST /api/propiedades/csv`
* Descripción:
  * Se utiliza para realizar el registro de propiedades dentro del sistema por medio de un archivo csv.
* Campos requeridos en cada entrada del csv:
```
    "usuario" : String,
    "password" : String,
    "costo" : Float con dos puntos decimales,
    "ubicacion" : String,
    "calle" : String,
    "numero" : String,
    "cruzamientos" : String,
    "colonia" : String,
    "metros" : Integer,
    "banos" : float,
    "habitaciones" : Integer,
    "fechaPublicacion" : String (Y-m-d H:i:s),
    "fechaCreacion" : String (Y-m-d H:i:s),
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
    },
    "4": {
      "costo" : 1050000.00,
      "ubicacion" : "Poniente",
      "direccion" : {
        "idDireccion" : 5,
        "calle" : "58",
        "numero" : "121",
        "cruzamientos" : "63 y 66",
        "colonia" : "México Poniente"
      },
      "metros" : 56,
      "banos" : 2.5,
      "habitaciones" : 3,
      "fechaPublicacion" : "2009-03-02 23:13:36",
      "fechaCreacion" : "2009-03-02 23:13:36",
      "idusuario" : 3
    },
    ...
  }     
```  
* Ejemplo de request: `POST /api/propiedades/csv`
  * Request:
```
    POST /api/propiedades/csv HTTP/1.1
    Host: localhost:8080
    User-Agent: insomnia/2020.4.2
    Content-Type: text/csv
    Accept: */*
    Content-Length: 1303
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
      },
      "2": {
        "costo" : 1050000.00,
        "ubicacion" : "Poniente",
        "direccion" : {
          "idDireccion" : 5,
          "calle" : "58",
          "numero" : "121",
          "cruzamientos" : "63 y 66",
          "colonia" : "México Poniente"
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

#### <a name="actualizarPropiedad"></a> Actualizar propiedad
* URI :
  * `PUT /api/propiedades/{id}`
* Descripción:
  * Se utiliza para modificar la información de una propiedad especifica registrada
  en el sistema.
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
* Ejemplo de request: `PUT /api/propiedades/1`
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

#### <a name="eliminarPropiedad"></a> Eliminar propiedad
* URI :
  * `DELETE /api/propiedades/{id}`
* Descripción:
  * Se utiliza para eliminar la información de una propiedad especifica registrada
  en el sistema.
* Respuesta (200OK):    
```json    
  {
    "respuesta": "La propiedad {id} ha sido eliminada"
  }
```       
* Ejemplo de request: `DELETE /api/propiedades/1`
  * Respuesta (200OK):
```json    
  {
    "respuesta": "La propiedad 1 ha sido eliminada"
  }
```  

<a name="calidad"></a>
## Criterios de Calidad de la API
Criterios de calidad clasificados por su enfoque de calidad

### Fiabilidad
- Toda funcionalidad del sistema y transacción de negocio va a responder al usuario en menos de 5 segundos.
- Los datos modificados en la base de datos van a ser actualizados en menos de 2 segundos.

### Eficiencia
- Al momento de realizar un proceso, este no debe de sobrepasar el 20% del uso del CPU.
- La carga en memoria no debe superar a los 20 Kb, 30 Kb.

### Seguridad
- El sistema va a estar protegido contra intrusiones externas o usuarios malintencionados.

### Usabilidad
- El tiempo de aprendizaje del sistema por un usuario va a ser menor a 2 horas.
- La tasa de errores cometidos por el usuario va a ser menor del 1% de las transacciones  totales ejecutadas en el sistema.
- El sistema va a proporcionar mensajes de error que sean informativos y orientados a usuario final.

### Dependabilidad
- El tiempo para iniciar o reiniciar el sistema va a ser menor a 2 minutos.
- El promedio de duración de fallas va a ser menor a 2 minutos.

### Compatibilidad/Portabilidad
- Las pruebas de software van a ser gestionadas con una herramienta de gestión de software testing.
- El programa va a estar escrito y preparado de manera comprensible para cualquier desarrollador nuevo que quiera comprender el programa.
