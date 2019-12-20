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
import modelo.Ingresso;


/**
 *
 * @author mathe
 */
@Dependent
public class IngressoRepositorio implements Serializable{
    
    @PersistenceContext(unitName = "CinemaniaPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Ingresso ingresso) {
        try {
            transaction.begin();
            em.persist(ingresso);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void delete(Long codigo) {
        try {
            transaction.begin();
            Ingresso ingresso;
            ingresso = em.getReference(Ingresso.class, codigo);
            em.remove(ingresso);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(IngressoRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Ingresso> findAll() {
        return em.createQuery("SELECT i FROM Ingresso i").getResultList();
    }
    
    public Ingresso findById(Long codigo) {
        Ingresso ingresso = new Ingresso();
        try {
            ingresso = em.find(Ingresso.class, codigo);
        } catch (Exception e) {
            System.out.println("Erro ao buscar compra por id: "+ e);
        }
        return ingresso;
    }
    public int qtdTicketByFilmeIdAndTypeTicket(Long idFilme, Long idTipo){

        return em.createQuery("SELECT i FROM Compra c INNER JOIN C.ingressos i where i.sessao.filme.id = ?1 AND i.tipo.ID = ?2").setParameter(1, idFilme).setParameter(2, idTipo).getResultList().size();
    }
 
}
