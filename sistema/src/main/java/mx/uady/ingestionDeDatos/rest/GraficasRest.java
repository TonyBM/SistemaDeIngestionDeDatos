package mx.uady.ingestionDeDatos.rest;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import javax.json.JsonObject;

import java.util.Collections;
import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import mx.uady.ingestionDeDatos.model.Grafica;
import mx.uady.ingestionDeDatos.service.GraficasService;

@RestController
@RequestMapping("/api/graficas")
public class GraficasRest {

    @Autowired
    GraficasService graficasService;

    @GetMapping("/ubicacion/casas")
    public ResponseEntity<Grafica> obtenerGraficaCasas() {
        return ResponseEntity.ok(graficasService.obtenerPropiedadesPorUbicacion());
    }

    @GetMapping("/ubicacion/precio")
    public ResponseEntity<Grafica> obtenerGraficaPrecios() {
        return ResponseEntity.ok(graficasService.obtenerPrecioPorUbicaciones());
    }

    @GetMapping("/metros/precio")
    public ResponseEntity<Grafica> obtenerGraficaMetros() {
        return ResponseEntity.ok(graficasService.obtenerPrecioPorMetrosCuadrados());
    }
}
