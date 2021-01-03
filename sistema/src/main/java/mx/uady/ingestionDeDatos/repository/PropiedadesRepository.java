package mx.uady.ingestionDeDatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.ingestionDeDatos.model.Propiedad;

@Repository
public interface PropiedadesRepository extends JpaRepository<Propiedad, Integer> {

}