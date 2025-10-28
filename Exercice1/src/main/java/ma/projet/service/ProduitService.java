package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Produit").list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }

    // #################### MÉTHODES SPÉCIFIQUES ####################


    public List<Produit> findByCategorie(Categorie categorie) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createQuery("FROM Produit p WHERE p.categorie = :categorie");
            query.setParameter("categorie", categorie);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createQuery(
                    "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                            "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin"
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }


    public void afficherProduitsParCommande(Commande commande) {
        // Utilisation de Locale.FRENCH pour s'assurer que le mois est en français
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        System.out.println("Commande : " + commande.getId() + "\t Date : " + sdf.format(commande.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\t\tQuantité");

        commande.getLigneCommandeProduits().forEach(lcp -> {
            System.out.println(
                    lcp.getProduit().getReference() + "\t\t" +
                            lcp.getProduit().getPrix() + " DH\t" +
                            lcp.getQuantite()
            );
        });
    }


    public List<Produit> findPrixSup100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createNamedQuery("findPrixSup100", Produit.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            session.close();
        }
    }
}