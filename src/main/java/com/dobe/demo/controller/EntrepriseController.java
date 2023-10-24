package com.dobe.demo.controller;

import com.dobe.demo.dao.EntrepriseDao;
import com.dobe.demo.model.Entreprise;
import com.dobe.demo.view.VueEntreprise;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class EntrepriseController {


        @Autowired
        private EntrepriseDao entrepriseDao;

        @GetMapping("/admin/entreprise")
         @JsonView(VueEntreprise.class)
        // requete pour afficher tout les elements
        public List<Entreprise> getListeEntreprise() {
//
//                List<Entreprise> entreprise = entrepriseDao.findAll();
//                return  entreprise;

                return entrepriseDao.findAll();

        }
        @GetMapping("/admin/entreprise/{id}")
        @JsonView(VueEntreprise.class)

        // requete pour rechercher par id
        public Entreprise getEntreprise (@PathVariable int id) {

                Entreprise entreprise = entrepriseDao
                        .findById(id)
                        .orElse(null);
                return  entreprise;
        }

        @DeleteMapping("/admin/entreprise/{id}")                             // requete de suppression
        public boolean supprimeEntreprise (@PathVariable int id) {

                entrepriseDao.deleteAllById(Collections.singleton(id));
                return true;
        }

        @PostMapping("/admin/entreprise")                                   // requete pour inserer ou ajouter un element
        public Entreprise enregistrerEntreprise (@RequestBody Entreprise entrepriseAenregistrer) {
                entrepriseDao.save(entrepriseAenregistrer);

                return entrepriseAenregistrer;
        }

}
