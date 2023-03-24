

package com.dobe.demo.controller;

import com.dobe.demo.dao.UtilisateurDao;
import com.dobe.demo.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    private UtilisateurDao utilisateurDao;

    @GetMapping("/utilisateur")
    public List<Utilisateur> getUtilisateur () {
        List<Utilisateur> listUtilisateur = utilisateurDao.findAll();
        return listUtilisateur;
    }

    @GetMapping("/utilisateur-franck")
    public Utilisateur getUtilisateurFranck () {
        return utilisateurDao.findByPrenom("Franck");
    }


    @GetMapping("/utilisateur/{id}")
    public ResponseEntity< Utilisateur> getUtilisateurFranck (@PathVariable int id) {


        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if(optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);

        }
       return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        //return utilisateurDao.findByPrenom("Franck");
    }

//    @PostMapping("/utilisateur")
//    public boolean ajoutUtilisateur(@RequestBody Utilisateur nouvelUtilisateur){
//
//        //Utilisateur nouvelUtilisateur = new Utilisateur();
////        nouvelUtilisateur.setId(4);
////        nouvelUtilisateur.setNom("toto");
////        nouvelUtilisateur.setPrenom("toto");
//
//
//        // si c'est un  update
//
//        if(nouvelUtilisateur.getId()!=null){
//            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId());
//            if (optional.isPresent()) {
//                utilisateurDao.save(nouvelUtilisateur);
//
//
//            }
//            return new ResponseEntity<>(nouvelUtilisateur, HttpStatus.BAD_REQUEST);
//        }
//
//
//        utilisateurDao.save(nouvelUtilisateur, HttpStatus.CREATED);
//        return true;
//    }


    @PostMapping("/utilisateur")
    public ResponseEntity<Utilisateur> ajoutUtilisateur(@RequestBody Utilisateur nouvelUtilisateur) {

        //si l'utilisateur fournit possède un id
        if(nouvelUtilisateur.getId() != null) {

            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId());

            //si c'est un update
            if(optional.isPresent()) {
                utilisateurDao.save(nouvelUtilisateur);
                return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.BAD_REQUEST);

        }

        utilisateurDao.save(nouvelUtilisateur);
        return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.CREATED);

    }
    @DeleteMapping("/utilisateur/{id}")
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {

        Optional <Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);

        if (utilisateurAsupprimer.isPresent()) {
            utilisateurDao.deleteById(id);

            return  new ResponseEntity<> (utilisateurAsupprimer.get(), HttpStatus.OK);
        }

       return new ResponseEntity<> (HttpStatus.NOT_FOUND);

//        utilisateurDao.deleteById(id);
//        return true;
    }
}





// franck

//
//    @DeleteMapping("/utilisateur/{id}")
//    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {
//
//        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);
//
//        if(utilisateurAsupprimer.isPresent()) {
//            utilisateurDao.deleteById(id);
//            return new ResponseEntity<>(utilisateurAsupprimer.get(),HttpStatus.OK);
//        }
//
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }



// Franck Bancept

//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//public class UtilisateurController {
//
//    @Autowired
//    private UtilisateurDao utilisateurDao;
//
//    @GetMapping("/utilisateurs")
//    public List<Utilisateur> getUtilisateurs() {
//        return utilisateurDao.findAll();
//    }
//
//    @GetMapping("/utilisateur/{id}")
//    public Utilisateur getUtilisateurFranck(@PathVariable int id) {
//
////        Optional<Utilisateur> optional = utilisateurDao.findById(id);
////        return optional.orElse(null);
//
//        return utilisateurDao.findById(id).orElse(null);
//
//    }
//}
