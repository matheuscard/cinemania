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
import modelo.Categoria;
import modelo.Compra;
import modelo.Filme;
import modelo.Ingresso;


/**
 *
 * @author math
 */
@Dependent
public class CompraRepositorio implements Serializable{
    
    @PersistenceContext(unitName = "CinemaniaPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Compra compra) {
        try {
            transaction.begin();
            em.persist(compra);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void delete(Long id) {
        try {
            transaction.begin();
            Compra compra;
            compra = em.getReference(Compra.class, id);
            em.remove(compra);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(CompraRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateCompraValue(Double total, Compra compra){
         try{
             transaction.begin();
             compra.setTotal(total);
             em.merge(compra);
             transaction.commit();
           } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(CompraRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public List<Compra> findAll() {
        return em.createQuery("SELECT c FROM Compra c").getResultList();
    }
    
    public Compra findById(Long id) {
        Compra compra = new Compra();
        try {
            compra = em.find(Compra.class, id);
        } catch (Exception e) {
            System.out.println("Erro ao buscar compra por id: "+ e);
        }
        return compra;
    }
    public List<Object[]> movieWin(){
          return  em.createQuery("SELECT i.sessao.filme.nome, SUM(i.tipo.valor) as v FROM Compra c INNER JOIN c.ingressos i "
                  + "WHERE i.sessao.filme.id = i.sessao.filme.id GROUP BY i.sessao.filme.nome ORDER BY v DESC").setMaxResults(1).getResultList();
    }
    public List<Object[]> sessionLoser(){
        return em.createQuery("SELECT i.sessao.numero, SUM(i.tipo.valor) as v FROM Sessao s INNER JOIN s.ingressos i WHERE i.sessao.numero = i.sessao.numero GROUP BY i.sessao.numero ORDER BY v ASC").setMaxResults(1).getResultList();
    }
}
