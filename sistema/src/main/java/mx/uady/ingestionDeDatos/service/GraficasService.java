package mx.uady.ingestionDeDatos.service;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.ingestionDeDatos.model.Ubicacion;
import mx.uady.ingestionDeDatos.repository.PropiedadRepository;
import mx.uady.ingestionDeDatos.exception.NotFoundException;
import mx.uady.ingestionDeDatos.model.Grafica;

@Service
public class GraficasService {

    @Autowired
    private PropiedadRepository PropiedadRepository;

    @Transactional
    public Grafica obtenerPropiedadesPorUbicacion() {
        Ubicacion[] ubicaciones = Ubicacion.values();
        Integer[] values = new Integer[ubicaciones.length];
        Ubicacion[] keys = new Ubicacion[ubicaciones.length];
        Integer index = 0;


        for (Ubicacion ubicacion : ubicaciones) {
            values[index] = PropiedadRepository.countByUbicacion(ubicacion);
            keys[index] = ubicacion;
            index++;
        }

        return new Grafica<Ubicacion, Integer>(keys, values);
    }

    @Transactional
    public Grafica obtenerPrecioPorUbicaciones() {
        Ubicacion[] ubicaciones = Ubicacion.values();
        Double[] precios = new Double[ubicaciones.length];
        Ubicacion[] keys = new Ubicacion[ubicaciones.length];
        Integer index = 0;

        for (Ubicacion ubicacion : ubicaciones) {

            precios[index] = PropiedadRepository.promedioPorUbicacion(ubicacion.name());
            keys[index] = ubicacion;
            index++;
        }

        return new Grafica<Ubicacion, Double>(keys, precios);
    }

    @Transactional
    public Grafica obtenerPrecioPorMetrosCuadrados() {
        Double[] metros = PropiedadRepository.findAllDistinctMetrosCuadrados();
        Double[] precios = new Double[metros.length];
        Double[] keys = new Double[metros.length];
        Integer index = 0;

        for (Double metro : metros) {
            precios[index] = PropiedadRepository.promedioPorMetrosCuadrados(metro);
            keys[index] = metro;
            index++;
        }

        return new Grafica<Double, Double>(keys, precios);
    }

}