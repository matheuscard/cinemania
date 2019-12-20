package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Compra;
import modelo.Filme;
import modelo.Ingresso;
import modelo.Sessao;
import modelo.Tipo;
import modelo.Usuario;
import repositorio.CompraRepositorio;
import repositorio.IngressoRepositorio;
import repositorio.TipoRepositorio;

/**
 *
 * @author Arnaldo Junior
 */
@Named
@SessionScoped
public class CompraControle implements Serializable {

    @Inject
    private Compra compra;

    @Inject
    private Ingresso ingresso;
    @Inject
    private CompraRepositorio compraRepositorio;
    @Inject
    private IngressoRepositorio ingressoRepositorio; 
    @Inject TipoRepositorio tipoRepositorio;
    List<Compra> compras = new ArrayList<>();
    int quantidade;
    int quantidadeInteira =0;
    int quantidadeMeia = 0;
    Double total = 0D;
    Double totalInteira = 0D;
    Double totalMeia = 0D;
    Long idFilme = 0l;
    Long idTipo = 0l;
    int quantidadeIngressoPorTipoFilme = 0;
    boolean mostrarTabelaAlternativa;
    List<Object[]> listaObj = new ArrayList<>();

    public List<Object[]> getListaObj() {
        return listaObj;
    }
    
    public void setListaObj(List<Object[]> listaObj) {
        this.listaObj = listaObj;
    }
    
    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }
    
    public int getQuantidade() {
        return quantidade;
    }

    public int getQuantidadeIngressoPorTipoFilme() {
        return quantidadeIngressoPorTipoFilme;
    }

    public void setQuantidadeIngressoPorTipoFilme(int quantidadeIngressoPorTipoFilme) {
        this.quantidadeIngressoPorTipoFilme = quantidadeIngressoPorTipoFilme;
    }
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    @PostConstruct
    public void inicializar() {
        //buscarTodosFilmes();
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public CompraRepositorio getCompraRepositorio() {
        return compraRepositorio;
    }

    public void setCompraRepositorio(CompraRepositorio compraRepositorio) {
        this.compraRepositorio = compraRepositorio;
    }

    public List<Compra> getCompras() {
        return compraRepositorio.findAll();
    }
    
    public void addIngresso() {
        
        
        String msg="Ingresso adcionado ao carrinho";
        try {
            ingressoRepositorio.create(ingresso);
            msg = "Ingresso " + ingresso.getCodigo()+ " cadastrado com sucesso!";
            compra.addIngresso(ingresso);
            
        } catch (Exception e) {
            msg = "Erro ao cadastrar compra!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public boolean isMostrarTabelaAlternativa() {
        return mostrarTabelaAlternativa;
    }

    public void setMostrarTabelaAlternativa(boolean mostrarTabelaAlternativa) {
        this.mostrarTabelaAlternativa = mostrarTabelaAlternativa;
    }
    
    public void cadastrar(Sessao sessao) {
        
        String msg;
        try {
            compra=new Compra();
            compraRepositorio.create(compra);
            totalInteira = 0D;
            totalMeia = 0D;
            total = 0D;
            for(int i=0; i<quantidadeInteira;i++){
                ingresso = new Ingresso();
                ingresso.setSessao(sessao);
                ingresso.setTipo(tipoRepositorio.findById(1l));
                compra.addIngresso(ingresso);
                ingressoRepositorio.create(ingresso);
                totalInteira += ingresso.getTipo().getValor();
            }
            for(int i=0; i<quantidadeMeia;i++){
                ingresso = new Ingresso();
                ingresso.setSessao(sessao);
                ingresso.setTipo(tipoRepositorio.findById(2l));
                compra.addIngresso(ingresso);
                ingressoRepositorio.create(ingresso);
                msg = "Ingresso " + ingresso.getCodigo() + "cadastrado com sucesso";
                totalMeia += ingresso.getTipo().getValor();
                
            }
            total= totalInteira+totalMeia;
            compraRepositorio.updateCompraValue(total, compra);
            msg = "Compra " + compra.getId() + " cadastrado com sucesso! Valor total da compra : " + compra.getTotal() +" "+total ;

        } catch (Exception e) {
            msg = "Erro ao cadastrar compra!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public void deletar(Long id) {
        String msg;
        try {
            compraRepositorio.delete(id);
            msg = "Presente deletado com sucesso!";
            this.compras = buscarTodosCompras();
        } catch (Exception e) {
            msg = "Erro ao deletar presente!";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msg));
    }

    public List<Compra> buscarTodosCompras() {
        try {
            this.compras = compraRepositorio.findAll();
        } catch (Exception e) {
            System.out.println("Erro ao buscar filmes!");
        }
        return compras;
    }
    
    public List<Tipo> getTipos() {
        return tipoRepositorio.findAll();
    }

    public Filme retornaFilmeClicado(Filme filme) {
        return filme;
    }

    public String navegacaoIngressosDoFilme(Usuario usuario, Sessao sessao) {
        System.out.println(usuario.getNome());
        ingresso.setSessao(sessao);
       // ingresso.setUsuario(usuario);
        System.out.println("Filme " + ingresso.getSessao().getFilme().getNome());
        return usuario.getNome().equals("admin") ? "ingresso_adm" : "ingresso";
    }

    public int getQuantidadeInteira() {
        return quantidadeInteira;
    }

    public void setQuantidadeInteira(int quantidadeInteira) {
        this.quantidadeInteira = quantidadeInteira;
    }

    public int getQuantidadeMeia() {
        return quantidadeMeia;
    }
    
    public void setQuantidadeMeia(int quantidadeMeia) {
        this.quantidadeMeia = quantidadeMeia;
    }
    public void quantidadeIngressoPorFilmeTipo(Long idFilme, Long idTipo){
         this.quantidadeIngressoPorTipoFilme = ingressoRepositorio.qtdTicketByFilmeIdAndTypeTicket(idFilme,idTipo);
    }
    public void filmeMais(){
        try {
        listaObj = compraRepositorio.movieWin();
        for (Object[] obj: listaObj) {
                System.out.println("Filme: "+ obj[0] +", VALOR: "+ obj[1]);
        }
        mostrarTabelaAlternativa = true;
       } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes mais caros: " + ex);
        }
    }
    public void sessaoMenos(){
        try {
        listaObj = compraRepositorio.sessionLoser();
        for (Object[] obj: listaObj) {
                System.out.println("Filme: "+ obj[0] +", VALOR: "+ obj[1]);
        }
        mostrarTabelaAlternativa = false;
       } catch (Exception ex) {
            System.out.println("Erro ao buscar presentes mais caros: " + ex);
        }
    
    }
}
