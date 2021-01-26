/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejerciciocinead;

import java.util.List;
import mapping.Disponible;
import org.hibernate.*;

/**
 *
 * @author pabli
 */
public class DisponibleDTO {

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

    public List<Disponible> listaDisponibles() {
        List<Disponible> disponibles;
        try {
            iniciaOperacion();
            disponibles = sesion.createQuery("from Disponible").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            sesion.close();
        }

        return disponibles;

    }

    public Disponible obtenerDisponible(int id) {
        Disponible d = null;
        try {
            iniciaOperacion();
            d = (Disponible) sesion.get(Disponible.class, id);
        } catch (HibernateException he) {
            manejaExcepcion(he);
            throw he;
        } finally {
            sesion.close();
        }
        return d;
    }

    public void reservar(Disponible d) {
        if (d.getEstado() == 1) {
            System.out.println("Esta sala no está disponible");
        } else {
            try {
                d.setEstado(1);
                iniciaOperacion();
                sesion.update(d);
                System.out.println(d.getId() + " ha sido reservada");
                tx.commit();
            } catch (HibernateException e) {
                manejaExcepcion(e);
                throw e;
            } finally {
                sesion.close();
            }
        }
    }
}
