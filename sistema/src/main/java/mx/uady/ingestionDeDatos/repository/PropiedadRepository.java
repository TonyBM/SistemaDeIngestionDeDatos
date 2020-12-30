package mx.uady.ingestionDeDatos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.ingestionDeDatos.model.Propiedad;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Pageable;

@Repository
public interface PropiedadRepository extends PagingAndSortingRepository<Propiedad, Integer> {
    public List<Propiedad> findByNombre(String name, Pageable pageable);
    public List<Propiedad> findByBanos(Integer banos, Pageable pageable);
    public List<Propiedad> findByPrecio(Float precio, Pageable pageable);
    public List<Propiedad> findByUbicacion(String ubicacion, Pageable pageable);
    public List<Propiedad> findByNumHabitaciones(Integer numHabitaciones, Pageable pageable);
    public List<Propiedad> findByMetrosCuadrados(Float metrosCuadrados, Pageable pageable);    
}