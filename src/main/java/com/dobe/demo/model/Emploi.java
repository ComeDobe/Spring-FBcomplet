package com.dobe.demo.model;

import com.dobe.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter


@EntityListeners(AuditingEntityListener.class)
public class Emploi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(VueUtilisateur.class)
    private Integer id;

   @Column(length =50, nullable = false)
   @JsonView(VueUtilisateur.class)
    private String nom;

   @Column(length =50, nullable = true)
    private String prenom;

}
