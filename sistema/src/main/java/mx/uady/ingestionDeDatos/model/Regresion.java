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
@Table(name = "regresiones_lineales")
public class Regresion {
    
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_regresion;
    
    @Column(name = "q1")
    @Getter
    @Setter
    private Double q1;

    @Column(name = "q2")
    @Getter
    @Setter
    private Double q2;

    @Column(name = "q3")
    @Getter
    @Setter
    private Double q3;

    @Column(name = "intercepto")
    @Getter
    @Setter
    private Double intercepto;
    
    @Column(name = "fecha_regresion")
    @Getter
    @Setter
    private Date fechaRegresion;

    public Regresion() {
    }

}