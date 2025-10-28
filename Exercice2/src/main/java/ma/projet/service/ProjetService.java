package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class ProjetService {

    public void create(Projet p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public Projet findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Projet.class, id);
        }
    }

    public List<Projet> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Projet", Projet.class).list();
        }
    }

    public void update(Projet p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    public void delete(Projet p) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(p);
            tx.commit();
        } catch (Exception ex) {
            if (tx != null) tx.rollback();
            ex.printStackTrace();
        }
    }

    // Afficher la liste des tâches planifiées pour un projet.
    public void afficherTachesPlanifiees(Projet projet) {
        if (projet == null) return;
        Projet p = findById(projet.getId());
        if (p == null || p.getTaches() == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Liste des tâches planifiées pour le projet : " + p.getNom());
        for (Tache t : p.getTaches()) {
            System.out.println(" - " + t.getNom() + " | Début prévu: " + (t.getDateDebut()==null?"N/A":sdf.format(t.getDateDebut())) + " | Fin prévue: " + (t.getDateFin()==null?"N/A":sdf.format(t.getDateFin())));
        }
    }

    // Afficher la liste des tâches réalisées avec les dates réelles (formaté)
    public void afficherTachesRealiseesAvecDatesReelles(Projet projet) {
        if (projet == null) return;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Projet : " + projet.getId() + "\t Nom : " + projet.getNom() + "\t Date début : " + (projet.getDateDebut()==null?"N/A":new SimpleDateFormat("dd MMMM yyyy").format(projet.getDateDebut())));
        System.out.println("Liste des tâches:");
        EmployeTacheService ets = new EmployeTacheService();
        List<EmployeTache> all = ets.getAll();
        for (EmployeTache et : all) {
            if (et == null || et.getTache() == null) continue;
            if (et.getTache().getProjet() != null && et.getTache().getProjet().getId() == projet.getId()) {
                String nom = et.getTache().getNom();
                String debut = et.getDateDebutReelle()==null?"N/A":sdf.format(et.getDateDebutReelle());
                String fin = et.getDateFinReelle()==null?"N/A":sdf.format(et.getDateFinReelle());
                System.out.printf("%3d  %-15s %12s %15s\n", et.getTache().getId(), nom, debut, fin);
            }
        }
    }
}
