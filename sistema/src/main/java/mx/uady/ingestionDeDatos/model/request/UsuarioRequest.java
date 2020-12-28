package mx.uady.ingestionDeDatos.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsuarioRequest {

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String usuario;

    @NotNull
    @Size(min = 5, max = 50)
    @NotEmpty
    private String password;

    private String secret;

    public UsuarioRequest() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    /**
     * @param secret the secret to set
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * @return the secret
     */
    public String getSecret() {
        return this.secret;
    }
}