package controle;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Usuario;

/**
 *
 * 
 * @author mathe
 */
@Named
@SessionScoped
public class LoginControle implements Serializable {
    
    @Inject
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void logarComMensagem() {
        String mensagem;
                
        if (usuario.getNome().equals("teste") && usuario.getSenha().equals("teste")) {
            mensagem = "Sucesso!";
        } else {
            mensagem = "Login inválido!";
        }
        FacesContext.getCurrentInstance().addMessage(mensagem, new FacesMessage(mensagem));
    }
    
    public String logarComNavegacao() {
        return usuario.getNome().equals("admin") ? "filmes/visualizar_adm" : "filmes/visualizar";
    }
    public String adicionarFilme() {
        return "cadastrar";
    }
}
