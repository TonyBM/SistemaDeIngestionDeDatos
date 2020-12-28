package mx.uady.ingestionDeDatos.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import mx.uady.ingestionDeDatos.model.Alumno;

@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Integer> {
    public boolean existsByNombre(String nombre);
}