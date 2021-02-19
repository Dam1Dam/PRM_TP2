# PRM_TP2

## Authors: Arthur Poilane | Damien Cantin

---

## Contexte

Dans les entreprises, quel que soit le service (marketing, RH, R&D, Production, etc.), on croule usuellement sous les documents numériques de tout format. L’optimisation du rangement est un problème récurrent afin de stocker, de classer, de retrouver les documents numériques et, bien sûr, de manière ultime, afin de les nettoyer voire de les détruire.

Un problème classique de classement consiste à détecter les doublons documentaires en vue de détruire les documents redondants afin de ne conserver que les documents uniques, c’est-à-dire « dé-doublonnés ». 

Dans le présent TP, nous allons rechercher des doublons parmi des fichiers PDF en recherchant les éventuelles similarités. Pour se faire une méthode nous a été présentée. Il s'agit de la méthode vectorielle (ou sac de mots) 1. Cette méthode est à la base de nombreux algorithmes utilisés dans les moteurs de recherche documentaire. Elle est efficace mais nous allons ici essayer de trouver une méthode plus rapide (en s'inspirant de la méthode vectorielle).

## Projet

Nous avons effectué plusieurs essais pour répondre à la problématique de ce TP mais la solution que nous souhaitons vous partager et celle que nous décrirons ici.

Cette solution réside dans un projet java comportant des dépendances gérées avec maven.

Une des dépendances est [PDFBox](https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox/2.0.4). Celle-ci nous permet de traiter le texte de tous les fichiers PDF du dossier buffer-in afin de l'analyser pour créer un bon vocabulaire ainsi que nos signatures dans deux classes éxécutables (cf: sections vocabulaire et signatures).


- ### Vocabulaire

La gestion du vocabulaire se fait dans cette [classe](src/main/java/VocabularyGenerator.java)

Pour la création de notre vocabulaire notre projet lit tous les mots de chaque fichiers PDF et les stockent dans un fichier txt avec des séparateurs pour pouvoir ensuite les utiliser. Il y a bien sur un système de filtrage permettant d'éviter des doublons de mots ou les mots jugés trop exotiques. Malheureusement cette solution produit un vocabulaire certes très riche mais aussi très lourd et consomme énormément les ressources de l'ordinateur. Ce vocabulaire comprends plus de 40000 mots.

Pour pallier ce défaut nous avons créé un fichier texte de substitution plus léger comportant les mots clefs de notre première base de vocabulaire. Ce fichier est complétement modulable et la grammaire peut être plus ou moins riche pour plus ou moins de précision. Ce vocabulaire est disponible [ici](simpleVoc.txt) (78 mots plus raisonnable pour nos machines mais beaucoup moins précis). 


- ### Signatures 

La gestion des signatures se fait dans cette [classe](src/main/java/SignGenerator.java)

Pour trouver les fichiers identiques et/ou semblables nous nous sommes inspirés de la méthode vectorielle. Notre classe java génère des signatures (par rapport au vocabulaire défini) pour chaque fichier PDF. Ces signatures sont stockées dans des tableaux d'entiers où chaque entrée représente un mot du vocabulaire. Notre classe compare ensuite ces signatures pour sortir les PDF considérés identiques comme visible dans ce [fichier output](output.txt). 

## Conclusion

La méthode que nous proposons est très rapide et modulable (il s'agit de changer le vocabulaire pour plus ou moins de précision). 
Nous nous tenons à votre disposition si vous avez quelconque questions concernant cette solution. 
