/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciocinead;

import java.util.ArrayList;
import java.util.List;
import mapping.PaseSala;
import mapping.PeliculaSala;
import mapping.Peliculas;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pabli
 */
public class PaseSalaDTO {
    private Session sesion;
    private Transaction tx;
    
    private void iniciaOperacion() throws HibernateException {
        sesion = HibernateUtil.getSessionFactory().openSession();
        tx = sesion.beginTransaction();
    }
    private void manejaExcepcion(HibernateException he) throws HibernateException {
        tx.rollback();
        throw new HibernateException("Error en la capa de acceso a datos", he);
    }
    
    public List<PaseSala> dimeHorariosDePelicula(Peliculas p) {
        List<PaseSala> pasesDeSalas = new ArrayList<>();
        try {
            iniciaOperacion();
            List<PeliculaSala> peliculasSalas;
            peliculasSalas = sesion.createQuery("from PeliculaSala ps where ps.pid=" + p.getPid()).list();
            peliculasSalas.forEach(ps -> {
                pasesDeSalas.add((PaseSala) sesion.createQuery("from PaseSala ps where ps.sid=" + ps.getSid()).uniqueResult());
            });
            
            if (pasesDeSalas == null) {
                System.out.println("Faltan valores para asignar en esa película. Probablemente no tenga pas eni hora asignada.");
            }
                    
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return pasesDeSalas;
    }
    
}
