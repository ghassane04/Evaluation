package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EmployeService {

    public void create(Employe e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public Employe findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Employe.class, id);
        }
    }

    public List<Employe> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe", Employe.class).list();
        }
    }

    public void update(Employe e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public void delete(Employe e) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(e);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // Afficher la liste des tâches réalisées par un employé.
    public void afficherTachesRealiseesParEmploye(Employe e) {
        if (e == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Tâches réalisées par : " + e.getNom() + " " + e.getPrenom());
        // Récupérer les employeTaches via une requête dans une session pour éviter LazyInitializationException
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> ets = session.createQuery("from EmployeTache et where et.employe.id = :eid", EmployeTache.class)
                    .setParameter("eid", e.getId())
                    .list();
            for (EmployeTache et : ets) {
                String debut = et.getDateDebutReelle() == null ? "N/A" : sdf.format(et.getDateDebutReelle());
                String fin = et.getDateFinReelle() == null ? "N/A" : sdf.format(et.getDateFinReelle());
                String tnom = et.getTache() == null ? "N/A" : et.getTache().getNom();
                System.out.println(" - " + tnom + " | Début: " + debut + " | Fin: " + fin);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Afficher la liste des projets gérés par un employé.
    public void afficherProjetsGeresParEmploye(Employe e) {
        if (e == null) return;
        System.out.println("Projets gérés par : " + e.getNom() + " " + e.getPrenom());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Projet> projets = session.createQuery("from Projet p where p.chefProjet.id = :eid", Projet.class)
                    .setParameter("eid", e.getId())
                    .list();
            for (Projet p : projets) {
                System.out.println("Projet : " + p.getId() + "\tNom : " + p.getNom() + "\tDate début : " + (p.getDateDebut() == null ? "N/A" : sdf.format(p.getDateDebut())));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
