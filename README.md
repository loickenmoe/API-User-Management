# API User Management

Cette API permet de gérer des utilisateurs avec authentification JWT, incluant l'inscription, la connexion, et les opérations CRUD sur les utilisateurs, en utilisant une base de données PostgreSQL.

## Prérequis

- **Java 17, 21, 22** (ou supérieur)
- **Maven** (pour la gestion des dépendances)
- **PostgreSQL** (installé et configuré localement)
- **Spring Boot 3.3.9**

## Installation

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/votre-utilisateur/user-management-api
   cd user-management-api

2. **Configurer la base de données PostgreSQL :** :   
   -Créez une base de données nommée userManagement_db :
   "CREATE DATABASE userManagement_db;"
   -Vérifiez les identifiants dans application.properties :
   spring.datasource.username=postgres
   spring.datasource.password=votre_mot_de_passe
   spring.datasource.url=jdbc:postgresql://localhost:5432/userManagement_db


## Lancer l'application
 "mvn spring-boot:run"
 -L'API sera disponible sur http://localhost:9006.

##   Utilisation de l'application :
    Endpoints
    Méthode	            Endpoint	                    Description	Accès Requis
    POST	            /api/auth/register	            Enregistrer un nouvel utilisateur	Public
    POST	            /api/auth/login	                Connexion (obtenir un JWT)	Public
    GET	                /api/users	                    Lister tous les utilisateurs	Authentifié
    GET	                /api/users/{id}	                Obtenir un utilisateur par ID	Authentifié
    PUT	                /api/users/{id}	                Mettre à jour un utilisateur par ID	Authentifié
    DELETE	            /api/users/{id}	                Supprimer un utilisateur par ID	Authentifié

## Lien Swagger :
http://localhost:9006/swagger-ui/index.html

##  Exemples de requêtes :

    -Étape 1: Enregistrement d'un utilisateur :
    POST http://localhost:9006/auth/register
    Content-Type: application/json
    {
     "name": "Jean Dupont",
     "email": "jean.dupont@example.com",
     "password": "MotDePasse123!"
    }

    Reponse attendue:
    {
      "id": 1,
      "name": "Jean Dupont",
      "email": "jean.dupont@example.com"
    }
    
    -Étape 2: Connexion (Authentification JWT) :
      POST http://localhost:9006/auth/login
      Content-Type: application/json
     {
      "email": "jean.dupont@example.com",
      "password": "MotDePasse123!"
     }

    Reponse attendue:
    {
       "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWFuLmR1cG9udEBleGFtcGxlLmNvbSIsImlhdCI6MTc0MzI1MDk4MSwiZXhwIjoxNzQzMzM3MzgxfQ.bEfoBOXJsvJH-A4-_5XkaW-jqOUIe3GFS1cx_nwg3i0",
       "tokenType": "Bearer"
    }

    -Étape 3: Accès protégé avec token (remplacer <VOTRE_TOKEN_ICI>)
     POST http://localhost:9006/users
     Authorization: Bearer <VOTRE_TOKEN_ICI>


    -Étape 4: Mise à jour de l'utilisateur (remplacer <USER_ID> et <VOTRE_TOKEN_ICI>)
     PUT http://localhost:9006/users/<USER_ID>
     Content-Type: application/json
     Authorization: Bearer <VOTRE_TOKEN_ICI>
     {
      "name": "Updated User",
      "email": "updated@example.com"
      }

    -Étape 5: Suppression de l'utilisateur (remplacer <USER_ID> et <VOTRE_TOKEN_ICI>)
     DELETE http://localhost:9006/users/<USER_ID>
     Authorization: Bearer <VOTRE_TOKEN_ICI> 

    -Étape 6: Vérification de la suppression (doit retourner 404)
     GET http://localhost:9006/users/<USER_ID>
     Authorization: Bearer <VOTRE_TOKEN_ICI> 


  