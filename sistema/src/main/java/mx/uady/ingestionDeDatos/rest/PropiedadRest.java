package mx.uady.ingestionDeDatos.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import javax.json.JsonObject;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.ingestionDeDatos.model.Propiedad;
import mx.uady.ingestionDeDatos.model.request.FindPropiedadRequest;
import mx.uady.ingestionDeDatos.service.PropiedadService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api")
public class PropiedadRest {

    @Autowired
    private PropiedadService propiedadService;

    @GetMapping("/propiedades/{page}")
    public ResponseEntity<Page<Propiedad>> obtenerPropiedad(@PathVariable Integer page) {
        Page<Propiedad> propiedades = propiedadService.getPropiedades(page);
        return ResponseEntity.ok(propiedades);
    }

    @PostMapping("/findPropiedad/{page}")
    public ResponseEntity<List<Propiedad>> obtenerPropiedadConFiltro(@RequestBody FindPropiedadRequest request, @PathVariable Integer page) {

        List<Propiedad> propiedades = propiedadService.getPropiedadesFiltradas(request.getType(), request.getValue(), page);
        return ResponseEntity.ok(propiedades);
    }
    
    @GetMapping("/propiedad/{id}")
    public ResponseEntity<Optional<Propiedad>> getPropiedadById(@PathVariable Integer id) {
        Optional<Propiedad> propiedad = propiedadService.getPropiedadById(id);
        return ResponseEntity.status(HttpStatus.OK).body(propiedad);
    }

    @DeleteMapping("/propiedad/{id}")
    public ResponseEntity deletePropiedad(@PathVariable Integer id){

        String response = propiedadService.borrarPropiedad(id);

        return ResponseEntity
            .ok()
            .body(Collections.singletonMap("Respuesta", response));
    }
    
}