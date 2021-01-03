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

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @Transactional
    public Usuario crearUsuario(UsuarioRequest request) {
        Usuario usuarioCrear = new Usuario();

        usuarioCrear.setUsuario(request.getUsuario());
        usuarioCrear.setPassword(request.getPassword());

        String secret = UUID.randomUUID().toString();
        usuarioCrear.setSecret(secret);

        Usuario usuarioGuardado = usuarioRepository.save(usuarioCrear);

        return usuarioGuardado;
    }
    
    public JsonObject login(String usuario, String password) {
        Usuario foundUser = usuarioRepository.findByUsuarioAndPassword(usuario, password);
        if(foundUser != null) {
            String secret = UUID.randomUUID().toString();
            foundUser.setSecret(secret);
            usuarioRepository.save(foundUser);

            String jwt = jwtTokenUtil.generateToken(foundUser);
            return Json.createObjectBuilder().add("JWT",jwt).build();
        }
        else {
            throw new NotFoundException();
        }
    }
    
    private String buildJWT(String usuario,String password,String secret) {
        
        String header = Json.createObjectBuilder()
            .add("alg", "HS256")
            .add("typ", "JWT")
            .build()
            .toString();
        
        //get Token Expiry Time
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 6);
        java.util.Date expirationDate = cal.getTime();
        
        String payload = Json.createObjectBuilder()
            .add("usuario", usuario)
            .add("password", password)
            .add("expTime", expirationDate.toString())
            .build()
            .toString();
        
        byte[] headerBytes = header.getBytes();
        String encodedHeader = Base64.getUrlEncoder().withoutPadding().encodeToString(headerBytes);
        
        byte[] payloadBytes = payload.getBytes();
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadBytes);
        
        byte[] signatureBytes = hmacSha256(encodedHeader + "." + encodedPayload, secret);
        String signature = Base64.getEncoder().encodeToString(signatureBytes);        
        
        return encodedHeader + "." + encodedPayload + "." + signature;
    }

    public Usuario logout() {

        Usuario foundUser = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(foundUser != null) {
            //foundUser.setSecret("");
            //usuarioRepository.save(foundUser);
            return foundUser;
        }
        else {
            throw new NotFoundException();
        }
    }

    public Usuario getUsuario(Integer id) {

        Optional<Usuario> opt = usuarioRepository.findById(id);

        if (opt.isPresent()) {
            return opt.get();
        }

        throw new NotFoundException();
    }
    
    public Usuario editarUsuario(Integer id, UsuarioRequest request) {
        return usuarioRepository.findById(id)
        .map(usuario -> {
            usuario.setUsuario(request.getUsuario());
            return usuarioRepository.save(usuario);
        })
        .orElseThrow(() -> new NotFoundException("La entidad usuario no pudo ser encontrada."));
    }
    
    public void borrarUsuario(Integer id) {
        List<Usuario> usuarios = new LinkedList<>();
        usuarioRepository.findAll().iterator().forEachRemaining(usuarios::add);
        if(usuarios.size() < id || id <= 0){
            throw new NotFoundException("La entidad usuario no pudo ser encontrada.");
        }
        
        usuarioRepository.deleteById(id);
    }

}