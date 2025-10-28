# Exercice 2 — Gestion de projets et tâches (Hibernate + tests)

Ce dépôt contient une petite application Java (Hibernate/JPA) qui illustre la gestion de projets, tâches, employés et liaisons "employé ↔ tâche". Ce README décrit l'exercice, la structure du projet, comment lancer les tests et où placer des captures/diagrammes.

## Objectifs de l'exercice

- Mettre en place la couche persistance avec des entités JPA : `Employe`, `Projet`, `Tache`, `EmployeTache`.
- Créer une classe utilitaire `HibernateUtil` pour la configuration Hibernate (MySQL en version main, H2 en mémoire pour les tests).
- Implémenter la couche service avec opérations CRUD et méthodes métier spécifiques :
  - Afficher les tâches réalisées par un employé.
  - Afficher les projets gérés par un employé.
  - Afficher les tâches planifiées pour un projet.
  - Afficher les tâches réalisées pour un projet avec les dates réelles (formatées).
  - Requêtes spécifiques : tâches dont le prix > 1000 DH (NamedQuery) et tâches réalisées entre deux dates.
- Rédiger des tests d'intégration JUnit qui utilisent H2 en mémoire pour valider ces comportements.

## Structure importante du projet

- `pom.xml` — dépendances Maven (Hibernate, MySQL, tests, H2 pour tests).
- `src/main/java/ma/projet/classes` — entités JPA : `Employe.java`, `Projet.java`, `Tache.java`, `EmployeTache.java`.
- `src/main/java/ma/projet/util/HibernateUtil.java` — configuration Hibernate (utilisée par l'application principale).
- `src/main/java/ma/projet/dao/IDao.java` — interface générique CRUD.
- `src/main/java/ma/projet/service/` — services : `EmployeService.java`, `ProjetService.java`, `TacheService.java`, `EmployeTacheService.java`.
- `src/main/java/ma/projet/presentation/Presentation.java` — classe d'exécution (runner) fournie dans le projet.
- `src/test/java/ma/projet/TestServices.java` — tests d'intégration JUnit 4 utilisant H2 en mémoire.
- `src/test/java/ma/projet/util/HibernateUtil.java` — version de `HibernateUtil` pour tests (H2).

## Commentaires importants

- Les tests utilisent H2 en mémoire (configuration dans `src/test/java/ma/projet/util/HibernateUtil.java`) pour éviter la dépendance à une base MySQL locale.
- La configuration Hibernate principale (dans `src/main/java/.../HibernateUtil.java`) est actuellement codée en dur pour MySQL. Vous pouvez l'harmoniser avec `src/main/resources/application.properties` si vous le souhaitez.

## Commandes utiles (cmd.exe / Windows)

- Compiler et empaqueter (sans tests) :

```bat
mvn -DskipTests package
```

- Lancer uniquement les tests d'intégration créés (`TestServices`) :

```bat
mvn -Dtest=TestServices test
```

- Lancer tous les tests :

```bat
mvn test
```

- Exécuter la classe principale `Presentation` (si vous avez configuré `exec-maven-plugin` ou depuis un IDE) :

```bat
mvn exec:java -Dexec.mainClass="ma.projet.presentation.Presentation"
```

(Remarque : `exec:java` nécessite que le plugin `exec-maven-plugin` soit présent dans `pom.xml` ou que vous lanciez la classe depuis votre IDE.)

## Ajout d'images / captures d'écran

Placez les images (diagrammes, captures d'écran, sorties attendues) dans un sous-dossier `docs/images/` puis référencez-les ici. Exemples :

- Diagramme d'architecture :

![Diagramme d'architecture](docs/images/architecture.png)

- Exemple de sortie console attendu :

![Sortie console](docs/images/sortie_console.png)

(Remplacez les chemins et ajoutez les fichiers image correspondants.)

## Exemple d'affichage attendu (format)

```
Projet : 4      Nom : Gestion de stock     Date début : 14 Janvier 2013
Liste des tâches:
Num  Nom             Date Début Réelle   Date Fin Réelle
12   Analyse         10/02/2013          20/02/2013
13   Conception      10/03/2013          15/03/2013
14   Développement   10/04/2013          25/04/2013
```

## Résolution des erreurs courantes

- `ClassNotFoundException: org.h2.Driver` lors des tests : la dépendance H2 a été ajoutée au `pom.xml` avec scope `test`.
- `LazyInitializationException` : les services utilisent désormais des requêtes HQL dans une session ouverte pour charger les collections (évite les accès hors session).

## Coverage des exigences

- Couche Persistance : entités présentes ✓
- `application.properties` : présent (attention aux valeurs actuellement utilisées) ✓
- `HibernateUtil` : présent (version main pour MySQL, version test pour H2) ✓
- Interface `IDao` : ajoutée ✓
- Services CRUD et méthodes métier demandées : implémentées ✓
- Tests JUnit (H2 en mémoire) : ajoutés ✓

## Prochaines améliorations (optionnelles)

- Remplacer `System.out.println` / `printStackTrace` par un logger (SLF4J + Logback).
- Centraliser la configuration Hibernate dans `application.properties` et faire lire `HibernateUtil` depuis ce fichier.
- Ajouter des tests unitaires supplémentaires et des assertions pour les méthodes d'affichage.

---

Si vous voulez, je peux :
- Ajouter les images `docs/images/architecture.png` et `docs/images/sortie_console.png` de démonstration.
- Harmoniser la configuration `HibernateUtil` pour qu'elle lise `application.properties`.
- Remplacer les `printStackTrace()` par du logging.

Dites-moi quelle option vous préférez et je l'implémente.  

