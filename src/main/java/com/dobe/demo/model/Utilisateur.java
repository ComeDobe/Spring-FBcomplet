package com.dobe.demo.model;

import com.dobe.demo.view.VueEntreprise;
import com.dobe.demo.view.VueUtilisateur;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter

//@Table(name = "")  pour renommer une table
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

   //@Column(length =50, nullable = false)
   @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;

   //@Column(length =50, nullable = true)
   @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String prenom;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String email;


    @JsonView({VueUtilisateur.class})
    private String nomImageProfil;

   private String motDepasse;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
//    @ManyToMany(fetch = FetchType.EAGER)  test ca veut dire à la demande par contre LAZY quant on a besoin

    @ManyToMany
    @JoinTable(name = "role_utilisateur", inverseJoinColumns = @JoinColumn(name = "role_id"))
   private List<Role> roles = new ArrayList<>();

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Pays pays;

    @ManyToOne

    @JsonView(VueUtilisateur.class)
    private  Entreprise entreprise;

    @ManyToMany

    @JoinTable(name = "recherche_emploi_utilisateur",
    inverseJoinColumns = @JoinColumn(name = "emploi_id")

    )
    @JsonView(VueUtilisateur.class)
    private Set<Emploi> emploisRecherche = new HashSet<Emploi>();

    @CreationTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDateTime updatedAt;


}
