package mx.uady.ingestionDeDatos.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import mx.uady.ingestionDeDatos.exception.NotFoundException;
import mx.uady.ingestionDeDatos.model.Alumno;
import mx.uady.ingestionDeDatos.model.request.AlumnoRequest;
import mx.uady.ingestionDeDatos.service.AlumnoSerivce;

@RestController
@RequestMapping("/api")
public class AlumnoRest {

    @Autowired
    private AlumnoSerivce alumnoService;

    @GetMapping("/alumnos")
    public ResponseEntity<List<Alumno>> getAlumnos() {
        return ResponseEntity.ok().body(alumnoService.getAlumnos());
    }

    @GetMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> getAlumno(@PathVariable Integer id){

        return ResponseEntity.ok().body(alumnoService.getAlumno(id));
    }

    @PutMapping("/alumnos/{id}")
    public ResponseEntity<Alumno> putAlumnos(@PathVariable Integer id, @RequestBody AlumnoRequest request)
            throws URISyntaxException {

        Alumno alumno = alumnoService.editarAlumno(id, request);

        return ResponseEntity
            .ok()
            .body(alumno);
    }

    @DeleteMapping("/alumnos/{id}")
    public ResponseEntity deleteAlumno(@PathVariable Integer id){

        String response = alumnoService.borrarAlumno(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }


}