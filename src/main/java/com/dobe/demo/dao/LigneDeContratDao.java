package com.dobe.demo.dao;

import com.dobe.demo.model.LigneDeContrat;
import com.dobe.demo.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneDeContratDao extends JpaRepository <LigneDeContrat, LigneDeContrat.IdLigneDeContrat> {

}

