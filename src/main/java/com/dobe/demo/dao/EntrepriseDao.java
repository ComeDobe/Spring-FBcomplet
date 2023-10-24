package com.dobe.demo.dao;

import com.dobe.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseDao extends JpaRepository <Entreprise, Integer> {


}
