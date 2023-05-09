package com.dobe.demo.controller;

import com.dobe.demo.dao.UtilisateurDao;
import com.dobe.demo.model.Utilisateur;
import com.dobe.demo.security.JwtUtils;
import com.dobe.demo.security.MonUserDetails;
import com.dobe.demo.security.MonUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import java.util.Optional;
import java.util.regex.Pattern;

@RestController
@CrossOrigin
public class ConnexionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UtilisateurDao utilisateurDao;


    @Autowired
    PasswordEncoder passwordEncoder;
@Autowired
    MonUserDetailsService userDetailsService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/connexion")
    ResponseEntity<String> connexion(@RequestBody Utilisateur utilisateur) {


        try {


            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    utilisateur.getEmail(),
                    utilisateur.getMotDepasse())

            );

        } catch (BadCredentialsException e) {

            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        MonUserDetails monUserDetails = (MonUserDetails)userDetailsService.loadUserByUsername(utilisateur.getEmail());

        // return  " utilisateur existe ";
        return new ResponseEntity<>(jwtUtils.generateJwt(monUserDetails), HttpStatus.OK);
    }

    @PostMapping("/inscription")
    public ResponseEntity<Utilisateur> inscription(@RequestBody Utilisateur utilisateur) {

        if (utilisateur.getId() != null) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (utilisateur.getMotDepasse().length() <= 3) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        if (!pattern.matcher(utilisateur.getEmail()).matches()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Utilisateur> optional = utilisateurDao.findByEmail(utilisateur.getEmail());
        if (optional.isPresent()) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String passwordHache = passwordEncoder.encode(utilisateur.getMotDepasse());
        utilisateur.setMotDepasse(passwordHache);

        utilisateurDao.save(utilisateur);
        return new ResponseEntity<>(utilisateur, HttpStatus.CREATED);
    }
}
