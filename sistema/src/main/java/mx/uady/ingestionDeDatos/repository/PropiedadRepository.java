package mx.uady.ingestionDeDatos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.Ubicacion;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad,Integer> {

        public List<Propiedad> findByNombre(String name);
        public Integer countByUbicacion(Ubicacion ubicacion);

        @Query(value = "SELECT IFNULL(AVG(precio), 0) as promedio FROM propiedades WHERE ubicacion = ?1", nativeQuery = true)
        public Double promedioPorUbicacion(String ubicacion);
}