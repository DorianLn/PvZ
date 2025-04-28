# Projet Plants vs Zombies - Backend

Ce projet implémente le backend d'une application inspirée du jeu Plants vs Zombies, développé dans le cadre du cours Java.

## Technologies Utilisées

- **Spring Boot** : Framework principal
- **Spring MVC** : Pour la gestion des endpoints REST
- **JdbcTemplate** : Pour l'accès aux données
- **MySQL** : Base de données
- **Maven** : Gestion des dépendances et build
- **Tomcat** : Serveur d'application
- **Jakarta** : Spécifications Java EE

## Structure du Projet

Le projet suit une architecture en couches :
- **Controllers** : Gestion des endpoints REST
- **Services** : Logique métier
- **DAO** : Accès aux données
- **Modèles** : Entités (Map, Plante, Zombie)

## Points Techniques Spécifiques

1. **Architecture REST** :
   - Endpoints CRUD pour les Maps, Plantes et Zombies
   - Format JSON pour les échanges de données

2. **Gestion des Relations** :
   - Liens entre Zombies et Maps
   - Validations des données

3. **Tests** :
   - Tests unitaires pour les DAOs
   - Tests unitaires pour les Services
   - Tests d'intégration

## Configuration

La base de données est configurée dans :
- `src/main/resources/database.properties`
- `com/epf/Persistance/Config_BDD.java`

## Déploiement

Le projet est packagé en WAR pour le déploiement sur Tomcat.

## Documentation API

### Endpoints Maps
- `GET /maps` : Liste toutes les maps
- `GET /maps/{id}` : Récupère une map par ID
- `POST /maps` : Crée une nouvelle map
- `PUT /maps/{id}` : Met à jour une map
- `DELETE /maps/{id}` : Supprime une map

### Endpoints Plantes
- `GET /plantes` : Liste toutes les plantes
- `GET /plantes/{id}` : Récupère une plante par ID
- `POST /plantes` : Crée une nouvelle plante
- `PUT /plantes/{id}` : Met à jour une plante
- `DELETE /plantes/{id}` : Supprime une plante

### Endpoints Zombies
- `GET /zombies` : Liste tous les zombies
- `GET /zombies/{id}` : Récupère un zombie par ID
- `POST /zombies` : Crée un nouveau zombie
- `PUT /zombies/{id}` : Met à jour un zombie
- `DELETE /zombies/{id}` : Supprime un zombie

## Solutions de Test

### Frameworks et Outils
- **JUnit 5** : Framework de test principal pour Java
  - Utilisé pour structurer les tests avec les annotations `@Test`
  - Fournit les assertions pour vérifier les résultats (`assertEquals`, `assertTrue`, etc.)
  - Supporte le cycle de vie des tests avec `@BeforeEach`

- **Mockito** : Framework de mock pour isoler les composants
  - Permet de simuler les dépendances (`@Mock`)
  - Injection automatique des mocks (`@InjectMocks`)
  - Vérification des interactions avec `verify()`
  - Simulation des comportements avec `when()`

### Architecture des Tests

1. **Tests des Entités**
   - Tests unitaires simples (test_plante.java, test_zombie.java, test_map.java)
   - Vérifient la création et les setters/getters
   - Ne nécessitent pas de mocks

2. **Tests des DAO**
   - Utilisent `@Mock` pour simuler JdbcTemplate
   - Testent les requêtes SQL et les mappings
   - Simulent les réponses de la base de données
   - Vérifient les paramètres des requêtes
   - Exemple : `PlanteDAOImptest`, `ZombieDAOImptest`, `MapDAOImptest`

3. **Tests des Services**
   - Utilisent `@Mock` pour simuler les DAOs
   - Testent la logique métier
   - Vérifient les interactions avec les DAOs
   - Exemple : `PlanteServiceTest`, `ZombieServiceTest`, `MapServiceTest`

### Pourquoi ces choix ?

1. **Mockito** :
   - Permet de tester en isolation
   - Évite la dépendance à une base de données réelle
   - Accélère l'exécution des tests
   - Permet de simuler des cas d'erreur difficiles à reproduire

2. **Architecture en couches des tests** :
   - Tests unitaires pour chaque niveau (Entity, DAO, Service)
   - Correspondance avec l'architecture de l'application
   - Facilite la maintenance et l'identification des problèmes

3. **Tests automatisés** :
   - Intégration avec Maven
   - Exécution systématique avant le déploiement
   - Garantit la non-régression
   - Documentation vivante du code

### Patterns de Test Utilisés

1. **Arrange-Act-Assert** :
   - Arrangement des données de test
   - Exécution de l'action à tester
   - Vérification des résultats attendus

2. **Builder Pattern** :
   - Construction fluide des objets de test
   - Améliore la lisibilité des tests
   - Facilite la maintenance

3. **Mock et Stub** :
   - Simulation des dépendances
   - Isolation des composants
   - Contrôle total sur l'environnement de test