package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.Dependent;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author mathe
 */
@Dependent
@Entity
public class Filme implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filme_gen")
    @SequenceGenerator(name = "filme_gen", sequenceName = "filmes_seq", initialValue = 1, allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String resumo;
    
    @Column(name = "folder_url", nullable = false)
    private String folderUrl;
    @Column(name = "folder_sessao", nullable = false)
    private String folderUrlSessao;
    
    @ManyToOne
    @JoinColumn(name = "fk_categoria", referencedColumnName = "id")
    private Categoria categoria;
    
    @OneToMany(mappedBy = "filme")
    private List<Sessao> sessoes = new ArrayList<>();
    
    public Filme(){}
    
    public Filme(String nome, String resumo, Categoria categoria) {
        this.nome = nome;
        this.resumo = resumo;
        this.categoria = categoria;
    }
    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getFolderUrl() {
        return folderUrl;
    }

    public String getFolderUrlSessao() {
        return folderUrlSessao;
    }

    public void setFolderUrlSessao(String folderUrlSessao) {
        this.folderUrlSessao = folderUrlSessao;
    }
    
    public void setFolderUrl(String folderUrl) {
        this.folderUrl = folderUrl;
    }

    public List<Sessao> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<Sessao> sessoes) {
        this.sessoes = sessoes;
    }
    
    public void addSessao(Sessao sessao){
        this.sessoes.add(sessao);
        sessao.setFilme(this);
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.nome);
        hash = 23 * hash + Objects.hashCode(this.resumo);
        hash = 23 * hash + Objects.hashCode(this.folderUrl);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Filme other = (Filme) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.resumo, other.resumo)) {
            return false;
        }
        if (!Objects.equals(this.folderUrl, other.folderUrl)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Filme{" + "nome=" + nome + ", resumo=" + resumo + ", folderUrl=" + folderUrl + '}';
    }
}