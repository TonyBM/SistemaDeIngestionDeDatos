package mx.uady.ingestionDeDatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.uady.ingestionDeDatos.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public Usuario findByUsuario(String usuario);
    public Usuario findBySecret(String secret);
    public Usuario findByUsuarioAndPassword(String usuario, String password);
}