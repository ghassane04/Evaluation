package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TacheService {

    public void create(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public Tache findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Tache.class, id);
        }
    }

    public List<Tache> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Tache", Tache.class).list();
        }
    }

    public void update(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public void delete(Tache t) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(t);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // Méthode nommée pour trouver les taches dont le prix est supérieur
    public List<Tache> findTachesPrixSup1000() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Tache> q = session.createNamedQuery("Tache.findByPrixSuperieur", Tache.class);
            q.setParameter("prix", 1000.0);
            return q.list();
        }
    }

    // Tâches réalisées entre deux dates (en se basant sur EmployeTache.dateDebutReelle/dateFinReelle)
    public List<Tache> findTachesRealiseesEntreDates(Date debut, Date fin) {
        if (debut == null || fin == null) return new ArrayList<>();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> all = session.createQuery("from EmployeTache", EmployeTache.class).list();
            Set<Tache> resultSet = new HashSet<>();
            for (EmployeTache et : all) {
                if (et == null) continue;
                Date dDebut = et.getDateDebutReelle();
                Date dFin = et.getDateFinReelle();
                if (dDebut == null || dFin == null) continue;
                if (!dDebut.before(debut) && !dFin.after(fin)) {
                    resultSet.add(et.getTache());
                }
            }
            return new ArrayList<>(resultSet);
        }
    }
}
