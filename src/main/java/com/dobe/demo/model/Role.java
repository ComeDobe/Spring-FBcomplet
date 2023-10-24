package com.dobe.demo.model;

import com.dobe.demo.view.VueEntreprise;
import com.dobe.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter

public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

    //@Column(length =50, nullable = false)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

}
