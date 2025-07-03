  











Rapport du projet














Description du jeu 

À l’origine, notre objectif était de développer un jeu principal composé de plusieurs mini-jeux. Chaque mini-jeu réussi devait permettre de récupérer une clé, et une fois toutes les clés réunies, le joueur pouvait accéder à un dernier défi menant à un trésor final.
Cependant, en raison d’un manque de temps, nous avons recentré notre travail sur un seul mini-jeu : le jeu de foot. Pour conserver la logique initiale, nous avons choisi de considérer chaque étape de ce jeu comme un mini-jeu à part entière. Ainsi, le joueur doit progresser de manière séquentielle : terminer l’étape 1 pour accéder à l’étape 2, puis finir les étapes 1 et 2 pour débloquer l’étape 3.
À l’issue de chaque étape réussie, le joueur obtient une clé. Une fois les trois étapes complétées et les trois clés collectées, il obtient le trésor final, ce qui marque la victoire.
Le principe du jeu de foot est simple : à chaque étape, le joueur doit réussir à marquer trois buts.
Étape 1 : Le joueur contrôle un avatar sur un terrain de football avec un ballon. L’objectif est de marquer trois buts dans une cage fixe. Une fois cela accompli, l’étape 2 se débloque et une première clé est obtenue.


Étape 2 : On retrouve les éléments de l’étape 1, mais un obstacle mobile vient compliquer le jeu. Celui-ci se déplace devant la cage et empêche le joueur de marquer facilement. Une fois les trois buts réussis, une seconde clé est obtenue.


Étape 3 : Un second obstacle, se déplaçant dans le sens inverse du premier, vient s’ajouter. Il rend la tâche encore plus difficile. À l’issue de cette étape, le joueur gagne la troisième clé et accède au trésor, concluant ainsi le jeu.




Description des réalisations 

Pour mener à bien ce projet, nous avons d’abord élaboré un diagramme de classes. Cette étape nous a permis de structurer efficacement l’application, d’éviter la création de classes inutiles, de réduire le code superflu et de garantir une architecture claire et cohérente.
Nous avons également rédigé un cahier des charges définissant les attentes du client, avec une hiérarchisation des exigences en trois niveaux de priorité.
Toutes les fonctionnalités de priorité 1, considérées comme essentielles, ont été intégralement réalisées.
Pour concevoir notre projet, nous avons adopté l’architecture MVC (Modèle-Vue-Contrôleur), qui nous a permis de bien séparer la logique métier, l’interface graphique et le contrôle des interactions.
Parmi les fonctionnalités prioritaires, nous avons implémenté l’interaction drag and drop : l’avatar peut se déplacer dans la salle principale pour accéder aux différents jeux.
L'utilisation d’images faisait également partie des priorités de niveau 1. Cela a été respecté à plusieurs niveaux :
L’avatar peut être soit par défaut, soit personnalisé par l’utilisateur (choix du sexe, nom, coupe de cheveux, tenue), chaque version étant entièrement construite à partir d’images.

L’interface graphique, comprenant la salle principale, la page d’accueil, la page de succès après une étape, le terrain de foot, etc., a été réalisée à l’aide de Scene Builder et d’éléments visuels.
Le plateau de jeu est représenté par la salle principale, où l’on trouve un chemin de trois jeux (étapes 1 à 3). Ces jeux doivent être complétés de manière séquentielle afin de remporter le trésor final.
Le jeu offre également des retours visuels et textuels à l’utilisateur, lui indiquant l’état de sa partie : tir réussi, échec, passage d’une étape, victoire, etc.
Ainsi, le plateau de jeu est fidèlement représenté, avec :
le chemin des jeux bien visible,


la mécanique de drag and drop vers chaque jeu,


l’affichage du score, des clés obtenues, et du trésor final,


des conditions de victoire clairement définies à travers des méthodes de contrôle précises.

 
Difficultés rencontrés
La création de l’avatar a représenté un véritable défi au début du projet. Nous ne savions pas comment assembler les différentes parties du corps pour former un personnage complet dans JavaFX. La construction d’un avatar à partir d’éléments visuels indépendants nous semblait complexe.
 Dans un premier temps, nous avons opté pour une image d’avatar par défaut. Par la suite, pour permettre la personnalisation, nous avons décomposé les images en plusieurs éléments (cheveux, tenue, etc.) que nous avons ensuite assemblés dynamiquement afin de générer un avatar personnalisé complet à l’écran.
Un autre point technique important a été la détection de la cage de but. Le terrain de football est en effet une image unique contenant visuellement le but. Il a donc fallu déterminer manuellement les coordonnées de la cage dans l’image pour définir une zone de but exploitable dans le code.
Concernant les obstacles, nous avons utilisé un seul fichier FXML pour les trois étapes du jeu de foot. Selon l’étape atteinte, les éléments affichés varient dynamiquement : des obstacles apparaissent ou non en fonction du niveau.
La gestion des collisions entre le ballon et les obstacles a également représenté une difficulté technique. Il a fallu calculer précisément les coordonnées du ballon et des obstacles, puis détecter lorsqu’ils se superposent pour simuler une collision. Cette logique permet notamment de faire échouer un tir si le ballon touche un obstacle.


. Retour sur la salle principale
 Observation
L’un des testeurs a rencontré une difficulté liée à la lisibilité des instructions. En effet, le texte rouge situé en bas de l’écran (« Glissez votre avatar sur une zone numérotée ») n'était pas bien visible. Ce texte est pourtant essentiel pour comprendre comment interagir avec la salle principale.
Analyse
Le manque de contraste entre le texte rouge et le fond graphique rend cette consigne peu perceptible. L'utilisateur n’a pas immédiatement compris qu’il fallait glisser l’avatar sur les numéros (1, 2, 3) pour accéder aux différentes étapes du jeu.








Nous avons amélioré la lisibilité du message.
Le texte “Glissez votre avatar sur une zone numérotée !” est désormais :
En gras et rouge vif
Placé dans un encadré blanc aux bords arrondis




2. Retour sur la page “Liste des avatars”
 Observation
Un autre testeur a exprimé le besoin de pouvoir revenir en arrière depuis la page de liste avatars. Il souhaitait retourner à l’écran d’accueil dans deux cas :
S’il ne voulait pas jouer avec les avatars proposés
S’il voulait plutôt créer un nouvel avatar au lieu de choisir un déjà existant
 Analyse
L’interface ne propose pas de bouton « Retour ». L’utilisateur se retrouve donc bloqué dans la salle liste avatars 





 Proposition d'amélioration
 on a ajouter un bouton « Retour » qui permet de revenir à l’écran d’accueil ce qui  permettra à l'utilisateur de par être bloqué dans la  page liste avatar. 

