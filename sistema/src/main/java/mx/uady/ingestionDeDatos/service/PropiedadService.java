package mx.uady.ingestionDeDatos.service;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
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

import static org.apache.commons.codec.digest.HmacUtils.hmacSha256;

import mx.uady.ingestionDeDatos.repository.PropiedadesRepository;
import mx.uady.ingestionDeDatos.model.Propiedad;

@Service
public class PropiedadService {

    @Autowired
    private PropiedadesRepository propiedadesRepository;

    public List<Propiedad> getPropiedades() {
        return propiedadesRepository.findAll();
    }
}