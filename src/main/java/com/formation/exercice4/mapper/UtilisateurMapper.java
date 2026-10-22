package com.formation.exercice4.mapper;

import com.formation.exercice4.dto.UtilisateurRequest;
import com.formation.exercice4.dto.UtilisateurResponse;
import com.formation.exercice4.model.Utilisateur;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {

    public Utilisateur versEntite(UtilisateurRequest utilisateurRequest) {
        return new Utilisateur(
                utilisateurRequest.getNom(),
                utilisateurRequest.getPrenom(),
                utilisateurRequest.getEmail(),
                utilisateurRequest.getAge()
        );
    }

    public UtilisateurResponse versResponse(Utilisateur utilisateur) {
        return new UtilisateurResponse(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail()
        );
    }
}