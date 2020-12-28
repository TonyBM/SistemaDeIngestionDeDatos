package mx.uady.ingestionDeDatos.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;

import javax.json.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mx.uady.ingestionDeDatos.model.Usuario;
import mx.uady.ingestionDeDatos.model.request.UsuarioRequest;
import mx.uady.ingestionDeDatos.service.UsuarioService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class UsuarioRest {

    @Autowired
    private UsuarioService usuarioService;

    // POST /api/register
    @PostMapping("/register")
    public ResponseEntity<Usuario> postUsuarios(@RequestBody @Valid UsuarioRequest request) throws URISyntaxException {
        Usuario usuario = null;

        if(request.getPassword().length() < 8) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(usuario);
        }

        usuario = usuarioService.crearUsuario(request);
        return ResponseEntity
            .created(new URI("/usuarios/" + usuario.getId()))
            .body(usuario);
    }
    
    //POST /api/login
    @PostMapping("/login")
    public ResponseEntity<JsonObject> login(@RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.login(request.getUsuario(), request.getPassword()));
    }

    //POST /api/logout
    @PostMapping("/logout")
    public ResponseEntity<Usuario> logout() {

        return ResponseEntity.ok(usuarioService.logout());
    }
    
    // GET /api/usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuario() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // GET /api/usuario/3
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable Integer id) {
        Usuario u = usuarioService.getUsuario(id);
        return ResponseEntity.status(HttpStatus.OK).body(u);
    }
    
    // PUT /api/usuario/3
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Usuario> registrarUsuario(@PathVariable Integer id, @RequestBody UsuarioRequest request) {
        Usuario usuario = usuarioService.editarUsuario(id, request);

        return ResponseEntity
            .ok()
            .body(usuario);
    }
    
    //DELETE /api/usuario/3
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity deleteUsuario(@PathVariable Integer id){

        usuarioService.borrarUsuario(id);

        return ResponseEntity
            .ok()
            .body("Usuario Borrado");
    }
}