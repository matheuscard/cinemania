package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Filme;
import modelo.Sessao;
import modelo.Usuario;
import repositorio.SessaoRepositorio;

/**
 *
 * @author mathe
 */
@Named
@SessionScoped
public class SessaoControle implements Serializable{

    @Inject
    private Sessao sessao;

    @Inject
    private SessaoRepositorio sessaoRepositorio;

    public Sessao getSessao() {
        return sessao;
    }
    Usuario usuario;
    public void cadastrar() {
        String msg;
        try {
            sessaoRepositorio.create(sessao);
            msg = "Sessao " + sessao.getNumero() + " cadastrada com sucesso!";
            sessao = new Sessao();
        } catch (Exception e) {
            msg = "Erro ao cadastrar sessao!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long numero) {
        String msg;
        try {
            sessaoRepositorio.delete(numero);
            msg = "Sessao deletada com sucesso!";
        } catch (Exception e) {
            msg = "Erro ao deletar sessão!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Sessao> getSessoes() {
        List<Sessao> sessoes = new ArrayList<>();
        try {
            sessoes = sessaoRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar sessoes!");
        }
        return sessoes;
    }

    public Sessao buscarSessaoPorId(Long numero) {
        return sessaoRepositorio.findById(numero);
    }

    public List<Sessao> buscarSessaoPorFilme(Long id) {
        return sessaoRepositorio.findByNumber(id);
    }
    public List<Sessao> buscarSessoesPorDatasIguais(){
        return null;
    }
    public List<Sessao> buscarSessoesPorDataEFilme(Long id,Long idData){
        return sessaoRepositorio.findSessoesByDataFilme(id, idData);
    }
    public String navegacaoSessoesDoFilme(Usuario usuario, Filme filme) {
        System.out.println(usuario.getNome());
        sessao.setFilme(filme);
        return usuario.getNome().equals("admin") ? "sessoes_adm" : "sessoes";
    }
    
}
