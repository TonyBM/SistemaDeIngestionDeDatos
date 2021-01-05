package mx.uady.ingestionDeDatos.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.Base64;
import java.util.Optional;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

import mx.uady.ingestionDeDatos.exception.NotFoundException;
import mx.uady.ingestionDeDatos.model.Usuario;
import mx.uady.ingestionDeDatos.model.request.UsuarioRequest;
import mx.uady.ingestionDeDatos.repository.UsuarioRepository;
import mx.uady.ingestionDeDatos.config.JwtTokenUtil;
import mx.uady.ingestionDeDatos.repository.PropiedadRepository;
import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.request.PropiedadRequest;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    private final Integer PAGE_SIZE = 5;

    public Page<Propiedad> getPropiedades(Integer page) {
        return propiedadRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
    }

    public Optional<Propiedad> getPropiedadById(Integer id) {
        return propiedadRepository.findById(id);
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

    public String borrarPropiedad(Integer id) {

        Optional<Propiedad> opt = propiedadRepository.findById(id);

        if(!opt.isPresent()){
            throw new NotFoundException("La propiedad no pudo ser encontrada.");
        }

        propiedadRepository.deleteById(id);
        return "La propiedad "+id+" ha sido borrada";

    }

    @Transactional
    public Propiedad crearPropiedad(PropiedadRequest request, Integer idDireccion) {
        Propiedad propiedadCreada = new Propiedad();

        propiedadCreada.setNombre(request.getNombre());
        propiedadCreada.setPrecio(request.getPrecio());
        propiedadCreada.setBanos(request.getBanos());
        propiedadCreada.setUbicacion(request.getUbicacion());
        propiedadCreada.setIdDireccion(idDireccion);
        propiedadCreada.setFechaPublicacion(request.getFechaPublicacion());
        propiedadCreada.setNumHabitaciones(request.getNumHabitaciones());
        propiedadCreada.setIdUsuario(request.getIdUsuario());
        propiedadCreada.setMetrosCuadrados(request.getMetrosCuadrados());
        propiedadCreada.setFecha_creacion(request.getFechaCreacion());

        Propiedad propiedadGuardada = propiedadRepository.save(propiedadCreada);

        return propiedadGuardada;
    }
}
