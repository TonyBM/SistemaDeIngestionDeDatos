package mx.uady.ingestionDeDatos.repository;

import mx.uady.ingestionDeDatos.model.Direccion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer>{
    public Direccion findByIdDireccion(Integer id);
}
