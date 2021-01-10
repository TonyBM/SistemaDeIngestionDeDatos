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
import java.util.StringTokenizer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.transaction.Transactional;
import mx.uady.ingestionDeDatos.config.DecodedToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

import mx.uady.ingestionDeDatos.exception.NotFoundException;
import mx.uady.ingestionDeDatos.model.Usuario;
import mx.uady.ingestionDeDatos.model.request.UsuarioRequest;
import mx.uady.ingestionDeDatos.repository.UsuarioRepository;
import mx.uady.ingestionDeDatos.config.JwtTokenUtil;
import mx.uady.ingestionDeDatos.repository.PropiedadRepository;
import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.Direccion;
import mx.uady.ingestionDeDatos.model.request.PropiedadRequest;
import mx.uady.ingestionDeDatos.repository.DireccionRepository;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired DireccionRepository direccionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

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

        Propiedad propiedad = opt.get();

        direccionRepository.deleteById(propiedad.getIdDireccion());
        propiedadRepository.deleteById(id);
        return "La propiedad "+id+" ha sido borrada";

    }

    public List<Propiedad> cargaMasiva(MultipartFile archivoPropiedades) {
        ArrayList<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
        try{
            String contenido = new String(archivoPropiedades.getBytes());
            String[] lineas = contenido.split("\n");
            StringTokenizer atributos;
            Propiedad propiedadAux;
            Direccion direccionAux;
            for(String linea: lineas) {

                propiedadAux = new Propiedad();
            direccionAux = new Direccion();
            atributos = new StringTokenizer(linea, ",");
            propiedadAux.setNombre(atributos.nextToken());
            propiedadAux.setPrecio(Float.parseFloat(atributos.nextToken()));
            propiedadAux.setBanos(Integer.parseInt(atributos.nextToken()));
            propiedadAux.setUbicacion(atributos.nextToken());
                
            direccionAux.setCalle(atributos.nextToken());
            direccionAux.setNumero(atributos.nextToken());
            direccionAux.setCruzamientos(atributos.nextToken());
            direccionAux.setColonia(atributos.nextToken());
            direccionAux.setCodigoPostal(atributos.nextToken());
            propiedadAux.setIdDireccion(this.crearDireccionParaCasa(direccionAux).getIdDireccion());

            propiedadAux.setFechaPublicacion(new Date());
            propiedadAux.setNumHabitaciones(Integer.parseInt(atributos.nextToken()));
            propiedadAux.setIdUsuario(Integer.parseInt(atributos.nextToken()));
            propiedadAux.setMetrosCuadrados(Float.parseFloat(atributos.nextToken()));
            propiedadAux.setFecha_creacion(new Date());
            listaPropiedades.add(propiedadAux);
            }


        } catch (Exception e) {
            return new ArrayList<Propiedad>();
        };
        listaPropiedades.forEach((propiedad) -> propiedadRepository.save(propiedad));

        return listaPropiedades;
    }

    @Transactional
    public Propiedad crearPropiedad(PropiedadRequest request, Integer page, Integer idDireccion) {

        Propiedad propiedadCreada = new Propiedad();

        propiedadCreada.setNombre(request.getNombre());
        propiedadCreada.setPrecio(request.getPrecio());
        propiedadCreada.setBanos(request.getBanos());
        propiedadCreada.setUbicacion(request.getUbicacion());
        propiedadCreada.setIdDireccion(idDireccion);
        propiedadCreada.setFechaPublicacion(new Date());
        propiedadCreada.setNumHabitaciones(request.getNumHabitaciones());
        propiedadCreada.setIdUsuario(request.getIdUsuario());
        propiedadCreada.setMetrosCuadrados(request.getMetrosCuadrados());
        propiedadCreada.setFecha_creacion(new Date());
        
        List<Propiedad> propiedades = this.getPropiedadesFiltradas("name", request.getNombre(), page);

        if(propiedades.isEmpty()) {
            Propiedad propiedadGuardada = propiedadRepository.save(propiedadCreada);
            return propiedadGuardada;
        }
        else {
            throw new RuntimeException("Propiedad repetida.");
        }
        
    }

    private Direccion crearDireccionParaCasa(Direccion direccion) {
       
        Direccion direccionGuardada = direccionRepository.save(direccion);
        
        return direccionGuardada;
    }
    @Transactional
    public Propiedad editarPropiedad(Integer idPropiedad, PropiedadRequest request){
        return propiedadRepository.findById(idPropiedad)
        .map(propiedad -> {
            propiedad.setNombre(request.getNombre());
            propiedad.setPrecio(request.getPrecio());
            propiedad.setBanos(request.getBanos());
            propiedad.setUbicacion(request.getUbicacion());
            propiedad.setFechaPublicacion(request.getFechaPublicacion());
            propiedad.setNumHabitaciones(request.getNumHabitaciones());
            propiedad.setMetrosCuadrados(request.getMetrosCuadrados());
            propiedad.setFecha_creacion(request.getFechaCreacion());
            return propiedadRepository.save(propiedad);
        })
        .orElseThrow(() -> new NotFoundException("La entidad propiedad no pudo ser encontrada."));
    }
}
