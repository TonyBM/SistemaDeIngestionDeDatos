package mx.uady.ingestionDeDatos.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class FindPropiedadRequest {

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String type;

    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String value;
}