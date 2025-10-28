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
        // Récupérer via la relation
        Employe emp = findById(e.getId());
        if (emp == null || emp.getEmployeTaches() == null) return;
        for (EmployeTache et : emp.getEmployeTaches()) {
            String debut = et.getDateDebutReelle() == null ? "N/A" : sdf.format(et.getDateDebutReelle());
            String fin = et.getDateFinReelle() == null ? "N/A" : sdf.format(et.getDateFinReelle());
            System.out.println(" - " + et.getTache().getNom() + " | Début: " + debut + " | Fin: " + fin);
        }
    }

    // Afficher la liste des projets gérés par un employé.
    public void afficherProjetsGeresParEmploye(Employe e) {
        if (e == null) return;
        System.out.println("Projets gérés par : " + e.getNom() + " " + e.getPrenom());
        Employe emp = findById(e.getId());
        if (emp == null || emp.getProjets() == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        for (Projet p : emp.getProjets()) {
            System.out.println("Projet : " + p.getId() + "\tNom : " + p.getNom() + "\tDate début : " + sdf.format(p.getDateDebut()));
        }
    }
}
