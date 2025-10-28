# Exercice 1 - Application de gestion de stock

Ce projet est une application Java console développée dans le cadre d'un exercice. Elle permet de gérer les entités de base d'un système de gestion de stock pour un magasin de produits informatiques, en utilisant Hibernate pour la persistance des données.

## 📜 Table des matières
1. [Fonctionnalités](#-fonctionnalités)
2. [Technologies Utilisées](#-technologies-utilisées)
3. [Structure du Projet](#-structure-du-projet)
4. [Prérequis](#-prérequis)
5. [Installation et Configuration](#-installation-et-configuration)
6. [Exécution de l'Application](#-exécution-de-lapplication)
7. [Détails de l'Implémentation](#-détails-de-limplémentation)

## ✨ Fonctionnalités

La couche de service implémente les fonctionnalités suivantes :

*   **Opérations CRUD complètes** pour les entités : `Produit`, `Categorie`, `Commande` et `LigneCommandeProduit`.
*   **Recherches spécifiques sur les produits :**
    *   Afficher la liste des produits appartenant à une catégorie donnée.
    *   Afficher la liste des produits commandés entre deux dates spécifiques.
    *   Afficher les produits dont le prix est supérieur à 100 DH en utilisant une **requête nommée (Named Query)**.
*   **Affichage Formaté :**
    *   Présenter de manière claire et lisible les détails d'une commande, incluant la liste des produits, leur prix et la quantité commandée.

## 🛠️ Technologies Utilisées

*   **Langage :** Java 8+
*   **Framework de persistance :** Hibernate ORM 5+
*   **Gestion de dépendances :** Apache Maven
*   **Base de données :** MySQL
*   **Tests :** JUnit 5 & Mockito (pour les tests unitaires non inclus dans le `main`)

## 📁 Structure du Projet

Le projet suit une architecture en couches classique pour séparer les responsabilités.
.
├── pom.xml
└── src
├── main
│ ├── java
│ │ └── ma
│ │ └── projet
│ │ ├── classes/ (Entités JPA : Produit, Categorie...)
│ │ ├── dao/ (Interface générique IDao)
│ │ ├── service/ (Logique métier : ProduitService...)
│ │ ├── presentaion/ (Classe principale de test d'intégration)
│ │ └── util/ (Classe utilitaire HibernateUtil)
│ └── resources
└── test
└── java/ (Dossier pour les tests unitaires)
code
Code
## 🔧 Prérequis

Avant de commencer, assurez-vous d'avoir installé les outils suivants sur votre système :
*   JDK 8 ou une version plus récente
*   Apache Maven
*   Un serveur de base de données MySQL

## ⚙️ Installation et Configuration

1.  **Cloner le projet** (ou le décompresser dans un répertoire de votre choix).

2.  **Configurer la base de données :**
    *   Ouvrez le fichier `src/main/resources/application.properties`.
    *   Modifiez les propriétés suivantes pour correspondre à votre configuration MySQL :

    ```xml
    <property name="connection.url">jdbc:mysql://localhost:3306/Exercice1?createDatabaseIfNotExist=true</property>
    <property name="connection.username">votre_utilisateur</property>
    <property name="connection.password">votre_mot_de_passe</property>
    ```
    *   Hibernate créera automatiquement la base de données `Exercice1` si elle n'existe pas, grâce au paramètre `createDatabaseIfNotExist=true`.

3.  **Installer les dépendances :**
    *   Ouvrez un terminal à la racine du projet et exécutez la commande Maven suivante :
    ```sh
    mvn clean install
    ```

## 🚀 Exécution de l'Application

Le point d'entrée pour tester l'ensemble des fonctionnalités est la classe `ma.projet.test.Presentation.java`. Elle se charge de créer les données nécessaires (catégories, produits, commandes) puis d'appeler les différentes méthodes des services pour valider leur fonctionnement.

Pour l'exécuter :
*   **Depuis votre IDE (IntelliJ, Eclipse, VSCode) :**
    1.  Ouvrez le fichier `Presentation.java`.
    2.  Faites un clic droit dans l'éditeur et sélectionnez "Run 'Test.main()'".

*   **Résultat de l'Exécution (Photo de la console) :**
<img width="3072" height="1920" alt="Exercice1test" src="https://github.com/user-attachments/assets/c8fed143-f005-40ff-9b87-ffb891bc91b8" />

    Vous devriez voir une sortie dans votre console similaire à celle-ci, qui prouve que toutes les fonctionnalités sont opérationnelles.

    ```bash
    ###################################################
    ##### VALIDATION DES FONCTIONNALITÉS #####
    ###################################################

    ---- Test 1 : Affichage des produits de la commande ID=1 ----
    Commande : 1	 Date : 14 mars 2013
    Liste des produits :
    Référence	Prix		Quantité
    ES12		120.0 DH	7
    ZR85		100.0 DH	14
    EE85		200.0 DH	5

    ---------------------------------------------------

    ---- Test 2 : Produits dont le prix est supérieur à 100 DH ----
    Référence : ES12, Prix : 120.0 DH
    Référence : EE85, Prix : 200.0 DH

    ---------------------------------------------------

    ---- Test 3 : Produits de la catégorie 'Ordinateurs Portables' ----
    Référence : ES12
    Référence : ZR85
    Référence : EE85

    ---------------------------------------------------

    ---- Test 4 : Produits commandés en 2013 ----
    Liste des produits commandés en 2013 :
    Référence : ES12
    Référence : ZR85
    Référence : EE85

    ---------------------------------------------------
    ```

## 📝 Détails de l'Implémentation

*   **Couche Persistance :** Les entités sont mappées en utilisant les annotations JPA standard. La relation Many-to-Many entre `Produit` et `Commande` est gérée via une classe d'association `LigneCommandeProduit` qui possède une clé primaire composée (`LigneCommandeProduitPK`).
*   **Couche Service :** La logique métier est encapsulée dans les classes de service. Elles utilisent une interface générique `IDao<T>` pour les opérations CRUD de base, ce qui favorise la réutilisation du code.
*   **Gestion des Sessions Hibernate :** La classe `HibernateUtil` implémente le pattern Singleton pour garantir qu'une seule instance de `SessionFactory` est créée pour toute l'application, optimisant ainsi les performances.
