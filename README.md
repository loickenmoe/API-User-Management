# API User Management

Cette API permet de gérer des utilisateurs avec authentification JWT, incluant l'inscription, la connexion, et les opérations CRUD sur les utilisateurs, en utilisant une base de données PostgreSQL.

## Prérequis

- **Java 17, 21** (ou supérieur)
- **Maven** (pour la gestion des dépendances)
- **PostgreSQL** (installé et configuré localement)
- **Spring Boot 3.x**

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

    -Créer un nouvelle article :
    POST http://localhost:9010/articles
    Content-Type: application/json
    {
    "title": "Introduction à Spring Boot",
    "content": "Spring Boot facilite le développement d'applications web Java."
    }

    Reponse attendue:
    {
    "id": 5,
    "title": "Introduction à Spring Boot",
    "content": "Spring Boot facilite le développement d'applications web Java.",
    "publishedAt": "2025-03-27T11:05:49.7210984",
    "comments": []
    }
    
    -Lister tous les articles :
    GET http://localhost:9010/articles
    Reponse attendue:
    [
    {"id":1,"title":"Mon premier article","content":"Ceci est le contenu de l'article.","publishedAt":"2025-03-26T15:38:14.360567","comments":[]},
    {"id":2,"title":"Mon premier article","content":"Ceci est le contenu de l'article.","publishedAt":"2025-03-26T15:42:41.47467","comments":[]},
    {"id":4,"title":"string","content":"string","publishedAt":"2025-03-27T10:07:13.825976","comments":[]},
    {"id":5,"title":"Introduction à Spring Boot","content":"Spring Boot facilite le développement d'applications web Java.","publishedAt":"2025-03-27T11:05:49.721098","comments":[]}
    ]
    
    -Supprimer un articles :
    DELETE http://localhost:9010/articles/2
    
    si on consulte la liste de tous les articles à nouveau, on obtient:
    [
    {"id":1,"title":"Mon premier article","content":"Ceci est le contenu de l'article.","publishedAt":"2025-03-26T15:38:14.360567","comments":[]},
    {"id":4,"title":"string","content":"string","publishedAt":"2025-03-27T10:07:13.825976","comments":[]},
    {"id":5,"title":"Introduction à Spring Boot","content":"Spring Boot facilite le développement d'applications web Java.","publishedAt":"2025-03-27T11:05:49.721098","comments":[]}
    ]


  