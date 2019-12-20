package controle;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Tipo;
import repositorio.TipoRepositorio;

/**
 *
 * @author mathe
 */
@Named
@RequestScoped
public class TipoControle {

    @Inject
    private Tipo tipo;
    
    @Inject
    private TipoRepositorio tipoRepositorio;

    public Tipo getTipo() {
        return tipo;
    }

    public void cadastrar() {
        String msg;
        try {
            tipoRepositorio.create(tipo);
            msg = "Tipo " + tipo.getDescricao() +" foi cadastrado com sucesso!";
            tipo = new Tipo();
        } catch (Exception e) {
            msg = "Erro ao cadastrar o tipo!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }
    
    public void deletar(Long id) {
        String msg;
        try {
            tipoRepositorio.delete(id);
            msg = "Tipo deletado com sucesso!";
        } catch (Exception e) {
            msg = "Erro ao deletar tipo!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Tipo> getTipos() {
        List<Tipo> tipos = new ArrayList<>();
        try {
            tipos = tipoRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar tipos!");
        }
        return tipos;
    }
    
    public Tipo buscarTipoPorId(Long id) {
        return tipoRepositorio.findById(id);
    }
}
