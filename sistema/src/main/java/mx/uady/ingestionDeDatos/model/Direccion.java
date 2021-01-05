package mx.uady.ingestionDeDatos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "direcciones") 
public class Direccion {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDireccion;
    
    @Column(name = "calle")
    @Getter
    @Setter
    private String calle;
    
    @Column(name = "numero")
    @Getter
    @Setter
    private String numero;
    
    @Column(name = "cruzamientos")
    @Getter
    @Setter
    private String cruzamientos;
    
    @Column(name = "colonia")
    @Getter
    @Setter
    private String colonia;
    
    @Column(name = "codigoPostal")
    @Getter
    @Setter
    private String codigoPostal;
    
    public Direccion() {
        
    }
    
}
