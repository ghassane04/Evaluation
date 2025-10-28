package ma.projet;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.util.HibernateUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestServices {

    private static EmployeService employeService = new EmployeService();
    private static ProjetService projetService = new ProjetService();
    private static TacheService tacheService = new TacheService();
    private static EmployeTacheService etService = new EmployeTacheService();

    @BeforeClass
    public static void setup() throws Exception {
        // initialise la session factory H2 (la classe de test utilise src/test/java/ma/projet/util/HibernateUtil)
        HibernateUtil.getSessionFactory();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Employe e1 = new Employe("Dupont", "Jean", "0600000001");
        employeService.create(e1);

        Projet p1 = new Projet("Gestion de stock", sdf.parse("2013-01-14"), sdf.parse("2013-12-31"), e1);
        projetService.create(p1);

        Tache t1 = new Tache("Analyse", sdf.parse("2013-02-10"), sdf.parse("2013-02-20"), 500.0, p1);
        Tache t2 = new Tache("Conception", sdf.parse("2013-03-10"), sdf.parse("2013-03-15"), 1500.0, p1);
        Tache t3 = new Tache("Developpement", sdf.parse("2013-04-10"), sdf.parse("2013-04-25"), 2000.0, p1);

        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);

        EmployeTache et1 = new EmployeTache(e1, t1, sdf.parse("2013-02-10"), sdf.parse("2013-02-20"));
        EmployeTache et2 = new EmployeTache(e1, t2, sdf.parse("2013-03-10"), sdf.parse("2013-03-15"));
        EmployeTache et3 = new EmployeTache(e1, t3, sdf.parse("2013-04-10"), sdf.parse("2013-04-25"));

        etService.create(et1);
        etService.create(et2);
        etService.create(et3);
    }

    @AfterClass
    public static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    public void testFindTachesPrixSup1000() {
        List<Tache> res = tacheService.findTachesPrixSup1000();
        Assert.assertNotNull(res);
        Assert.assertTrue(res.size() >= 2);
    }

    @Test
    public void testFindTachesRealiseesEntreDates() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = sdf.parse("2013-02-01");
        Date d2 = sdf.parse("2013-03-31");
        List<Tache> res = tacheService.findTachesRealiseesEntreDates(d1, d2);
        Assert.assertNotNull(res);
        // devrait inclure Analyse et Conception
        Assert.assertTrue(res.stream().anyMatch(t -> "Analyse".equals(t.getNom())));
        Assert.assertTrue(res.stream().anyMatch(t -> "Conception".equals(t.getNom())));
    }

    @Test
    public void testAffichages() throws Exception {
        // Exécution visuelle : ne fait pas d'assert, mais doit s'exécuter sans exception
        Employe e = employeService.findAll().get(0);
        projetService.afficherTachesPlanifiees(projetService.findAll().get(0));
        projetService.afficherTachesRealiseesAvecDatesReelles(projetService.findAll().get(0));
        employeService.afficherTachesRealiseesParEmploye(e);
        employeService.afficherProjetsGeresParEmploye(e);
    }
}

