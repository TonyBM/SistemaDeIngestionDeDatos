package mx.uady.ingestionDeDatos.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "propiedades")
public class Propiedad {
    
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPropiedad;
    
    @Column(name = "nombre")
    @Getter
    @Setter
    private String nombre;

    @Column(name = "precio")
    @Getter
    @Setter
    private Float precio;

    @Column(name = "banos")
    @Getter
    @Setter
    private Integer banos;

    @Column(name = "ubicacion")
    @Getter
    @Setter
    private String ubicacion;

    @Column(name = "id_direccion")
    @Getter
    @Setter
    private Integer idDireccion;
    
    @Column(name = "fecha_publicacion")
    @Getter
    @Setter
    private Date fechaPublicacion;
    
    @Column(name = "num_habitaciones")
    @Getter
    @Setter
    private Integer numHabitaciones;
    
    @Column(name = "id_usuario")
    @Getter
    @Setter
    private Integer idUsuario;

    @Column(name = "metros_cuadrados")
    @Getter
    @Setter
    private Float metrosCuadrados;

    @Column(name = "fecha_creacion")
    @Getter
    @Setter
    private Date fecha_creacion;

    public Propiedad() {
    }

}