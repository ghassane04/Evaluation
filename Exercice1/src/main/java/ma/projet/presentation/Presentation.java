package ma.projet.presentation;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Presentation {
    public static void main(String[] args) {
        // Initialisation des services
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cmdService = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // ---- Création des données de base ----

        // 1. Création des catégories
        Categorie cat1 = new Categorie("PC", "Ordinateurs Portables");
        Categorie cat2 = new Categorie("IMP", "Imprimantes");
        cs.create(cat1);
        cs.create(cat2);

        // 2. Création des produits
        Produit prod1 = new Produit("ES12", 120, cat1);
        Produit prod2 = new Produit("ZR85", 100, cat1); // Prix exact de 100, ne sera pas dans la liste > 100
        Produit prod3 = new Produit("EE85", 200, cat1);
        Produit prod4 = new Produit("HP-L", 1500, cat2);

        ps.create(prod1);
        ps.create(prod2);
        ps.create(prod3);
        ps.create(prod4);

        // 3. Création d'une commande
        Commande cmd1 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateCmd = sdf.parse("2013-03-14");
            cmd1 = new Commande(dateCmd);
            cmdService.create(cmd1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 4. Ajout des lignes de commande (liaison produits-commande)
        if (cmd1 != null) {
            lcs.create(new LigneCommandeProduit(prod1, cmd1, 7));
            lcs.create(new LigneCommandeProduit(prod2, cmd1, 14));
            lcs.create(new LigneCommandeProduit(prod3, cmd1, 5));
        }

        System.out.println("###################################################");
        System.out.println("##### VALIDATION DES FONCTIONNALITÉS #####");
        System.out.println("###################################################\n");

        // Test 1: Afficher les produits d'une commande donnée
        System.out.println("---- Test 1 : Affichage des produits de la commande ID=1 ----");
        Commande commandeTest = cmdService.findById(1);
        if (commandeTest != null) {
            ps.afficherProduitsParCommande(commandeTest);
        }
        System.out.println("\n---------------------------------------------------\n");

        // Test 2: Afficher les produits dont le prix est supérieur à 100 DH
        System.out.println("---- Test 2 : Produits dont le prix est supérieur à 100 DH ----");
        List<Produit> produitsChers = ps.findPrixSup100();
        if (produitsChers != null) {
            for (Produit p : produitsChers) {
                System.out.println("Référence : " + p.getReference() + ", Prix : " + p.getPrix() + " DH");
            }
        }
        System.out.println("\n---------------------------------------------------\n");

        // Test 3: Afficher la liste des produits par catégorie
        System.out.println("---- Test 3 : Produits de la catégorie 'Ordinateurs Portables' ----");
        Categorie categorieRecherchee = cs.findById(1);
        if (categorieRecherchee != null) {
            List<Produit> produitsParCategorie = ps.findByCategorie(categorieRecherchee);
            if (produitsParCategorie != null) {
                for (Produit p : produitsParCategorie) {
                    System.out.println("Référence : " + p.getReference());
                }
            }
        }
        System.out.println("\n---------------------------------------------------\n");

        // Test 4: Afficher les produits commandés entre deux dates
        System.out.println("---- Test 4 : Produits commandés en 2013 ----");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateDebut = sdf.parse("2013-01-01");
            Date dateFin = sdf.parse("2013-12-31");
            List<Produit> produitsCommandes = ps.findProduitsCommandesEntreDates(dateDebut, dateFin);
            if (produitsCommandes != null) {
                System.out.println("Liste des produits commandés en 2013 :");
                for (Produit p : produitsCommandes) {
                    System.out.println("Référence : " + p.getReference());
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("\n---------------------------------------------------\n");
    }
}