package com.dobe.demo.dao;

import com.dobe.demo.model.Pays;
import com.dobe.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaysDao extends JpaRepository <Pays, Integer> {

}

