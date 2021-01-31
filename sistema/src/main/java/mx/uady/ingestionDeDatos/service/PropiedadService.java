package mx.uady.ingestionDeDatos.service;

import java.lang.*;
import java.io.*;

import java.io.UnsupportedEncodingException;
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
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import mx.uady.ingestionDeDatos.config.DecodedToken;

import org.renjin.script.RenjinScriptEngine;
import org.renjin.sexp.*;
import org.renjin.primitives.matrix.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.*;
import java.util.Optional;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import javax.script.ScriptException;
import java.lang.InterruptedException;

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
import mx.uady.ingestionDeDatos.model.request.PrediccionRequest;
import mx.uady.ingestionDeDatos.model.Ubicacion;
import mx.uady.ingestionDeDatos.repository.UsuarioRepository;
import mx.uady.ingestionDeDatos.config.JwtTokenUtil;
import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.Direccion;
import mx.uady.ingestionDeDatos.model.request.PropiedadRequest;
import mx.uady.ingestionDeDatos.model.Regresion;
import mx.uady.ingestionDeDatos.repository.RegresionRepository;
import mx.uady.ingestionDeDatos.repository.DireccionRepository;
import mx.uady.ingestionDeDatos.repository.PagedPropiedadRepository;
import mx.uady.ingestionDeDatos.repository.PropiedadRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class PropiedadService {

    @Autowired
    private PagedPropiedadRepository pagedPropiedadRepository;

    @Autowired DireccionRepository direccionRepository;
    
    @Autowired
    private PropiedadRepository propiedadRepository;

    @Autowired 
	private RegresionRepository regresionRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    private final Integer PAGE_SIZE = 5;

    public Page<Propiedad> getPropiedades(Integer page) {
        return pagedPropiedadRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
    }

    public Optional<Propiedad> getPropiedadById(Integer id) {
        return pagedPropiedadRepository.findById(id);
    }

    public List<Propiedad> getPropiedadesFiltradas(String type, String value, Integer page) {

        switch(type) {
            case "name":
                return pagedPropiedadRepository.findByNombre(value, PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "precio":
                return pagedPropiedadRepository.findByPrecio(Float.parseFloat(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "baños":
                return pagedPropiedadRepository.findByBanos(Integer.parseInt(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "ubicacion":
                return pagedPropiedadRepository.findByUbicacion(Ubicacion.valueOf(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "habitaciones":
                return pagedPropiedadRepository.findByNumHabitaciones(Integer.parseInt(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            case "area":
                return pagedPropiedadRepository.findByMetrosCuadrados(Float.parseFloat(value), PageRequest.of(page, PAGE_SIZE, Sort.by("idPropiedad")));
            default:
                return new ArrayList<Propiedad>();
        }
    }

    public String getPrediccionById(Integer id) throws InterruptedException, IOException, URISyntaxException, ScriptException{
        Optional<Propiedad> optP = pagedPropiedadRepository.findById(id);
        if(!optP.isPresent()){
            throw new NotFoundException("La propiedad no pudo ser encontrada.");
        }
        Propiedad propiedad = optP.get();
        return prediccionPropiedad(propiedad.getBanos(), propiedad.getNumHabitaciones(), propiedad.getMetrosCuadrados());
    }

    public String getPrediccionByRequest(PrediccionRequest request) throws InterruptedException, IOException, URISyntaxException, ScriptException{
        return prediccionPropiedad(request.getBanos(), request.getNumHabitaciones(), request.getMetrosCuadrados());
    }

    private String prediccionPropiedad(Integer banos, Integer habitaciones, Float metros) throws InterruptedException, IOException, URISyntaxException, ScriptException {
        Optional<Regresion> optR = regresionRepository.findById(1);

        if(!optR.isPresent()){
            throw new NotFoundException("La regresion no pudo ser encontrada.");
        }

        Regresion regresion = optR.get();
        List<Double> regresionVals = new ArrayList<Double>();
        List<Double> propiedadVals = new ArrayList<Double>();

        regresionVals.add( regresion.getIntercepto() );
		regresionVals.add( regresion.getQ1() );
        regresionVals.add( regresion.getQ2() );
        regresionVals.add( regresion.getQ3() );
        
        propiedadVals.add( Double.valueOf(banos));
		propiedadVals.add( Double.valueOf(habitaciones));
        propiedadVals.add( Double.valueOf(metros));

        Double[] regresion_ar = new Double[regresionVals.size()];
		Double[] propiedad_ar = new Double[propiedadVals.size()];
		regresion_ar = regresionVals.toArray(regresion_ar);
		propiedad_ar = propiedadVals.toArray(propiedad_ar);
        
        RenjinScriptEngine engine = new RenjinScriptEngine();
		InputStream i = PropiedadService.class.getClassLoader().getResourceAsStream("regresion-lineal.R");
		BufferedReader r = new BufferedReader(new InputStreamReader(i));
		String val = "";
        String l;
        while((l = r.readLine()) != null) {
        	val = val + l + "\n";
        } 
		i.close();
        String meanScriptContent = val;
        engine.put("regresion_values", regresion_ar);
		engine.put("new_values", propiedad_ar);
		engine.eval(meanScriptContent);
		Vector result = (Vector)engine.eval("predecir_precio(regresion_values,new_values)");
        Double value = result.getElementAsDouble(0);
        return "La prediccion de precio de la propiedad es "+value;
    }

    public String borrarPropiedad(Integer id) {

        Optional<Propiedad> opt = pagedPropiedadRepository.findById(id);

        if(!opt.isPresent()){
            throw new NotFoundException("La propiedad no pudo ser encontrada.");
        }

        Propiedad propiedad = opt.get();

        direccionRepository.deleteById(propiedad.getIdDireccion());
        //pagedPropiedadRepository.deleteById(id);
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
                propiedadAux.setUbicacion(Ubicacion.valueOf(atributos.nextToken()));
                    
                direccionAux.setCalle(atributos.nextToken());
                direccionAux.setNumero(atributos.nextToken());
                direccionAux.setCruzamientos(atributos.nextToken());
                direccionAux.setColonia(atributos.nextToken());
                direccionAux.setCodigoPostal(atributos.nextToken());
                propiedadAux.setIdDireccion(this.crearDireccionParaCasa(direccionAux).getIdDireccion());

                propiedadAux.setFechaPublicacion(new Date());
                propiedadAux.setNumHabitaciones(Integer.parseInt(atributos.nextToken()));
                propiedadAux.setIdUsuario(getUsuarioLoggeado().getId());
                propiedadAux.setMetrosCuadrados(Float.parseFloat(atributos.nextToken()));
                propiedadAux.setFecha_creacion(new Date());
                listaPropiedades.add(propiedadAux);
            }


        } catch (Exception e) {
            return new ArrayList<Propiedad>();
        };
        listaPropiedades.forEach((propiedad) -> pagedPropiedadRepository.save(propiedad));

        return listaPropiedades;
    }

    @Transactional
    public Propiedad crearPropiedad(PropiedadRequest request, Integer idDireccion) throws UnsupportedEncodingException {

        Propiedad propiedadCreada = new Propiedad();

        propiedadCreada.setNombre(request.getNombre());
        propiedadCreada.setPrecio(request.getPrecio());
        propiedadCreada.setBanos(request.getBanos());
        propiedadCreada.setUbicacion(Ubicacion.valueOf(request.getUbicacion()));
        propiedadCreada.setIdDireccion(idDireccion);
        propiedadCreada.setFechaPublicacion(new Date());
        propiedadCreada.setNumHabitaciones(request.getNumHabitaciones());
        propiedadCreada.setIdUsuario(getUsuarioLoggeado().getId());
        propiedadCreada.setMetrosCuadrados(request.getMetrosCuadrados());
        propiedadCreada.setFecha_creacion(new Date());
        
        List<Propiedad> propiedades = propiedadRepository.findByNombre(request.getNombre());

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
    
    private Usuario getUsuarioLoggeado() throws UnsupportedEncodingException {
        
        HttpServletRequest httpRequest = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION); 
        DecodedToken token = DecodedToken.getDecoded(authHeader);
        
        Usuario usuario = usuarioRepository.findByUsuario(token.sub);
        
        return usuario;
    }
    
    @Transactional
    public Propiedad editarPropiedad(Integer idPropiedad, PropiedadRequest request){
        return pagedPropiedadRepository.findById(idPropiedad)
        .map(propiedad -> {
            propiedad.setNombre(request.getNombre());
            propiedad.setPrecio(request.getPrecio());
            propiedad.setBanos(request.getBanos());
            propiedad.setUbicacion(Ubicacion.valueOf(request.getUbicacion()));
            propiedad.setFechaPublicacion(new Date());
            propiedad.setNumHabitaciones(request.getNumHabitaciones());
            propiedad.setMetrosCuadrados(request.getMetrosCuadrados());
            propiedad.setFecha_creacion(new Date());
            return pagedPropiedadRepository.save(propiedad);
        })
        .orElseThrow(() -> new NotFoundException("La entidad propiedad no pudo ser encontrada."));
    }
}
