
INSERT INTO pays( id , nom ) VALUES
 (1, 'France'),
 (2, 'Allemagne'),
 (3, 'Espagne');

INSERT INTO entreprise( id , nom ) VALUES
(1, 'Amazon'),
(2, 'Google'),
( 3, 'Red hat');



--INSERT INTO role( id , nom ) VALUES
--   (1, 'ROLE_UTILISATEUR'),
--   (2, 'ROLE_ADMINISTRATEUR');


INSERT INTO emploi( id , nom ) VALUES
(1, 'Developpeur'),
(2, 'Testeur'),
(3, 'Chef de projet');

INSERT INTO `role` (`id`, `nom`) VALUES
(1, 'ROLE_UTILISATEUR'),
(2, 'ROLE_ADMINISTRATEUR'),
(3, 'ROLE_SUPER_ADMINISTRATEUR');


INSERT INTO utilisateur(prenom,nom, pays_id, entreprise_id, email, mot_depasse, created_at, updated_at) VALUES
 ("chris","lebron", 1,1, "c@a.com", "$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",  UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ("John","JAMES", 2, 1, "a@a.com", "$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q",UTC_TIMESTAMP(), UTC_TIMESTAMP()),
       ("Steeve","SMITH", 3,1, "b@a.com", "$2a$10$wXW2wHA2bu1TdQ26p.2UoehWv8m92w88kabSeL.348VqkpWvSt51q", UTC_TIMESTAMP(), UTC_TIMESTAMP());

 INSERT INTO role_utilisateur (utilisateur_id, role_id) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 3);


INSERT INTO recherche_emploi_utilisateur( utilisateur_id , emploi_id ) VALUES
(1, 1),
(1, 2),
(2, 2);


INSERT INTO contrat( id , date_de_creation , date_de_retour ) VALUES
(1, '2023-05-09', '2023-05-11'),
(2, '2023-05-10', NULL);

INSERT INTO materiel( id , nom , numero ) VALUES
(1, 'Ecran 30\"', 123),
(2, 'Ecran 30\"', 456),
(3, 'Clavier', 789);

INSERT INTO ligne_de_contrat( contrat_id , materiel_id , date_de_retour_anticipe ) VALUES
(1, 1, NULL),
(1, 3, '2023-05-10'),
(2, 2, NULL);