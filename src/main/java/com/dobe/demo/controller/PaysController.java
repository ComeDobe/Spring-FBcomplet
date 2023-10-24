

package com.dobe.demo.controller;

import com.dobe.demo.dao.PaysDao;
import com.dobe.demo.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {

    @Autowired
    private PaysDao paysDao;

    @GetMapping("/liste-pays")
    public List<Pays> getPays () {
        List<Pays> listPays = paysDao.findAll();
        return listPays;
    }
//
//    @GetMapping("/pays-franck")
//    public Pays getPaysFranck () {
//        return paysDao.findByPrenom("Franck");
//    }


    @GetMapping("/admin/pays/{id}")
    public ResponseEntity< Pays> getPaysFranck (@PathVariable int id) {


        Optional<Pays> optional = paysDao.findById(id);
        if(optional.isPresent()) {
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);

        }
       return new ResponseEntity <>(HttpStatus.NOT_FOUND);
        //return paysDao.findByPrenom("Franck");
    }

//    @PostMapping("/pays")
//    public boolean ajoutPays(@RequestBody Pays nouvelPays){
//
//        //Pays nouvelPays = new Pays();
////        nouvelPays.setId(4);
////        nouvelPays.setNom("toto");
////        nouvelPays.setPrenom("toto");
//
//
//        // si c'est un  update
//
//        if(nouvelPays.getId()!=null){
//            Optional<Pays> optional = paysDao.findById(nouvelPays.getId());
//            if (optional.isPresent()) {
//                paysDao.save(nouvelPays);
//
//
//            }
//            return new ResponseEntity<>(nouvelPays, HttpStatus.BAD_REQUEST);
//        }
//
//
//        paysDao.save(nouvelPays, HttpStatus.CREATED);
//        return true;
//    }


    @PostMapping("/admin/pays")
    public ResponseEntity<Pays> ajoutPays(@RequestBody Pays nouvelPays) {

        //si l'pays fournit possède un id
        if(nouvelPays.getId() != null) {

            Optional<Pays> optional = paysDao.findById(nouvelPays.getId());

            //si c'est un update
            if(optional.isPresent()) {
                paysDao.save(nouvelPays);
                return new ResponseEntity<>(nouvelPays,HttpStatus.OK);
            }

            //si il y a eu une tentative d'insertion d'un pays avec un id qui n'existait pas
            return new ResponseEntity<>(nouvelPays,HttpStatus.BAD_REQUEST);

        }

        paysDao.save(nouvelPays);
        return new ResponseEntity<>(nouvelPays,HttpStatus.CREATED);

    }
    @DeleteMapping("/pays/{id}")
    public ResponseEntity<Pays> supprimePays(@PathVariable int id) {

        Optional <Pays> paysAsupprimer = paysDao.findById(id);

        if (paysAsupprimer.isPresent()) {
            paysDao.deleteById(id);

            return  new ResponseEntity<> (paysAsupprimer.get(), HttpStatus.OK);
        }

       return new ResponseEntity<> (HttpStatus.NOT_FOUND);

//        paysDao.deleteById(id);
//        return true;
    }
}





// franck

//
//    @DeleteMapping("/pays/{id}")
//    public ResponseEntity<Pays> supprimePays(@PathVariable int id) {
//
//        Optional<Pays> paysAsupprimer = paysDao.findById(id);
//
//        if(paysAsupprimer.isPresent()) {
//            paysDao.deleteById(id);
//            return new ResponseEntity<>(paysAsupprimer.get(),HttpStatus.OK);
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
//public class PaysController {
//
//    @Autowired
//    private PaysDao paysDao;
//
//    @GetMapping("/payss")
//    public List<Pays> getPayss() {
//        return paysDao.findAll();
//    }
//
//    @GetMapping("/pays/{id}")
//    public Pays getPaysFranck(@PathVariable int id) {
//
////        Optional<Pays> optional = paysDao.findById(id);
////        return optional.orElse(null);
//
//        return paysDao.findById(id).orElse(null);
//
//    }
//}
