# TourGuide – Application de recommandations touristiques
TourGuide est une application Java dédiée aux voyageurs, permettant de localiser les attractions touristiques proches, de suivre les déplacements des utilisateurs, et de leur attribuer des récompenses. Elle intègre des services tiers, un système de calcul distribué, et un pipeline CI/CD pour garantir performance, fiabilité et évolutivité.

# Objectifs du projet
- Offrir des recommandations touristiques rapides et pertinentes.
- Gérer efficacement un grand nombre d’utilisateurs simultanés.
- Mettre en place un pipeline CI/CD pour automatiser les tests et les déploiements.
- Optimiser les performances via multithreading et traitement asynchrone.

# Fonctionnalités principales
- Localisation des attractions proches
- Attribution de récompenses pour les visites
- Recommandation des 5 attractions les plus proches
- Traitement parallèle des utilisateurs pour le suivi et les récompenses
- Intégration continue avec GitLab CI/CD

# Architecture technique
| Composant           | Rôle                                         |
|---------------------|----------------------------------------------|
| Client	Interface  | utilisateur                                  |
| Serveur Spring Boot | 	Traitement des requêtes et logique métier   |
| gpsUtil             | 	Localisation des attractions                |
| RewardsCentral      | 	Calcul des points de récompense             |
| TripPricer          | 	Offres de voyage                            |
| GitLab CI/CD        | 	Automatisation des tests et du build        |

# Technologies

> Java 17  
> Spring Boot 3.1.1  
> JUnit 5  
> gpsUtil
> TripePricer
> RewardCentral

# Installation des dépendances locales

> Run :
> mvn install:install-file -Dfile=/libs/gpsUtil.jar -DgroupId=gpsUtil -DartifactId=gpsUtil -Dversion=1.0.0 -Dpackaging=jar  
>mvn install:install-file -Dfile=/libs/RewardCentral.jar -DgroupId=rewardCentral -DartifactId=rewardCentral -Dversion=1.0.0 -Dpackaging=jar  
>mvn install:install-file -Dfile=/libs/TripPricer.jar -DgroupId=tripPricer -DartifactId=tripPricer -Dversion=1.0.0 -Dpackaging=jar

# Optimisations techniques
1. Recommandation des attractions
    - Création de la classe NearbyAttractions pour structurer les données.
      -Méthode getNearbyAttractions() triant les attractions par distance et limitant à 5 résultats.

2. Traitement parallèle
    - Refactorisation des méthodes calculateRewards() et trackUserLocation() pour accepter une List<User>.
    - Utilisation de CompletableFuture et ExecutorService pour paralléliser les calculs.

3. CI/CD avec GitLab
    - Pipeline structuré en 3 étapes : setup, test, build.
    - Exécution automatique des tests unitaires et génération du .jar à chaque commit.

# Lancer les tests
    - mvn test

# Build du projet
    - mvn clean package
