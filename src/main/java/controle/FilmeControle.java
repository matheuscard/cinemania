package controle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Categoria;
import modelo.Filme;
import repositorio.CategoriaRepositorio;
import repositorio.FilmeRepositorio;
import repositorio.SessaoRepositorio;

/**
 *
 * @author Arnaldo Junior
 */
@Named
@RequestScoped
public class FilmeControle{

    @Inject
    private Filme filme;

    private String categoria;

    @Inject
    private FilmeRepositorio filmeRepositorio;
    @Inject 
    private SessaoRepositorio sessaoRepositorio;
    
    @Inject
    private CategoriaRepositorio categoriaRepositorio;

    List<Filme> filmes = new ArrayList<>();

    @PostConstruct
    public void inicializar() {
        buscarTodosFilmes();
    }

    public Filme getFilme() {
        return filme;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public List<Filme> getFilmes() {
        return filmes;
    }

    public List<Categoria> getCategorias() {
        return categoriaRepositorio.findAll();
    }

    public void cadastrar() {
        String msg;
        try {
            filmeRepositorio.create(filme);
            msg = "Filme " + filme.getNome()+ " cadastrado com sucesso!";
            filme = new Filme();
        } catch (Exception e) {
            msg = "Erro ao cadastrar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long id) {
        String msg;
        try {
            filmeRepositorio.delete(id);
            msg = "Presente deletado com sucesso!";
            this.filmes = buscarTodosFilmes();
        } catch (Exception e) {
            msg = "Erro ao deletar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Filme> buscarTodosFilmes() {
        try {
            this.filmes = filmeRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar filmes!");
        }
        return filmes;
    }

    public void buscarPorCategoria(ValueChangeEvent e) {
        this.categoria = e.getNewValue().toString();
        Categoria categoriaBuscada;
        try {
            if (categoria.isEmpty()) {
                this.filmes = filmeRepositorio.findAll();
            } else {
                //this.presentes = presenteRepositorio.findByCategoria(categoria);
                 categoriaBuscada = categoriaRepositorio.findByName(categoria);
                 System.out.println("FILMES: "+ categoriaBuscada.getFilmes().size());
                 filmes = categoriaBuscada.getFilmes();
                 if (filmes.isEmpty()) {
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Nenhum filme encontrado!"));
                 }
            }
        } catch (Exception ex) {
            System.out.println("Erro ao buscar filme por categoria: " + ex);
        }
    }
    public Filme buscarFilmePorId(Long id) {
        return filmeRepositorio.findById(id);
    }
    public Filme retornaFilmeClicado(Filme filme){
        return filme;
    }
    public String navegacaoSessaoPorDataEFilme(Long id,Long idData){
        SessaoControle sessaoControle = new SessaoControle();
        filme.setSessoes(sessaoControle.buscarSessoesPorDataEFilme(id, idData));
        return "versessoes";
    }
}
