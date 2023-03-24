package com.dobe.demo.dao;

import com.dobe.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface UtilisateurDao  extends JpaRepository <Utilisateur, Integer> {

  Utilisateur findByPrenom(String prenom) ;

  List<Utilisateur> findAll();
}

