package mx.uady.ingestionDeDatos.model.request;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PrediccionRequest {
    
    
    @NotNull
    @Getter
    @Setter
    private Integer banos;
    
    @NotNull
    @Getter
    @Setter
    private Integer numHabitaciones;
    
    @NotNull
    @Getter
    @Setter
    private Float metrosCuadrados;
    
    public PrediccionRequest() {

    }
}
