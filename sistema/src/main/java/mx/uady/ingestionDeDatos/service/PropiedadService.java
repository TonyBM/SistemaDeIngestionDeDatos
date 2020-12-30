package mx.uady.ingestionDeDatos.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.Base64;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.uady.ingestionDeDatos.exception.NotFoundException;
import mx.uady.ingestionDeDatos.model.Usuario;
import mx.uady.ingestionDeDatos.model.request.UsuarioRequest;
import mx.uady.ingestionDeDatos.repository.UsuarioRepository;
import mx.uady.ingestionDeDatos.config.JwtTokenUtil;
import org.springframework.data.domain.PageRequest;
import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

import mx.uady.ingestionDeDatos.repository.PropiedadRepository;
import mx.uady.ingestionDeDatos.model.Propiedad;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;
    
    private final Integer PAGE_SIZE = 5;

    public Page<Propiedad> getPropiedades(Integer page) {
        return propiedadRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
    }

    public List<Propiedad> getPropiedadesFiltradas(String type, String value, Integer page) {

        switch(type) {
            case "name":
                return propiedadRepository.findByNombre(value, PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad"))); 
            case "precio":
                return propiedadRepository.findByPrecio(Float.parseFloat(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "baños":
                return propiedadRepository.findByBanos(Integer.parseInt(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "ubicacion":
                return propiedadRepository.findByUbicacion(value, PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "habitaciones":
                return propiedadRepository.findByNumHabitaciones(Integer.parseInt(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "area":
                return propiedadRepository.findByMetrosCuadrados(Float.parseFloat(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            default:
                return new ArrayList<Propiedad>();
        }
    }
}