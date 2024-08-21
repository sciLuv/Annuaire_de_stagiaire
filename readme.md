# Annuaire EQL

## Description

**Annuaire EQL** est une application développée en Java qui permet la gestion d'un annuaire de stagiaires. Ce projet est conçu pour apprendre les bases de Java, avec JavaFX pour l'interface utilisateur, et à implémenter une unité de persistance.

L'application offre plusieurs fonctionnalités, telles que la gestion des utilisateurs (administrateurs, super-administrateurs, utilisateurs de base), la création et la gestion de promotions, l'ajout et la modification des informations des stagiaires, ainsi que l'exportation de données en format PDF.

## Fonctionnalités

- **Gestion des utilisateurs** :
  - **Administrateurs** : Gestion des promotions, ajout/modification de stagiaires, accès à des fonctionnalités avancées.
  - **Super Administrateurs** : Droits étendus, y compris la gestion des administrateurs.
  - **Utilisateurs de base** : Consultation des informations des stagiaires et des promotions.

- **Gestion des promotions** :
  - Création et modification des promotions.
  - Ajout de stagiaires à une promotion.

- **Gestion des stagiaires** :
  - Ajout, modification et suppression de stagiaires.

- **Exportation PDF** :
  - Exportation des informations des stagiaires et des promotions en PDF via la bibliothèque iText.

## Technologies utilisées

- **Langage** : Java
- **Interface utilisateur** : JavaFX
- **Structure de données** : Arbre binaire pour la persistance des données
- **Génération de PDF** : iText

## Installation

1. **Pré-requis** :
   - Java JDK 8 (si version supérieur il sera nécéssaire d'ajouter en dépendance JavaFX)

2. **Instructions** :
   - Clonez ce dépôt sur votre machine locale.
   - Ouvrez le projet dans votre IDE.
   - Assurez-vous que toutes les dépendances (comme iText) sont bien configurées.
   - Compilez et exécutez le projet depuis votre IDE.

## Structure du projet

- `src/` : Contient le code source de l'application, structuré en différents packages pour la gestion des utilisateurs, l'affichage via JavaFX, et la persistance des données.
- `user_doc/` : Contient la documentation utilisateur pour les différents types d'utilisateurs de l'application (Administrateur, Super Administrateur, Utilisateur de base).
- `db/` : Contient les fichiers de persistance des données sous forme binaire et textuelle.


(picture_readme/pic1.png)
(picture_readme/pic2.png)
