package mx.uady.ingestionDeDatos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.ingestionDeDatos.model.Propiedad;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad,Integer> {
        public List<Propiedad> findByNombre(String name);
}