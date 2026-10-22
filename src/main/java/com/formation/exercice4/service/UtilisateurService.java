package com.formation.exercice4.service;

import com.formation.exercice4.model.Utilisateur;
import com.formation.exercice4.repository.UtilisateurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Page<Utilisateur> recupererTous(Pageable pageable) {
        return utilisateurRepository.findAll(pageable);
    }

    public Optional<Utilisateur> recupererParId(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur creer(Utilisateur utilisateur) {
        utilisateur.setId(null);
        return utilisateurRepository.save(utilisateur);
    }

    public Optional<Utilisateur> mettreAJour(Long id, Utilisateur utilisateurModifie) {
        return utilisateurRepository.findById(id)
                .map(utilisateurExistant -> {
                    utilisateurExistant.setNom(utilisateurModifie.getNom());
                    utilisateurExistant.setPrenom(utilisateurModifie.getPrenom());
                    utilisateurExistant.setEmail(utilisateurModifie.getEmail());
                    utilisateurExistant.setAge(utilisateurModifie.getAge());
                    return utilisateurRepository.save(utilisateurExistant);
                });
    }

    public boolean supprimer(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Utilisateur> recupererParEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }
}