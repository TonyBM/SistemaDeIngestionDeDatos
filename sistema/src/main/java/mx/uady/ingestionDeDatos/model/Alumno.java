package mx.uady.sicei.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "alumnos")
public class Alumno {

    // POJO: Plain Java Object. No existe ninguna accion
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // f(x) = y
    private Integer id;

    @Column
    private String nombre;


    @OneToOne
    @JoinColumn(name = "id_usuario") // alumno.id_usuario
    private Usuario usuario;

    public Alumno() {
    }

    public Alumno(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Alumno id(Integer id) {
        this.id = id;
        return this;
    }

    public Alumno nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            "}";
    }

}
