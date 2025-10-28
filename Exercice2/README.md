<<<<<<< HEAD
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

=======
# Exercice 2 - Application de gestion de projets

Ce projet est une application Java en mode console conçue pour répondre aux besoins d'un bureau d'études. Elle permet de suivre les employés, les projets, les tâches, et de lier ces entités pour calculer les temps passés et les coûts. Le système utilise Hibernate pour la couche de persistance des données.

## 📜 Table des matières
1. [Fonctionnalités](#-fonctionnalités)
2. [Technologies Utilisées](#-technologies-utilisées)
3. [Structure du Projet](#-structure-du-projet)
4. [Prérequis](#-prérequis)
5. [Installation et Configuration](#-installation-et-configuration)
6. [Exécution de l'Application](#-exécution-de-lapplication)
7. [Détails de l'Implémentation](#-détails-de-limplémentation)

## ✨ Fonctionnalités

L'application met en œuvre une logique métier complète pour la gestion de projets :

*   **Opérations CRUD** pour les entités : `Projet`, `Tache`, `Employe`, et `EmployeTache`.
*   **Fonctionnalités `EmployeService` :**
    *   Afficher la liste de toutes les tâches réalisées par un employé spécifique.
    *   Afficher la liste des projets gérés par un employé en tant que chef de projet.
*   **Fonctionnalités `ProjetService` :**
    *   Afficher la liste des tâches planifiées pour un projet donné.
    *   Afficher un rapport formaté des tâches réalisées pour un projet, incluant les dates de début et de fin réelles.
*   **Fonctionnalités `TacheService` :**
    *   Obtenir la liste des tâches dont le prix est supérieur à 1000 DH, en utilisant une **requête nommée (Named Query)**.
    *   Obtenir la liste de toutes les tâches qui ont été réalisées entre deux dates spécifiques.

## 🛠️ Technologies Utilisées

*   **Langage :** Java 8+
*   **Framework de persistance :** Hibernate ORM 5+
*   **Gestion de dépendances :** Apache Maven
*   **Base de données :** MySQL
*   **Tests :** JUnit 5 & Mockito (pour les tests unitaires)

## 📁 Structure du Projet

L'architecture du projet est organisée en couches distinctes pour une meilleure maintenabilité.
.
├── pom.xml
└── src
├── main
│ ├── java
│ │ └── ma
│ │ └── projet
│ │ ├── classes/ (Entités JPA : Employe, Projet, Tache...)
│ │ ├── dao/ (Interface générique IDao)
│ │ ├── service/ (Logique métier : ProjetService...)
│ │ ├── presentation/ (Classe principale de test d'intégration)
│ │ └── util/ (Classe utilitaire HibernateUtil)
│ └── resources
└── test
└── java/ (Dossier pour les tests unitaires)
code
Code
## 🔧 Prérequis

Pour compiler et exécuter ce projet, vous aurez besoin de :
*   JDK (Java Development Kit) 8 ou supérieur
*   Apache Maven
*   Un serveur de base de données MySQL actif

## ⚙️ Installation et Configuration

1.  **Récupérer le projet :** Clonez le dépôt Git ou téléchargez et décompressez l'archive ZIP.

2.  **Configurer la base de données :**
    *   Naviguez vers le fichier `src/main/resources/applications.properties`.
    *   Assurez-vous que les informations de connexion correspondent à votre environnement MySQL. Modifiez les lignes suivantes si nécessaire :

    ```xml
    <property name="connection.url">jdbc:mysql://localhost:3306/Exercice2?createDatabaseIfNotExist=true</property>
    <property name="connection.username">votre_utilisateur_mysql</property>
    <property name="connection.password">votre_mot_de_passe_mysql</property>
    ```
    *   La base de données `gestion_projets_db` sera créée automatiquement au premier lancement si elle n'existe pas.

3.  **Compiler le projet :**
    *   Ouvrez une invite de commande à la racine du projet.
    *   Exécutez la commande Maven pour télécharger les dépendances et compiler le code :
    ```sh
    mvn clean install
    ```

## 🚀 Exécution de l'Application

La classe `ma.projet.presentation.Presentation.java` sert de point d'entrée pour l'application. Elle peuple la base de données avec un jeu de données de test, puis exécute une série de validations pour démontrer les fonctionnalités implémentées.

*   **Pour lancer le test depuis un IDE (comme IntelliJ ou Eclipse) :**
    1.  Localisez et ouvrez le fichier `Presentation.java`.
    2.  Faites un clic droit sur le fichier et choisissez l'option "Run 'Test.main()'".

*   **Résultat de l'Exécution (Photo de la console) :**
  <img width="3072" height="1920" alt="Exercice2test" src="https://github.com/user-attachments/assets/849edc41-7033-4c9e-a0fe-35b20b4a7832" />
<img width="3072" height="1920" alt="Exercie2testService" src="https://github.com/user-attachments/assets/dc32472f-3022-4764-bd86-b64d48c6b7bb" />


    La sortie dans la console confirmera le bon fonctionnement de chaque méthode de service. Elle devrait ressembler à ceci :

    ```bash
    ######################################
    #####       TESTS DES SERVICES     #####
    ######################################

    ---- Test 1 : Affichage formaté des tâches d'un projet ----
    Projet : 1	 Nom : Gestion de stock	 Date début : 14 janvier 2013
    Liste des tâches:
    Num	Nom		        Date Début Réelle	Date Fin Réelle
    1	Analyse		    10/02/2013		    20/02/2013
    2	Conception		10/03/2013		    15/03/2013
    3	Développement	10/04/2013		    25/04/2013

    -------------------------------------------------------

    ---- Test 2 : Tâches dont le prix est > 1000 DH ----
      - Analyse (Prix: 1200.0 DH)
      - Conception (Prix: 1500.0 DH)

    -------------------------------------------------------

    ---- Test 3 : Tâches réalisées en Février 2013 ----
      - Analyse

    -------------------------------------------------------
    ```

## 📝 Détails de l'Implémentation

*   **Modèle de Données :** Le diagramme de classes est implémenté avec les annotations JPA. La relation `Many-to-Many` entre `Employe` et `Tache` est matérialisée par l'entité `EmployeTache`, qui contient des attributs supplémentaires (les dates réelles) et utilise une clé primaire composée (`EmployeTachePK`).
*   **Couche d'Accès aux Données :** L'interface `IDao<T>` fournit un contrat standard pour les opérations CRUD, promuovant une conception cohérente à travers les différents services.
*   **Requêtes Avancées :** Le projet démontre l'utilisation de requêtes HQL, d'une **requête nommée** (`@NamedQuery`) pour la performance et la réutilisabilité, ainsi que de la manipulation de relations complexes au sein de la couche service.
>>>>>>> 043d62310ba846b83c019eed78a6150f55fe3d82
