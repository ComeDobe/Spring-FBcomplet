package com.dobe.demo.model;


import com.dobe.demo.view.VueEntreprise;
import com.dobe.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

    @OneToMany (mappedBy = "entreprise")
    @JsonView(VueEntreprise.class)
    private Set< Utilisateur> listeEmploye = new HashSet<Utilisateur>();

}
