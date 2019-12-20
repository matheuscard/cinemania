package repositorio;

import java.io.Serializable;
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
import modelo.Tipo;

/**
 *
 * @author mathe
 */
@Dependent
public class TipoRepositorio implements Serializable{

    @PersistenceContext(unitName = "CinemaniaPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Tipo tipo) {
        try {
            transaction.begin();
            em.persist(tipo);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }

    public void delete(Long id) {
        try {
            transaction.begin();
            Tipo tipo;
            tipo = em.getReference(Tipo.class, id);
            em.remove(tipo);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(TipoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Tipo> findAll() {
        return em.createQuery("SELECT t FROM Tipo t").getResultList();
    }
    
    public Tipo findById(Long id) {
        Tipo tipo = new Tipo();
        try {
            tipo = em.find(Tipo.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar tipo por id: "+ e);
        }
        return tipo;
    }
    
}
