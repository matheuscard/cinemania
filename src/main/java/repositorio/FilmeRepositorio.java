package repositorio;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Categoria;
import modelo.Filme;


/**
 *
 * @author mathe
 */
@Dependent
public class FilmeRepositorio {
    
    
    @PersistenceContext(unitName = "CinemaniaPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Filme filme) {
        try {
            transaction.begin();
            Categoria categoria = em.find(Categoria.class, filme.getCategoria().getId());
            categoria.addFilme(filme);
            em.persist(filme);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void delete(Long id) {
        try {
            transaction.begin();
            Filme filme;
            filme = em.getReference(Filme.class, id);
            em.remove(filme);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(FilmeRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Filme> findAll() {
        return em.createQuery("SELECT f FROM Filme f").getResultList();
    }
    
    public List<Filme> findByCategoria(String categoria) {
        return em.createQuery("SELECT f FROM Filme f WHERE f.categoria.nome = ?1").
                setParameter(1, categoria).getResultList();
    }
    public Filme findById(Long id) {
        Filme filme = new Filme();
        try {
            filme = em.find(Filme.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme por id: "+ e);
        }
        return filme;
    }
}
