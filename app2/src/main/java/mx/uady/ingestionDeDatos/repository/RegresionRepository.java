package mx.uady.ingestionDeDatos.repository;

import java.util.List;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import mx.uady.ingestionDeDatos.model.Regresion;

@Repository
public interface RegresionRepository extends JpaRepository<Regresion,Integer> {
        public List<Regresion> findByFechaRegresion(Date fechaRegresion);
}