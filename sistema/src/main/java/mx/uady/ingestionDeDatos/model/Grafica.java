package mx.uady.ingestionDeDatos.model;


import lombok.Getter;
import lombok.Setter;

public class Grafica<T, V> {

    @Getter
    @Setter
    private T[] XAxisValues;

    @Getter
    @Setter
    private V[] YAxisValues;

    public Grafica(T[] XAxisValues, V[] YAxisValues) {
        this.XAxisValues = XAxisValues;
        this.YAxisValues = YAxisValues;
    }
}