package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.enterprise.context.Dependent;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


/**
 *
 * @author mathe
 */
@Dependent
@Entity
public class Ingresso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_ingresso")
    @SequenceGenerator(name = "gen_ingresso", initialValue = 10, sequenceName = "seq_ingresso")
    private Long codigo;
    @ManyToOne
    @JoinColumn(name = "fk_tipo", referencedColumnName = "id")
    private Tipo tipo;
    private Sessao sessao;
   // private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "fk_compra", referencedColumnName = "id")
    private Compra compra;
    


    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Sessao getSessao() {
        return sessao;
    }

    public void setSessao(Sessao sessao) {
        this.sessao = sessao;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + Objects.hashCode(this.codigo);
        hash = 31 * hash + Objects.hashCode(this.tipo);
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
        final Ingresso other = (Ingresso) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ingresso{" + "codigo=" + codigo + ", tipo=" + tipo + ", sessao=" + sessao +  '}';
    }
}