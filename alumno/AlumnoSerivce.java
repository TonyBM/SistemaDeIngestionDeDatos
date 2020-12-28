package mx.uady.ingestionDeDatos.service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import mx.uady.ingestionDeDatos.exception.NotFoundException;

import mx.uady.ingestionDeDatos.model.Alumno;
import mx.uady.ingestionDeDatos.model.request.AlumnoRequest;
import mx.uady.ingestionDeDatos.model.Usuario;
import mx.uady.ingestionDeDatos.repository.AlumnoRepository;
import mx.uady.ingestionDeDatos.repository.UsuarioRepository;

@Service
public class AlumnoSerivce {

    @Autowired
    private AlumnoRepository alumnoRepository;
    @Autowired 
    UsuarioRepository usuarioRepository;

    public List<Alumno> getAlumnos() {

        List<Alumno> alumnos = new LinkedList<>();
        alumnoRepository.findAll().iterator().forEachRemaining(alumnos::add); // SELECT(id, nombre)
        
        return alumnos;
    }
    
    @Transactional
    public Alumno crearAlumno(AlumnoRequest request) {

        Alumno alumno = new Alumno();
        Usuario usuario = new Usuario();

        usuario.setUsuario(request.getNombre());
        usuario.setPassword(request.getPassword());
        String secret = UUID.randomUUID().toString();
        usuario.setSecret(secret);
        usuario = usuarioRepository.save(usuario);

        alumno.setNombre(request.getNombre());
        alumno.setUsuario(usuario);
        alumno = alumnoRepository.save(alumno); // INSERT

        return alumno;
    }

    public Alumno getAlumno(Integer AlumnoID) {

        return alumnoRepository.findById(AlumnoID)
            .orElseThrow(() -> new NotFoundException("La entidad alumno no pudo ser encontrada."));
    }

    public Alumno editarAlumno(Integer AlumnoID, AlumnoRequest request) {

        return alumnoRepository.findById(AlumnoID)
        .map(alumno -> {
            alumno.setNombre(request.getNombre());
            return alumnoRepository.save(alumno);
        })
        .orElseThrow(() -> new NotFoundException("La entidad alumno no pudo ser encontrada."));
    }

    public String borrarAlumno(Integer AlumnoID) {

        List<Alumno> alumnos = new LinkedList<>();
        alumnoRepository.findAll().iterator().forEachRemaining(alumnos::add);
        if(alumnos.size() < AlumnoID || AlumnoID <= 0){
            throw new NotFoundException("La entidad alumno no pudo ser encontrada.");
        }
        alumnoRepository.deleteById(AlumnoID);
        return "Alumno "+AlumnoID+" Borrado";

    }

    public boolean alumnoExiste(AlumnoRequest request) {
        return alumnoRepository.existsByNombre(request.getNombre());
    }
    
}
