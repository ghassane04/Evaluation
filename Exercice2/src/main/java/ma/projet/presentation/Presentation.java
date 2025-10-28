package ma.projet.presentation;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Presentation {
    public static void main(String[] args) throws ParseException {
        // Services
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // --- Création des données ---
        Employe chef = new Employe("BOUGERFAOUI", "Ghassane", "0650811309");
        es.create(chef);

        Projet projet = new Projet("Gestion de stock", sdf.parse("2013-01-14"), sdf.parse("2013-08-14"), chef);
        ps.create(projet);

        Tache t1 = new Tache("Analyse", sdf.parse("2013-02-01"), sdf.parse("2013-02-28"), 1200, projet);
        Tache t2 = new Tache("Conception", sdf.parse("2013-03-01"), sdf.parse("2013-03-30"), 1500, projet);
        Tache t3 = new Tache("Développement", sdf.parse("2013-04-01"), sdf.parse("2013-05-30"), 800, projet);
        ts.create(t1);
        ts.create(t2);
        ts.create(t3);

        ets.create(new EmployeTache(chef, t1, sdf.parse("2013-02-10"), sdf.parse("2013-02-20")));
        ets.create(new EmployeTache(chef, t2, sdf.parse("2013-03-10"), sdf.parse("2013-03-15")));
        ets.create(new EmployeTache(chef, t3, sdf.parse("2013-04-10"), sdf.parse("2013-04-25")));

        // --- Validation des fonctionnalités ---
        System.out.println("######################################");
        System.out.println("#####       TESTS DES SERVICES     #####");
        System.out.println("######################################\n");

        // Test ProjetService : Afficher les tâches avec dates réelles
        System.out.println("---- Test 1 : Affichage formaté des tâches d'un projet ----");
        Projet pTest = ps.findById(1);
        ps.afficherTachesRealiseesAvecDatesReelles(pTest);
        System.out.println("\n-------------------------------------------------------\n");

        // Test TacheService : Tâches > 1000 DH
        System.out.println("---- Test 2 : Tâches dont le prix est > 1000 DH ----");
        List<Tache> tachesChers = ts.findTachesPrixSup1000();
        for(Tache t : tachesChers){
            System.out.println("  - " + t.getNom() + " (Prix: " + t.getPrix() + " DH)");
        }
        System.out.println("\n-------------------------------------------------------\n");

        // Test TacheService : Tâches entre deux dates réelles
        System.out.println("---- Test 3 : Tâches réalisées en Février 2013 ----");
        List<Tache> tachesFevrier = ts.findTachesRealiseesEntreDates(sdf.parse("2013-02-01"), sdf.parse("2013-02-28"));
        for(Tache t : tachesFevrier){
            System.out.println("  - " + t.getNom());
        }
    }
}