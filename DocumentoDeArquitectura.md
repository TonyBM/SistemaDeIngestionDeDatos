# # Home Run Software
## Indice
## Arquitectura

### Descripción de la Arquitectura utilizada
La arquitectura por capas es una de las formas más simples de los patrones arquitectonicos de software. En esta arquitectura los componentes son organizados en capas horizontales. Estas capas son independientes y se comunican unicamente con las capas adjacentes. Una arquitectura por capas general puede constar de n-capas sin embargo la implementación más comun consta de 4 capas que son: Capa de Presentación,Capa de Logica de negocio (Bussiness Layer), Capa de Persistencia y Capa de Almacenamiento. Las ventajas de usar esta arquitectura es que es sencilla de implementar y probar. Las desventajas es que la aplicación se vuelve dificil de modificar y escalar. 
### Definición teórica de Microservicios

### Diagrama de arquitectura con descripción
### Diagrama de secuencia
### Diagrama Entidad Relación
### Descripción Entidades

## Documentación de# # Home Run Software
## Indice
## Arquitectura

### Descripción de la Arquitectura utilizada
La arquitectura por capas es una de las formas más simples de los patrones arquitectonicos de software. En esta arquitectura los componentes son organizados en capas horizontales. Estas capas son independientes y se comunican unicamente con las capas adjacentes. Una arquitectura por capas general puede constar de n-capas sin embargo la implementación más comun consta de 4 capas que son: Capa de Presentación,Capa de Logica de negocio (Bussiness Layer), Capa de Persistencia y Capa de Almacenamiento. Las ventajas de usar esta arquitectura es que es sencilla de implementar y probar. Las desventajas es que la aplicación se vuelve dificil de modificar y escalar. 
### Definición teórica de Microservicios
Es un acercamineto para desarrollar una aplicacion como un conjunto de pequeños servicios, cada uno corriendo sus propios procesos y comunicando con mecanismos ligeros. Cada microservicio debe ser despeglable y escalable de manera independiente y cada servicio tiene limites definidos permitiendo que los diferentes servicios sean escritos en diferentes lenguajes de programación.

### Diagrama de arquitectura con descripción
### Diagrama de secuencia
### Diagrama Entidad Relación
### Descripción Entidades

## Documentación de la API
## Criterios de calidad la API
## Criterios de calidad
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


