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
│ │ ├── test/ (Classe principale de test d'intégration)
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
  *   Naviguez vers le fichier `src/main/resources/application.properties`.
  *   Assurez-vous que les informations de connexion correspondent à votre environnement MySQL. Modifiez les lignes suivantes si nécessaire :

    ```xml
    <property name="connection.url">jdbc:mysql://localhost:3306/exercice2?createDatabaseIfNotExist=true</property>
    <property name="connection.username">votre_utilisateur_mysql</property>
    <property name="connection.password">votre_mot_de_passe_mysql</property>
    ```
  *   La base de données `Exercice2` sera créée automatiquement au premier lancement si elle n'existe pas.

3.  **Compiler le projet :**
  *   Ouvrez une invite de commande à la racine du projet.
  *   Exécutez la commande Maven pour télécharger les dépendances et compiler le code :
    ```sh
    mvn clean install
    ```

## 🚀 Exécution de l'Application

La classe `ma.projet.resentation.Presentation.java` sert de point d'entrée pour l'application. Elle peuple la base de données avec un jeu de données de test, puis exécute une série de validations pour démontrer les fonctionnalités implémentées.

*   **Pour lancer le test depuis un IDE (comme IntelliJ ou Eclipse) :**
  1.  Localisez et ouvrez le fichier `Presentation.java`.
  2.  Faites un clic droit sur le fichier et choisissez l'option "Run 'Presentation.main()'".

*   **Résultat de l'Exécution (Photo de la console) :**
    <img width="3072" height="1920" alt="Exercice2test" src="https://github.com/user-attachments/assets/60f3f186-c285-4a2f-9ab2-544f02ce7034" />
    <img width="3072" height="1920" alt="Exercie2testService" src="https://github.com/user-attachments/assets/15dbbb40-1e80-4655-88a0-5e6a27b35864" />

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