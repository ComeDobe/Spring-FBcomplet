package com.dobe.demo.security;

import com.dobe.demo.model.Role;
import com.dobe.demo.model.Utilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonUserDetails implements UserDetails {


    private Utilisateur utilisateur;
    public MonUserDetails (Utilisateur utilisateur){
        this.utilisateur = utilisateur;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        GrantedAuthority role = utilisateur.isAdmin()
//                ? new SimpleGrantedAuthority( " ROLE_ADMINISTRATEUR ")
//                : new SimpleGrantedAuthority (" ROLE_UTILISATEUR ");
//        return role ;
//


//        List<GrantedAuthority> roles = new ArrayList<>();
//
//        if(utilisateur.isAdmin()) {
//            roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
//        } else {
//            roles.add(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
//        }
//        return roles;

        // une autre methode

//        return utilisateur.isAdmin()
//                ? List.of(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"))
//                : List.of(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
//        return List.of(new SimpleGrantedAuthority(utilisateur.isAdmin() ? "ROLE_ADMINISTRATEUR" : "ROLE_UTILISATEUR"));

//        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().getNom()));

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: utilisateur.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getNom()));
        }
        return authorities;
    // autre methode

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//
//        List<GrantedAuthority> roles = new ArrayList<>();
//
//        if(utilisateur.isAdmin()) {
//            roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
//        } else {
//            roles.add(new SimpleGrantedAuthority("ROLE_UTILISATEUR"));
//        }
//
//        return roles;
    }


    @Override
    public String getPassword() {

        return utilisateur.getMotDepasse();
    }

    @Override
    public String getUsername() {

        return utilisateur.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
