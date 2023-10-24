package com.dobe.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.dobe.demo.model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void creationUtilisateur_idUtilisateurNull() {
		Utilisateur utilisateur= new Utilisateur();
		assertNull(utilisateur.getId());
	}

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;
	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	void appelUrlRacine_OKattendu() throws Exception {
		mvc.perform(get("/")).andExpect(content().string("le serveur marche mais y rien ici"));
	}


	void utilisateurNonConnecteappelUrlUtilisateur_403attendu() throws Exception {
		mvc.perform(get("/utilisateurs")).andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(roles = {"UTILISATEUR"})
	void utilisateurConnecteappelUrlListeUtilisateur_OKattendu() throws Exception {
		mvc.perform(get("/utilisateur")).andExpect(status().isOk());
	}


	@Test
	@WithMockUser(roles = {"ADMINISTRATEUR"})
	void administrateurConnecteappelUrlListeUtilisateur_OKattendu() throws Exception {
		mvc.perform(get("/utilisateur")).andExpect(status().isOk());
	}

	@PostConstruct
	public void init(){
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}


}
