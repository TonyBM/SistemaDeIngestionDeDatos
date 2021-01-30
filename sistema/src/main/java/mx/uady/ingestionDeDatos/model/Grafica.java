package mx.uady.ingestionDeDatos.model;


import lombok.Getter;
import lombok.Setter;

public class Grafica<T> {

    @Getter
    @Setter
    private T[] XAxisValues;

    @Getter
    @Setter
    private Integer[] YAxisValues;

    public Grafica(T[] XAxisValues, Integer[] YAxisValues) {
        this.XAxisValues = XAxisValues;
        this.YAxisValues = YAxisValues;
    }
}