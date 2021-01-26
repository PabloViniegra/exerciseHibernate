/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciocinead;

import java.util.List;
import mapping.Peliculas;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author pabli
 */
public class CineDTO {
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
    
    public List<Peliculas> peliculasDeEsteActor(String aux) {
        List<Peliculas> peliculas;
        try {
            iniciaOperacion();
            peliculas = sesion.createQuery("from Peliculas p where p.actores='" + aux + "'").list();
        }catch(HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            sesion.close();
        }
        return peliculas;
    }
    
    public int nuevaPelicula(Peliculas p) throws HibernateException {
        int id;
        try {
            iniciaOperacion();
            id = (int) sesion.save(p);
            tx.commit();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            sesion.close();
        }
        return id;
    }
    
    public void actualizaPelicula(Peliculas p) throws HibernateException {

        try {
            iniciaOperacion();
            sesion.update(p);
            tx.commit();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            sesion.close();
        }

    }
    
    public void eliminaPelicula(Peliculas p) throws HibernateException {

        try {
            iniciaOperacion();
            sesion.delete(p);
            tx.commit();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            sesion.close();
        }

    }
    
    public Peliculas obtenPelicula(int pid) throws HibernateException {
        Peliculas p = null;
        try {
            iniciaOperacion();
            p = (Peliculas) sesion.get(Peliculas.class, pid);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return p;
    }
    
    public List<Peliculas> obtenListaPeliculas() throws HibernateException {
        List<Peliculas> lista = null;
        try {
            iniciaOperacion();
            lista = sesion.createQuery("from Peliculas").list();
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return lista;
    }
    
    
}
