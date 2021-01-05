/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.uady.ingestionDeDatos.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class DireccionRequest {
    
    @NotNull
    @Size(min = 2, max = 50)
    @NotEmpty
    @Getter
    @Setter
    private String calle;
    
    @NotNull
    @Size(min = 1, max = 20)
    @NotEmpty
    @Getter
    @Setter
    private String numero;
    
    @NotNull
    @Size(min = 3, max = 50)
    @NotEmpty
    @Getter
    @Setter
    private String cruzamientos;
    
    @NotNull
    @Size(min = 3, max = 50)
    @NotEmpty
    @Getter
    @Setter
    private String colonia;
    
    @NotNull
    @Size(min = 5, max = 6)
    @NotEmpty
    @Getter
    @Setter
    private String codigoPostal;
}
