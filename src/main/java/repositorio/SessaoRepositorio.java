/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositorio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import modelo.Filme;
import modelo.Sessao;

/**
 *
 * @author mathe
 */
@Dependent
public class SessaoRepositorio implements Serializable{
    @PersistenceContext(unitName = "CinemaniaPU")
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    public void create(Sessao sessao) {
        try {
            transaction.begin();
            Filme filme = em.find(Filme.class, sessao.getFilme().getId());
            filme.addSessao(sessao);
            em.persist(sessao);
            transaction.commit();
        } catch (IllegalStateException | SecurityException | HeuristicMixedException | HeuristicRollbackException | NotSupportedException | RollbackException | SystemException e) {
            System.out.println("Erro: " + e);
        }
    }

    public void delete(Long numero) {
        try {
            transaction.begin();
            Sessao sessao;
            sessao = em.getReference(Sessao.class, numero);
            em.remove(sessao);
            transaction.commit();

        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(CategoriaRepositorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Sessao> findAll() {
        return em.createQuery("SELECT s FROM Sessao s").getResultList();
    }
    
    public Sessao findById(Long numero) {
        Sessao sessao = new Sessao();
        try {
            sessao = em.find(Sessao.class, numero);
        } catch (Exception e) {
            System.out.println("Erro ao buscar categoria por id: "+ e);
        }
        return sessao;
    }
    
    public List<Sessao> findByNumber(Long id) {
        List<Sessao> sessoes =  new ArrayList<>();;
        try {
            sessoes = (List<Sessao>) em.createQuery("SELECT s FROM Sessao s WHERE s.filme.id = ?1").
                    setParameter(1, id).setMaxResults(4).getResultList();
        } catch(NoResultException nre) {
            System.out.println("Erro ao buscar categoria por nome: "+ nre);
        }
        return sessoes;
    }
    public List<Sessao> findSessoesByDataFilme( Long id,Long idData){
        return em.createQuery("SELECT s FROM Sessao s WHERE s.filme.id = ?1 AND s.data.id = ?2")
                .setParameter(1, id).setParameter(2,idData).getResultList() ;
    }
}
