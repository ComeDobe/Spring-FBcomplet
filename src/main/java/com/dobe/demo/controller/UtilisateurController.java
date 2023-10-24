

package com.dobe.demo.controller;

import com.dobe.demo.dao.UtilisateurDao;
import com.dobe.demo.model.Role;
import com.dobe.demo.model.Utilisateur;
import com.dobe.demo.security.JwtUtils;
import com.dobe.demo.service.FichierService;
import com.dobe.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@CrossOrigin
public class UtilisateurController {

    @Autowired
    JwtUtils jwtUtils;


    @Autowired
    FichierService fichierService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurDao utilisateurDao;

//    @GetMapping("/utilisateur-allemand")
//@GetMapping("/utilisateur-par-pays/{monpays}")

    @GetMapping("/utilisateur")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateur () {

//    public List<Utilisateur> getUtilisateur (@PathVariable String monpays) {
        List<Utilisateur> listUtilisateur = utilisateurDao.findAll();
        return listUtilisateur;

//        return utilisateurDao.trouverutilisateurSelonPays(monpays);
    }

    @GetMapping("/utilisateur-franck")
    public Utilisateur getUtilisateurFranck () {
        return utilisateurDao.findByPrenom("Franck");
    }


    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity< Utilisateur> getUtilisateurFranck (@PathVariable int id) {

//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//

        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if(optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);

        }
       return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        //return utilisateurDao.findByPrenom("Franck");
    }


    @GetMapping("/profil")
    @JsonView(VueUtilisateur.class)
    public  ResponseEntity < Utilisateur> getProfil(@RequestHeader ("Authorization") String bearer){
        String jwt = bearer.substring(7);

        Claims donnees = jwtUtils.getData(jwt);
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());

        if ( utilisateur.isPresent() ){
            return  new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        }

        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping("/image-profil/{idUtilisateur}")
    public ResponseEntity<byte []> getImageProfil(@PathVariable int idUtilisateur) {

        Optional<Utilisateur> optional = utilisateurDao.findById(idUtilisateur);
        if (optional.isPresent()) {
            String nomImage = optional.get().getNomImageProfil();

            try {
                byte [] image = fichierService.getImageByName(nomImage);

                HttpHeaders enTete = new HttpHeaders();
                String mimetype = Files.probeContentType(new File(nomImage).toPath());
                enTete.setContentType(MediaType.valueOf(mimetype));

                return new ResponseEntity<>(image, enTete, HttpStatus.OK);
            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);


            } catch (IOException e) {
            System.out.println("le test du mimetype a echoué ");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PostMapping("/utilisateur")                  // pour ajouter un element dans la base de donnée
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


    @PostMapping("/admin/utilisateur")
    public ResponseEntity<Utilisateur> ajoutUtilisateur(
            @RequestPart("utilisateur"  ) Utilisateur nouvelUtilisateur,
            @Nullable @RequestParam("fichier")MultipartFile fichier)

    {



        //si l'utilisateur fournit possède un id
        if(nouvelUtilisateur.getId() != null) {

            Optional<Utilisateur> optional = utilisateurDao.findById(nouvelUtilisateur.getId());

            //si c'est un update
            if(optional.isPresent()) {

                Utilisateur userToUpdate = optional.get();
                userToUpdate.setNom(nouvelUtilisateur.getNom());
                userToUpdate.setPrenom(nouvelUtilisateur.getPrenom());
                userToUpdate.setEmail(nouvelUtilisateur.getEmail());
                userToUpdate.setPays(nouvelUtilisateur.getPays());

                utilisateurDao.save(userToUpdate);

//                if (fichier!=null) {
//
//                    try {
//                        fichierService.transfertVersDossierUpload(fichier, "image_profil");
//                    } catch (IOException e) {
//
//                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//
//                    }
//                }
                return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.BAD_REQUEST);

        }

        Role role = new Role();
        role.setId(1);
        nouvelUtilisateur.getRoles().add(role);

        String passwordHache = passwordEncoder.encode("root");
        nouvelUtilisateur.setMotDepasse(passwordHache);

//        nouvelUtilisateur.setCreatedAt(LocalDate.now());



        if (fichier!=null) {


            try {

                String nomImage = UUID.randomUUID() + "-" + fichier.getOriginalFilename();
                nouvelUtilisateur.setNomImageProfil(nomImage);
                fichierService.transfertVersDossierUpload(fichier, nomImage);

            } catch (IOException e) {

                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }

        utilisateurDao.save(nouvelUtilisateur);

        return new ResponseEntity<>(nouvelUtilisateur,HttpStatus.CREATED);

    }
    @DeleteMapping("/admin/utilisateur/{id}")
    @JsonView (VueUtilisateur.class)
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id) {

        Optional <Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);

        if (utilisateurAsupprimer.isPresent()) {
            utilisateurDao.deleteById(id);

//            return  new ResponseEntity<> (utilisateurAsupprimer.get(), HttpStatus.OK);
            return  new ResponseEntity<> (null, HttpStatus.OK);
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
