package mx.uady.ingestionDeDatos.model.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PropiedadRequest {

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    @Getter
    @Setter
    private String nombre;
    
    @NotNull
    @Getter
    @Setter
    private Float precio;
    
    @NotNull
    @Getter
    @Setter
    private Integer banos;
    
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 11)
    @Getter
    @Setter
    private String ubicacion;
    
    @NotNull
    @Getter
    @Setter
    private DireccionRequest direccion;
    
    @Getter
    @Setter
    private Date fechaPublicacion; //yyyy-MM-dd hh:mm:ss
    
    @NotNull
    @Getter
    @Setter
    private Integer numHabitaciones;
    
    @NotNull
    @Getter
    @Setter
    private Integer idUsuario;
    
    @NotNull
    @Getter
    @Setter
    private Float metrosCuadrados;
    
    @Getter
    @Setter
    private Date fechaCreacion; //yyyy-MM-dd hh:mm:ss
    
    public PropiedadRequest() {

    }
}
